package com.ssa.ironyard.fitness.dao;

public interface DAO<T> {

    T update(T domain);

    T insert(T domain);

    T read(Integer id);

    boolean delete(Integer id);

  
}
