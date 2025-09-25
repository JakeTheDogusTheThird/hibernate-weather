package com.example.weather.service;

import com.example.weather.models.PlanetName;
import com.example.weather.models.Weather;

public class MarsWeatherValidator implements WeatherValidator {
  @Override
  public boolean isValid(Weather weather) {
    return weather.getWindSpeed() >= 0
        && weather.getTemperature() > -140
        && weather.getPressure() > 0 && weather.getPressure() < 15
        && PlanetName.MARS.equals(weather.getPlanet().getName());
  }
}
