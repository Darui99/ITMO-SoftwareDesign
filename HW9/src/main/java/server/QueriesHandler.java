package server;

import com.xebialabs.restito.server.StubServer;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.glassfish.grizzly.http.Method;

import java.util.Objects;
import java.util.function.Consumer;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.stringContent;
import static com.xebialabs.restito.semantics.Condition.*;

public class QueriesHandler {
    public static void processWithStub(Runnable task) {
        withStubServer(s -> {
            handleQueryWithStub(s, "yandex");
            handleQueryWithStub(s, "google");
            handleQueryWithStub(s, "yahoo");

            task.run();
        });
    }

    private static void handleQueryWithStub(StubServer s, String service) {
        whenHttp(s)
                .match(method(Method.GET), startsWithUri("/" + service), custom(c -> {
                    String q = c.getParameters().get("q")[0];
                    if (Objects.equals(service, "yandex") && q.charAt(0) == 'x'
                    || Objects.equals(service, "google") && q.charAt(0) == 'y'
                    || Objects.equals(service, "yahoo") && q.charAt(0) == 'z') {
                        lag();
                    }
                    return true;
                }))
                .then(stringContent(generateResponse(service)));
    }

    private static void lag() {
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // pass
            }
            //System.out.println("lag " + (i + 1));
        }
    }

    private static String generateResponse(String searchService) {
        JSONArray arr = new JSONArray();
        for (int i = 1; i <= 10; i++) {
            JSONObject cur = new JSONObject();
            cur.put(Integer.toString(i), searchService + "-" + i);
            arr.add(cur);
        }
        return arr.toString();
    }

    private static void withStubServer(Consumer<StubServer> callback) {
        StubServer stubServer = null;
        try {
            stubServer = new StubServer(13737).run();
            callback.accept(stubServer);
        } finally {
            if (stubServer != null) {
                stubServer.stop();
            }
        }
    }
}
