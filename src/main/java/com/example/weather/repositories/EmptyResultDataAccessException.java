package com.example.weather.repositories;

public class EmptyResultDataAccessException extends RuntimeException {
  public EmptyResultDataAccessException(String message) {
    super(message);
  }
}
