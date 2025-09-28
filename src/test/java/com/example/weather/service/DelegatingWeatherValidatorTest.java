package com.example.weather.service;

import com.example.weather.models.Planet;
import com.example.weather.models.PlanetName;
import com.example.weather.models.Weather;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DelegatingWeatherValidatorTest {
  private static final Map<PlanetName, WeatherValidator> validators = Map.of(
      PlanetName.EARTH, new EarthWeatherValidator(),
      PlanetName.MARS, new MarsWeatherValidator()
  );

  private static final DelegatingWeatherValidator validator = new DelegatingWeatherValidator(validators);

  @ParameterizedTest
  @NullSource
  void isValid_ShouldReturnFalseForNullWeather(Weather weather) {
    assertFalse(validator.isValid(weather));
  }

  @ParameterizedTest
  @MethodSource("nullReferenceProvider")
  void isValid_ShouldReturnFalseForNullReferences(Planet planet, LocalDate date) {
    Weather weather = new Weather(
        planet,
        date,
        20,
        100_000,
        5
    );
    assertFalse(validator.isValid(weather));
  }

  static Stream<Arguments> nullReferenceProvider() {
    return Stream.of(
        Arguments.of(new Planet(PlanetName.EARTH), null),
        Arguments.of(null, LocalDate.now())
    );
  }

  @Test
  void isValid_ShouldReturnTrueForAllConditionsMet() {
    Weather weather = new Weather(
        new Planet(PlanetName.EARTH),
        LocalDate.now(),
        20,
        100_000,
        5
    );
    assertTrue(validator.isValid(weather));
  }
}
