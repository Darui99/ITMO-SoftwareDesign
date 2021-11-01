package drawing;

import geometry.Vector;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class JavaFxDrawing extends Application implements DrawingApi  {
    private static GraphicsContext context;
    private final int width = 700;
    private final int height = 700;
    private final double thickness = 7.0d;

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Canvas canvas = new Canvas(width, height);
        context = canvas.getGraphicsContext2D();
        context.setLineWidth(thickness);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void run() {
        new Thread(JavaFxDrawing::launch).start();
    }


    @Override
    public long getDrawingAreaWidth() {
        return width;
    }

    @Override
    public long getDrawingAreaHeight() {
        return height;
    }

    @Override
    public void drawCircle(Vector center, double radius) {
        context.setFill(Color.BLUE);
        context.fillOval(center.x - radius, center.y - radius, 2.0d * radius, 2.0d * radius);
    }

    @Override
    public void drawLine(Vector point1, Vector point2) {
        context.setStroke(Color.BLACK);
        context.strokeLine(point1.x, point1.y, point2.x, point2.y);
    }
}
