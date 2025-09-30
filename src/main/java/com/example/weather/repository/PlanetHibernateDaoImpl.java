package com.example.weather.repository;

import com.example.weather.model.Planet;
import org.hibernate.SessionFactory;

public class PlanetHibernateDaoImpl
    extends BaseHibernateCrudDaoImpl<Planet, Long>
    implements PlanetDao {
  public PlanetHibernateDaoImpl(SessionFactory sessionFactory) {
    super(sessionFactory, Planet.class);
  }
}
