package com.epam.pool;

import java.sql.Connection;

public interface IConnectionPool {

    Connection getConnection();
    void retrieveConnection(Connection connection);
    void close();
}

