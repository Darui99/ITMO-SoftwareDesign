import actor.ActorMessage;
import actor.MasterActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import api.GoogleAPI;
import api.YahooAPI;
import api.YandexAPI;
import scala.concurrent.Await;
import scala.concurrent.Future;
import server.QueriesHandler;


import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class SearchAggregator {
    private final static Timeout TIMEOUT = Timeout.create(Duration.ofMinutes(1));

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Exactly one argument required");
            return;
        }

        final String query = args[0];
        QueriesHandler.processWithStub(() -> {
            ActorSystem actorSystem = ActorSystem.create("MySystem");
            ActorRef masterActor = actorSystem.actorOf(Props.create(MasterActor.class,
                    actorSystem, List.of(new YandexAPI(), new GoogleAPI(), new YahooAPI())));
            Future<Object> future = Patterns.ask(masterActor, new ActorMessage(query), TIMEOUT);
            try {
                String result = (String) Await.result(future, TIMEOUT.duration());
                System.out.println(result);
            } catch (InterruptedException | TimeoutException e) {
                System.out.println("Error occurred: " + e.getMessage());
            }
            actorSystem.stop(masterActor);
            actorSystem.terminate();
        });
    }
}
