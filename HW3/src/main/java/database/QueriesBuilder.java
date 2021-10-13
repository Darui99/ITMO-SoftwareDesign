package database;

public class QueriesBuilder {
    public static String generateCreateQuery(final String name, Attribute... attributes) {
        StringBuilder res = new StringBuilder();
        res.append("CREATE TABLE IF NOT EXISTS ").append(name);
        res.append("\n(").append("ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL");
        for (Attribute attribute : attributes) {
            res.append(",\n").append(attribute.getSummary());
        }
        res.append(")");
        return res.toString();
    }
}
