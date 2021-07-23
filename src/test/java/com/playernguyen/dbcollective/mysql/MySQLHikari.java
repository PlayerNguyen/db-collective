package com.playernguyen.dbcollective.mysql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Logger;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

@Ignore // Remove this line to test with MySQL
public class MySQLHikari {
    private static MySQLDispatch dispatch;

    @BeforeClass
    public static void setup() throws IOException, ClassNotFoundException, SQLException {
        URL url = MySQL.class.getResource("/database.properties");
        if (url == null) {
            throw new NullPointerException("not found database configuration file");
        }

        InputStream stream = url.openStream();

        Properties properties = new Properties();
        properties.load(stream);
        properties.forEach((a, b) -> {
            System.out.println(a + " -> " + b);
        });

        dispatch = new MySQLDispatch();
        dispatch.setVerbose(true);
        dispatch.setLogger(Logger.getLogger(MySQL.class.getName()));

        dispatch.setHost(properties.getProperty("Host"));
        dispatch.setPort(properties.getProperty("Port"));
        dispatch.setUsername(properties.getProperty("Username"));
        dispatch.setPassword(properties.getProperty("Password"));
        dispatch.setDatabase(properties.getProperty("Database"));
        dispatch.setOptions(properties.getProperty("Options"));

        dispatch.execute(callback -> {
        }, "CREATE TABLE IF NOT EXISTS model (id int not null primary key auto_increment, height int not null, weight int not null)");

        for (int i = 0; i < 1000; i++) {
            dispatch.execute((updatedRows) -> {
            }, "INSERT INTO model (height, weight) VALUES (?, ?) ", new Random().nextInt(1000),
                    new Random().nextInt((1000)));
        }
    }

    @AfterClass
    public static void teardown() throws SQLException {
        dispatch.execute(callback -> {

        }, "DROP TABLE IF EXISTS model");
    }

    @Test
    public void shouldConnected() throws SQLException {
        assertNotNull(dispatch.openConnection());
    }

    @Test
    public void shouldContains() throws SQLException {
        dispatch.executeQuery(rs -> {
            int counter = 0;
            while (rs.next()) {
                counter++;
            }

            assertEquals(1000, counter);
        }, "SELECT * FROM model");
    }
}
