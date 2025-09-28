package com.example.weather.service;

import com.example.weather.models.Weather;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class WeatherTest {
  @Test
  void equals_ObjectsWithTheSameId_ShouldBeEqual() {
    Weather weather1 = new Weather();
    Weather weather2 = new Weather();
    weather1.setId(1L);
    weather2.setId(1L);

    assertEquals(weather1, weather2);
  }

  @Test
  void equals_ObjectsWithTheDifferentId_ShouldNotBeEqual() {
    Weather weather1 = new Weather();
    Weather weather2 = new Weather();
    weather1.setId(1L);
    weather2.setId(2L);
    assertNotEquals(weather1, weather2);
  }

  @Test
  void equals_ObjectsOfDifferentInstance_ShouldNotBeEqual() {
    Weather weather1 = new Weather();
    weather1.setId(1L);
    Object ob = new Object();
    assertNotEquals(weather1, ob);
  }

  @Test
  void hashCode_ObjectsWithTheSameId_ShouldBeEqual() {
    Weather weather1 = new Weather();
    Weather weather2 = new Weather();
    weather1.setId(1L);
    weather2.setId(1L);
    assertEquals(weather1.hashCode(), weather2.hashCode());
  }
}
