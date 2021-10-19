package database;

import domain.Product;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductResultSetParser {

    public static List<Product> parseResultSetAsEntities(final ResultSet res) {
        try {
            List<Product> parsed = new ArrayList<>();
            while (res.next()) {
                parsed.add(new Product(res.getString("name"), res.getInt("price")));
            }
            res.close();
            return parsed;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int parseResultSetAsInt(final ResultSet res) {
        try {
            int parsed;
            if (res.next()) {
                parsed = res.getInt(1);
            } else {
                throw new SQLException("ResultSet does not have data to read");
            }
            res.close();
            return parsed;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
