package database;

public class Attribute {
    private final String name;
    private final String type;
    private final boolean nullable;
    private String value;

    public Attribute(final String name, final String type, final boolean nullable) {
        this.name = name;
        this.type = type;
        this.nullable = nullable;
    }

    public String getSummary() {
        String res = name + " " + type + " ";
        if (nullable) {
            res += "NULL";
        } else {
            res += "NOT NULL";
        }
        return res;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
