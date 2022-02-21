package actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import api.GoogleAPI;
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

class MasterActorTest {
    private final static Timeout TIMEOUT = Timeout.create(Duration.ofMinutes(1));
    private final ActorSystem actorSystem = ActorSystem.create("MySystem");

    private ActorRef createMaster() {
        return actorSystem.actorOf(Props.create(MasterActor.class,
                actorSystem, List.of(new YandexAPI(), new GoogleAPI(), new YahooAPI())));
    }

    private String getMasterRes(ActorRef masterActor, String query) {
        AtomicReference<String> res = new AtomicReference<>("");
        QueriesHandler.processWithStub(() -> {
            Future<Object> future = Patterns.ask(masterActor, new ActorMessage(query), TIMEOUT);
            try {
                res.set((String) Await.result(future, TIMEOUT.duration()));
            } catch (InterruptedException | TimeoutException e) {
                // pass
            }
        });
        return res.get();
    }

    @Test
    public void test1_default() {
        assertEquals("yandex: yandex-1 yandex-2 yandex-3 yandex-4 yandex-5\n" +
                "google: google-1 google-2 google-3 google-4 google-5\n" +
                "yahoo: yahoo-1 yahoo-2 yahoo-3 yahoo-4 yahoo-5\n",
                getMasterRes(createMaster(), "hello"));
    }

    @Test
    public void test2_no_yandex() {
        assertEquals("google: google-1 google-2 google-3 google-4 google-5\n" +
                        "yahoo: yahoo-1 yahoo-2 yahoo-3 yahoo-4 yahoo-5\n",
                getMasterRes(createMaster(), "xepher"));
    }

    @Test
    public void test3_no_google() {
        assertEquals("yandex: yandex-1 yandex-2 yandex-3 yandex-4 yandex-5\n" +
                        "yahoo: yahoo-1 yahoo-2 yahoo-3 yahoo-4 yahoo-5\n",
                getMasterRes(createMaster(), "yoyo"));
    }

    @Test
    public void test4_no_yahoo() {
        assertEquals("yandex: yandex-1 yandex-2 yandex-3 yandex-4 yandex-5\n" +
                        "google: google-1 google-2 google-3 google-4 google-5\n",
                getMasterRes(createMaster(), "zoo"));
    }
}