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


// @Ignore // Remove this line to test with MySQL
public class MySQL {

    private static MySQLDispatch dispatch;

    @BeforeClass
    public static void setup() throws ClassNotFoundException, SQLException {

        dispatch = new MySQLDispatch();
        dispatch.setVerbose(true);
        dispatch.setLogger(Logger.getLogger(MySQL.class.getName()));

        dispatch.setHost("localhost");
        dispatch.setPort("3306");
        dispatch.setUsername(System.getenv("DB_USER"));
        dispatch.setPassword(System.getenv("DB_PASSWORD"));
        dispatch.setDatabase(System.getenv("DB_DATABASE"));
        dispatch.setOptions(System.getenv("DB_OPTIONS"));

        dispatch.execute(callback -> {
        }, "CREATE TABLE IF NOT EXISTS model (id int not null primary key auto_increment, height int not null, weight int not null)");

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            dispatch.execute((updatedRows) -> {
            }, "INSERT INTO model (height, weight) VALUES (?, ?) ", new Random().nextInt(1000),
                    new Random().nextInt((1000)));
            
        }
        long end = System.currentTimeMillis();
        System.out.println("Inserted in " + (end - start) + "ms");
    }

    @AfterClass
    public static void teardown() throws SQLException {
        dispatch.execute(callback -> {
        }, "DROP TABLE IF EXISTS model");
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
