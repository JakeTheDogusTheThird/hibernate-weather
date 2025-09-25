package com.example.weather.service;

import com.example.weather.models.PlanetName;
import com.example.weather.models.Weather;

import java.util.Map;

public class DynamicPressureComputation implements WeatherComputation {

  private static final Map<PlanetName, Double> AIR_DENSITY = Map.of(
      PlanetName.EARTH, 1.225,
      PlanetName.MARS, 0.020
  );

  @Override
  public double compute(Weather weather) {
    double airDensity = AIR_DENSITY.get(weather.getPlanet().getName());
    double windSpeed = weather.getWindSpeed();
    return 0.5 * airDensity * windSpeed * windSpeed;
  }

  @Override
  public String getName() {
    return "DynamicPressure";
  }
}

