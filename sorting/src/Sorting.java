import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sorting {
    /**
     * compare index 0 with rest of elements, swap with index whos value is lowest
     * @param input
     */
    public static long selectionSort(ArrayList<Integer> input) {
        long startTime = System.currentTimeMillis( ); 
        
        int length = input.size();

        // loop through every position in nums
        for (int i = 0; i < length; i++) {
            int min = input.get(i);
            int indexOfMin = i;

            // loop from current to end of array
            // looking for the minimum
            for (int j = i + 1; j < length; j++) {
                if (input.get(j) < min) {
                    // new min found
                    min = input.get(j);
                    indexOfMin = j;
                }
            }
            // looking for minimum done, swap
            int temp = input.get(i);
            input.set(i, min);
            input.set(indexOfMin,temp);
        }

        long endTime = System.currentTimeMillis( ); 
        long executionTime = endTime - startTime;
        return executionTime;
    }

    public static long mergeSort(ArrayList<Integer> input) {
        long startTime = System.currentTimeMillis( );
        int inputLength = input.size();

        // base case
        if (inputLength >= 2) {
            // passed array larger than 1 element
            int midIndex = inputLength / 2;
            // split into two
            ArrayList<Integer> leftHalf = new ArrayList<>(midIndex);
            ArrayList<Integer> rightHalf = new ArrayList<>(inputLength - midIndex);

            // populate arrays
            // for left half
            for (int i = 0; i < midIndex; i++) {
                leftHalf.add(input.get(i));
            }
            // for right half
            for (int i = midIndex; i < inputLength; i++) {
                // i - midIndex because right needs to start at middle of input
                // this offsets the index of the inputArray to populate right half
                rightHalf.add(input.get(i));
            }

            // recursive call to split arrays
            mergeSort(leftHalf);
            mergeSort(rightHalf);

            // merge final split arrays
            // index for left, right, and combined array
            int i = 0, j = 0, k = 0;
            int leftSize = leftHalf.size();
            int rightSize = rightHalf.size();

            // itterate through left and right arrays
            // adding the lower value element to a final merged array
            // loop until having itered through an entire array
            while (i < leftSize && j < rightSize) {

                if (leftHalf.get(i) <= rightHalf.get(j)) {
                    // left half is smaller put into merged array
                    input.set(k, leftHalf.get(i));
                    i++;
                }else {
                    // right half is smaller than left
                    input.set(k, rightHalf.get(j));
                    j++;
                }
                // after insertion iter combined array
                k++;
            }
            // one array has ran out of elements to look through
            // add that sides array 
            while (i < leftSize) {
                input.set(k, leftHalf.get(i));
                i++;
                k++;
            }

            while (j < rightSize) {
                input.set(k, rightHalf.get(j));
                j++;
                k++;
            }
        }

        long endTime = System.currentTimeMillis( ); 
        long executionTime = endTime - startTime;
        return executionTime;
    }

    public static void quickSort(ArrayList<Integer> input) {
        quickSort(input, 0, input.size() - 1);
    }

    private static void quickSort(ArrayList<Integer> input, int lowIndex, int highIndex) {
        // base case
        if (lowIndex >= highIndex) {
            // subarray is one element
            return;
        }

        // create a random pivot somewhere between low and high index
        int pivotIndex = new Random().nextInt(highIndex - lowIndex) + lowIndex;
        int pivot = input.get(pivotIndex);
        // swap the random pivot with the last element in input
        swap(input, pivotIndex, highIndex);

        // create different partitions until pointers meet, returns index where they met
        int leftPointer = partition(input, lowIndex, highIndex, pivot);

        // recursive quicksort on elements left of pivot
        quickSort(input, lowIndex, leftPointer - 1);
        // recursive quicksort on elements right of pivot
        quickSort(input, leftPointer + 1, highIndex);
    }

    private static void swap(ArrayList<Integer> input, int index1, int index2) {
        int temp = input.get(index1);
        input.set(index1, input.get(index2));
        input.set(index2, temp);
    }

    /**
     * create a left and right pointer
     * left pointer begins at the leftmost element in an array
     * increase index looking for element > pivot
     * once such element is found, stop in place
     * right pointer begins at right most element (left of pivot)
     * decreasing index until element < pivot
     * left pointer and right pointer swap positions
     * repeat the process
     * once the left pointer is at the same element as right
     * this process ends 
     * finally the pivot swaps with the left pointer element
     * all numbers left of pivot are <
     * all numbers right of pivot are >=
     * @param array
     * @param lowIndex
     * @param highIndex
     * @param pivot
     * @return
     */
    private static int partition(ArrayList<Integer> input, int lowIndex, int highIndex, int pivot) {
        int leftPointer = lowIndex;
        int rightPointer = highIndex - 1;

        // create the loop to itterate pointers until they meet
        while (leftPointer < rightPointer) {

            // increment left pointer until greater than pivot is found
            // or equals rightPointer location
            while (input.get(leftPointer) <= pivot && leftPointer < rightPointer) {
                leftPointer++;
            }
            // decrement right pointer until less than pivot is found
            // or equals right pointer location
            while (input.get(rightPointer) >= pivot && leftPointer < rightPointer) {
                rightPointer--;
            }
            
            // each pointer found an element greater than or less than pivot
            // swap them
            swap(input, leftPointer, rightPointer);
        }
        if (input.get(leftPointer) > input.get(highIndex)) {
            // left pointer is greater than right
            // swap them
            swap(input, leftPointer, highIndex);
        }else {
            leftPointer = highIndex;
        }
        return leftPointer;
    }

    public static void heapSort(ArrayList<Integer> input) {
        // TODO: Implement this method.
    }

    public static void radixSort(ArrayList<Integer> input) {
        // TODO: Implement this method.
    }

    public static void mainHelper() {
	// Implement this method to sort out 50,000, 100,000, 150,000, 200,000, 250,000, 300,000 integers
	// Printout the results as described in the assignment.
        ArrayList<Integer> list1 = new ArrayList<>();
        ArrayList<Integer> list2 = new ArrayList<>();
        ArrayList<Integer> list3 = new ArrayList<>();
        ArrayList<Integer> list4 = new ArrayList<>();
        ArrayList<Integer> list5 = new ArrayList<>();
        ArrayList<Integer> list6 = new ArrayList<>();

        Random rand = new Random();

        for (int i = 0; i < 50_000; i++) {
            // add 50,000 list1 and above
            list1.add(rand.nextInt(500_000));
            list2.add(rand.nextInt(500_000));
            list3.add(rand.nextInt(500_000));
            list4.add(rand.nextInt(500_000));
            list5.add(rand.nextInt(500_000));
            list6.add(rand.nextInt(500_000));
        }
        for (int i = 0; i < 50_000; i++) {
            // add 50,000 list2 and above
            list2.add(rand.nextInt(500_000));
            list3.add(rand.nextInt(500_000));
            list4.add(rand.nextInt(500_000));
            list5.add(rand.nextInt(500_000));
            list6.add(rand.nextInt(500_000));

        }
        for (int i = 0; i < 50_000; i++) {
            // add 50,000 list3 and above
            list3.add(rand.nextInt(500_000));
            list4.add(rand.nextInt(500_000));
            list5.add(rand.nextInt(500_000));
            list6.add(rand.nextInt(500_000));

        }
        for (int i = 0; i < 50_000; i++) {
            // add 50,000 list4 and above
            list4.add(rand.nextInt(500_000));
            list5.add(rand.nextInt(500_000));
            list6.add(rand.nextInt(500_000));

        }
        for (int i = 0; i < 50_000; i++) {
            // add 50,000 list5 and above
            list5.add(rand.nextInt(500_000));
            list6.add(rand.nextInt(500_000));

        }
        for (int i = 0; i < 50_000; i++) {
            // add 50,000 elements to all list6
            list6.add(rand.nextInt(500_000));

        }
        // mergeSort(list1);
        // for (Integer elem : list1) {
        //     System.out.println(elem);
        // }
        System.out.println("Array Size  |  Selection    Merge    Quick    Heap    Radix");
        System.out.printf("50,000        %,d millis  %,d millis", selectionSort(list1), mergeSort(list1));
        
    }

    public static void main(String[] args) {
        mainHelper();
    }

}
