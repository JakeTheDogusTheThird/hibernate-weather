package com.example.weather.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "planets")
public class Planet {
  @Setter
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(unique = true)
  private PlanetName name;
  public Planet(PlanetName name) { this.name = name; }

  @Override
  public final boolean equals(Object o) {
    if (!(o instanceof Planet planet)) {
      return false;
    }
    return name == planet.name;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(name);
  }
}
