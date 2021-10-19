package sql;

import sql.Attribute;

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

    public static String generateInsertQuery(final String name, Attribute... attributes) {
        StringBuilder res = new StringBuilder();
        res.append("INSERT INTO ").append(name);
        res.append("(");
        for (int i = 0; i < attributes.length; i++) {
            res.append(attributes[i].getName());
            if (i != attributes.length - 1) {
                res.append(", ");
            }
        }
        res.append(")").append(" VALUES ");
        res.append("(");
        for (int i = 0; i < attributes.length; i++) {
            if (attributes[i].getType().equals("TEXT")) {
                res.append("\"").append(attributes[i].getValue()).append("\"");
            } else {
                res.append(attributes[i].getValue());
            }
            if (i != attributes.length - 1) {
                res.append(", ");
            }
        }
        res.append(")");
        return res.toString();
    }

    public static String generateGetAllQuery(final String name) {
        return "SELECT * FROM " + name;
    }

    public static String generateGetMaxQuery(final String name) {
        return "SELECT * FROM " + name + " ORDER BY PRICE DESC LIMIT 1";
    }

    public static String generateGetMinQuery(final String name) {
        return "SELECT * FROM " + name + " ORDER BY PRICE LIMIT 1";
    }

    public static String generateGetSumQuery(final String name, final String attribute) {
        return "SELECT SUM(" + attribute + ") FROM " + name;
    }

    public static String generateGetCountQuery(final String name) {
        return "SELECT COUNT(*) FROM " + name;
    }
}
