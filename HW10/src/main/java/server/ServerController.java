package server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.Currency;
import rx.Observable;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import mongo.MongoDBController;

public class ServerController {
    private final MongoDBController mongo;

    public ServerController(MongoDBController mongo) {
        this.mongo = mongo;
    }

    public Observable<String> process(HttpServerRequest<ByteBuf> request) {
        Observable<String> result;
        try {
            final String path = request.getDecodedPath().substring(1);
            switch (path) {
                case "register_user":
                    result = registerUser(request);
                    break;
                case "show_user":
                    result = showUser(request);
                    break;
                case "register_product":
                    result = registerProduct(request);
                    break;
                case "show_product":
                    result = showProduct(request);
                    break;
                default:
                    throw new RuntimeException("Incorrect request");
            }
        } catch (final Exception e) {
            result = Observable.just("Error while parsing request: " + e.getMessage());
        }
        return result;
    }

    private Observable<String> registerUser(HttpServerRequest<ByteBuf> request) {
        final List<String> id = request.getQueryParameters().get("id");
        final List<String> name = request.getQueryParameters().get("name");
        final List<String> currency = request.getQueryParameters().get("currency");
        return mongo.registerUser(Integer.parseInt(id.get(0)), name.get(0), Currency.getFromString(currency.get(0)));
    }

    private Observable<String> showUser(HttpServerRequest<ByteBuf> request) {
        final List<String> id = request.getQueryParameters().get("id");
        return mongo.showUser(Integer.parseInt(id.get(0)));
    }

    private Observable<String> registerProduct(HttpServerRequest<ByteBuf> request) {
        final List<String> id = request.getQueryParameters().get("id");
        final List<String> name = request.getQueryParameters().get("name");
        final Map<Currency, Double> prices = new HashMap<>();
        for (Currency currency : Currency.values()) {
            final List<String> cur = request.getQueryParameters().get(currency.toString());
            prices.put(currency, Double.parseDouble(cur.get(0)));
        }
        return mongo.registerProduct(Integer.parseInt(id.get(0)), name.get(0), prices);
    }

    private Observable<String> showProduct(HttpServerRequest<ByteBuf> request) {
        final List<String> productId = request.getQueryParameters().get("productId");
        final List<String> userId = request.getQueryParameters().get("userId");
        return mongo.showProduct(Integer.parseInt(productId.get(0)), Integer.parseInt(userId.get(0)));
    }

}
