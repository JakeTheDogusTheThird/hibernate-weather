package com.example.weather.service;

import com.example.weather.models.Weather;

public interface WeatherComputation {
  double compute(Weather weather);

  String getName();
}
