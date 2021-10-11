package logic;

import static com.xebialabs.restito.builder.stub.StubHttp.whenHttp;
import static com.xebialabs.restito.semantics.Action.stringContent;
import static com.xebialabs.restito.semantics.Condition.*;
import static org.junit.jupiter.api.Assertions.*;

import com.xebialabs.restito.server.StubServer;
import org.glassfish.grizzly.http.Method;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.function.Consumer;

import static logic.Common.generateTestSample;

class NewsfeedClientWithStubTest {

    private final NewsfeedClient client;

    private final static int PORT = 13737;

    NewsfeedClientWithStubTest() throws IOException {
        client = new NewsfeedClient("http", "localhost", PORT);
    }

    @Test
    public void test1() {
        testWithStub("#autumn", 60L * 60L * 3L);
    }

    @Test
    public void test2() {
        testWithStub("#spring", 60L * 60L * 5L);
    }

    @Test
    public void test3() {
        testWithStub("#winter", 60L * 60L * 10L);
    }


    private void testWithStub(String hashtag, long gap) {
        long endTime = System.currentTimeMillis() / 1000L;
        long startTime = endTime - gap;

        withStubServer(s -> {
            int expected = (int) generateTestSample().stream()
                    .filter(p -> p[0].equals(hashtag) && startTime <= Long.parseLong(p[1]) && Long.parseLong(p[1]) <= endTime)
                    .count();

            whenHttp(s)
                    .match(method(Method.GET), startsWithUri("/method/newsfeed.search"))
                    .then(stringContent(generateResponse(expected)));

            int actual;
            try {
                actual = client.getPostsCount(hashtag, startTime, endTime);
            } catch (IOException e) {
                actual = -1;
            }
            assertEquals(actual, expected);
        });
    }

    private String generateResponse(int count) {
        return "{\"response\":{\"items\":[],\"count\":" + count + ",\"total_count\":" + count + "}}";
    }

    private void withStubServer(Consumer<StubServer> callback) {
        StubServer stubServer = null;
        try {
            stubServer = new StubServer(PORT).run();
            callback.accept(stubServer);
        } finally {
            if (stubServer != null) {
                stubServer.stop();
            }
        }
    }
}