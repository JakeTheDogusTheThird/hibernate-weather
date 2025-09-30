package com.example.weather.service;

import com.example.weather.model.Planet;
import com.example.weather.model.PlanetName;
import com.example.weather.model.Weather;
import com.example.weather.repository.EmptyResultDataAccessException;
import com.example.weather.repository.WeatherDao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class WeatherService {
  private final WeatherDao weatherDao;
  private final WeatherValidator validator;
  private final Map<PlanetName, RankCalculator> calculators;
  private final Map<MetricType, WeatherMetrics> metrics;
  private final WeatherComparator weatherComparator;

  public WeatherService(
      WeatherDao weatherDao,
      WeatherValidator validator,
      Map<PlanetName, RankCalculator> calculators,
      Map<MetricType, WeatherMetrics> metrics,
      WeatherComparator weatherComparator
  ) {
    this.weatherDao = weatherDao;
    this.validator = validator;
    this.calculators = calculators;
    this.metrics = metrics;
    this.weatherComparator = weatherComparator;
  }

  public int calculateRank(Weather weather) {
    RankCalculator calculator = calculators.get(weather.getPlanet().getName());
    return calculator.calculateRank(weather);
  }

  public double computeWeatherMetric(Weather weather, MetricType metricType) {
    WeatherMetrics metric = this.metrics.get(metricType);
    return metric.compute(weather);
  }

  public Weather findById(long id) throws EmptyResultDataAccessException {
    Optional<Weather> weather = this.weatherDao.findById(id);
    if (weather.isEmpty()) {
      throw new EmptyResultDataAccessException(String.format(
          "Weather with id: %d not found",
          id));
    }
    return weather.get();
  }

  public Weather create(Weather weather) {
    if (validator.isValid(weather)) {
      return this.weatherDao.save(weather);
    }
    return null;
  }

  public Weather update(Weather weather) {
    if (validator.isValid(weather)) {
      return this.weatherDao.update(weather);
    }
    return null;
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

  public List<Weather> getSortedWeathersByPlanet(Planet planet) {
    return findAllByPlanet(planet.getName())
        .stream()
        .sorted(this.weatherComparator)
        .toList();
  }

  public List<Weather> getPlanetWeathersRankedInRange(Planet planet, long lowRank, long highRank) {
    return findAllByPlanet(planet.getName())
        .stream()
        .filter(weather -> calculateRank(weather) >= lowRank && calculateRank(weather) <= highRank)
        .toList();
  }
}
