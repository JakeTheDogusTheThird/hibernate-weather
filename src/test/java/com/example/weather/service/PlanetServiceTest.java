package com.example.weather.service;

import com.example.weather.model.Planet;
import com.example.weather.model.PlanetName;
import com.example.weather.repository.EmptyResultDataAccessException;
import com.example.weather.repository.PlanetDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlanetServiceTest {
  @Mock
  private PlanetDao planetDao;
  private PlanetService planetService;
  private AutoCloseable closeable;

  @BeforeEach
  public void setup() {
    PlanetValidator validator = new PlanetValidator();
    closeable = MockitoAnnotations.openMocks(this);
    planetService = new PlanetService(planetDao, validator);
  }

  @AfterEach
  void tearDown() throws Exception {
    closeable.close();
  }

  @Test
  void whenCreate_validPlanet_createdSuccessfully() {
    Planet planet = new Planet(PlanetName.EARTH);
    when(planetDao.save(planet)).thenReturn(planet);
    Planet result = planetService.create(planet);
    verify(planetDao).save(planet);
    assertEquals(planet, result);
  }

  @Test
  void whenCreate_invalidPlanet_returnNull() {
    Planet planet = new Planet();
    Planet result = planetService.create(planet);
    verify(planetDao, never()).save(any());
    assertNull(result);
  }

  @Test
  void whenFindById_existingPlanetId_returnPlanet() {
    Planet expected = new Planet(PlanetName.EARTH);
    expected.setId(1L);
    when(planetDao.findById(1L)).thenReturn(Optional.of(expected));
    Planet result = planetService.findById(1L);
    assertEquals(expected, result);
  }

  @Test
  void whenFindById_notExistingPlanetId_returnNull() {
    when(planetDao.findById(1L)).thenReturn(Optional.empty());
    EmptyResultDataAccessException exception = assertThrows(
        EmptyResultDataAccessException.class,
        () -> planetService.findById(1L)
    );

    assertEquals(
        "No planet with id : 1",
        exception.getMessage()
    );
  }

  @Test
  void whenUpdate_validPlanet_updatedSuccessfully() {
    Planet expected = new Planet(PlanetName.EARTH);
    expected.setId(1L);
    when(planetDao.update(expected)).thenReturn(expected);
    Planet result = planetService.update(expected);
    verify(planetDao).update(expected);
    assertEquals(expected, result);
  }

  @Test
  void whenUpdate_invalidPlanet_returnNull() {
    Planet planet = new Planet();
    planet.setId(1L);
    Planet result = planetService.update(planet);
    verify(planetDao, never()).update(any());
    assertNull(result);
  }

  @Test
  void whenDeleteById_givenId_deletesSuccessfully() {
    long id = 1L;
    planetService.deleteById(id);
    verify(planetDao).deleteById(id);
  }

  @Test
  void whenFindAll_returnAllPlanets() {
    Planet earth =  new Planet(PlanetName.EARTH);
    Planet mars = new Planet(PlanetName.MARS);
    earth.setId(1L);
    mars.setId(2L);
    List<Planet> expected = List.of(earth, mars);
    when(planetDao.findAll()).thenReturn(expected);
    List<Planet> result = planetService.findAll();
    assertEquals(expected, result);
  }


}
