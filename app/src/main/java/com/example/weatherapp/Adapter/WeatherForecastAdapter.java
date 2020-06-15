package com.example.weatherapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.weatherapp.Common.Common;
import com.example.weatherapp.Model.WeatherApiResult;
import com.example.weatherapp.R;
import com.squareup.picasso.Picasso;

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.MyViewHolder> {
    Context context;
    WeatherApiResult weatherApiResult;


    public WeatherForecastAdapter(Context context, WeatherApiResult weatherApiResult) {
        this.context = context;
        this.weatherApiResult = weatherApiResult;
    }

    /******************************* Management of RecyclerView ***************************/
    /********************* Integration of cardView with forecast information **************/

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_weather_forecast,parent,false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.get().load(new StringBuilder("https://openweathermap.org/img/wn/").append(weatherApiResult.daily.get(position).weather.get(0).getIcon()).append(".png").toString()).into(holder.img_weather);
        holder.txt_date_time.setText(new StringBuilder(Common.convertUnixToDate(weatherApiResult.daily.get(position).getDt())).append(" ").append(Common.convertUnixToDay(weatherApiResult.daily.get(position).getDt())));
        holder.txt_temperature.setText(new StringBuilder(String.valueOf(Math.round(weatherApiResult.daily.get(position).temp.getDay()))).append("°C"));
    }

    @Override
    public int getItemCount() {
        return weatherApiResult.daily.size();
    }

    /************************************End of management of RecyclerView***********************/


    public class MyViewHolder extends RecyclerView.ViewHolder {
     TextView txt_date_time,txt_temperature;
     ImageView img_weather;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_weather = (ImageView)itemView.findViewById(R.id.img_weather);
            txt_date_time=(TextView)itemView.findViewById(R.id.txt_date);
            txt_temperature=(TextView)itemView.findViewById(R.id.txt_temperature);
        }
    }
}
