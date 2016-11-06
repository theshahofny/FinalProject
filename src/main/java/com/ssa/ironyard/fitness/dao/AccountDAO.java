package com.ssa.ironyard.fitness.dao;

import com.ssa.ironyard.fitness.model.Account;

public interface AccountDAO extends DAO<Account>{

    Account readByUsername(String username);

    Account eagerRead(Integer id);

}
