package com.example.weather.service;

import com.example.weather.models.Weather;

public interface WeatherMetrics {
  double compute(Weather weather);
}
