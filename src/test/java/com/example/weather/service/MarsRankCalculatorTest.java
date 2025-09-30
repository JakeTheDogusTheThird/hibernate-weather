package com.example.weather.service;

import com.example.weather.model.Planet;
import com.example.weather.model.PlanetName;
import com.example.weather.model.Weather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MarsRankCalculatorTest {
  private RankCalculator calculator;
  @BeforeEach
  void setup() {
    calculator = new MarsWeatherRankCalculator();
  }

  @Test
  void calculateRank_withValidRanges_ReturnsCorrectRank() {
    Weather weather = new Weather(
        new Planet(PlanetName.MARS),
        LocalDate.now(),
        -60,
        610,
        0
    );

    int result = calculator.calculateRank(weather);
    int marginOfError = 10;
    int expectedRank = 1000;
    assertEquals(expectedRank, result, marginOfError);
  }
}
