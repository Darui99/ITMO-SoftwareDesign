package actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import akka.util.Timeout;
import api.ResponseData;
import api.SearchServiceAPI;
import scala.concurrent.Await;
import scala.concurrent.Future;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class MasterActor extends AbstractActor {
    private final ActorSystem actorSystem;
    private final List<SearchServiceAPI> apis;
    private final Timeout TIMEOUT = Timeout.create(Duration.ofSeconds(3));

    public MasterActor(ActorSystem actorSystem, List<SearchServiceAPI> apis) {
        this.actorSystem = actorSystem;
        this.apis = apis;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(ActorMessage.class, msg -> sender().tell(getTopResponses(msg), self())).build();
    }

    private String getTopResponses(ActorMessage message) {
        String query = message.getMessage();

        List<ActorRef> childActors = new ArrayList<>();
        for (SearchServiceAPI api : apis) {
            childActors.add(actorSystem.actorOf(Props.create(ChildActor.class, api)));
        }

        List<Future<Object>> futures = new ArrayList<>();
        for (ActorRef child : childActors) {
            futures.add(Patterns.ask(child, new ActorMessage(query), TIMEOUT));
        }

        StringBuilder result = new StringBuilder();
        for (Future<Object> future : futures) {
            try {
                ResponseData data = (ResponseData) Await.result(future, TIMEOUT.duration());
                result.append(data.getServiceName()).append(": ")
                        .append(String.join(" ", data.getTopResponses())).append("\n");
            } catch (InterruptedException | TimeoutException e) {
                // pass
            }
        }

        for (ActorRef child : childActors) {
            actorSystem.stop(child);
        }

        return result.toString();
    }
}
