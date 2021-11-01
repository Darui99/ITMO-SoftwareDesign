package drawing;

import geometry.Vector;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class AwtDrawing implements DrawingApi {
    private final Graphics2D graphics2D;
    private final int width = 700;
    private final int height = 700;
    private final int thickness = 7;

    public AwtDrawing() {
        Frame frame = new Frame();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        frame.setSize(width, height);
        frame.setVisible(true);
        graphics2D = (Graphics2D) frame.getGraphics();
        graphics2D.setStroke(new BasicStroke(thickness));
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
        graphics2D.setPaint(Color.blue);
        graphics2D.fill(new Ellipse2D.Double(center.x - radius, center.y - radius, 2.0d * radius, 2.0d * radius));
    }

    @Override
    public void drawLine(Vector point1, Vector point2) {
        graphics2D.setPaint(Color.black);
        graphics2D.draw(new Line2D.Double(point1.x, point1.y, point2.x, point2.y));
    }
}
