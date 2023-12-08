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

    private int getResizedKeyIndex(K key, int iteration, int newSize) {
        return (key.hashCode() + (iteration + iteration * iteration) / 2) % newSize;
    }

    @Override
    public int size() {
        // size of map
        return entries.length;
    }

    @Override
    public boolean isEmpty() {
        // numElements is the amount of keys in map
        return numElements == 0;
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
        // initial index from probing function
        int index = getKeyIndex(key, 0);

        for (int i = 0; i < entries.length; i++) {
            // the slot from initial index
            Map.Entry<K, V> entry = entries[index];

            if (entry == null) {
                // a new key will be inserted
                if ((double) numElements / entries.length > 0.5) {
                    // resizing if entries is half full
                    resize();
                }
                // set the entry at index as the key value pair
                entries[index] = new SimpleEntry<>(key, value);
                numElements++;

                // there was no previous value return null
                return null;
            } else if (entry.getKey().equals(key)) {
                // previous key pair replace the value 
                V oldValue = entry.getValue(); // store the previous value
                entries[index].setValue(value);

                return oldValue; // return the old value
            }

            index = getKeyIndex(key, i + 1);
        }
        return null;
    }

    private void resize() {
        // double old size to keep map power of 2
        int newSize = entries.length * 2;
        Map.Entry<K,V>[] newEntries = new Map.Entry[newSize];
    
        for (Map.Entry<K, V> entry : entries) {
            if (entry != null) {
                // entry contains a mapping
                int newIndex = getResizedKeyIndex(entry.getKey(), 0, newSize);
                for (int i = 0; i < newSize; i++) {
                    int newIndexAttempt = getResizedKeyIndex(entry.getKey(), i, newSize);
                    if (newEntries[newIndexAttempt] == null) {
                        newEntries[newIndexAttempt] = entry;
                        break;
                    }
                }
            }
        }
    
        this.entries = newEntries; // updating the entries to new entries
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
        Set<K> keySet = new HashSet<>(numElements);
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
