package com.playernguyen.dbcollective.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.playernguyen.dbcollective.functions.Preconditions;

/**
 * Implements class to perform a connection to MySQL class.
 */
public class MySQLDispatch extends MySQLDispatchWrapper {

    public MySQLDispatch() throws ClassNotFoundException {
        // Check contains driver
        Class.forName("com.mysql.jdbc.Driver");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Connection openConnection() throws SQLException {
        // Precondition to check all fields not null
        Preconditions.shouldNotNull(getHost(), "a host variable is null");
        Preconditions.shouldNotNull(getPort(), "a port variable is null");
        Preconditions.shouldNotNull(getUsername(), "a username variable is null");
        Preconditions.shouldNotNull(getPassword(), "a password variable is null");
        Preconditions.shouldNotNull(getDatabase(), "a database variable is null");

        // Build a url
        String url = String.format("jdbc:mysql://%s:%s/%s?%s", getHost(), getPort(), getDatabase(),
                (getOptions() != null ? getOptions() : ""));

        // Return a connection
        return DriverManager.getConnection(url, getUsername(), getPassword());
    }

}