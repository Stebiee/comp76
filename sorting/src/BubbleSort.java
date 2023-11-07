import java.util.Random;
/**O(n^2)
 * itter through array comparing adjacent pairs
 * swap their position if not in the same order
 * 65312
 * 65 312 swap
 * 56312
 * 56 312 good
 * 56312
 * 5 63 12 swap
 * 53612
 * 53 612 swap
 * 35612
 * 35 612 good
 * 3 56 12 good
 * 35 61 2 swap
 * 35162
 * 3 51 62 swap
 * 31562
 * 31 562 swap
 * 13562
 * 135 62 swap
 * 13526
 * 13 52 6 swap
 * 13256
 * 1 32 56 swap
 * 12356 sorted
 * @author Esteban Madrigal
 */
public class BubbleSort {
    private static void bubbleSort(int[] numbers) {
        boolean haveSwapped = true;

        // compare if a swap has occured
        // if not then 
        while(haveSwapped) {
            // set to false so that if compared with no swap
            // it doesnt enter the while loop again 
            haveSwapped = false;

            // itter through array checking if element after
            // is greater or less than 
            // numbers.length -1 since there is no after element 
            // at end of the list
            for (int i = 0; i < numbers.length - 1; i++) {
                if (numbers[i] > numbers[i + 1]) {
                    // out of order, swap
                    // since a swap occurs 
                    haveSwapped = true;

                    int temp = numbers[i];
                    numbers[i] = numbers[i + 1];
                    numbers[i + 1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        // how many elements array holds
        // 100_000 takes about 14 seconds\
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
        bubbleSort(numbers);
        totalTime = System.currentTimeMillis() - startTime;

        System.out.printf("sorted %,d: %d milliseconds\n", size, totalTime);
    }
}
