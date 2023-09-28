import java.util.ArrayList;

/**
 * Generics assignment.
 * @author Esteban Madrigal
 */
public class Generics {

    /**
     * Sorts the passed ArrayList by starting with first element
     * and if one of the following elements are less than the current
     * the smaller replaces that index and the rest of the list
     * is shifted +1 index
     * @param list The list to be sorted.
     */
    public static <E extends Comparable<E>> void sort(ArrayList<E> list) {
        int currentMinIndex;
        E currentMin;
        
        // amount of times looped is list size - 1
        for(int i = 0; i < list.size() - 1; i++) {
            currentMinIndex = i;
            currentMin = list.get(currentMinIndex);

            // loop starts at +1 because it shouldnt compare index 0 and 0
            // and +i allows the +1 change to scale as i increases
            for(int j = 1 + i; j < list.size(); j++) {

                // checks if currentMin is less than following element
                // plus following element index++ etc.
                if(currentMin.compareTo(list.get(j)) > 0) {
                    // list.get(j) is less than or equals currentMin
                    currentMin = list.get(j);
                    currentMinIndex = j;
                }

            if(list.get(currentMinIndex) != list.get(i)){
                // shifts all current elements right 1 index
                // then inserts currentMin into index i
                list.add(i, currentMin);
                list.remove(currentMinIndex);
            }

            }
        }
    }
}
