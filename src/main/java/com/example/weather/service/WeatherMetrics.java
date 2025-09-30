package com.example.weather.service;

import com.example.weather.model.Weather;

public interface WeatherMetrics {
  double compute(Weather weather);
}
