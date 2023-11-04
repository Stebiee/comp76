import java.util.Arrays;
import java.util.Random;

public class App {
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
            // compares elements of either array increasing the index
            // of the array with the smaller value and making the same comparison
            
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
        // one of the halves ran out of elements
        // add the remaining array to combined
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

    public static void main(String[] args) throws Exception {
        // Create a sample array of integers
        Random r = new Random();
        Integer[] arr = new Integer[10];
        int running = 0;
        while (running <= 10) {
            for (int i = 0; i < arr.length; i++) {
                arr[i] = r.nextInt(1000);
            }

            System.out.print("\n\nBefore sort: ");
            for (Integer num : arr) {
                System.out.print(num + " ");
            }

            // Call the mergeSort method to sort the array
            mergeSort(arr);

            // Print the sorted array
            System.out.print("\nAfter sort: ");
            for (Integer num : arr) {
                System.out.print(num + " ");
            }
            
            running++;
        }
    }
}
