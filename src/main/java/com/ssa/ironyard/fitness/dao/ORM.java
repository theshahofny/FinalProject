package com.ssa.ironyard.fitness.dao;

import java.sql.ResultSet;

public interface ORM<T> {

    String projection();

    String table();

    T map(ResultSet results);

    String prepareInsert();

    String prepareUpdate();

    String prepareRead();

    String prepareDelete();
    
}