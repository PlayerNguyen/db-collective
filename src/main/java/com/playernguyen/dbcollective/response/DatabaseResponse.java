package com.playernguyen.dbcollective.response;

import java.sql.SQLException;

/**
 * Seemly works like Consumer. However, this class will throw SQLException when
 * error occurred. This class was created in order to change the way we connect
 * the functional.
 */
@FunctionalInterface
public interface DatabaseResponse<T> {
    /**
     * Accepts and bares an value as next function.
     * 
     * @param value a value to bares
     * @throws SQLException an exception of SQL executions, connections
     */
    void accept(T value) throws SQLException;
}
