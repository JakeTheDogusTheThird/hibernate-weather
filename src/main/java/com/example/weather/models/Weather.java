package com.example.weather.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "weather")
public class Weather {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private LocalDate day;

  @Column
  private int temperature;

  @Column
  private int pressure;

  @Column(name = "wind_speed")
  private int windSpeed;

  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  private Planet planet;

  public Weather(Planet planet, LocalDate day, int temperature, int pressure, int windSpeed) {
    this.day = day;
    this.temperature = temperature;
    this.pressure = pressure;
    this.windSpeed = windSpeed;
    this.planet = planet;
  }
}
