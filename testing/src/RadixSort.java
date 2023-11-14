import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class RadixSort {

    public static void main(String[] args) {
        ArrayList<Integer> data = new ArrayList<>(Arrays.asList(122, 431, 565, 22, 1, 47, 787));
        System.out.println("Before sorting");
        System.out.println(data);
        RadixSort rs = new RadixSort();
        rs.radixsort(data);
        System.out.println("After sorting");
        System.out.println(data);
    }

    void countingsort(ArrayList<Integer> list, int place) {
    int size = list.size();
    ArrayList<Integer> output = new ArrayList<>(Collections.nCopies(size, 0));

    ArrayList<Integer> tempcount = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0));

    for (int i = 0; i < size; i++)
        tempcount.set((list.get(i) / place) % 10, tempcount.get((list.get(i) / place) % 10) + 1);

    for (int i = 1; i < 10; i++)
        tempcount.set(i, tempcount.get(i) + tempcount.get(i - 1));

    for (int i = size - 1; i >= 0; i--) {
        output.set(tempcount.get((list.get(i) / place) % 10) - 1, list.get(i));
        tempcount.set((list.get(i) / place) % 10, tempcount.get((list.get(i) / place) % 10) - 1);
    }

    list.clear();
    list.addAll(output);
}

    

    void radixsort(ArrayList<Integer> list) {
        int max = list.get(0);

        for (int i = 1; i < list.size(); i++) {
            if (max < list.get(i)) {
                max = list.get(i);
            }
        }

        for (int p = 1; max / p > 0; p *= 10) {
            countingsort(list, p);
        }
    }
}
