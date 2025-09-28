package com.example.weather.service;

import com.example.weather.models.Planet;
import com.example.weather.models.PlanetName;
import com.example.weather.models.Weather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EarthWeatherValidatorTest {
  private WeatherValidator validator;
  @BeforeEach
  void setup() {
    validator = new EarthWeatherValidator();
  }

  @Test
  void isValid_ShouldReturnTrueForAllConditionsMet() {
    Planet earth = new Planet(PlanetName.EARTH);
    Weather weather = new Weather(
        earth,
        LocalDate.now(),
        20,
        100_000,
        5
    );
    assertTrue(validator.isValid(weather));
  }

  @ParameterizedTest
  @ValueSource(ints = {-5, 150})
  void isValid_ShouldReturnFalseForInvalidWindSpeed(int windSpeed) {
    Weather weather = new Weather(
        new Planet(PlanetName.EARTH),
        LocalDate.now(),
        20,
        100_000,
        windSpeed
    );
    assertFalse(validator.isValid(weather));
  }

  @ParameterizedTest
  @ValueSource(ints = {-100, 100})
  void isValid_ShouldReturnFalseForInvalidTemperature(int temperature) {
    Weather weather = new Weather(
        new Planet(PlanetName.EARTH),
        LocalDate.now(),
        temperature,
        100_000,
        10
    );
    assertFalse(validator.isValid(weather));
  }

  @ParameterizedTest
  @ValueSource(ints = {-100, 200_000})
  void isValid_ShouldReturnFalseForInvalidPressure(int pressure) {
    Weather weather = new Weather(
        new Planet(PlanetName.EARTH),
        LocalDate.now(),
        20,
        pressure,
        10
    );
    assertFalse(validator.isValid(weather));
  }

  @Test
  void isValid_ShouldReturnFalseForOtherPlanetThanEarth() {
    Weather weather = new Weather(
        new Planet(PlanetName.MARS),
        LocalDate.now(),
        20,
        100_000,
        10
    );
    assertFalse(validator.isValid(weather));
  }
}
