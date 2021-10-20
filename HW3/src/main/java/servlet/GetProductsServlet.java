package servlet;

import database.ProductsDatabase;
import domain.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetProductsServlet extends AbstractProductServlet {

    public GetProductsServlet(ProductsDatabase database) {
        super(database);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            responseBuilder.addRowsFromList(Product.convertList(database.getAll()));
            sendResponse(responseBuilder.getAllAndFlush(), response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
