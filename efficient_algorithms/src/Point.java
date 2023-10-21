import java.util.Comparator;

/**
 * Assignment for finding closest points.
 * @author Esteban Madrigal
 */
public class Point implements Comparable<Point> {
    public Point(double x, double y) {
        // TODO: Implement this method.
    }

    @Override
    public boolean equals(Object o) {
        // TODO: Implement this method.
        return false;
    }

    @Override
    public int hashCode() {
        // TODO: Implement this method.
        return 0;
    }

    @Override
    public String toString() {
        // TODO: Implement this method.
        return "TODO";
    }

    @Override
    public int compareTo(Point that) {
        // TODO: Implement this method.
        return 0;
    }

    public static class CompareY implements Comparator<Point> {

        @Override
        public int compare(Point o1, Point o2) {
            // TODO: Implement this method.
            return 0;
        }
    }
}
