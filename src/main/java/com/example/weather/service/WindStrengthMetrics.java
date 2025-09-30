package com.example.weather.service;

import com.example.weather.model.Weather;

public class WindStrengthMetrics implements WeatherMetrics {

  public static final double BASELINE_FACTOR = 0.836;
  private static final double BEAUFORT_EXPONENT = 2.0 / 3.0;

  //Beaufort Scale (wind classification)
  @Override
  public double compute(Weather weather) {
    double windSpeed = weather.getWindSpeed();
    return Math.pow(windSpeed / BASELINE_FACTOR, BEAUFORT_EXPONENT);
  }
}
