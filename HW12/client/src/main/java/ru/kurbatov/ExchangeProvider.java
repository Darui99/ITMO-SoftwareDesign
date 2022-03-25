package ru.kurbatov;

import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;

import static ru.kurbatov.URLHelper.sendThenReceive;

public class ExchangeProvider {
    private final String host;

    public ExchangeProvider(String host) {
        this.host = host;
    }

    public int getStockPrice(String stock) {
        String url = UriComponentsBuilder.newInstance().scheme("http").host(host).path("/get_price")
                .queryParam("name", stock).build().toUriString();
        return Integer.parseInt(sendThenReceive(url));
    }

    public int buyStock(String stock, int number) {
        String url = UriComponentsBuilder.newInstance().scheme("http").host(host).path("/buy")
                .queryParam("name", stock).queryParam("number", number).build().toUriString();
        String receive = sendThenReceive(url);
        try {
            return Integer.parseInt(receive);
        } catch (Exception e) {
            throw new RuntimeException(receive);
        }
    }

    public int sellStock(String stock, int number) {
        String url = UriComponentsBuilder.newInstance().scheme("http").host(host).path("/sell")
                .queryParam("name", stock).queryParam("number", number).build().toUriString();
        return Integer.parseInt(sendThenReceive(url));
    }
}
