package com.example.weather.util;

import com.example.weather.models.Planet;
import com.example.weather.models.Weather;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

  private static final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().build();

  public SessionFactory buildSessionFactory() {
    try {
      return new MetadataSources(registry)
          .addAnnotatedClass(Planet.class)
          .addAnnotatedClass(Weather.class)
          .buildMetadata()
          .buildSessionFactory();
    } catch (Throwable ex) {
      System.err.println("Initial SessionFactory creation failed." + ex);
      throw new ExceptionInInitializerError(ex);
    }
  }
}
