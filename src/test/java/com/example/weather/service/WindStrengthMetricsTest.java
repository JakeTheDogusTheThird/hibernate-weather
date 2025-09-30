package com.example.weather.service;

import com.example.weather.model.Planet;
import com.example.weather.model.PlanetName;
import com.example.weather.model.Weather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WindStrengthMetricsTest {
  private WindStrengthMetrics metrics;

  @BeforeEach
  void setUp() {
    metrics = new WindStrengthMetrics();
  }

  @Test
  void givenEarthWeather_whenCompute_returnExpectedResult() {
    Weather earthWeather = new Weather(
        new Planet(PlanetName.EARTH),
        LocalDate.now(),
        20,
        100_000,
        5
    );
    double expectedResult = 3.294;
    double marginOfError = 0.001;
    double result = metrics.compute(earthWeather);
    assertEquals(expectedResult, result, marginOfError);
  }
}
