package org.example;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Custom collection class that implements the Set interface for ElectricAppliance objects.
 * It uses an array as its internal data structure to store unique elements.
 * The initial capacity is 15, and the capacity increases by 30% when full.
 */
public class CustomApplianceSet implements Set<ElectricAppliance> {

    /** The default initial number of elements the internal array can hold. */
    private static final int INITIAL_CAPACITY = 15;
    /** The factor by which the internal array capacity increases when it is full (1.3 = 30% increase). */
    private static final double CAPACITY_INCREASE_FACTOR = 1.3;
    /** Internal array to store the set elements. */
    private ElectricAppliance[] elements;
    /** The current number of elements in the set. */
    private int size;


    /**
     * Constructs an empty CustomApplianceSet with the default initial capacity (15). (Constructor 1)
     */
    public CustomApplianceSet() {
        this.elements = new ElectricAppliance[INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Constructs a CustomApplianceSet containing the specified single element. (Constructor 2)
     * The initial capacity is 15.
     * @param appliance The single ElectricAppliance object to be added to the set.
     */
    public CustomApplianceSet(ElectricAppliance appliance) {
        this(); // Call the no-argument constructor
        if (appliance != null) {
            this.add(appliance);
        }
    }

    /**
     * Constructs a CustomApplianceSet containing the elements of the specified collection. (Constructor 3)
     * @param collection The collection whose elements are to be placed into this set.
     * @throws NullPointerException if the specified collection is null.
     */
    public CustomApplianceSet(Collection<? extends ElectricAppliance> collection) {
        if (collection == null) {
            throw new NullPointerException("Collection cannot be null.");
        }
        // Initialize capacity based on collection size or default, plus a safety margin
        int initialCapacity = Math.max(INITIAL_CAPACITY, (int) (collection.size() * CAPACITY_INCREASE_FACTOR));

        this.elements = new ElectricAppliance[initialCapacity];
        this.size = 0;

        this.addAll(collection);
    }

    /**
     * Increases the capacity of the array by 30% if the size reaches' capacity.
     */
    private void ensureCapacity() {
        if (size == elements.length) {
            int newCapacity = (int) (elements.length * CAPACITY_INCREASE_FACTOR);
            if (newCapacity <= elements.length) {
                newCapacity = elements.length + 1; // Ensure at least 1 increase
            }
            elements = Arrays.copyOf(elements, newCapacity);
            System.out.printf("[INFO]: Array capacity increased from %d to %d.\n", size, newCapacity);
        }
    }

    /**
     * Finds the index of a specified element.
     * @param o The element to search for.
     * @return The index of the element, or -1 if not found.
     */
    private int indexOf(Object o) {
        // Note: For array-based Set, the search complexity is O(N).
        if (o == null) {
            // Nulls are not supported by the add method in this implementation,
            // but we check the existing elements array just in case.
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (o.equals(elements[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Returns the number of elements in this set (its cardinality).
     * @return The number of elements in this set.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns {@code true} if this set contains no elements.
     * @return {@code true} if this set is empty.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns {@code true} if this set contains the specified element.
     * @param o Element whose presence in this set is to be tested.
     * @return {@code true} if this set contains the specified element.
     */
    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    /**
     * Adds the specified element to this set if it is not already present.
     * @param electricAppliance Element to be added to this set.
     * @return {@code true} if this set did not already contain the specified element.
     * @throws NullPointerException if the specified element is null.
     */
    @Override
    public boolean add(ElectricAppliance electricAppliance) {
        if (electricAppliance == null) {
            throw new NullPointerException("Cannot add null elements to this set.");
        }

        if (contains(electricAppliance)) {
            return false; // Element is already present (Set property)
        }

        ensureCapacity();
        elements[size] = electricAppliance;
        size++;
        return true;
    }

    /**
     * Removes the specified element from this set if it is present.
     * @param o Object to be removed from this set, if present.
     * @return {@code true} if this set contained the specified element.
     */
    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index < 0) {
            return false; // Element not found
        }

        // Shift elements to the left to fill the gap (Array-specific removal)
        int numMoved = size - 1 - index;
        if (numMoved > 0) {
            System.arraycopy(elements, index + 1, elements, index, numMoved);
        }
        elements[--size] = null; // Clear the last reference and decrease size
        return true;
    }

    /**
     * Removes all the elements from this set. The set will be empty after this call returns.
     */
    @Override
    public void clear() {
        // Clear all references for garbage collection and reset size
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    /**
     * Returns an iterator over the elements in this set.
     * @return An iterator over the elements in this set.
     */
    @Override
    public Iterator<ElectricAppliance> iterator() {
        return new ApplianceIterator();
    }

    /**
     * A simple implementation of an Iterator for the array-based set.
     */
    private class ApplianceIterator implements Iterator<ElectricAppliance> {
        private int cursor = 0; // Index of next element to return
        private int lastRet = -1; // Index of last element returned; -1 if no such element

        /**
         * Returns {@code true} if the iteration has more elements.
         * @return {@code true} if the iterator has more elements.
         */
        @Override
        public boolean hasNext() {
            return cursor < size;
        }

        /**
         * Returns the next element in the iteration.
         * @return The next element in the iteration.
         * @throws NoSuchElementException if the iteration has no more elements.
         */
        @Override
        public ElectricAppliance next() {
            if (cursor >= size) {
                throw new NoSuchElementException();
            }
            lastRet = cursor;
            return elements[cursor++];
        }

        /**
         * Removes from the underlying collection the last element returned by this iterator.
         * The element can only be removed once per call to {@code next()}.
         * @throws IllegalStateException if the {@code next} method has not yet been called,
         * or the {@code remove} method has already been called after the last call to {@code next}.
         */
        @Override
        public void remove() {
            if (lastRet < 0) {
                throw new IllegalStateException();
            }

            // Call the main class's remove method, which shifts array elements
            CustomApplianceSet.this.remove(elements[lastRet]);
            cursor = lastRet; // Adjust cursor after removal
            lastRet = -1; // Invalidate lastRet
        }
    }

    /**
     * Adds all the elements in the specified collection to this set if they're not already present.
     * @param c Collection containing elements to be added to this set.
     * @return {@code true} if this set changed as a result of the call.
     */
    @Override
    public boolean addAll(Collection<? extends ElectricAppliance> c) {
        boolean modified = false;

        for (ElectricAppliance appliance : c) {
            if (add(appliance)) {
                modified = true;
            }
        }
        return modified;
    }

    /**
     * Returns {@code true} if this set contains all the elements of the specified collection.
     * @param c Collection to be checked for containment in this set.
     * @return {@code true} if this set contains all the elements of the specified collection.
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Removes from this set all of its elements that are contained in the specified collection.
     * @param c Collection containing elements to be removed from this set.
     * @return {@code true} if this set changed as a result of the call.
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = false;

        for (Object o : c) {
            if (this.remove(o)) {
                modified = true;
            }
        }
        return modified;
    }

    /**
     * Returns an array containing all the elements in this set in proper sequence.
     * @return An array containing all the elements in this set.
     */
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    /**
     * Returns an array containing all the elements in this set in proper sequence;
     * the runtime type of the returned array is that of the specified array.
     * @param a The array into which the elements of this set are to be stored, if it is big enough;
     * otherwise, a new array of the same runtime type is allocated for this purpose.
     * @return An array containing all the elements in this set.
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            // If the array is too small, create a new one of the correct component type.
            return (T[]) Arrays.copyOf(elements, size);
        }

        // Copy elements into the provided array
        for (int i = 0; i < size; i++) {
            a[i] = (T) elements[i];
        }

        if (a.length > size) {
            a[size] = null; // Set the element immediately following the last set element to null
        }

        return a;
    }

    /**
     * Retains only the elements in this set that are contained in the specified collection.
     * Implemented using the two-pointer approach for array-based structure efficiency.
     * @param c Collection containing elements to be retained in this set.
     * @return {@code true} if this set changed as a result of the call.
     * @throws NullPointerException if the specified collection is null.
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null) {
            throw new NullPointerException("Collection cannot be null.");
        }

        boolean modified = false;
        int writeIndex = 0;

        // Use two-pointer approach: readIndex iterates through all elements,
        // writeIndex tracks where retained elements should be placed.
        for (int readIndex = 0; readIndex < size; readIndex++) {
            ElectricAppliance currentAppliance = elements[readIndex];

            // Check if the current element is contained in the target collection 'c'
            if (c.contains(currentAppliance)) {
                if (writeIndex != readIndex) {
                    elements[writeIndex] = currentAppliance;
                }
                writeIndex++;
            } else {
                modified = true;
            }
        }

        // Cleanup: If elements were removed (writeIndex < original size), clear trailing references and update size.
        if (writeIndex < size) {
            modified = true;

            // Null out remaining references to allow garbage collection
            for (int i = writeIndex; i < size; i++) {
                elements[i] = null;
            }
            size = writeIndex; // Update the new size
        }

        return modified;
    }
}
