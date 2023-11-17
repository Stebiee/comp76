import java.util.Arrays;
import java.util.Comparator;

/**
 * this class contains methods which use 2 mergeSort methods 
 * to be used to sort objects
 */
public class MergeSortTest2 {

    /**
     * This method sorts the given array using Merge Sort. Sorting is done in place (ie after the
     * method completes, the passed in array is in sorted order).
     */
    public static <E extends Comparable<E>> void mergeSort(E[] list) {
        int middle;
        int ListLength = list.length;

        if (ListLength < 2) {
            // list cannot be sorted
            return;
        }

        // split list into two similar in length arrays
        middle = ListLength / 2;
        E[] leftHalf = Arrays.copyOfRange(list, 0, middle);
        E[] rightHalf = Arrays.copyOfRange(list, middle, ListLength);

        // recursive call to merge sort
        mergeSort(leftHalf);
        mergeSort(rightHalf);

        // merging the sorted arrays
        merge(list, leftHalf, rightHalf);
    }

    /**
     * After mergeSort divides the array, order the elements
     * and combine them into one
     * @param list origional list to be sorted
     * @param leftHalf left element to be compared
     * @param rightHalf right element to be compared
     */
    private static <E extends Comparable<E>> void merge (E[] list, E[] leftHalf, E[] rightHalf) {
        // index of left, right, combined arrays
        int i = 0, j = 0, k = 0;
        int leftSize = leftHalf.length;
        int rightSize = rightHalf.length;

        // loop until an array runs out of elements
        while (i < leftSize && j < rightSize) {

            // compares elements adding the smaller to tail of
            // combined array while increasing that halfs index
            if (leftHalf[i].compareTo(rightHalf[j]) <= 0) {
                // left element <= right
                list[k] = leftHalf[i];
                i++;
            } else {
                // right element < left
                list[k] = rightHalf[j];
                j++;
            }
            // itterate the combined array
            k++;
        }
        // one of the arrays has ran out of elements
        // itterate through the array which still contains elements
        // adding them to the tail of combined array
        while (i < leftSize) {
            list[k] = leftHalf[i];
            i++;
            k++;
        }
        while (j < rightSize) {
            list[k] = rightHalf[j];
            j++;
            k++;
        }

    }

    /**
     * This method sorts the given array using Merge Sort using the passed in Comparator.
     * Sorting is done in place (ie after the method completes, the passed in array is in
     * sorted order).
     * 
     * @param list list to be sorted
     * @param comparator Comparator
     */
    public static <E> void mergeSort(E[] list, Comparator<? super E> comparator) {
        int middle;
        int ListLength = list.length;

        if (ListLength < 2) {
            // list cannot be sorted
            return;
        }

        // split list into two similar in length arrays
        middle = ListLength / 2;
        E[] leftHalf = Arrays.copyOfRange(list, 0, middle);
        E[] rightHalf = Arrays.copyOfRange(list, middle, ListLength);

        // recursive call to merge sort
        mergeSort(leftHalf, comparator);
        mergeSort(rightHalf, comparator);

        // merging the sorted arrays
        merge(list, leftHalf, rightHalf, comparator);
    }

    /**
     * After mergeSort divides the array, order the elements
     * and combine them into one
     * @param list origional list 
     * @param leftHalf left element to be compared
     * @param rightHalf right element to be compared
     * @param comparator Comparator 
     */
    private static <E> void merge(E[] list, E[] leftHalf, E[] rightHalf, Comparator<? super E> comparator) {
        // index of left, right, combined arrays
        int i = 0, j = 0, k = 0;
        int leftSize = leftHalf.length;
        int rightSize = rightHalf.length;

        // loop until an array runs out of elements
        while (i < leftSize && j < rightSize) {

            // compares elements adding the smaller to tail of
            // combined array while increasing that halfs index
            if (comparator.compare(leftHalf[i], rightHalf[j]) <= 0) {
                // left <= right
                list[k] = leftHalf[i];
                i++;
            } else {
                // right < left
                list[k] = rightHalf[j];
                j++;
            }
            // itterate the combined array
            k++;
        }
        // one of the arrays has ran out of elements
        // itterate through the array which still contains elements
        // adding them to the tail of combined array
        while (i < leftSize) {
            list[k] = leftHalf[i];
            i++;
            k++;
        }
        while (j < rightSize) {
            list[k] = rightHalf[j];
            j++;
            k++;
        }
    }

    /**
     * 
     */
    static class Circle extends GeometricObject {
        double radius;
        
        public Circle(double radius) {
            if (radius <= 0) {
                throw new IllegalArgumentException("Radius must be a positive nonzero double");
            }
            this.radius = radius;
        }

        public boolean equals(Object o) {
            
            if (this == o) {
                // reference to the same object
                return true;
            }else if (o == null || !(o instanceof Circle)) {
                // o isnt a circle or has no value
                return false;
            }else{
                // return whether radius are equal to eachother
                Circle other = (Circle)o;
                return Double.compare(other.radius, this.radius) == 0;
            }
        }

        @Override
        public double getArea() {
            return Math.PI * Math.pow(radius, 2);
        }

        @Override
        public double getPerimeter() {
            return 2 * Math.PI * this.radius;
        }
    }

    static class Rectangle extends GeometricObject {
        double width, height;

