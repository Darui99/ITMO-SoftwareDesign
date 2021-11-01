package graph;

import drawing.DrawingApi;
import geometry.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListGraph extends Graph {
    List<Edge> list;

    public ListGraph(DrawingApi drawingApi) {
        super(drawingApi);
    }

    @Override
    public void getGraph(Scanner in) {
        int numberOfEdges = in.nextInt();
        numberOfVertices = 0;
        list = new ArrayList<>();
        for (int i = 0; i < numberOfEdges; i++) {
            int firstVer = in.nextInt();
            int secondVer = in.nextInt();
            numberOfVertices = Math.max(numberOfVertices, Math.max(firstVer, secondVer));
            list.add(new Edge(firstVer - 1, secondVer - 1));
        }
    }

    @Override
    public void drawGraph() {
        List<Vector> vertices = helper.getVerticesCenters(numberOfVertices);
        for (Edge edge : list) {
            drawingApi.drawLine(vertices.get(edge.firstVer), vertices.get(edge.secondVer));
        }
        vertices.forEach(v -> drawingApi.drawCircle(v, helper.calculateRadius(numberOfVertices)));
    }
}
