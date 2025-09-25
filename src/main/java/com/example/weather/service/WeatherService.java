package com.example.weather.service;

import com.example.weather.models.PlanetName;
import com.example.weather.models.Weather;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeatherService {
  private final Map<PlanetName, WeatherValidator> validators;
  private final Map<PlanetName, RankCalculator> calculators;
  private final List<WeatherComputation> computations;

  public WeatherService(Map<PlanetName, WeatherValidator> validators,
                        Map<PlanetName, RankCalculator> calculators,
                        List<WeatherComputation> computations) {
    this.validators = validators;
    this.calculators = calculators;
    this.computations = computations;
  }

  public boolean validate(Weather weather) {
    WeatherValidator validator = validators.get(weather.getPlanet().getName());
    if (validator == null) {
      throw new IllegalArgumentException("No validator for planet: " + weather.getPlanet().getName());
    }
    return validator.isValid(weather);
  }

  public int calculateRank(Weather weather) {
    RankCalculator calculator = calculators.get(weather.getPlanet().getName());
    if (calculator == null) {
      throw new IllegalArgumentException("No calculator for planet: " + weather.getPlanet().getName());
    }
    return calculator.calculateRank(weather);
  }

  public Map<String, Double> computeMetrics(Weather weather) {
    Map<String, Double> results = new HashMap<>();
    for (WeatherComputation comp : computations) {
      results.put(comp.getName(), comp.compute(weather));
    }
    return results;
  }
}
