package servlet;

import database.ProductsDatabase;
import domain.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class QueryServlet extends AbstractProductServlet {
    public QueryServlet(ProductsDatabase database) {
        super(database);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String command = request.getParameter("command");

        try {
            switch (command) {
                case "max":
                    responseBuilder.addRowsFromList(Product.convertList(database.getMax()));
                    break;

                case "min":
                    responseBuilder.addRowsFromList(Product.convertList(database.getMin()));
                    break;

                case "sum":
                    responseBuilder.addLine("Summary price: " + database.getSumByPrice());
                    break;

                case "count":
                    responseBuilder.addLine("Number of products: " + database.getCount());
                    break;

                default:
                    throw new RuntimeException("Unknown command in query");
            }
            sendResponse(responseBuilder.getAllAndFlush(), response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
