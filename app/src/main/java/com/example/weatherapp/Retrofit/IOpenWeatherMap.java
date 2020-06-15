package com.example.weatherapp.Retrofit;



import com.example.weatherapp.Model.WeatherApiResult;
import com.example.weatherapp.Model.WeatherHistoricalResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeatherMap {

//****************************Interface for REST API requests*************************//

    //******** Two available requests, first one for current weather and forecast information
    //******** Second one for historical weather information

    @GET("onecall")
    Observable<WeatherApiResult> getWeatherByLatLon(@Query("lat") String lat,
                                                    @Query("lon") String lng,
                                                    @Query("exclude") String exclude,
                                                    @Query("appid") String appid,
                                                    @Query("units") String unit);

    @GET("onecall/timemachine")
    Observable<WeatherHistoricalResult> getHistoryWeatherByLatLon(@Query("lat") String lat,
                                                                 @Query("lon") String lng,
                                                                 @Query("dt") int dt,
                                                                 @Query("appid") String appid,
                                                                 @Query("units") String unit);

}
