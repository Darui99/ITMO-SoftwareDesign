package servlet;

import database.ProductsDatabase;
import html.ResponseBuilder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class AbstractProductServlet extends HttpServlet {
    protected final ProductsDatabase database;
    protected final ResponseBuilder responseBuilder;

    public AbstractProductServlet(final ProductsDatabase database) {
        this.database = database;
        responseBuilder = new ResponseBuilder();
    }

    public void sendResponse(String response, final HttpServletResponse servletResponse) throws IOException {
        if (response != null) {
            servletResponse.getWriter().println(response);
        }
        servletResponse.setContentType("text/html");
        servletResponse.setStatus(HttpServletResponse.SC_OK);
    }
}
