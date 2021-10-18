package database;

import domain.Product;
import sql.Attribute;

import java.util.List;
import java.sql.*;
import java.util.Arrays;

import static sql.QueriesBuilder.*;

public class ProductsDatabase {
    private static final String SCHEME = "jdbc:sqlite";
    public static final String NAME = "PRODUCTS";
    private static final Attribute[] ATTRIBUTES = { new Attribute("NAME", "TEXT", false),
            new Attribute("PRICE", "INT", false) };

    private final String dbFile;

    public ProductsDatabase(final String dbFile) {
        this.dbFile = dbFile;
    }

    private void executeUpdate(final String sql) throws SQLException {
        try (Connection c = DriverManager.getConnection(SCHEME + ":" + dbFile)) {
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }
    }

    private List<Product> executeQuery(final String sql) throws SQLException {
        try (Connection c = DriverManager.getConnection(SCHEME + ":" + dbFile)) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<Product> parsed = ProductResultSetParser.parseResultSet(rs);
            stmt.close();
            return parsed;
        }
    }

    public void create() throws SQLException {
        executeUpdate(generateCreateQuery(NAME, ATTRIBUTES));
    }

    public void insert(final String name, final int price) throws SQLException {
        Attribute[] attributes = Arrays.copyOf(ATTRIBUTES, ATTRIBUTES.length);
        attributes[0].setValue(name);
        attributes[1].setValue(Integer.toString(price));
        executeUpdate(generateInsertQuery(NAME, attributes));
    }

    public List<Product> getAll() throws SQLException {
        return executeQuery(generateGetAllQuery(NAME));
    }
}
