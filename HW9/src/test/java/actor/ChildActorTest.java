package actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import api.GoogleAPI;
import api.ResponseData;
import api.YahooAPI;
import api.YandexAPI;
import org.junit.jupiter.api.Test;
import scala.concurrent.Await;
import scala.concurrent.Future;
import server.QueriesHandler;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class ChildActorTest {
    private final static Timeout TIMEOUT = Timeout.create(Duration.ofMinutes(1));
    private final ActorSystem actorSystem = ActorSystem.create("MySystem");

    private ResponseData getChildRes(ActorRef childActor, String query) {
        AtomicReference<ResponseData> res = new AtomicReference<>(null);
        QueriesHandler.processWithStub(() -> {
            Future<Object> future = Patterns.ask(childActor, new ActorMessage(query), TIMEOUT);
            try {
                res.set((ResponseData) Await.result(future, TIMEOUT.duration()));
            } catch (InterruptedException | TimeoutException e) {
                // pass
            }
        });
        return res.get();
    }

    @Test
    public void test1_check_yandex() {
        ActorRef child = actorSystem.actorOf(Props.create(ChildActor.class, new YandexAPI()));
        ResponseData res;

        res = getChildRes(child, "test1");
        assertEquals("yandex", res.getServiceName());
        assertEquals(List.of("yandex-1", "yandex-2", "yandex-3", "yandex-4", "yandex-5"), res.getTopResponses());

        res = getChildRes(child, "xepher");
        assertEquals("yandex", res.getServiceName());
        assertEquals(List.of("yandex-1", "yandex-2", "yandex-3", "yandex-4", "yandex-5"), res.getTopResponses());
    }

    @Test
    public void test2_check_google() {
        ActorRef child = actorSystem.actorOf(Props.create(ChildActor.class, new GoogleAPI()));
        ResponseData res;

        res = getChildRes(child, "test2");
        assertEquals("google", res.getServiceName());
        assertEquals(List.of("google-1", "google-2", "google-3", "google-4", "google-5"), res.getTopResponses());

        res = getChildRes(child, "yoyo");
        assertEquals("google", res.getServiceName());
        assertEquals(List.of("google-1", "google-2", "google-3", "google-4", "google-5"), res.getTopResponses());
    }

    @Test
    public void test3_check_yahoo() {
        ActorRef child = actorSystem.actorOf(Props.create(ChildActor.class, new YahooAPI()));
        ResponseData res;

        res = getChildRes(child, "test3");
        assertEquals("yahoo", res.getServiceName());
        assertEquals(List.of("yahoo-1", "yahoo-2", "yahoo-3", "yahoo-4", "yahoo-5"), res.getTopResponses());

        res = getChildRes(child, "zoo");
        assertEquals("yahoo", res.getServiceName());
        assertEquals(List.of("yahoo-1", "yahoo-2", "yahoo-3", "yahoo-4", "yahoo-5"), res.getTopResponses());
    }
}