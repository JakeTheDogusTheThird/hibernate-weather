package service;

import com.example.weather.models.Planet;
import com.example.weather.models.PlanetName;
import com.example.weather.models.Weather;
import com.example.weather.service.EarthWeatherValidator;
import com.example.weather.service.WeatherValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EarthValidatorTest {
  private WeatherValidator validator;
  @BeforeEach
  void setup() {
    WeatherValidator validator = new EarthWeatherValidator();
  }

  @Test
  void isValid_ShouldReturnTrueForAllConditionsMet() {
    Planet earth = new Planet(PlanetName.EARTH);
    Weather weather = new Weather(
        earth,
        LocalDate.now(),
        20,
        100_000,
        5
    );
    assertTrue(validator.isValid(weather));
  }

  @ParameterizedTest
  @NullSource
  void isValid_ShouldReturnFalseForNullWeather(Weather weather) {
    assertFalse(validator.isValid(weather));
  }

  @ParameterizedTest
  @MethodSource("nullReferenceProvider")
  void isValid_ShouldReturnFalseForNullReferences(Planet planet, LocalDate date) {
    Weather weather = new Weather(
        planet,
        date,
        20,
        100_000,
        5
    );
    assertFalse(validator.isValid(weather));
  }

  static Stream<Arguments> nullReferenceProvider() {
    return Stream.of(
        Arguments.of(new Planet(PlanetName.EARTH), null),
        Arguments.of(null, LocalDate.now())
    );
  }
}
