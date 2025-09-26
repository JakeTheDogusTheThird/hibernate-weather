package com.example.weather.repositories;

import com.example.weather.models.Planet;
import org.hibernate.SessionFactory;

public class PlanetHibernateDaoImpl
    extends BaseHibernateCrudDaoImpl<Planet, Long>
    implements PlanetDao {
  public PlanetHibernateDaoImpl(SessionFactory sessionFactory) {
    super(sessionFactory, Planet.class);
  }
}
