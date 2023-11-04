import java.util.Arrays;
import java.util.Comparator;

public class Test {

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

    // merges two sorted Arrays
    private static <E extends Comparable<E>> void merge (E[] list, E[] leftHalf, E[] rightHalf) {
        // index of left, right, combined arrays
        int i = 0, j = 0, k = 0;
        int leftSize = leftHalf.length;
        int rightSize = rightHalf.length;

        // loop until elements run out of left or right array
        while (i < leftSize && j < rightSize) {

            // compares elements of either adding the smaller to
            // combined array while increasing that halfs index
            // then making the same comparison
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
        // if one of the halves ran out of elements
        // add the remaining array to tail of combined
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

    private static <E> void merge(E[] list, E[] leftHalf, E[] rightHalf, Comparator<? super E> comparator) {
        // index of left, right, combined arrays
        int i = 0, j = 0, k = 0;
        int leftSize = leftHalf.length;
        int rightSize = rightHalf.length;

        // loop until elements run out of left or right array
        while (i < leftSize && j < rightSize) {

            // uses comparator for both arrays adding the smaller 
            // to combined array while increasing that halfs index
            // then making the same comparison
            if (comparator.compare(leftHalf[i], rightHalf[j]) <= 0) {
                list[k] = leftHalf[i];
                i++;
            } else {
                list[k] = rightHalf[j];
                j++;
            }
            // itterate the combined array
            k++;
        }
        // if one of the halves ran out of elements
        // add the remaining array to tail of combined
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
        // compares o1 and o2 by their perimeters
        public int compare(GeometricObject o1, GeometricObject o2) {
            return Double.compare(o1.getPerimeter(), o2.getPerimeter());
        }
    }

    public static void testSortingUsingComparable() {
        // TODO: Add at least 3 test cases here. Think of all the ways you want to verify that your code works.
        // The objects you are trying to sort should be a mix of Circles and Rectangles.
        // Also add a comment on what other test cases you can think of adding.
        Circle circle1 = new Circle(2.0);
        Circle circle2 = new Circle(5.0);
        Rectangle rect1 = new Rectangle(3, 2);
        Rectangle rect2 = new Rectangle(4,1);
        // declare a circle with radius -1
        // declare a rectangle with width 0 length 1

        GeometricObject[] objects = {circle1, circle2, rect1, rect2};

        mergeSort(objects);
        
    }

    public static void testSortingUsingComparator() {
        // TODO: Add at least 3 test cases to verify that your code works correctly when using the
        // ComparePerimeter comparator
        // The objects you are trying to sort should be a mix of Circles and Rectangles.
        // Also add a comment on what other test cases you can think of adding.
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
    }
}
