package com.example.weather.repositories;

import com.example.weather.models.Weather;

import java.util.List;

public interface WeatherDao extends CrudDao<Weather, Long> {
  List<Weather> findAllByPlanet(String name);
}
