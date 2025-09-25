package com.example.weather.service;

import com.example.weather.models.PlanetName;
import com.example.weather.models.Weather;

public class EarthWeatherValidator implements WeatherValidator {
  private static final int MIN_PRESSURE_MMHG = 560;
  private static final int MAX_PRESSURE_MMHG = 960;
  private static final int MIN_WIND_SPEED = 0;
  private static final int MAX_WIND_SPEED = 113;
  private static final int MIN_TEMPERATURE = -89;
  private static final int MAX_TEMPERATURE = 57;
  @Override
  public boolean isValid(Weather weather) {
    return weather.getWindSpeed() > MIN_WIND_SPEED
        && weather.getWindSpeed() < MAX_WIND_SPEED
        && weather.getTemperature() > MIN_TEMPERATURE
        && weather.getTemperature() < MAX_TEMPERATURE
        && weather.getPressure() > MIN_PRESSURE_MMHG
        && weather.getPressure() < MAX_PRESSURE_MMHG
        && PlanetName.EARTH.equals(weather.getPlanet().getName());
  }
}
