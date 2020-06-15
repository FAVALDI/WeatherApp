package com.example.weatherapp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.Adapter.WeatherForecastAdapter;
import com.example.weatherapp.Adapter.WeatherHistoricalAdapter;
import com.example.weatherapp.Common.Common;
import com.example.weatherapp.Model.WeatherApiResult;
import com.example.weatherapp.Model.WeatherHistoricalResult;
import com.example.weatherapp.Retrofit.IOpenWeatherMap;
import com.example.weatherapp.Retrofit.RetrofitClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class TodayWeatherFragment extends Fragment {


   /************************** The main processing and the function calls to displays the information is made by this class*************/

    ImageView img_weather;
    TextView txt_city_name,txt_temperature,txt_description,txt_date_time,txt_wind,txt_pressure,txt_humidity,txt_sunrise,txt_sunset,txt_geo_coord;
    LinearLayout weather_panel;
    ProgressBar loading;
    Switch switch_Time;
    TextView txt_city_name_forecast, txt_geo_coord_forecast;
    RecyclerView recycler_forecast;
    Spinner cities_List;
    int dateUnixFormat, city_Array_Position;
    boolean timeSelection = true;
    public int index, time;
    public WeatherHistoricalResult[] weatherHistoricalResultArray=new WeatherHistoricalResult[5];

    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;
    CompositeDisposable compositeDisposable_historical;
    IOpenWeatherMap mService_historical;
    static TodayWeatherFragment instance ;


    public static TodayWeatherFragment getInstance(){
        if (instance == null)
            instance = new TodayWeatherFragment();
        return  instance;
    }


    public TodayWeatherFragment() {
     compositeDisposable = new CompositeDisposable();
     Retrofit retrofit = RetrofitClient.getInstance();
     mService = retrofit.create(IOpenWeatherMap.class);

     compositeDisposable_historical = new CompositeDisposable();
        Retrofit retrofit_historical = RetrofitClient.getInstance();
     mService_historical = retrofit_historical.create(IOpenWeatherMap.class);

    }


    @SuppressLint("CutPasteId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView =  inflater.inflate(R.layout.fragment_today_weather, container, false);

        img_weather = (ImageView)itemView.findViewById(R.id.img_weather);
        cities_List = (Spinner)itemView.findViewById(R.id.cities_List);
        txt_city_name = (TextView)itemView.findViewById(R.id.txt_city_name);
        txt_temperature = (TextView)itemView.findViewById(R.id.txt_temperature);
        txt_description = (TextView)itemView.findViewById(R.id.txt_description);
        txt_date_time = (TextView)itemView.findViewById(R.id.txt_date_time);
        txt_wind = (TextView)itemView.findViewById(R.id.txt_wind);
        txt_pressure = (TextView)itemView.findViewById(R.id.txt_pressure);
        txt_humidity = (TextView)itemView.findViewById(R.id.txt_humidity);
        txt_sunrise = (TextView)itemView.findViewById(R.id.txt_sunrise);
        txt_sunset = (TextView)itemView.findViewById(R.id.txt_sunset);
        txt_geo_coord = (TextView)itemView.findViewById(R.id.txt_geo_coord);
        switch_Time = (Switch)itemView.findViewById(R.id.switch_Time);
        weather_panel = (LinearLayout)itemView.findViewById(R.id.weather_panel);
        loading = (ProgressBar)itemView.findViewById(R.id.loading);
        txt_geo_coord_forecast = (TextView)itemView.findViewById(R.id.txt_geo_coord);
        txt_city_name_forecast = (TextView)itemView.findViewById(R.id.txt_city_name);
        recycler_forecast= (RecyclerView)itemView.findViewById(R.id.recycler_forecast);

        recycler_forecast.setHasFixedSize(true);
        recycler_forecast.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));



        ArrayAdapter citiesAdapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_dropdown_item,Common.cities);
        cities_List.setAdapter(citiesAdapter);
        cities_List.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                city_Array_Position = position;
                getWeatherInformation();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        getWeatherInformation();
        switch_Time.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (switch_Time.isChecked()){
                    timeSelection = false;
                    switch_Time.setText("Historical 5 days");
                    weather_panel.setVisibility(View.GONE);
                    loading.setVisibility(View.VISIBLE);
                    getWeatherInformation();
                }else {
                    timeSelection=true;
                    switch_Time.setText("Forecast 7 days");
                    weather_panel.setVisibility(View.GONE);
                    loading.setVisibility(View.VISIBLE);
                    getWeatherInformation();
                }


            }
        });
        return itemView;

    }


    private void getWeatherInformation() {
        compositeDisposable.add(mService.getWeatherByLatLon(Common.latitude[city_Array_Position],
                Common.longitude[city_Array_Position],
                "minutely,hourly",
                Common.API_ID,
                "metric")
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherApiResult>() {
                               @Override
                               public void accept(WeatherApiResult weatherApiResult) throws Exception {

                                   Picasso.get().load(new StringBuilder("https://openweathermap.org/img/wn/").append(weatherApiResult.current.getWeather().get(0).getIcon()).append(".png").toString()).into(img_weather);

                                   //Load inforamtion
                                   txt_city_name.setText(Common.cities[city_Array_Position]);
                                   txt_description.setText(new StringBuilder(weatherApiResult.current.weather.get(0).getDescription()));
                                   txt_temperature.setText(new StringBuilder(String.valueOf(Math.round(weatherApiResult.current.getTemp()))).append("°C").toString());
                                   txt_date_time.setText(new StringBuilder(Common.convertUnixToWeekDay(weatherApiResult.current.getDt())).append(" ").append(Common.convertUnixToDate(weatherApiResult.current.getDt())).append("-").append(Common.convertUnixToDay(weatherApiResult.current.getDt())).toString());
                                   txt_pressure.setText(new StringBuilder(String.valueOf(weatherApiResult.current.getPressure())).append(" hpa").toString());
                                   txt_humidity.setText(new StringBuilder(String.valueOf(weatherApiResult.current.getHumidity())).append("%").toString());
                                   txt_sunrise.setText(Common.convertUnixToHour(weatherApiResult.current.getSunrise()));
                                   txt_sunset.setText(Common.convertUnixToHour(weatherApiResult.current.getSunset()));
                                   txt_wind.setText(new StringBuilder("Speed: ").append(weatherApiResult.current.wind_speed).append("m/s"));
                                   txt_geo_coord.setText(new StringBuilder("[").append(Common.latitude[city_Array_Position]).append(Common.longitude[city_Array_Position]).append("]").toString());

                                   dateUnixFormat = weatherApiResult.current.getDt();

                                   // Display panel
                                   weather_panel.setVisibility(View.VISIBLE);
                                   loading.setVisibility(View.GONE);

/***************************************** Time selection **************************/
                                   if(timeSelection){
                                       displayForecastWeather(weatherApiResult);
                                   } else if (!timeSelection) {
                                       getHistoricalWeatherInformation();
                                   }
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(getActivity(), "ERROR" +throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                )
        );
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }


    private void getHistoricalWeatherInformation() {


        for (int days_index = 0; days_index <5 ; days_index++) {

            compositeDisposable_historical.add(mService_historical.getHistoryWeatherByLatLon(
                    Common.latitude[city_Array_Position],
                    Common.longitude[city_Array_Position],
                    (dateUnixFormat-(86400*(days_index+1))),
                    Common.API_ID,
                    "metric")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<WeatherHistoricalResult>() {
                                   @Override
                                   public void accept(WeatherHistoricalResult weatherHistoricalResult) throws Exception {

                                       displayHistoricalWeather(weatherHistoricalResult);

                                   }
                               },
                            new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    Toast.makeText(getActivity(), "ERROR HISTORICAL" +throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    Log.d("ERROR HISTORICAL", throwable.getMessage());
                                }
                            }
                    )
            );
        }
    }

    @SuppressLint("SetTextI18n")
    private void displayHistoricalWeather(WeatherHistoricalResult weatherHistoricalResult) {

        txt_city_name_forecast.setText(Common.cities[city_Array_Position]);
        txt_geo_coord_forecast.setText("[" + Common.latitude[city_Array_Position] + Common.longitude[city_Array_Position] + "]");

        if (index < 5) {
            weatherHistoricalResultArray[index] = weatherHistoricalResult;
            Log.d("grabando", "displayHistoricalWeather: ");
            index=index+1;
        }
        if(index==5)  {

            WeatherHistoricalAdapter adapter = new WeatherHistoricalAdapter(getContext(),weatherHistoricalResultArray);
            recycler_forecast.setAdapter(adapter);
            Log.d("FORECAST", "IMPRIMIENDO : ");
            index=0;
        }

    }


    private void displayForecastWeather(WeatherApiResult weatherApiResult) {

        txt_city_name_forecast.setText(new StringBuilder(Common.cities[city_Array_Position]).toString());
        txt_geo_coord_forecast.setText(new StringBuilder("[").append(Common.latitude[city_Array_Position]).append(Common.longitude[city_Array_Position])
                .append("]").toString());
        WeatherForecastAdapter adapter = new WeatherForecastAdapter(getContext(),weatherApiResult);
        recycler_forecast.setAdapter(adapter);
        Log.d("FORECAST", "displayForecastWeather() returned: ");
    }

}