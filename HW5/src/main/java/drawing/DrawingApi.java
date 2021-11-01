package drawing;

import geometry.Vector;

public interface DrawingApi {
    long getDrawingAreaWidth();

    long getDrawingAreaHeight();

    void drawCircle(Vector center, double radius);

    void drawLine(Vector point1, Vector point2);
}
