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

import org.junit.*;

public class MySQLHikari {
    private static MySQLHikariDispatch dispatch;

    @BeforeClass
    public static void setup() throws ClassNotFoundException, SQLException {

        dispatch = new MySQLHikariDispatch(
                "localhost",
                "3306",
                System.getenv("DB_USER"),
                System.getenv("DB_PASSWORD"),
                System.getenv("DB_DATABASE"),
                System.getenv("DB_OPTIONS")
        );
        dispatch.setVerbose(true);
        dispatch.setLogger(Logger.getLogger(MySQL.class.getName()));

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
        dispatch.execute(callback -> { }, "DROP TABLE IF EXISTS model");
    }

    @Test
    public void shouldConnected() throws SQLException {
        dispatch.openConnection(Assert::assertNotNull);
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
