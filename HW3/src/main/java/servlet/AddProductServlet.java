package servlet;

import database.ProductsDatabase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddProductServlet extends AbstractProductServlet {

    public AddProductServlet(ProductsDatabase database) {
        super(database);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        int price = Integer.parseInt(request.getParameter("price"));

        try {
            database.insert(name, price);
            responseBuilder.addHeader("OK", 3);
            sendResponse(responseBuilder.getAllAndFlush(), response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
