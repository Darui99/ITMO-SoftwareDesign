package html;

import domain.DatabaseEntity;
import domain.Product;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ResponseBuilderTest {
    private ResponseBuilder responseBuilder = new ResponseBuilder();

    private String wrapToBody(String content) {
        return "<html><body>\n" + content + "\n</body></html>";
    }

    @Test
    public void test_header() {
        responseBuilder.addHeader("OK", 3);
        assertEquals(responseBuilder.getAllAndFlush(), wrapToBody("<h3>OK</h3>"));
    }

    @Test
    public void test_line() {
        responseBuilder.addLine("Hello, world!");
        assertEquals(responseBuilder.getAllAndFlush(), wrapToBody("Hello, world! </br>"));
    }

    @Test
    public void test_list() {
        List<DatabaseEntity> list = new ArrayList<>();
        list.add(new Product("Apple", 30));
        list.add(new Product("Orange", 40));
        list.add(new Product("Lemon", 50));
        responseBuilder.addRowsFromList(list);
        assertEquals(responseBuilder.getAllAndFlush(), wrapToBody("Apple\t30 </br>\nOrange\t40 </br>\nLemon\t50 </br>"));
    }

    @Test
    public void test_all() {
        responseBuilder.addHeader("OK", 3);
        responseBuilder.addLine("Hello, world!");
        List<DatabaseEntity> list = new ArrayList<>();
        list.add(new Product("Apple", 30));
        list.add(new Product("Orange", 40));
        list.add(new Product("Lemon", 50));
        responseBuilder.addRowsFromList(list);

        String expected = "<h3>OK</h3>";
        expected += "\n" + "Hello, world! </br>" + "\n";
        expected += "Apple\t30 </br>\nOrange\t40 </br>\nLemon\t50 </br>";
        assertEquals(responseBuilder.getAllAndFlush(), wrapToBody(expected));
    }
}