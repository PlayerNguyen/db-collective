package com.playernguyen.dbcollective.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.playernguyen.dbcollective.DispatchAbstract;
import com.playernguyen.dbcollective.response.DatabaseResponse;

/**
 * An SQLite dispatch, deal with SQL Driver.
 */
public class SQLiteDispatch extends DispatchAbstract {
    private final String PREFIX = "jdbc:sqlite:";
    private final String fileName;

    /**
     * Create a new dispatch with file name for SQLite to use
     * 
     * @param fileName a file name to use
     * @throws ClassNotFoundException
     */
    public SQLiteDispatch(String fileName) throws ClassNotFoundException {

        this.fileName = fileName;

        Class.forName("org.sqlite.JDBC");
    }

    @Override
    public void openConnection(DatabaseResponse<Connection> connection) throws SQLException {
        try (Connection conn = DriverManager.getConnection(PREFIX + fileName)) {
            connection.accept(conn);
        }
    }

}
