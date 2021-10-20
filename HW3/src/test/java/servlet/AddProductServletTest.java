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
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class AddProductServletTest {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final int PORT = 8082;
    private static final Server SERVER = new Server(PORT);
    private static final String SERVLET_PATH = "/add-product";

    @BeforeAll
    public static void setUp() throws Exception {
        File file = File.createTempFile("tmp", "db");
        file.deleteOnExit();

        ProductsDatabase database = new ProductsDatabase(file.getAbsolutePath());
        database.create();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        SERVER.setHandler(context);

        context.addServlet(new ServletHolder(new AddProductServlet(database)), SERVLET_PATH);
        SERVER.start();
    }

    @AfterAll
    public static void stopServer() throws Exception {
        SERVER.stop();
    }

    private String wrapToBody(String content) {
        return "<html><body>\n" + content + "\n</body></html>\r\n";
    }

    @Test
    public void test_add() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(
                        URI.create("http://localhost:" + PORT + SERVLET_PATH + "?name=apple&price=30"))
                .header("accept", "text/html").build();

        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(wrapToBody("<h3>OK</h3>"), response.body());
    }
}