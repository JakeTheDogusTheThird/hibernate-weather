package com.example.weather.service;

import com.example.weather.models.Weather;

public interface WeatherValidator {
  boolean isValid(Weather weather);
}