        public Rectangle(double width, double height) {
            if (width <= 0 || height <= 0) {
                throw new IllegalArgumentException("sides must be a positive non-zero double");
            }

            this.width = width;
            this.height = height;
        }

        public boolean equals(Object o) {
            if (this == o) {
                // reference to the same object
                return true;
            }else if (o == null || !(o instanceof Rectangle)) {
                // o isnt a rectangle or has no value
                return false;
            }else{
                // return whether width and height are equal
                Rectangle other = (Rectangle)o;
                return Double.compare(other.width, this.width) == 0
                    && Double.compare(other.height, this.height) == 0;
            }
        }

        @Override
        public double getArea() {
            return this.width * this.height;
        }

        @Override
        public double getPerimeter() {
            return 2 * (this.width + this.height);
        }

    }

    static class ComparePerimeter implements Comparator<GeometricObject> {

        @Override
        // compares o1 and o2 by their perimeters, then by area if perimeter equal
        public int compare(GeometricObject o1, GeometricObject o2) {
            if (Double.compare(o1.getPerimeter(), o2.getPerimeter()) == 0) {
                // perimeters equal compare their areas
                return Double.compare(o1.getArea(), o2.getArea());
            }
            // perimeters are either less or greater than
            return Double.compare(o1.getPerimeter(), o2.getPerimeter());
        }
    }

    /**
     * tests sorting an array of geometric objects using Comparable
     */
    public static void testSortingUsingComparable() {
        
        Circle circle1 = new Circle(5.0);
        Circle circle2 = new Circle(3.0);
        Circle circle3 = new Circle(12.0 / Math.PI);
        Rectangle rectangle1 = new Rectangle(4.0, 4.0);
        Rectangle rectangle2 = new Rectangle(6.0, 6.0);

        // Test Case 1: Sorting Circles and Rectangles
        GeometricObject[] objects = {circle1, rectangle1, circle2};
        mergeSort(objects);

        System.out.println("\nTest Case 1: Sorting Circles and Rectangles");
        for (GeometricObject obj : objects) {
            System.out.println("Perimeter " + obj.getPerimeter() + ", Area: " + obj.getArea());
        }
        
        // Test Case 2: Sorting Rectangles with same perimeter as a circle
        GeometricObject[] objects2 = {circle3, rectangle2};
        mergeSort(objects2);

        System.out.println("\nTest Case 2: Sorting Rectangles with same perimeter as a circle");
        for (GeometricObject obj : objects2) {
            System.out.println("Perimeter " + obj.getPerimeter() + ", Area: " + obj.getArea());
        }

        // Test Case 3: Sorting array with single element
        GeometricObject[] objects3 = {circle1};
        mergeSort(objects3);

        System.out.println("\nTest Case 3: Sorting array with single element");
        for (GeometricObject obj : objects3) {
            System.out.println("Perimeter " + obj.getPerimeter() + ", Area: " + obj.getArea());
        }

        // create a circle obj with negative radius
        // create a rectangle obj with length 0
    }

    /**
     * tests sorting an array of geometric objects using a comparitor
     * on objects perimeter, if equal then their area
     */
    public static void testSortingUsingComparator() {
        Circle circle1 = new Circle(5.0);
        Circle circle2 = new Circle(3.0);
        Circle circle3 = new Circle(12.0 / Math.PI);
        Rectangle rectangle1 = new Rectangle(4.0, 4.0);
        Rectangle rectangle2 = new Rectangle(6.0, 6.0);

        Comparator<GeometricObject> comparePerimeter = new ComparePerimeter();

        // Test Case 1: Sorting Circles and Rectangles
        GeometricObject[] objects = {circle1, rectangle1, circle2};
        mergeSort(objects, comparePerimeter);

        System.out.println("\nTest Case 1: Sorting Circles and Rectangles by Perimeter");
        for (GeometricObject obj : objects) {
            System.out.println("Perimeter " + obj.getPerimeter() + ", Area: " + obj.getArea());
        }

        // Test Case 2: Sorting Circles and Rectangles with same perimeter
        GeometricObject[] objects2 = {circle3, rectangle2};
        mergeSort(objects2, comparePerimeter);

        System.out.println("\nTest Case 2: Sorting Circles and Rectangles with Same Perimeter");
        for (GeometricObject obj : objects2) {
            System.out.println("Perimeter " + obj.getPerimeter() + ", Area: " + obj.getArea());
        }

        // Test Case 3: Sorting array with single element
        GeometricObject[] objects3 = {circle1};
        mergeSort(objects3, comparePerimeter);

        System.out.println("\nTest Case 3: Sorting array with single element");
        for (GeometricObject obj : objects3) {
            System.out.println("Perimeter " + obj.getPerimeter() + ", Area: " + obj.getArea());
        }

        // create a circle obj with negative radius
        // create a rectangle obj with length 0
    }

    static abstract class GeometricObject implements Comparable<GeometricObject> {
        /** Should return the area of the object. */
        public abstract double getArea();
      
        /** Should return the perimeter of the object */
        public abstract double getPerimeter();
      
        // The default comparison uses the area to compare two objects.
        public int compareTo(GeometricObject that) {
            if (this.getArea() < that.getArea()) {
                return -1;
            } else if (this.getArea() > that.getArea()) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    public static void main(String[] args) {
        testSortingUsingComparable();
        System.out.println("=====================================");
        testSortingUsingComparator();
    }
}
