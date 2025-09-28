package com.example.weather.service;

import com.example.weather.models.Planet;
import com.example.weather.models.PlanetName;
import com.example.weather.models.Weather;
import com.example.weather.service.MarsWeatherRankCalculator;
import com.example.weather.service.RankCalculator;
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
