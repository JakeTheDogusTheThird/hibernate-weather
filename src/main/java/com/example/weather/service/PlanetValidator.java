package com.example.weather.service;

import com.example.weather.models.Planet;

import java.util.Objects;

public class PlanetValidator implements Validator<Planet> {
  public boolean isValid(Planet planet) {
    return Objects.nonNull(planet)
        && Objects.nonNull(planet.getName());
  }
}
