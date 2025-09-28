package com.example.weather.repositories;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface CrudDao<T, ID extends Serializable> {
  Optional<T> findById(ID id);
  List<T> findAll();
  T save(T entity);
  T update(T entity) throws EmptyResultDataAccessException;
  void deleteById(ID id) throws EmptyResultDataAccessException;
}
