package com.example.weather.service;

import com.example.weather.models.Planet;
import com.example.weather.repositories.EmptyResultDataAccessException;
import com.example.weather.repositories.PlanetDao;

import java.util.List;
import java.util.Optional;

public class PlanetService {
  private final PlanetDao planetDao;
  private final PlanetValidator planetValidator;

  public PlanetService(PlanetDao planetDao, PlanetValidator planetValidator) {
    this.planetDao = planetDao;
    this.planetValidator = planetValidator;
  }

  public Planet findById(long id) {
    Optional<Planet> planet = this.planetDao.findById(id);
    if (planet.isEmpty()) {
      throw new EmptyResultDataAccessException(String.format("No planet with id : %d", id));
    }
    return planet.get();
  }

  public Planet create(Planet planet) {
    if (planetValidator.isValid(planet)) {
      return this.planetDao.save(planet);
    }
    return null;
  }

  public Planet update(Planet planet) {
    if (planetValidator.isValid(planet)) {
      return this.planetDao.update(planet);
    }
    return null;
  }

  public void deleteById(long id) {
    this.planetDao.deleteById(id);
  }

  public List<Planet> findAll() {
    return this.planetDao.findAll();
  }


}
