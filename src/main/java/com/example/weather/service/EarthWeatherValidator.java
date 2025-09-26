package com.example.weather.service;

import com.example.weather.models.PlanetName;
import com.example.weather.models.Weather;

public class EarthWeatherValidator implements WeatherValidator {
  private static final int MIN_PRESSURE_PA = 74_662;
  private static final int MAX_PRESSURE_PA = 127_990;
  private static final int MIN_WIND_SPEED = 0;
  private static final int MAX_WIND_SPEED = 113;
  private static final int MIN_TEMPERATURE = -89;
  private static final int MAX_TEMPERATURE = 57;

  @Override
  public boolean isValid(Weather weather) {
    return areReferencesValid(weather)
        && isValidWindSpeed(weather.getWindSpeed())
        && isValidTemperature(weather.getTemperature())
        && isValidPressure(weather.getPressure())
        && PlanetName.EARTH.equals(weather.getPlanet().getName());
  }

  private boolean isValidWindSpeed(int windSpeed) {
    return windSpeed > MIN_WIND_SPEED && windSpeed < MAX_WIND_SPEED;
  }

  private boolean isValidTemperature(int temperature) {
    return temperature > MIN_TEMPERATURE && temperature < MAX_TEMPERATURE;
  }

  private boolean isValidPressure(int pressure) {
    return pressure > MIN_PRESSURE_PA && pressure < MAX_PRESSURE_PA;
  }
}
