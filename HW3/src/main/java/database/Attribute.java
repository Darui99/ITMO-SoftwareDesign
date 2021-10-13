package database;

public class Attribute {
    private final String name;
    private final String type;
    private final boolean nullable;

    public Attribute(final String name, final String type, final boolean nullable) {
        this.name = name;
        this.type = type;
        this.nullable = nullable;
    }

    public String getSummary() {
        String res = name + " " + type + " ";
        if (nullable) {
            res += "null";
        } else {
            res += "not null";
        }
        return res;
    }
}
