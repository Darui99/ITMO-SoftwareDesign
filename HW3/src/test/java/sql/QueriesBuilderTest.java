package sql;

import org.junit.jupiter.api.Test;
import sql.Attribute;

import static org.junit.jupiter.api.Assertions.*;
import static sql.QueriesBuilder.*;

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

    @Test
    public void test_get_all() {
        String actual = generateGetAllQuery("PRODUCTS");
        String expected = "SELECT * FROM PRODUCTS";
        assertEquals(expected, actual);
    }

    @Test
    public void test_get_max() {
        String actual = generateGetMaxQuery("PRODUCTS");
        String expected = "SELECT * FROM PRODUCTS ORDER BY PRICE DESC LIMIT 1";
        assertEquals(expected, actual);
    }

    @Test
    public void test_get_min() {
        String actual = generateGetMinQuery("PRODUCTS");
        String expected = "SELECT * FROM PRODUCTS ORDER BY PRICE LIMIT 1";
        assertEquals(expected, actual);
    }

    @Test
    public void test_get_sum() {
        String actual = generateGetSumQuery("PRODUCTS", "PRICE");
        String expected = "SELECT SUM(PRICE) FROM PRODUCTS";
        assertEquals(expected, actual);
    }

    @Test
    public void test_get_count() {
        String actual = generateGetCountQuery("PRODUCTS");
        String expected = "SELECT COUNT(*) FROM PRODUCTS";
        assertEquals(expected, actual);
    }
}