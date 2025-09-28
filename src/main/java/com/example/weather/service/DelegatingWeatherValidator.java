package com.example.weather.service;

import com.example.weather.models.PlanetName;
import com.example.weather.models.Weather;

import java.util.Map;

public class DelegatingWeatherValidator implements WeatherValidator {
  private final Map<PlanetName, WeatherValidator> validators;

  public DelegatingWeatherValidator(Map<PlanetName, WeatherValidator> validators) {
    this.validators = validators;
  }

  @Override
  public boolean isValid(Weather weather) {
    return hasValidReferences(weather)
        && getWeatherValidator(weather).isValid(weather);
  }

  private WeatherValidator getWeatherValidator(Weather weather) {
    return validators.get(weather.getPlanet().getName());
  }

  private boolean hasValidReferences(Weather weather) {
    return weather != null
        && weather.getPlanet() != null
        && weather.getDay() != null;
  }
}
