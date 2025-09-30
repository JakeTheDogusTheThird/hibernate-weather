package com.example.weather.service;

import com.example.weather.model.Planet;
import com.example.weather.model.PlanetName;
import com.example.weather.model.Weather;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MarsWeatherValidatorTest {
  private WeatherValidator validator;
  @BeforeEach
  void setup() {
    validator = new MarsWeatherValidator();
  }

  @Test
  void isValid_ShouldReturnTrueForAllConditionsMet() {
    Planet earth = new Planet(PlanetName.MARS);
    Weather weather = new Weather(
        earth,
        LocalDate.now(),
        20,
        500,
        5
    );
    assertTrue(validator.isValid(weather));
  }

  @ParameterizedTest
  @ValueSource(ints = {-5, 150})
  void isValid_ShouldReturnFalseForInvalidWindSpeed(int windSpeed) {
    Weather weather = new Weather(
        new Planet(PlanetName.MARS),
        LocalDate.now(),
        20,
        500,
        windSpeed
    );
    assertFalse(validator.isValid(weather));
  }

  @ParameterizedTest
  @ValueSource(ints = {-200, 100})
  void isValid_ShouldReturnFalseForInvalidTemperature(int temperature) {
    Weather weather = new Weather(
        new Planet(PlanetName.MARS),
        LocalDate.now(),
        temperature,
        500,
        10
    );
    assertFalse(validator.isValid(weather));
  }

  @ParameterizedTest
  @ValueSource(ints = {-100, 200_000})
  void isValid_ShouldReturnFalseForInvalidPressure(int pressure) {
    Weather weather = new Weather(
        new Planet(PlanetName.MARS),
        LocalDate.now(),
        20,
        pressure,
        10
    );
    assertFalse(validator.isValid(weather));
  }

  @Test
  void isValid_ShouldReturnFalseForOtherPlanetThanMars() {
    Weather weather = new Weather(
        new Planet(PlanetName.EARTH),
        LocalDate.now(),
        20,
        500,
        10
    );
    assertFalse(validator.isValid(weather));
  }
}
