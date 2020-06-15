package com.example.weatherapp.Retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit instance;


//********************* Retrofit2 method to make http requests ***************//

    public static Retrofit getInstance() {
            if (instance == null) {
                instance = new Retrofit.Builder()
                        .baseUrl("https://api.openweathermap.org/data/2.5/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();

        }
        return instance;

    }


}
