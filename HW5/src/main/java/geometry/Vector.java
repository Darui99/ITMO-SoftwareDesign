package geometry;

public class Vector {
    public double x, y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    Vector add(Vector v) {
        return new Vector(x + v.x, y + v.y);
    }

    Vector sub(Vector v) {
        return new Vector(x - v.x, y - v.y);
    }

    Vector mult(double k) {
        return new Vector(x * k, y * k);
    }

    double len() {
        return Math.hypot(x, y);
    }
}
