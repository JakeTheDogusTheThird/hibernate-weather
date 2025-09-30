package com.example.weather.repository;

import jakarta.persistence.Table;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class BaseHibernateCrudDaoImpl<T, ID extends Serializable> implements CrudDao<T, ID> {
  private final SessionFactory sessionFactory;
  private final Class<T> persistentClass;

  public BaseHibernateCrudDaoImpl(SessionFactory sessionFactory, Class<T> persistentClass) {
    this.sessionFactory = sessionFactory;
    this.persistentClass = persistentClass;
  }

  @Override
  public Optional<T> findById(ID id) {
    return Optional.ofNullable(executeInTransaction(session -> session.find(persistentClass, id)));
  }

  @Override
  public List<T> findAll() {
    return executeInTransaction(session -> session.createQuery(
                "from " + persistentClass.getName(), persistentClass
            )
            .list()
    );
  }

  @Override
  public T save(T entity) {
    return executeInTransaction(session -> {
      session.persist(entity);
      return entity;
    });
  }

  @Override
  public T update(T entity) {
    return executeInTransaction(session -> session.merge(entity));
  }

  @Override
  public void deleteById(ID id) {
    executeInTransaction(session -> {
      String entityName = persistentClass.getAnnotation(Table.class).name();
      if (entityName == null || entityName.isBlank()) {
        entityName = persistentClass.getSimpleName();
      }
      Query<Integer> query = session.createNativeQuery("DELETE FROM " + entityName + " WHERE id = ?", Integer.class);
      query.setParameter(1, id);
      query.executeUpdate();
      return null;
    });
  }

  protected <R> R executeInTransaction(Function<Session, R> action) {
    Transaction transaction = null;
    R result;
    try (Session session = this.sessionFactory.getCurrentSession()) {
      transaction = session.beginTransaction();
      result = action.apply(session);
      transaction.commit();
    } catch (RuntimeException e) {
      if (transaction != null) {
        transaction.rollback();
      }
      throw e;
    }
    return result;
  }
}

