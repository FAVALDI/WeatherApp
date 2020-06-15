# Weather APP 
## Fabián Naula V.

## OBJETIVE 

This program allows the users GET the current weather, forecast (7 days) and the historical (5 days) information which is fetched directly from <https://OpenWeatherMap.org>
Adicionally, users are able to check the weather information of 10 cities that are listed on the drop-down list.

## APP DESIGN STRUCTURE

This app has been developed using **java on Android Studio**.
To make the code more viable to read, it has been dividen 7 main sections depending on what is the main role of each one.

It´s been divided in:

Classes:
- Main Activity:			 Main class to initialize the activity main and the fragments.
- TodayWeatherFragment:		 The main processing and the function calls to displays the information is made by this fragment.
- Adapters				     To manage the information as a bridge between the UI component and the data source.
- Common					 Class with general purpose information for main classes
- Models					 Model for JSON response of weather server.
- Retrofit					 Its encharged of make the http requests to the OpenWeatherMap server.

Resources:
-Layout						 Contains the UI components such as the main activity, fragments and card views.

>The User interface has a **drop-down list** to select the city that we want to check the weather.
>Under our drop-down list we have the **current weather information**.
>Underneath is a **switch button** to toogle the adicional information displayed (forecast or historical).
		

**This app have errors management and validation for the information that we pull from the server.**

### This development satisfies the following requirements:

 - **Current weather** => It shows the current weather of the selected city.
 - **Forecast**		   => It shows the forecast for the next 7 days.
 - **Historical** 	   => It shows the historical for the last 5 days.

 
 -------------

## Requirements to run the app

1. To run this app you must have an android device with at least android 5.0.
2. You need to have internet access.

 -------------

# Usage

## Testing

1. Install the apk file in your Android phone.
2. Open the WeatherApp
3. Select in the drop-down list the city to be check.
4. As default is displayed the **forecast** information. If you want to check the **historical** information just swipe the switch button, so then 
it will be displayed in the bottom.