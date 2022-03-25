package ru.kurbatov;

import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ExchangeProvider {
    private final String host;

    public ExchangeProvider(String host) {
        this.host = host;
    }

    private String sendThenReceive(String strUrl) {
        URL url;
        try {
            url = new URL(strUrl);
        } catch (MalformedURLException e) {
            throw new UncheckedIOException(e);
        }
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
            StringBuilder buffer = new StringBuilder();
            String inputLine;

            while((inputLine = in.readLine()) != null) {
                buffer.append(inputLine);
                //buffer.append("\n");
            }
            return buffer.toString();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
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
