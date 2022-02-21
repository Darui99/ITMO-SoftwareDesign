package actor;

import akka.actor.AbstractActor;
import api.ResponseData;
import api.SearchServiceAPI;

public class ChildActor extends AbstractActor {
    private final SearchServiceAPI api;

    public ChildActor(SearchServiceAPI api) {
        this.api = api;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(ActorMessage.class, msg -> sender().tell(getDataViaAPI(msg), self())).build();
    }

    private ResponseData getDataViaAPI(ActorMessage message) {
        return new ResponseData(api.getServiceName(), api.requestTop(message.getMessage()));
    }
}
