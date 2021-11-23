package com.playernguyen.dbcollective.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.playernguyen.dbcollective.DispatchAbstract;
import com.playernguyen.dbcollective.response.DatabaseResponse;
import org.sqlite.SQLiteConfig;

/**
 * An SQLite dispatch, deal with SQL Driver.
 */
public class SQLiteDispatch extends DispatchAbstract {
    private final String fileName;
    private final SQLiteConfig config;

    /**
     * Create a new dispatch with file name for SQLite to use
     *
     * @param fileName a file name to use
     * @param config a sqlite configuration
     */
    public SQLiteDispatch(String fileName, SQLiteConfig config) throws ClassNotFoundException {

        this.fileName = fileName;
        this.config = config;

        Class.forName("org.sqlite.JDBC");
    }
    /**
     * Create a new dispatch with file name for SQLite to use
     *
     * @param name a file name to use
     */
    public SQLiteDispatch(String name) throws ClassNotFoundException {
        this.fileName = name;
        this.config = new SQLiteConfig();

        Class.forName("org.sqlite.JDBC");
    }


    @Override
    public void openConnection(DatabaseResponse<Connection> connection) throws SQLException {
        String PREFIX = "jdbc:sqlite:";
        try (Connection conn = DriverManager.getConnection(PREFIX + fileName, config.toProperties())) {
            connection.accept(conn);
        }
    }

}
