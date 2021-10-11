package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class UrlReader {
    public static String readAsText(String sourceUrl) throws IOException {
        URL url = new URL(sourceUrl);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
            StringBuilder buffer = new StringBuilder();
            String inputLine;

            while((inputLine = in.readLine()) != null) {
                buffer.append(inputLine);
                buffer.append("\n");
            }
            return buffer.toString();
        }
    }
}
