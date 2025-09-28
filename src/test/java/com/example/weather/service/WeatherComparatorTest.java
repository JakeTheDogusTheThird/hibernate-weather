package com.example.weather.service;

import com.example.weather.models.Planet;
import com.example.weather.models.PlanetName;
import com.example.weather.models.Weather;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeatherComparatorTest {
  @Test
  void testCompare() {
    EarthWeatherRankCalculator rankCalculator = new EarthWeatherRankCalculator();
    WeatherComparator comparator = new WeatherComparator(rankCalculator);
    Weather weather1 = new Weather(
        new Planet(PlanetName.EARTH),
        LocalDate.now(),
        20,
        100_000,
        5
    );

    Weather weather2 = new Weather(
        new Planet(PlanetName.EARTH),
        LocalDate.now(),
        20,
        100_000,
        5
    );
    int expectedResult = 0;
    int marginOfError = 10;
    assertEquals(expectedResult, comparator.compare(weather1, weather2), marginOfError);
  }
}
