package com.ssa.ironyard.fitness.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import com.ssa.ironyard.fitness.model.Account;

@Component
public class AccountDAOimpl extends AbstractSpringDAO<Account> implements AccountDAO {

    protected AccountDAOimpl(ORM<Account> orm, DataSource dataSource) {
        super(orm, dataSource);
    }

    @Autowired
    public AccountDAOimpl(DataSource datasource) {
        this(new AccountORM() {
        }, datasource);
    }

    @Override
    public Account readByUsername(String username) {
        if (null == username)
            return null;
        return this.springTemplate.query(((AccountORM) this.orm).eagerPrepareReadByUsername(),
                (PreparedStatement ps) -> ps.setString(1, username), (ResultSet cursor) -> {
                    if (cursor.next())
                        return this.orm.map(cursor);
                    return null;
                });
    }

    public int clear() {
        return this.springTemplate.update(((AccountORM) this.orm).clear());
    }

    @Override
    public Account eagerRead(Integer id) {
        if (null == id)
            return null;
        return this.springTemplate.query(((AccountORM) this.orm).eagerPrepareReadByUsername(),
                (PreparedStatement ps) -> ps.setInt(1, id), (ResultSet cursor) -> {
                    if (cursor.next())
                        return this.orm.map(cursor);
                    return null;
                });

    }

    @Override
    protected void insertPreparer(PreparedStatement insertStatement, Account domainToInsert) throws SQLException {

        mapDomainToPreparedStatement(insertStatement, domainToInsert, 1);

    }

    @Override
    protected Account afterInsert(Account copy, Integer id) {
        Account a = copy.clone();
        a.setId(id);
        a.setLoaded(true);
        return a;
    }

    @Override
    protected Account afterUpdate(Account copy) {
        Account a = copy.clone();
        return a;
    }

    @Override
    protected PreparedStatementSetter updatePreparer(Account domainToUpdate) {
        return (PreparedStatement ps) -> {
            int lastParameter = mapDomainToPreparedStatement(ps, domainToUpdate, 1);
            ps.setInt(lastParameter, domainToUpdate.getId());
        };
    }

    static int mapDomainToPreparedStatement(PreparedStatement preparedStatement, Account account, int parameterIndex)
            throws SQLException {

        preparedStatement.setString(parameterIndex++, account.getUsername());
        preparedStatement.setString(parameterIndex++, account.getPassword().getSalt());
        preparedStatement.setString(parameterIndex++, account.getPassword().getHash());
        preparedStatement.setString(parameterIndex++, account.getFirstName());
        preparedStatement.setString(parameterIndex++, account.getLastName());
        preparedStatement.setDouble(parameterIndex++, account.getHeight());
        preparedStatement.setDouble(parameterIndex++, account.getWeight());
        preparedStatement.setString(parameterIndex++, String.valueOf(account.getGender().abbrev));
        preparedStatement.setInt(parameterIndex++, account.getAge());
        preparedStatement.setInt(parameterIndex++, account.getGoal().getId());
        return parameterIndex;
    }

}
