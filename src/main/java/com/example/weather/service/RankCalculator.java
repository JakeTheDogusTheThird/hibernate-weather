package com.example.weather.service;

import com.example.weather.models.Weather;

public interface RankCalculator {
  int calculateRank(Weather weather);
}
