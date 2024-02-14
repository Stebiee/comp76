import java.lang.RuntimeException;

public class Circle extends GeometricObject{
    double radius;

    public Circle(double radius){
        if (radius <= 0){
            throw new RuntimeException("Radius must be greater than zero");
        }

        this.radius = radius;
    }

    // implementing getArea() from base class GeometricObject test
    @Override
    public double getArea() {
        return Math.PI * Math.pow(radius, 2);
    }

    // implementing getPerimeter() from base class GeometricObject
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }
}