package database;

import domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductsDatabaseTest {
    private ProductsDatabase database;

    @BeforeEach
    void setUp() throws SQLException, IOException {
        File tempFile = File.createTempFile("tmp", "db");
        tempFile.deleteOnExit();

        database = new ProductsDatabase(tempFile.getAbsolutePath());
        database.create();

        database.insert("Apple", 30);
        database.insert("Orange", 40);
        database.insert("Lemon", 50);
    }

    @Test
    public void test_get_all() throws SQLException {
        assertEquals(database.getAll(), List.of(new Product("Apple", 30),
                new Product("Orange", 40), new Product("Lemon", 50)));
    }

    @Test
    public void test_get_max() throws SQLException {
        assertEquals(database.getMax(), List.of(new Product("Lemon", 50)));
    }

    @Test
    public void test_get_min() throws SQLException {
        assertEquals(database.getMin(), List.of(new Product("Apple", 30)));
    }

    @Test
    public void test_get_sum() throws SQLException {
        assertEquals(database.getSumByPrice(), 120);
    }

    @Test
    public void test_get_count() throws SQLException {
        assertEquals(database.getCount(), 3);
    }
}