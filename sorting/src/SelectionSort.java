import java.util.Random;

/** O(n^2)
 * 4 5 1 2 3 
 * begins by comparing index 0 to the rest of the list
 * once having gone through the whole list then keep 
 * at that position or swap with the index whos value was lowest
 * 15423
 * 
 * @author Esteban Madrigal
 */
public class SelectionSort {

    private static void selectionSort(int[] numbers) {
        int length = numbers.length;

        // loop to go through every position in nums
        for (int i = 0; i < length - 1; i++) {
            int min = numbers[i];
            int indexOfMin = i;

            // loop from current to end of array
            // looking for the minimum
            for (int j = i + 1; j < length; j++) {
                // new minimum val found
                if (numbers[j] < min) {
                    min = numbers[j];
                    indexOfMin = j;
                }
            }
            // looking for minimum done 
            swap(numbers, i, indexOfMin);
        }
    }
    
    private static void swap(int[] numbers, int a, int b) {
        int temp = numbers[a];
        numbers[a] = numbers[b];
        numbers[b] = temp;
    }

    public static void main(String[] args) {
        // how many elements array holds
        // 100_000 takes about 1.8 seconds
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
        selectionSort(numbers);
        totalTime = System.currentTimeMillis() - startTime;

        System.out.printf("sorted %,d: %d milliseconds\n", size, totalTime);
    }
}
