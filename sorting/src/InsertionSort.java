import java.util.Random;

/**
 * O(n^2)
 * insertion sort loops through an array
 * it walks through the array inserting it to the sorted array
 * 31862
 * 3 862
 *  3862
 * 13862
 * 13 62
 * 13862
 * 138 2
 * 13 82
 * 13682
 * 1368 
 * 136 8
 * 13 68
 * 1 368
 * 12368
 * 
 * @author Esteban Madrigal
 */
public class InsertionSort {
    
    private static void insertionSort(int[] inputArray) {

        // walk through unsorted array
        for (int i = 1; i < inputArray.length; i++) {
            // i initial is one to skip the first element
            // currentValue is for element to be moved into sorted array
            int currentValue = inputArray[i];

            // walk back the array comparing looking for element > current
            // stopping when at the beginning or when a current >= element
            // j is one left of currentValue
            int j = i - 1;
            while (j >=0 && inputArray[j] > currentValue) {
                inputArray[j + 1] = inputArray[j];
                j--;
            }
            // correct place found insert currentValue
            inputArray[j + 1] = currentValue;
        }
    }

    public static void main(String[] args) {
        // how many elements array holds
        // 100_000 takes about 2.6 seconds
        int size = 100_000;
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
        insertionSort(numbers);
        totalTime = System.currentTimeMillis() - startTime;

        System.out.printf("sorted %,d: %d milliseconds\n", size, totalTime);
    }
}
