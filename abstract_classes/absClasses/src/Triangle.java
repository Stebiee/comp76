import java.lang.RuntimeException;

public class Triangle extends GeometricObject{
    double side1, side2, side3;

    public Triangle(double side1, double side2, double side3){
        // making sure any pair of sides are greater than residual side
        // also making sure sides are greater than zero
        if ((side1 + side2 < side3) || (side2 + side3 < side1) ||
            (side1 + side3 < side2)){
            throw new RuntimeException("Invalid triangle was given");
        } else if (side1 <= 0 || side2 <= 0 || side3 <= 0) {
            throw new RuntimeException("Invalid triangle was given");
        }

        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;
    }

    // implementing getPerimeter() from base class GeometricObject
    @Override
    public double getPerimeter(){
        return side1 + side2 + side3;
    }

    // implement getArea() from base class GeometricObject
    // using Heron's formula to get area using a triangles side lengths
    @Override
    public double getArea(){
        // getting half the perimeter of the triangle
        double x = getPerimeter() / 2;

        // using Heron's formula to get area using a triangles side lengths
        return Math.sqrt(x * (x - side1) * (x - side2) * (x - side3));
    }
}