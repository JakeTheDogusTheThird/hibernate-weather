package com.example.weather.service;

import com.example.weather.models.Weather;

import java.util.Comparator;

public class WeatherComparator implements Comparator<Weather> {
  private final RankCalculator rankCalculator;
  public WeatherComparator(RankCalculator rankCalculator) {
    this.rankCalculator = rankCalculator;
  }

  @Override
  public int compare(Weather o1, Weather o2) {
    return rankCalculator.calculateRank(o1) - rankCalculator.calculateRank(o2);
  }
}
