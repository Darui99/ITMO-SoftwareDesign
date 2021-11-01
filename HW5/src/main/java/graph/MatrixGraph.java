package graph;

import drawing.DrawingApi;
import geometry.Vector;

import java.util.List;
import java.util.Scanner;

public class MatrixGraph extends Graph {
    private boolean[][] matrix;

    public MatrixGraph(DrawingApi drawingApi) {
        super(drawingApi);
    }

    @Override
    public void getGraph(Scanner in) {
        numberOfVertices = in.nextInt();
        matrix = new boolean[numberOfVertices][];
        for (int i = 0; i < numberOfVertices; i++) {
            matrix[i] = new boolean[numberOfVertices];
            for (int j = 0; j < numberOfVertices; j++) {
                switch (in.nextInt()) {
                    case 0:
                        matrix[i][j] = false;
                        break;

                    case 1:
                        matrix[i][j] = true;
                        break;

                    default:
                        throw new RuntimeException("Incorrect input");
                }
            }
        }
    }

    @Override
    public void drawGraph() {
        List<Vector> vertices = helper.getVerticesCenters(numberOfVertices);
        for (int i = 0; i < numberOfVertices; i++) {
            for (int j = 0; j < numberOfVertices; j++) {
                if (matrix[i][j]) {
                    drawingApi.drawLine(vertices.get(i), vertices.get(j));
                }
            }
        }
        vertices.forEach(v -> drawingApi.drawCircle(v, helper.calculateRadius(numberOfVertices)));
    }
}
