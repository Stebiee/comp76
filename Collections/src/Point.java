import java.util.Comparator;

/**
 * Assignment for Java Collections Framework.
 * implements methods to compare coordinate pairs
 * @author Esteban Madrigal
 */
public class Point implements Comparable<Point> {
    // x = x coordinate, y = y coordinate
    private double x, y;

    
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * compares the Point object with passed object
     * @return true if objects equal
     * @return false if objects aren't equal
     * 
     */
    @Override
    public boolean equals(Object o) {
        var obj = (Point) o;

        // if x coords and y coord pairs equal return true
        return Double.compare(obj.x, this.x) == 0 
            && Double.compare(obj.y, this.y) == 0;
    }

    /**
     * returns a string representations of x and y pair
     */
    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")" ;
    }

    /**
     * return if x coords are < or > eachother 
     * if x coords == then implement the same comparison for y
     * @return -1 if object is less than arguemnt
     * @return 0 if object equals argument
     * @return 1 if object is less than argument
     */
    @Override
    public int compareTo(Point o) {
        // initial comparison for x coords
        if(this.x < o.x){
            return -1;
        }else if(this.x > o.x){
            return 1;
        }else{
            // x coords equal eachother, compare y coords
            if(this.y < o.y){
                return -1;
            }else if(this.y > o.y){
                return 1;
            }else{
                return 0;
            }
        }
    }

    /**
     * 
     */
    public static class CompareY implements Comparator<Point> {

        /**
        * runs comparison on first argument to second
        * if y coords == then implement the same comparison for x
        * @return -1 if o1 y is less than o2
        * @return 0 if o1 x,y and o2 x,y equal
        * @return 1 if o1 y is greater than o2
        */
        @Override
        public int compare(Point o1, Point o2) {
            if(o1.y < o2.y){
                // first y is < than second
                return -1;
            }else if(o1.y > o2.y){
                // first y is > second
                return 1;
            }else{
                // y coords equal, check x
                if(o1.x < o2.x){
                    // first x is < than second
                    return -1;
                }else if(o1.x > o2.x){
                    // first x is > than second
                    return 1;
                }else{
                    // x coords equal
                    return 0;
                }
            }
        }
    }
}
