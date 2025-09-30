package com.example.weather.service;

import com.example.weather.model.Planet;

import java.util.Objects;

public class PlanetValidator implements Validator<Planet> {
  public boolean isValid(Planet planet) {
    return Objects.nonNull(planet)
        && Objects.nonNull(planet.getName());
  }
}
