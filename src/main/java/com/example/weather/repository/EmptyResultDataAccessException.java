package com.example.weather.repository;

public class EmptyResultDataAccessException extends RuntimeException {
  public EmptyResultDataAccessException(String message) {
    super(message);
  }
}
