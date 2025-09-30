package com.example.weather.service;

import com.example.weather.model.Weather;

public class EarthWeatherRankCalculator implements RankCalculator {
  private static final int IDEAL_TEMPERATURE = 20;
  private static final int MAX_TEMPERATURE = 50;
  private static final int MIN_TEMPERATURE = -50;
  private static final int MAX_PARAMETER_SCORE = 1;
  private static final int MIN_PARAMETER_SCORE = 0;
  private static final int MAX_RANK = 1000;
  private static final int MAX_HUMAN_WIND = 30;
  private static final int MIN_HUMAN_WIND = 0;
  private static final int IDEAL_PRESSURE_PA = 101_324;
  private static final int MIN_PRESSURE_PA = 74_662;
  private static final int MAX_PRESSURE_PA = 127_990;
  private static final double PRESSURE_NORMALIZATION = 26_664.4;
  private static final double TEMP_WEIGHT = 0.6;
  private static final double PRESSURE_WEIGHT = 0.3;
  private static final double WIND_WEIGHT = 0.1;


  @Override
  public int calculateRank(Weather weather) {
    double tempScore = scoreTemperature(weather.getTemperature());
    double pressureScore = scorePressure(weather.getPressure());
    double windScore = scoreWind(weather.getWindSpeed());

    double rankNormalized = TEMP_WEIGHT * tempScore
        + PRESSURE_WEIGHT * pressureScore
        + WIND_WEIGHT * windScore;
    return (int) (MAX_RANK * rankNormalized);
  }

  private double scoreTemperature(int temperature) {
    if (temperature < MIN_TEMPERATURE || temperature > MAX_TEMPERATURE) {
      return MIN_PARAMETER_SCORE;
    }
    return MAX_PARAMETER_SCORE - (double) Math.abs(temperature - IDEAL_TEMPERATURE) / IDEAL_TEMPERATURE;
  }

  private double scorePressure(int pressure) {
    if (pressure < MIN_PRESSURE_PA || pressure > MAX_PRESSURE_PA) {
      return MIN_PARAMETER_SCORE;
    }
    return MAX_PARAMETER_SCORE - Math.abs(pressure - IDEAL_PRESSURE_PA) / PRESSURE_NORMALIZATION;
  }

  private double scoreWind(int windSpeed) {
    if (windSpeed < MIN_HUMAN_WIND || windSpeed > MAX_HUMAN_WIND) {
      return MIN_PARAMETER_SCORE;
    }
    return MAX_PARAMETER_SCORE - windSpeed / (double) MAX_HUMAN_WIND;
  }
}
