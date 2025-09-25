package com.example.weather.service;

import com.example.weather.models.Weather;

public class WindStrengthComputation implements WeatherComputation {
  //Beaufort Scale (wind classification)
  @Override
  public double compute(Weather weather) {
    double v = weather.getWindSpeed(); // m/s
    return Math.pow(v / 0.836, 2.0 / 3.0);
  }

  @Override
  public String getName() {
    return "WindStrength";
  }
}
