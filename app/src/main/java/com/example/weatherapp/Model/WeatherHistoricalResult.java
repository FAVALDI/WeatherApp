package com.example.weatherapp.Model;

import java.util.List;


public class WeatherHistoricalResult {


//************************************Model for JSON response of weather server*******************//

        public double lat;
        public double lon;
        public String timezone;
        public int timezone_offset;
        public Current current;
        public List<Hourly> hourly;


}
