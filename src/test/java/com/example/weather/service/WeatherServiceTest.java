package com.example.weather.service;

import com.example.weather.model.Planet;
import com.example.weather.model.PlanetName;
import com.example.weather.model.Weather;
import com.example.weather.repository.EmptyResultDataAccessException;
import com.example.weather.repository.WeatherDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class WeatherServiceTest {
  @Mock
  private WeatherDao weatherDao;

  private WeatherService weatherService;

  private AutoCloseable closeable;

  @BeforeEach
  void setUp() {
    closeable = MockitoAnnotations.openMocks(this);

    Map<PlanetName, WeatherValidator> validators = Map.of(
        PlanetName.EARTH, new EarthWeatherValidator(),
        PlanetName.MARS, new MarsWeatherValidator()
    );
    WeatherValidator validator = new DelegatingWeatherValidator(validators);

    Map<PlanetName, RankCalculator> calculators = Map.of(
        PlanetName.EARTH, new EarthWeatherRankCalculator(),
        PlanetName.MARS, new MarsWeatherRankCalculator()
    );

    Map<MetricType, WeatherMetrics> metrics = Map.of(
        MetricType.WIND_STRENGTH, new WindStrengthMetrics(),
        MetricType.WIND_PRESSURE, new DynamicPressureMetrics()
    );
    WeatherComparator weatherComparator = new WeatherComparator(new EarthWeatherRankCalculator());

    weatherService = new WeatherService(
        weatherDao,
        validator,
        calculators,
        metrics,
        weatherComparator
    );
  }

  @AfterEach
  void tearDown() throws Exception {
    closeable.close();
  }

  @Test
  void create_validWeather_savesSuccessfully() {
    Weather weather = new Weather(
        new Planet(PlanetName.EARTH),
        LocalDate.now(),
        20,
        100_000,
        5
    );
    Weather savedWeather = new Weather(
        new Planet(PlanetName.EARTH),
        LocalDate.now(),
        20,
        100_000,
        5
    );
    savedWeather.setId(1L);

    when(weatherDao.save(weather)).thenReturn(savedWeather);

    Weather result = weatherService.create(weather);
    verify(weatherDao).save(weather);
    assertEquals(savedWeather, result);
  }

  @Test
  void createInvalidWeather_returnNull() {
    Weather weather = new Weather();
    Weather result = weatherService.create(weather);
    verify(weatherDao, never()).save(any());
    assertNull(result);
  }

  @Test
  void findById_validWeather_findsSuccessfully() {
    Weather weather = new Weather(
        new Planet(PlanetName.EARTH),
        LocalDate.now(),
        20,
        100_000,
        5
    );
    weather.setId(1L);
    when(weatherDao.findById(1L)).thenReturn(Optional.of(weather));
    Weather result = weatherService.findById(1L);
    verify(weatherDao).findById(1L);
    assertEquals(weather, result);
  }

  @Test
  void findById_invalidWeather_throwEmptyResultDataAccessException() {
    when(weatherDao.findById(1L)).thenReturn(Optional.empty());

    EmptyResultDataAccessException exception = assertThrows(
        EmptyResultDataAccessException.class,
        () -> weatherService.findById(1L)
    );

    assertEquals(
        "Weather with id: 1 not found",
        exception.getMessage()
    );
  }

  @Test
  void update_validWeather_updatesSuccessfully() {
    Weather expectedUpdatedWeather = new Weather(
        new Planet(PlanetName.EARTH),
        LocalDate.now(),
        20,
        100_000,
        10
    );
    expectedUpdatedWeather.setId(1L);

    when(weatherDao.update(expectedUpdatedWeather)).thenReturn(expectedUpdatedWeather);
    Weather result = weatherService.update(expectedUpdatedWeather);
    verify(weatherDao).update(expectedUpdatedWeather);

    assertEquals(1L, result.getId());
    assertEquals(10, result.getWindSpeed());
  }

  @Test
  void update_invalidWeather_returnNull() {
    Weather weather = new Weather();
    Weather result = weatherService.update(weather);
    verify(weatherDao, never()).update(any());
    assertNull(result);
  }

  @Test
  void deleteById_givenId_deletesSuccessfully() {
    long id = 1L;
    weatherService.deleteById(id);
    verify(weatherDao, times(1)).deleteById(id);
  }

  @Test
  void findAll_existWeathers_returnsAllWeathers() {
    Weather weather1 = new Weather(
        new Planet(PlanetName.EARTH),
        LocalDate.now(),
        20,
        100_000,
        5
    );
    Weather weather2 = new Weather(
        new Planet(PlanetName.EARTH),
        LocalDate.now(),
        20,
        100_000,
        10
    );
    List<Weather> expectedList = List.of(weather1, weather2);

    when(weatherDao.findAll()).thenReturn(expectedList);
    List<Weather> result = weatherService.findAll();
    verify(weatherDao).findAll();
    assertEquals(expectedList, result);
  }

  @Test
  void findAll_noWeathers_returnsEmptyList() {
    when(weatherDao.findAll()).thenReturn(List.of());

    List<Weather> weathers = weatherService.findAll();

    assertNotNull(weathers);
    assertTrue(weathers.isEmpty());
    verify(weatherDao, times(1)).findAll();
  }

  @Test
  void findAllByPlanet_existWeathersByPlanet_returnsAllWeathersByPlanet() {
    Weather weather1 = new Weather(
        new Planet(PlanetName.EARTH),
        LocalDate.now(),
        20,
        100_000,
        5
    );
    Weather weather2 = new Weather(
        new Planet(PlanetName.EARTH),
        LocalDate.now(),
        20,
        600,
        10
    );
    List<Weather> expectedList = List.of(weather1, weather2);

    when(weatherDao.findAllByPlanet(PlanetName.EARTH.toString())).thenReturn(expectedList);
    List<Weather> result = weatherService.findAllByPlanet(PlanetName.EARTH);
    verify(weatherDao).findAllByPlanet(PlanetName.EARTH.toString());
    assertEquals(expectedList, result);
  }

  @Test
  void getPlanetWeathersSortedBy_givenComparatorAndPlanet_returnsSortedWeathersByPlanet() {
    Weather weather1 = new Weather(
        new Planet(PlanetName.EARTH),
        LocalDate.now(),
        20,
        101_324,
        0
    );
    Weather weather2 = new Weather(
        new Planet(PlanetName.EARTH),
        LocalDate.now(),
        20,
        101_324,
        40
    );
    Weather weather3 = new Weather(
        new Planet(PlanetName.EARTH),
        LocalDate.now(),
        20,
        150_000,
        0
    );

    List<Weather> weathers = List.of(weather1, weather2, weather3);
    List<Weather> expectedSortedWeathers = List.of(weather1, weather3, weather2);
    when(weatherDao.findAllByPlanet(PlanetName.EARTH.toString())).thenReturn(weathers);
    List<Weather> result = weatherService.getSortedWeathersByPlanet(new Planet(PlanetName.EARTH));
    verify(weatherDao).findAllByPlanet(PlanetName.EARTH.toString());
    assertEquals(expectedSortedWeathers, result);
  }

  @Test
  void getPlanetWeathersRankedInRange_whenGivenPlanetAndRanges_returnsWeathersInRange() {
    Weather weather1 = new Weather(
        new Planet(PlanetName.EARTH),
        LocalDate.now(),
        20,
        101_324,
        0
    );
    Weather weather2 = new Weather(
        new Planet(PlanetName.EARTH),
        LocalDate.now(),
        20,
        101_324,
        40
    );
    Weather weather3 = new Weather(
        new Planet(PlanetName.EARTH),
        LocalDate.now(),
        20,
        150_000,
        0
    );

    List<Weather> weathers = List.of(weather1, weather2, weather3);
    List<Weather> expected = List.of(weather2, weather3);
    when(weatherDao.findAllByPlanet(PlanetName.EARTH.toString())).thenReturn(weathers);
    List<Weather> result = weatherService.getPlanetWeathersRankedInRange(
        new Planet(PlanetName.EARTH),
        700,
        900);
    verify(weatherDao).findAllByPlanet(PlanetName.EARTH.toString());
    assertEquals(expected, result);
  }

  @Test
  void computeWeatherMetric_whenWindStrengthMetric_returnsMetric() {
    Weather weather = new Weather(
        new Planet(PlanetName.EARTH),
        LocalDate.now(),
        20,
        100_000,
        5
    );
    double expectedResult = 3.294;
    double marginOfError = 0.001;
    double result = weatherService.computeWeatherMetric(weather, MetricType.WIND_STRENGTH);

    assertEquals(expectedResult, result, marginOfError);
  }

  @Test
  void computeWeatherMetric_whenWindPressureMetric_returnsMetric() {
    Weather weather = new Weather(
        new Planet(PlanetName.EARTH),
        LocalDate.now(),
        20,
        100_000,
        5
    );
    double expectedResult = 15.312;
    double marginOfError = 0.001;
    double result = weatherService.computeWeatherMetric(weather, MetricType.WIND_PRESSURE);

    assertEquals(expectedResult, result, marginOfError);
  }
}
