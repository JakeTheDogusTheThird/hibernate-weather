package com.example.weather.repository;

import com.example.weather.model.Weather;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class WeatherHibernateDaoImpl
    extends BaseHibernateCrudDaoImpl<Weather, Long>
    implements WeatherDao {
  public WeatherHibernateDaoImpl(SessionFactory sessionFactory) {
    super(sessionFactory, Weather.class);
  }

  public List<Weather> findAllByPlanet(String planet) {
    return executeInTransaction(session -> {
      Query<Weather> query = session.createQuery(
          """
              SELECT *
                FROM weather AS w
                JOIN planets
                     ON w.planet_id = planets.id
               WHERE planets.name = ?
              """, Weather.class);
      query.setParameter(1, planet);
      return query.getResultList();
    });
  }
}
