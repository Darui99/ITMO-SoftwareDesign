package database;

import domain.Product;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductResultSetParser {

    public static List<Product> parseResultSet(final ResultSet res) throws SQLException {
        List<Product> parsed = new ArrayList<>();
        while (res.next()) {
            parsed.add(new Product(res.getString("name"), res.getInt("price")));
        }
        res.close();
        return parsed;
    }
}
