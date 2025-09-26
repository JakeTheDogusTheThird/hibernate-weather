package com.example.weather.service;

public interface Validator<T> {
  boolean isValid(T t);
}
