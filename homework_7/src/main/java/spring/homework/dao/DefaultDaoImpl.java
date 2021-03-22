package spring.homework.dao;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

public abstract class DefaultDaoImpl {
    protected final JdbcOperations jdbc;
    protected final NamedParameterJdbcOperations namedParameterJdbcOperations;

    protected DefaultDaoImpl(JdbcOperations jdbc, NamedParameterJdbcOperations namedParameterJdbcOperations) {
        this.jdbc = jdbc;
        this.namedParameterJdbcOperations = namedParameterJdbcOperations;
    }
}
