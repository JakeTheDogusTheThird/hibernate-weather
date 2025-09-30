package com.example.weather.service;

import com.example.weather.model.PlanetName;
import com.example.weather.model.Weather;

public class EarthWeatherValidator implements WeatherValidator {
  private static final int MIN_PRESSURE_PA = 74_662;
  private static final int MAX_PRESSURE_PA = 127_990;
  private static final int MIN_WIND_SPEED = 0;
  private static final int MAX_WIND_SPEED = 113;
  private static final int MIN_TEMPERATURE = -89;
  private static final int MAX_TEMPERATURE = 57;

  @Override
  public boolean isValid(Weather weather) {
    return isEarth(weather.getPlanet().getName())
        && isWindSpeedValid(weather.getWindSpeed())
        && isTemperatureValid(weather.getTemperature())
        && isPressureValid(weather.getPressure());
  }

  private static boolean isEarth(PlanetName planet) {
    return PlanetName.EARTH.equals(planet);
  }

  private boolean isWindSpeedValid(int windSpeed) {
    return windSpeed > MIN_WIND_SPEED
        && windSpeed < MAX_WIND_SPEED;
  }

  private boolean isTemperatureValid(int temperature) {
    return temperature > MIN_TEMPERATURE
        && temperature < MAX_TEMPERATURE;
  }

  private boolean isPressureValid(int pressure) {
    return pressure > MIN_PRESSURE_PA
        && pressure < MAX_PRESSURE_PA;
  }
}
