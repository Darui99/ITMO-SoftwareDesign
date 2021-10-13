package database;

import org.junit.jupiter.api.Test;

import static database.QueriesBuilder.generateCreateQuery;
import static org.junit.jupiter.api.Assertions.*;

class QueriesBuilderTest {

    @Test
    public void test_create1() {
        String actual = generateCreateQuery("PRODUCTS", new Attribute("NAME", "TEXT", false),
                new Attribute("PRICE", "INT", false));

        String expected = "CREATE TABLE IF NOT EXISTS PRODUCTS\n" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "NAME TEXT NOT NULL,\n" +
                "PRICE INT NOT NULL)";

        assertEquals(expected, actual);
    }

    @Test
    public void test_create2_no_attributes() {
        String actual = generateCreateQuery("IDS");

        String expected = "CREATE TABLE IF NOT EXISTS IDS\n" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL)";

        assertEquals(expected, actual);
    }

    @Test
    public void test_create3_one_nullable() {
        String actual = generateCreateQuery("LINKS", new Attribute("SOURCE", "TEXT", true));

        String expected = "CREATE TABLE IF NOT EXISTS LINKS\n" +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
                "SOURCE TEXT NULL)";

        assertEquals(expected, actual);
    }
}