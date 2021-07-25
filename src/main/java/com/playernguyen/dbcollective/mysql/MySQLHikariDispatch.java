package com.playernguyen.dbcollective.mysql;

import java.sql.Connection;
import java.sql.SQLException;

import com.playernguyen.dbcollective.response.DatabaseResponse;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Implements class to perform a connection to MySQL class with Hikari
 * connection pools.
 */
public class MySQLHikariDispatch extends MySQLDispatchWrapper {

    private HikariDataSource source;

    /**
     * Basic construction with args
     * 
     * @param host     a host of mysql server
     * @param port     a port of mysql server
     * @param username a username of mysql server
     * @param password a password of mysql server
     * @param database a database of mysql server
     * @param options  extra options to connect, as url parameter
     * @throws ClassNotFoundException no driver found
     */
    public MySQLHikariDispatch(String host, String port, String username, String password, String database,
            String options) throws ClassNotFoundException {
        // Check contains driver
        Class.forName("com.mysql.jdbc.Driver");

        setHost(host);
        setPort(port);
        setUsername(username);
        setPassword(password);
        setDatabase(database);
        setOptions(options);
        
        // Build a data source
        rebuildDataSource();
    }

    /**
     * A data source of this class. NOTE: Any update must call {@link #rebuildDataSource()}
     * 
     * @return a data source
     */
    public HikariDataSource getDataSource() {
        return source;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void openConnection(DatabaseResponse<Connection> connection) throws SQLException {
        // Create new instance whether is null
        if (this.source == null) {
            rebuildDataSource();
        }

        // open a connection with try-with-resource, bare it with a response callback
        try (Connection conn = this.source.getConnection()) {
            connection.accept(conn);
        }
    }

    /**
     * Rebuilds a data source in connection pool
     */
    public void rebuildDataSource() {
        this.source = new HikariDataSource();
        String url = String.format("jdbc:mysql://%s:%s/%s?%s", getHost(), getPort(), getDatabase(),
                (getOptions() != null ? getOptions() : ""));
        this.source.setJdbcUrl(url);
        this.source.setUsername(getUsername());
        this.source.setPassword(getPassword());
        this.source.setMaximumPoolSize(20);
    }

    /**
     * Close data source after use or clean by GC.
     * 
     * {@inheritDoc}
     */
    @Override
    protected void finalize() throws Throwable {
        // super.finalize();
        // Datasource close
        if (this.source != null) {
            source.close();
        }
    }

}