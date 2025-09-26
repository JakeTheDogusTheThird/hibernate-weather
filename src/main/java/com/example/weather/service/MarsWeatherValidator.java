package com.example.weather.service;

import com.example.weather.models.PlanetName;
import com.example.weather.models.Weather;

public class MarsWeatherValidator implements WeatherValidator {
  private static final int MIN_WIND_SPEED = 0;
  private static final int MAX_WIND_SPEED = 29;
  private static final int MIN_TEMPERATURE = -143;
  private static final int MAX_TEMPERATURE = 29;
  private static final int MAX_PRESSURE_PA = 1155;
  private static final int MIN_PRESSURE_PA = 30;

  @Override
  public boolean isValid(Weather weather) {
    return areReferencesValid(weather)
        && weather.getWindSpeed() >= MIN_WIND_SPEED
        && weather.getWindSpeed() <= MAX_WIND_SPEED
        && weather.getTemperature() > MIN_TEMPERATURE
        && weather.getTemperature() <= MAX_TEMPERATURE
        && weather.getPressure() > MIN_PRESSURE_PA
        && weather.getPressure() < MAX_PRESSURE_PA
        && PlanetName.MARS.equals(weather.getPlanet().getName());
  }
}
