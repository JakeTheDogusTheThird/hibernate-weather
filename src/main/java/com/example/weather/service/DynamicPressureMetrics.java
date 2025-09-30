package com.example.weather.service;

import com.example.weather.model.PlanetName;
import com.example.weather.model.Weather;

import java.util.Map;

public class DynamicPressureMetrics implements WeatherMetrics {
  private static final double MEAN_EARTH_AIR_DENSITY = 1.225;
  private static final double MEAN_MARS_AIR_DENSITY = 0.020;
  private static final Map<PlanetName, Double> AIR_DENSITY = Map.of(
      PlanetName.EARTH, MEAN_EARTH_AIR_DENSITY,
      PlanetName.MARS, MEAN_MARS_AIR_DENSITY
  );
  public static final double KINETIC_ENERGY_FACTOR = 0.5;

  @Override
  public double compute(Weather weather) {
    double airDensity = AIR_DENSITY.get(weather.getPlanet().getName());
    double windSpeed = weather.getWindSpeed();
    return KINETIC_ENERGY_FACTOR * airDensity * windSpeed * windSpeed;
  }
}

