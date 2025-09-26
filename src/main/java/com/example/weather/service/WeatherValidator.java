package com.example.weather.service;

import com.example.weather.models.Weather;

import java.util.Objects;

public interface WeatherValidator extends Validator<Weather>{
  default boolean areReferencesValid(Weather weather) {
    return Objects.nonNull(weather)
        && Objects.nonNull(weather.getPlanet())
        && Objects.nonNull(weather.getDay());
  }
}
