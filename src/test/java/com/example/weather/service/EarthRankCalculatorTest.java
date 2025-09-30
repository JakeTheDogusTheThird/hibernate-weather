package com.example.weather.service;

import com.example.weather.model.Planet;
import com.example.weather.model.PlanetName;
import com.example.weather.model.Weather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EarthRankCalculatorTest {
  private RankCalculator calculator;
  @BeforeEach
  void setup() {
    calculator = new EarthWeatherRankCalculator();
  }

  @ParameterizedTest
  @CsvSource({
      "20,101324,0,1000",
      "-60,101324,0,400",
      "60,101324,0,400",
      "20,70000,0,700",
      "20,150000,0,700",
      "20,101324,-2,900",
      "20,101324,40,900"
  })
  void calculateRank_withValidRanges_ReturnsCorrectRank(
      int temperature,
      int pressure,
      int windSpeed,
      int expected
  ) {
    Weather weather = new Weather(
        new Planet(PlanetName.EARTH),
        LocalDate.now(),
        temperature,
        pressure,
        windSpeed
    );

    int result = calculator.calculateRank(weather);
    int marginOfError = 10;
    assertEquals(expected, result, marginOfError);
  }
}
