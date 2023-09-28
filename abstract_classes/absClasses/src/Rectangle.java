import java.lang.RuntimeException;

public class Rectangle extends GeometricObject{
    public double length;
    public double height;

    // creating constructor which throws an exception
    // if values passed are <= 0
    public Rectangle(double length, double height){
        if (length <= 0 || height <= 0){
            throw new RuntimeException("Given Values must be greater than zero");
        }

        this.length = length;
        this.height = height;
    }

    // implementing getArea() from base class GeometricObject
    @Override
    public double getArea() {
        return length * height;
    }

    // implementing getPerimeter() from base class GeometricObject
    @Override
    public double getPerimeter() {
        return 2 * (length + height);
    }
}