package html;

import domain.DatabaseEntity;

import java.util.List;

public class ResponseBuilder {
    private final StringBuilder response;

    public ResponseBuilder() {
        this.response = new StringBuilder();
    }

    public void addHeader(final String header, int level) {
        response.append("<h").append(level).append(">");
        response.append(header);
        response.append("</h").append(level).append(">");
        response.append("\n");
    }

    public void addRows(List<DatabaseEntity> list) {
        for (DatabaseEntity entity : list) {
            response.append(entity.getRowString());
            response.append("</br>").append("\n");
        }
    }

    public String getAllAndFlush() {
        String res = "<html><body>\n" + response + "</body></html>";
        response.setLength(0);
        return res;
    }
}
