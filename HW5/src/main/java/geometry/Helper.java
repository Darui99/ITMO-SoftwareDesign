package geometry;

import java.util.ArrayList;
import java.util.List;

public class Helper {
    private final double width;
    private final double height;

    public Helper(long width, long height) {
        this.width = (double) width;
        this.height = (double) height;
    }

    public static double toRadian(double angle) {
        return angle / 180.0d * Math.PI;
    }

    private double calculateBig() {
        return (Math.min(width, height) - 10.0d) / 3.0d;
    }

    private Vector calculateCenter() {
        return new Vector(width / 2.0d, height / 2.0d);
    }

    public double calculateRadius(int n) {
        if (n == 1) {
            return Math.min(width, height) / 3.0d;
        }
        return calculateBig() * Math.sqrt(2.0d * (1.0d - Math.cos(toRadian(360.0d / (double) n)))) / 4.25d;
    }

    public List<Vector> getVerticesCenters(int n) {
        List<Vector> res = new ArrayList<>(n);
        if (n == 1) {
            res.add(calculateCenter());
            return res;
        }
        for (int i = 0; i < n; i++) {
            double angle = toRadian(360.0d / (double) n * (double) i);
            Vector direction = new Vector(Math.cos(angle), Math.sin(angle));
            res.add(calculateCenter().add(direction.mult(calculateBig())));
        }
        return res;
    }
}
