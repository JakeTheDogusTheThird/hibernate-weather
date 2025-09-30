package com.example.weather.service;

import com.example.weather.model.Planet;
import com.example.weather.model.PlanetName;
import com.example.weather.model.Weather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DynamicPressureMetricsTest {
  private DynamicPressureMetrics metrics;

  @BeforeEach
  void setUp() {
    metrics = new DynamicPressureMetrics();
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
    double expectedResult = 15.312;
    double marginOfError = 0.001;
    double result = metrics.compute(earthWeather);
    assertEquals(expectedResult, result, marginOfError);
  }

  @Test
  void givenMarsWeather_whenCompute_returnExpectedResult() {
    Weather earthWeather = new Weather(
        new Planet(PlanetName.MARS),
        LocalDate.now(),
        20,
        610,
        5
    );
    double expectedResult = 0.25;
    double marginOfError = 0.001;
    double result = metrics.compute(earthWeather);
    assertEquals(expectedResult, result, marginOfError);
  }
}
