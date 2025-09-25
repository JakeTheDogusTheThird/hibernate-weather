package com.example.weather.service;

import com.example.weather.models.Weather;

public class MarsWeatherRankCalculator implements RankCalculator {
  private static final int IDEAL_TEMPERATURE = -60;
  private static final int IDEAL_PRESSURE = 610;
  private static final int IDEAL_WIND_SPEED = 0;

  private static final double TEMPERATURE_NORMALIZATION = 65.0; // max deviation from ideal
  private static final double PRESSURE_NORMALIZATION = 400.0;    // max deviation from ideal
  private static final double WIND_NORMALIZATION = 30.0;

  private static final int MAX_RANK = 1000;
  private static final double TEMPERATURE_WEIGHT = 0.5;
  private static final double PRESSURE_WEIGHT = 0.3;
  private static final double WIND_WEIGHT = 0.2;
  public static final double MAX_PARAMETER_SCORE = 1.0;

  @Override
  public int calculateRank(Weather weather) {
    double tempScore = scoreParameter(weather.getTemperature(),
        IDEAL_TEMPERATURE,
        TEMPERATURE_NORMALIZATION);

    double pressureScore = scoreParameter(weather.getPressure(),
        IDEAL_PRESSURE,
        PRESSURE_NORMALIZATION);

    double windScore = scoreParameter(weather.getWindSpeed(),
        IDEAL_WIND_SPEED,
        WIND_NORMALIZATION);

    double normalizedRank = TEMPERATURE_WEIGHT * tempScore + PRESSURE_WEIGHT * pressureScore + WIND_WEIGHT * windScore;
    return (int) (MAX_RANK * normalizedRank);
  }

  private double scoreParameter(double value, double ideal, double normalization) {
    return MAX_PARAMETER_SCORE - Math.abs(value - ideal) / normalization;
  }

}
