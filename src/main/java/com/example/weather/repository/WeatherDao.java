package com.example.weather.repository;

import com.example.weather.model.Weather;

import java.util.List;

public interface WeatherDao extends CrudDao<Weather, Long> {
  List<Weather> findAllByPlanet(String name);
}
