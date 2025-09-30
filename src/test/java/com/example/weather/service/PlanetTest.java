package com.example.weather.service;

import com.example.weather.model.Planet;
import com.example.weather.model.PlanetName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PlanetTest {
  @Test
  void equals_ObjectsWithTheSamePlanetName_ShouldBeEqual() {
    Planet earth1 = new Planet(PlanetName.EARTH);
    Planet earth2 = new Planet(PlanetName.EARTH);
    assertEquals(earth1, earth2);
  }

  @Test
  void equals_ObjectsWithDifferentPlanetName_ShouldNotBeEqual() {
    Planet earth1 = new Planet(PlanetName.EARTH);
    Planet earth2 = new Planet(PlanetName.MARS);
    assertNotEquals(earth1, earth2);
  }

  @Test
  void equals_ObjectsOfDifferentInstance_ShouldNotBeEqual() {
    Planet earth = new Planet(PlanetName.EARTH);
    Object ob = new Object();
    assertNotEquals(earth, ob);
  }

  @Test
  void hashcode_ObjectsWithTheSamePlanetName_ShouldBeEqual() {
    Planet earth1 = new Planet(PlanetName.EARTH);
    Planet earth2 = new Planet(PlanetName.EARTH);
    assertEquals(earth1.hashCode(), earth2.hashCode());
  }
}
