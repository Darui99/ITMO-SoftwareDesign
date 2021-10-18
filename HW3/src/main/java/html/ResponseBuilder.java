package html;

import database.Attribute;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResponseBuilder {
    private final Attribute[] attributes;
    StringBuilder response;

    public ResponseBuilder(final Attribute[] attributes) {
        this.attributes = attributes;
        this.response = new StringBuilder();
    }

    public void addHeader(final String header) {
        response.append("<h1>").append(header).append("</h1>").append("\n");
    }

    public void parseAndAddResultSet(final ResultSet res) throws SQLException {
        while (res.next()) {
            for (Attribute attribute : attributes) {
                switch (attribute.getType()) {
                    case "TEXT":
                        response.append(res.getString(attribute.getName())).append("\t");
                        break;

                    case "INT":
                        response.append(res.getInt(attribute.getName())).append("\t");
                        break;

                    default:
                        throw new RuntimeException("Unknown attribute type");
                }
                response.append("</br>").append("\n");
            }
        }
    }

    private void addResponse(final HttpServletResponse servletResponse) throws IOException {
        servletResponse.getWriter().println("<html><body>");
        servletResponse.getWriter().println(response.toString());
        response.setLength(0);
        servletResponse.getWriter().println("</body></html>");
    }

    private void sendResponse(final HttpServletResponse servletResponse) throws IOException {
        addResponse(servletResponse);
        servletResponse.setContentType("text/html");
        servletResponse.setStatus(HttpServletResponse.SC_OK);
        servletResponse.getWriter().println("OK");
    }
}
