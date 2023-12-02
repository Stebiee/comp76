import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.ListIterator;
import java.util.function.Function;

/**
 * Assignment for the Hashing module. Implement a HashMap that uses quadratic probing as a
 * hash collision avoidance method.
 * You need to implement all the TODOs.
 * @author Esteban Madrigal
 */
public class MyHashMap<K, V> implements Map<K, V> {
    // We start with a table size that is a power of 2. The combination of this table size
    // and the quadratic probing formula guarantees that all slots will be visited.
    public static int INITIAL_TABLE_SIZE = 4;

    // The array to use for the hash table. 
    // NOTE We store Objects in this array as it is not possible to create an array of Type V due
    // to type erasure.
    @SuppressWarnings("rawtypes")
    Map.Entry entries[] = new Map.Entry[INITIAL_TABLE_SIZE];

    // The number of elements in the hash table.
    int numElements = 0;

    /**
     * Returns the index to use. This uses the fact that if the table size is a power of two, 
     * and the probing function is (i + i^2)/2 then all slots will be visited. See
     * https://research.cs.vt.edu/AVresearch/hashing/quadratic.php
     * @param key
     * @param iteration The iteration of quadratic probing we are going to use.
     * @return
     */
    private int getKeyIndex(K key, int iteration) {
        return (key.hashCode() + (iteration + iteration*iteration)/2) % entries.length;
    }

    private int getValueIndex(V value, int iteration) {
        return (value.hashCode() + (iteration + iteration * iteration) / 2) % entries.length;
    }

    @Override
    public int size() {
        return numElements;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        // iterate through entries until key is found
        for (int i = 0; i < entries.length; i++) {
            Map.Entry<K, V> entry = entries[getKeyIndex((K) key, i)];

            if (entry == null) {
                // slot in entries is empty
                return false;
            }

            if (entry.getKey().equals(key)) {
                // slot in entries is key
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        // iterate through entries until value is found
        for (int i = 0; i < entries.length; i++) {
            Map.Entry<K, V> entry = entries[getValueIndex((V) value, i)];

            if (entry == null) {
                // slot in entries is empty
                return false;
            }

            if (entry.getValue().equals(value)) {
                // slot in entries is key
                return true;
            }
        }

        return false;
    }

    @Override
    public V get(Object key) {
        // iterate through entries until key is found
        for (int i = 0; i < entries.length; i++) {
            Map.Entry<K, V> entry = entries[getKeyIndex((K) key, i)];

            if (entry == null) {
                // slot in entries is empty
                return null;
            }

            if (entry.getKey().equals(key)) {
                // slot in entries is key
                return entry.getValue();
            }
        }

        return null;
    }

    @Override
    public V put(K key, V value) {
        int index = getKeyIndex(key, 0);

        for (int i = 0; i < entries.length; i++) {
            Map.Entry<K, V> entry = entries[index];

            if (entry == null) {
                entries[index] = new SimpleEntry<>(key, value);
                numElements++;

                // Check if resizing is needed (50% full condition)
                if ((double) numElements / entries.length >= 0.5) {
                    resizeAndRehash();
                }

                return null;
            } else if (entry.getKey().equals(key)) {
                V oldValue = entry.getValue();
                entries[index].setValue(value);
                return oldValue;
            }

            index = getKeyIndex(key, i + 1);
        }
        return null;
    }

    // Resize the array and rehash the elements
    private void resizeAndRehash() {
        // Double the array size and create a new array
        int newSize = entries.length * 2;
        Map.Entry[] newEntries = new Map.Entry[newSize];

        // Rehash each element into the new array
        for (Map.Entry<K, V> entry : entries) {
            if (entry != null) {
                int newIndex = getKeyIndex(entry.getKey(), 0);
                for (int i = 0; i < newSize; i++) {
                    int newIndexAttempt = (newIndex + i + i * i) % newSize;
                    if (newEntries[newIndexAttempt] == null) {
                        newEntries[newIndexAttempt] = entry;
                        break;
                    }
                }
            }
        }

        // Update the array reference and resize-related variables
        entries = newEntries;
        // You may need to update any other variables related to the array size or resizing logic
    }

    @Override
    public V remove(Object key) {
        int index = getKeyIndex((K) key, 0);

        for (int i = 0; i < entries.length; i++) {
            Map.Entry<K, V> entry = entries[index];

            if (entry == null) {
                return null;
            }

            if (entry.getKey().equals(key)) {
                V removedValue = entry.getValue();
                entries[index] = null;
                numElements--;
                return removedValue;
            }

            index = getKeyIndex((K) key, i + 1);
        }

        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        Arrays.fill(entries, null);
        numElements = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for (Map.Entry<K, V> entry : entries) {
            if (entry != null) {
                keySet.add(entry.getKey());
            }
        }
        return keySet;
    }

    @Override
    public Collection<V> values() {
        List<V> values = new ArrayList<>();
        for (Map.Entry<K, V> entry : entries) {
            if (entry != null) {
                values.add(entry.getValue());
            }
        }
        return values;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entrySet = new HashSet<>();
        for (Map.Entry<K, V> entry : entries) {
            if (entry != null) {
                entrySet.add(entry);
            }
        }
        return entrySet;
    }
}
