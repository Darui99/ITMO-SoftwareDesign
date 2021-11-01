import drawing.AwtDrawing;
import drawing.DrawingApi;
import drawing.JavaFxDrawing;
import graph.Graph;
import graph.ListGraph;
import graph.MatrixGraph;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args == null) {
            System.out.println("args is null");
            return;
        }
        if (args.length != 2) {
            System.out.println("args.size have to be 2 (drawing api and graph's adjacency type");
        }
        DrawingApi drawingApi;
        switch (args[0]) {
            case "awt":
                drawingApi = new AwtDrawing();
                break;

            case "javafx":
                JavaFxDrawing.run();
                drawingApi = new JavaFxDrawing();
                break;

            default:
                throw new RuntimeException("Unknown drawing api");
        }

        Graph graph;
        switch (args[1]) {
            case "matrix":
                graph = new MatrixGraph(drawingApi);
                break;

            case "list":
                graph = new ListGraph(drawingApi);
                break;

            default:
                throw new RuntimeException("Unknown graph's adjacency type");
        }

        graph.getGraph(new Scanner(System.in));
        graph.drawGraph();
    }
}
