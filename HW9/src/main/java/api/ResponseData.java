package api;

import java.util.List;

public class ResponseData {
    private final String serviceName;
    private final List<String> topResponses;

    public ResponseData(String serviceName, List<String> topResponses) {
        this.serviceName = serviceName;
        this.topResponses = topResponses;
    }

    public String getServiceName() {
        return serviceName;
    }

    public List<String> getTopResponses() {
        return topResponses;
    }
}
