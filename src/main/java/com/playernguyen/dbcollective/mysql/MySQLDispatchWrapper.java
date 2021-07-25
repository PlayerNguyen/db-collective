package com.playernguyen.dbcollective.mysql;

import com.playernguyen.dbcollective.DispatchAbstract;

/**
 * MySQLDispatchWrapper, an abstract class to deal with a MySQL server.
 */
public abstract class MySQLDispatchWrapper extends DispatchAbstract {

    private String host = "localhost";
    private String port = "3306";
    private String username;
    private String password;
    private String database;
    private String options;

    public MySQLDispatchWrapper() throws ClassNotFoundException {
        // Check contains driver
        Class.forName("com.mysql.jdbc.Driver");
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDatabase() {
        return database;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getOptions() {
        return options;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

}