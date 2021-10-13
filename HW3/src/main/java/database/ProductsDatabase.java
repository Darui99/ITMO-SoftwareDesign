package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static database.QueriesBuilder.generateCreateQuery;

public class ProductsDatabase {
    private static final String SCHEME = "jdbc:sqlite";
    public static final String NAME = "PRODUCTS";
    public static final Attribute[] ATTRIBUTES = { new Attribute("NAME", "TEXT", false),
            new Attribute("PRICE", "INT", false) };

    private final String dbFile;

    public ProductsDatabase(final String dbFile) {
        this.dbFile = dbFile;
    }

    public void create() throws SQLException {
        try (Connection c = DriverManager.getConnection(SCHEME + ":" + dbFile)) {
            String sql = generateCreateQuery(NAME, ATTRIBUTES);
            Statement stmt = c.createStatement();

            stmt.executeUpdate(sql);
            stmt.close();
        }
    }
}
