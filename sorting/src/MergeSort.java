import java.util.Random;
/** 
 * beginning with an un-ordered array
 * split into a left and right half
 * then merge sort each half recursively
 * once each half of array is sorted, merge them to one array
 *          38,27,43,3,9,82,10
 *       38,27,43,3      9,82,10
 *     38,27   43,3     9,82    10
 *   38  27   43  3    9  82    10 splitting is done
 *    27,38    3,43    9,82     10 begin merging the elements while sorting
 *        3,27,38,43     9,19,82
 *          3,9,10,27,38,43,82
 * @author Esteban Madrigal
 */
public class MergeSort {
    public static void mergeSort(int[] inputArray) {
        int inputLength = inputArray.length;

        // base case
        if (inputLength < 2) {
            // array is one element so already sorted
            return;
        }

        // split the array in two
        int midIndex = inputLength / 2;
        int[] leftHalf = new int[midIndex];
        int[] rightHalf = new int[inputLength - midIndex];

        // populate the split arrays
        // for left half
        for (int i = 0; i < midIndex; i++) {
            leftHalf[i] = inputArray[i];
        }
        // for right half
        for (int i = midIndex; i < inputLength; i++) {
            // i - midIndex because right needs to start at 0
            // this offsets the index of the inputArray to populate right half
            rightHalf[i - midIndex] = inputArray[i];
        }

        // merge sort each half of the array
        mergeSort(leftHalf);
        mergeSort(rightHalf);

        // merge the final split arrays
        merge(inputArray, leftHalf, rightHalf);
    }

    private static void merge(int[] inputArray, int[] leftHalf, int[] rightHalf) {
        // index for left, right, and combined array
        int i = 0, j = 0, k = 0;
        int leftSize = leftHalf.length;
        int rightSize = rightHalf.length;

        // itterate through left and right arrays
        // adding the lower value element to a final merged array
        // loop until having itered through an entire array
        while (i < leftSize && j < rightSize) {

            if (leftHalf[i] <= rightHalf[j]) {
                // left half is smaller put into merged array
                inputArray[k] = leftHalf[i];
                i++;
            }else {
                // right half is smaller than left
                inputArray[k] = rightHalf[j];
                j++;
            }
            // after insertion iter combined array
            k++;
        }
        // one array has ran out of elements to look through
        // add that sides array 
        while (i < leftSize) {
            inputArray[k] = leftHalf[i];
            i++;
            k++;
        }

        while (j < rightSize) {
            inputArray[k] = rightHalf[j];
            j++;
            k++;
        }
    }


    public static void main(String[] args) {
        // how many elements array holds
        // 1_000_000 takes about 40 milliseconds
        int size = 1_000_000;
        long startTime;
        long totalTime;

        // int[] to sort
        int[] numbers = new int[size];
        // random numbers to insert
        Random rand = new Random();

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = rand.nextInt(1_000_000);
        }

        // timing how long the program took
        startTime = System.currentTimeMillis();
        mergeSort(numbers);
        totalTime = System.currentTimeMillis() - startTime;

        for (int element : numbers){
            System.out.println(element);
        }
        System.out.printf("sorted %,d: %d milliseconds\n", size, totalTime);
    }
}
