import java.util.Random;
/**
 * first choose a pivot point, index of pivot can be chosen arbitrarily 
 * then move all less than elements to the left of pivot
 * while moving greater than elements to the right of pivot
 * called partitioning
 * 132546
 * pivot is 4
 * 1 4
 * 13 4
 * 132 4
 * 132 4 5
 * 132 4 56
 * quicksort all the values left and right of pivot recursively
 * resulting in single element arrays, sorted ready to be merged
 * 
 * @author Esteban Madrigal
 */
public class QuickSort {
    public static void quickSort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }

    private static void quickSort(int[] array, int lowIndex, int highIndex){
        // base case
        if (lowIndex >= highIndex) {
            // subarray is one element 
            return;
        }

        // creating a random pivot somewhere between low and high index
        int pivotIndex = new Random().nextInt(highIndex - lowIndex) + lowIndex;
        int pivot = array[pivotIndex];
        // swap the random pivot with the last element in array 
        swap(array, pivotIndex, highIndex);

        // creates different partitions until pointers meet returns index where they meet
        int leftPointer = partition(array, lowIndex, highIndex, pivot);

        // recursive quicksort on elements left of pivot
        quickSort(array, lowIndex, leftPointer - 1);
        // recursive quicksort on elements right of pivot
        quickSort(array, leftPointer + 1, highIndex);
    }

    private static void swap(int[] array, int index1, int index2) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    private static int partition (int[] array, int lowIndex, int highIndex,int pivot) {
        // create a left and right pointer
        // left pointer begins at the leftmost element in an array
        // increase index looking for element > pivot
        // once such element is found, stop in place
        // right pointer begins at right most element (left of pivot)
        // decreasing index until element < pivot
        // left pointer and right pointer swap positions
        // repeat the process
        // once the left pointer is at the same element as right
        // this process ends 
        // finally the pivot swaps with the left pointer element
        // all numbers left of pivot are <
        // all numbers right of pivot are >=
        int leftPointer = lowIndex;
        int rightPointer = highIndex - 1;

        // create the loop to itterate pointers until they meet
        while (leftPointer < rightPointer) {

            // increment left pointer until greater than pivot is found
            // or equals rightPointer location
            while (array[leftPointer] <= pivot && leftPointer < rightPointer) {
                leftPointer++;
            }
            // decrement right pointer until less than pivot is found
            // or equals right pointer location
            while (array[rightPointer] >= pivot && leftPointer < rightPointer) {
                rightPointer--;
            }
            
            // each pointer found an element greater than or less than pivot
            // swap them
            swap(array, leftPointer, rightPointer);
        }

        if (array[leftPointer] > array[highIndex]) {
            // left pointer is greater than right
            // swap them
            swap(array, leftPointer, highIndex);
        }else {
            leftPointer = highIndex;
        }
        return leftPointer;
    }

    public static void main(String[] args) {
        // how many elements array holds
        // 1_000_000 takes about 145 milliseconds
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
        quickSort(numbers);
        totalTime = System.currentTimeMillis() - startTime;

        System.out.printf("sorted %,d: %d milliseconds\n", size, totalTime);
    }
}
