package com.example.weather;

import com.example.weather.models.Planet;
import com.example.weather.models.PlanetName;
import com.example.weather.models.Weather;
import com.example.weather.repositories.PlanetDao;
import com.example.weather.repositories.PlanetHibernateDaoImpl;
import com.example.weather.service.PlanetService;
import com.example.weather.service.PlanetValidator;
import com.example.weather.util.HibernateUtil;
import org.hibernate.SessionFactory;

import java.time.LocalDate;


/*
* private LocalDate day;
  private int temperature;
  private int pressure;
  @Column(name = "wind_speed")
  private int windSpeed;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Planet planet;*/
public class Main {
  public static void main(String[] args) {
    HibernateUtil util = new HibernateUtil();
    SessionFactory factory = util.buildSessionFactory();

    Planet earth = new Planet(PlanetName.EARTH);
    Planet mars = new Planet(PlanetName.MARS);

    PlanetDao planetDao = new PlanetHibernateDaoImpl(factory);
    PlanetValidator planetValidator = new PlanetValidator();
    PlanetService planetService = new PlanetService(planetDao, planetValidator);
    planetService.create(earth);
    planetService.create(mars);

    planetService.deleteById(earth.getId());
  }
}
