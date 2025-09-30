package com.example.weather.service;

import com.example.weather.model.Weather;

public interface RankCalculator {
  int calculateRank(Weather weather);
}
