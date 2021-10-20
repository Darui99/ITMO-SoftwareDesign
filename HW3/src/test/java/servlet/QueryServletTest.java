package servlet;

import database.ProductsDatabase;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

class QueryServletTest {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final int PORT = 8082;
    private static final Server SERVER = new Server(PORT);
    private static final String SERVLET_PATH = "/query";

    @BeforeAll
    public static void setUp() throws Exception {
        File file = File.createTempFile("tmp", "db");
        file.deleteOnExit();

        ProductsDatabase database = new ProductsDatabase(file.getAbsolutePath());
        database.create();

        database.insert("Apple", 30);
        database.insert("Orange", 40);
        database.insert("Lemon", 50);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        SERVER.setHandler(context);

        context.addServlet(new ServletHolder(new QueryServlet(database)), SERVLET_PATH);
        SERVER.start();
    }

    @AfterAll
    public static void stopServer() throws Exception {
        SERVER.stop();
    }

    private String wrapToBody(String content) {
        return "<html><body>\n" + content + "\n</body></html>\r\n";
    }

    private HttpRequest createRequest(final String command) {
        HttpRequest request = HttpRequest.newBuilder(
                        URI.create("http://localhost:" + PORT + SERVLET_PATH + "?command=" + command))
                .header("accept", "text/html").build();
        return request;
    }

    @Test
    public void test_max() throws IOException, InterruptedException {
        HttpResponse<String> response = CLIENT.send(createRequest("max"), HttpResponse.BodyHandlers.ofString());
        assertEquals( wrapToBody("Lemon\t50 </br>"), response.body());
    }

    @Test
    public void test_min() throws IOException, InterruptedException {
        HttpResponse<String> response = CLIENT.send(createRequest("min"), HttpResponse.BodyHandlers.ofString());
        assertEquals( wrapToBody("Apple\t30 </br>"), response.body());
    }

    @Test
    public void test_sum() throws IOException, InterruptedException {
        HttpResponse<String> response = CLIENT.send(createRequest("sum"), HttpResponse.BodyHandlers.ofString());
        assertEquals( wrapToBody("Summary price: 120 </br>"), response.body());
    }

    @Test
    public void test_count() throws IOException, InterruptedException {
        HttpResponse<String> response = CLIENT.send(createRequest("count"), HttpResponse.BodyHandlers.ofString());
        assertEquals( wrapToBody("Number of products: 3 </br>"), response.body());
    }
}