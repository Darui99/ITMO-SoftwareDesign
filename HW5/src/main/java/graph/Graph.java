package graph;

import drawing.DrawingApi;
import geometry.Helper;
import java.util.Scanner;

public abstract class Graph {
    protected int numberOfVertices;
    protected DrawingApi drawingApi;
    protected Helper helper;

    public Graph(DrawingApi drawingApi) {
        this.drawingApi = drawingApi;
        helper = new Helper(drawingApi.getDrawingAreaWidth(), drawingApi.getDrawingAreaHeight());
    }

    public abstract void getGraph(Scanner in);

    public abstract void drawGraph();
}
