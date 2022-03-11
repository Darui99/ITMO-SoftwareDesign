package domain;

public enum Currency {
    RUB, USD, EUR;

    public static Currency getFromString(String name) {
        switch (name) {
            case "RUB":
                return Currency.RUB;
            case "USD":
                return Currency.USD;
            case "EUR":
                return Currency.EUR;
            default:
                throw new RuntimeException("Unknown currency");
        }
    }
}
