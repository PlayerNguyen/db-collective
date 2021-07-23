package com.playernguyen.dbcollective;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.playernguyen.dbcollective.response.DatabaseResponse;

/**
 * A current to dispatch a
 */
public interface Dispatch {

    /**
     * Set to use this class in silent mode or not. Whether is true, display all
     * messages into a logger was declared. False otherwise;
     * 
     * @param b a verbose variable
     */
    void setVerbose(boolean b);

    /**
     * Get if Dispatch is in verbose mode or not. Which info out a raw sql (not
     * include data).
     * 
     * @return a verbose mode.
     */
    boolean isVerbose();

    /**
     * A logger to send whether verbose mode is on.
     * 
     * @return a verbose mode.
     */
    Logger getLogger();

    /**
     * Set a logger to definitely send verbose mode
     * 
     * @param logger a logger
     */
    void setLogger(Logger logger);

    /**
     * Open new SQL connection. NOTE: You must close the connection after querying
     * things. Otherwise, it can cause memory leaks.
     * 
     * @return a connection
     * @throws SQLException throws when catch error of sql
     */
    Connection openConnection() throws SQLException;

    /**
     * Prepares a statement in try-with-resource block. In order not to cause memory
     * leaks.
     * 
     * @param statement a statement to prepare
     * @param sql       a query
     * @param objects   varargs object list, alter to sql.
     * @throws SQLException throws when catch error of sql
     */
    default void preparedStatement(DatabaseResponse<PreparedStatement> statement, String sql, Object... objects)
            throws SQLException {
        try (Connection conn = openConnection(); PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            if (isVerbose() && this.getLogger() != null) {
                this.getLogger().info("dispatching a new prepared statement with " + sql);
            }

            // dispatch parameter
            for (int i = 0; i < objects.length; i++) {
                preparedStatement.setObject(i + 1, objects[i]);
            }

            // accept a prepared statement
            statement.accept(preparedStatement);
        }
    }

    /**
     * Execute a prepared statement as update by calling
     * {@link PreparedStatement#executeUpdate()}.
     * 
     * @param updatedRow a number of rows are updated.
     * @param sql        a query
     * @param objects    varargs object list, alter to sql.
     * @throws SQLException throws when catch error of sql
     * @see PreparedStatement#executeUpdate()
     */
    default void executeUpdate(DatabaseResponse<Integer> updatedRow, String sql, Object... objects)
            throws SQLException {
        this.preparedStatement((statement) -> {
            if (isVerbose() && this.getLogger() != null) {
                getLogger().info("dispatching a update with " + sql);
            }

            updatedRow.accept(statement.executeUpdate());
        }, sql, objects);
    }

    /**
     * Execute a prepared statement as query result by calling
     * {@link PreparedStatement#executeQuery()}.
     * 
     * @param resultSetCallback a {@link ResultSet} object.
     * @param sql               a query
     * @param objects           varargs object list, alter to sql.
     * @throws SQLException throws when catch error of sql
     * @see PreparedStatement#executeQuery()
     */
    default void executeQuery(DatabaseResponse<ResultSet> resultSetCallback, String sql, Object... objects)
            throws SQLException {
        this.preparedStatement((statement) -> {
            if (isVerbose() && this.getLogger() != null) {
                getLogger().info("dispatching a query with " + sql);
            }

            resultSetCallback.accept(statement.executeQuery());
        }, sql, objects);
    }

    /**
     * Execute a prepared statement by calling {@link PreparedStatement#execute()}.
     * 
     * @param callback a boolean callback. Which <code>true</code> if the first
     *                 result is a <code>ResultSet</code> object; <code>false</code>
     *                 if the first result is an update count or there is no result
     * @param sql      a query
     * @param objects  varargs object list, alter to sql.
     * @throws SQLException throws when catch error of sql
     * @see PreparedStatement#execute()
     */
    default void execute(DatabaseResponse<Boolean> callback, String sql, Object... objects) throws SQLException {
        this.preparedStatement((statement) -> {
            if (isVerbose() && this.getLogger() != null) {
                getLogger().info("dispatching a default execute with " + sql);
            }

            callback.accept(statement.execute());
        }, sql, objects);
    }
}
