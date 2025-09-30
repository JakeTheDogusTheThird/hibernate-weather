package com.example.weather.service;

import com.example.weather.model.Planet;
import com.example.weather.model.PlanetName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlanetValidatorTest {
  @Test
  void isValid_ShouldReturnTrueForAllConditionsMet() {
    Planet planet = new Planet(PlanetName.EARTH);
    PlanetValidator planetValidator = new PlanetValidator();
    assertTrue(planetValidator.isValid(planet));
  }

  @ParameterizedTest
  @NullSource
  void isValid_ShouldReturnFalseForNullPlanet(Planet planet) {
    PlanetValidator planetValidator = new PlanetValidator();
    assertFalse(planetValidator.isValid(planet));
  }

  @ParameterizedTest
  @NullSource
  void isValid_ShouldReturnFalseForInvalidPlanet(PlanetName name) {
    PlanetValidator planetValidator = new PlanetValidator();
    assertFalse(planetValidator.isValid(new Planet(name)));
  }
}
