package com.playernguyen.dbcollective.sqlite;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Logger;

import com.playernguyen.dbcollective.Dispatch;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class SQLite {
    private static final String FILE_NAME = "model.sqlite";
    private static Dispatch dispatch;

    @BeforeClass
    public static void setup() throws SQLException, ClassNotFoundException {
        dispatch = new SQLiteDispatch(FILE_NAME);
        dispatch.setVerbose(false);
        dispatch.setLogger(Logger.getLogger("Dispatch"));

        dispatch.executeUpdate((updatedRow) -> {

        }, "CREATE TABLE IF NOT EXISTS model (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, height INTEGER NOT NULL, weight INTEGER NOT NULL)");

        // Create 1000 record samples
        for (int i = 0; i < 1000; i++) {
            dispatch.executeUpdate(updatedRow -> {

            }, "INSERT INTO model (height, weight) VALUES (?, ?)", new Random().nextInt(1000),
                    new Random().nextInt(2000));
        }
    }

    @AfterClass
    public static void teardown() throws SQLException {
        dispatch.executeUpdate(updatedRow -> {
        }, "DROP TABLE IF EXISTS model");
    }

    @Test
    public void shouldConnected() throws SQLException {
        assertNotNull(dispatch.openConnection());
    }

    @Test
    public void shouldContainsAppendedItem() throws SQLException {
        dispatch.executeQuery(rs -> {
            int counter = 0;
            while (rs.next()) {
                counter++;
            }
            assertEquals(1000, counter);
        }, "SELECT * FROM model");
    }

}
