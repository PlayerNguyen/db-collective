package com.playernguyen.dbcollective.mysql;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Implements class to perform a connection to MySQL class with Hikari
 * connection pools.
 */
public class MySQLHikariDispatch extends MySQLDispatchWrapper {

    private final HikariConfig hikariConfig = new HikariConfig();
    private HikariDataSource source;

    public MySQLHikariDispatch() throws ClassNotFoundException {
        // Check contains driver
        Class.forName("com.mysql.jdbc.Driver");
        // follows a initial set up in Hikari, as default, you can all of this
        // properties.
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        // Build a url from configured item
        String url = String.format("jdbc:mysql://%s:%s/%s?%s", getHost(), getPort(), getDatabase(),
                (getOptions() != null ? getOptions() : ""));
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(getUsername());
        hikariConfig.setPassword(getPassword());
        // Add more config after initialized is available
        this.source = new HikariDataSource(hikariConfig);
    }

    public HikariConfig getHikariConfig() {
        return hikariConfig;
    }

    @Override
    public Connection openConnection() throws SQLException {
        // Create data source
        return source.getConnection();
    }

    @Override
    protected void finalize() throws Throwable {
        // super.finalize();
        // Datasource close
        if (source != null) {
            source.close();
        }
    }

}