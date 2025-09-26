package com.example.weather.service;

import com.example.weather.models.Planet;
import com.example.weather.models.PlanetName;
import com.example.weather.models.Weather;
import com.example.weather.repositories.EmptyResultDataAccessException;
import com.example.weather.repositories.WeatherDao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class WeatherService {
  private final WeatherDao weatherDao;
  private final Map<PlanetName, WeatherValidator> validators;
  private final Map<PlanetName, RankCalculator> calculators;
  private final Map<MetricType, WeatherMetrics> metrics;
  private final PlanetService planetService;

  public WeatherService(
      WeatherDao weatherDao,
      Map<PlanetName, WeatherValidator> validators,
      Map<PlanetName, RankCalculator> calculators,
      Map<MetricType, WeatherMetrics> metrics,
      PlanetService planetService
  ) {
    this.weatherDao = weatherDao;
    this.validators = validators;
    this.calculators = calculators;
    this.metrics = metrics;
    this.planetService = planetService;
  }

  public boolean isValid(Weather weather) {
    try {
      Planet planet = planetService.findById(weather.getPlanet().getId());
    } catch (EmptyResultDataAccessException ex) {
      return false;
    }
    WeatherValidator validator = validators.get(weather.getPlanet().getName());
    if (validator == null) {
      throw new IllegalArgumentException(String.format("No validator for planet: %s", weather.getPlanet().getName()));
    }
    return validator.isValid(weather);
  }

  public int calculateRank(Weather weather) {
    RankCalculator calculator = calculators.get(weather.getPlanet().getName());
    if (calculator == null) {
      throw new IllegalArgumentException(String.format("No calculator for planet: %s", weather.getPlanet().getName()));
    }
    return calculator.calculateRank(weather);
  }

  public double computeWeatherMetric(Weather weather, MetricType metricType) {
    WeatherMetrics metric = this.metrics.get(metricType);
    return metric.compute(weather);
  }

  public Weather findById(long id) {
    Optional<Weather> weather = this.weatherDao.findById(id);
    if (weather.isEmpty()) {
      throw new EmptyResultDataAccessException(String.format(
          "Weather with id: %d not found",
          id));
    }
    return weather.get();
  }

  public Weather create(Weather weather) {
    if (isValid(weather)) {
      return this.weatherDao.save(weather);
    }
    return null;
  }

  public Weather update(Weather weather) {
    if (isValid(weather)) {
      return this.weatherDao.save(weather);
    }
    return this.weatherDao.update(weather);
  }

  public void deleteById(long id) {
    this.weatherDao.deleteById(id);
  }

  public List<Weather> findAll() {
    return this.weatherDao.findAll();
  }

  public List<Weather> findAllByPlanet(PlanetName name) {
    return this.weatherDao.findAllByPlanet(name.toString());
  }

  public List<Weather> getPlanetWeathersSortedBy(Planet planet, WeatherComparator comparator) {
    return findAllByPlanet(planet.getName())
        .stream()
        .sorted()
        .toList();
  }

  public List<Weather> getPlanetWeathersRankedInRange(Planet planet, long lowRank, long highRank) {
    return findAllByPlanet(planet.getName())
        .stream()
        .filter(weather -> calculateRank(weather) > lowRank && calculateRank(weather) < highRank)
        .toList();
  }

}
