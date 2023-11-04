public class Pair {
    Point p1, p2;

    public Pair(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    /**
     * makes use of the distance formula
     * to get the distance of two Point objects
     * @return distance between two points
     */
    public int getDistance() {
        // using distance formula for two points
        return (int)Math.sqrt(Math.pow((p2.x - p1.x), 2) + Math.pow((p2.y - p1.y), 2));
    }
}
