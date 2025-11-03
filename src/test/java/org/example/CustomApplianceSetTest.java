package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive Unit tests for the CustomApplianceSet class,
 * validating its implementation of the Set interface using an array.
 */
class CustomApplianceSetTest {

    // --- Fixtures (Test Data) ---
    private ElectricAppliance fridge1;
    private ElectricAppliance laptop1;
    private ElectricAppliance dryer1;
    private ElectricAppliance fridge2;
    private ElectricAppliance laptop2_duplicate;
    private CustomApplianceSet emptySet;

    @BeforeEach
    void setUp() {
        // Unique appliances
        fridge1 = new Refrigerator("Samsung Fridge", 150, 0.8, true);
        laptop1 = new Laptop("MacBook Pro", 60, 0.2, 16);
        dryer1 = new HairDryer("Philips Dryer", 1800, 5.5, 3);
        fridge2 = new Refrigerator("Mini-Bar", 80, 0.5, false);

        // Appliance duplicate of laptop1 (same equals/hashCode)
        laptop2_duplicate = new Laptop("MacBook Pro", 60, 0.2, 16);

        emptySet = new CustomApplianceSet();
    }

    @Test
    @DisplayName("Test Constructor 1: Empty Set Initialization")
    void testConstructorEmpty() {
        assertTrue(emptySet.isEmpty(), "New set should be empty.");
        assertEquals(0, emptySet.size(), "Size should be zero.");
    }

    @Test
    @DisplayName("Test Constructor 2: Single Element")
    void testConstructorSingleElement() {
        CustomApplianceSet set = new CustomApplianceSet(fridge1);
        assertEquals(1, set.size(), "Size should be 1.");
        assertTrue(set.contains(fridge1), "Set must contain the initial element.");
        assertFalse(set.isEmpty(), "Set should not be empty.");
    }

    @Test
    @DisplayName("Test Constructor 2: Null Element")
    void testConstructorSingleElementNull() {
        CustomApplianceSet set = new CustomApplianceSet((ElectricAppliance) null);
        assertEquals(0, set.size(), "Size should be 0 for null initial element.");
    }

    @Test
    @DisplayName("Test Constructor 3: Collection with Unique Elements")
    void testConstructorCollectionUnique() {
        List<ElectricAppliance> list = Arrays.asList(fridge1, laptop1, dryer1);

        CustomApplianceSet set = new CustomApplianceSet(list);

        assertEquals(3, set.size(), "Size must match unique elements in collection.");
        assertTrue(set.contains(laptop1));
    }

    @Test
    @DisplayName("Test Constructor 3: Collection with Duplicates")
    void testConstructorCollectionDuplicates() {
        List<ElectricAppliance> list = Arrays.asList(fridge1, laptop1, laptop2_duplicate, fridge1);

        CustomApplianceSet set = new CustomApplianceSet(list);

        assertEquals(2, set.size(), "Size must exclude duplicates (laptop1 and laptop2 are equal).");
        assertTrue(set.contains(fridge1));
        assertTrue(set.contains(laptop1));
    }

    @Test
    @DisplayName("Test Constructor 3: Null Collection throws Exception")
    void testConstructorCollectionNull() {
        assertThrows(NullPointerException.class, () -> new CustomApplianceSet((Collection<ElectricAppliance>) null),
                "Passing a null collection should throw NullPointerException.");
    }

    @Test
    @DisplayName("Test Add: Adding a new unique element")
    void testAddUnique() {
        assertTrue(emptySet.add(fridge1), "Adding a unique element should return true.");
        assertEquals(1, emptySet.size());
    }

    @Test
    @DisplayName("Test Add: Adding a duplicate element")
    void testAddDuplicate() {
        emptySet.add(laptop1);
        assertFalse(emptySet.add(laptop2_duplicate), "Adding a duplicate element should return false.");
        assertEquals(1, emptySet.size(), "Size must not change after adding a duplicate.");
    }

    @Test
    @DisplayName("Test Add: Adding null throws NullPointerException")
    void testAddNull() {
        assertThrows(NullPointerException.class, () -> emptySet.add(null),
                "Adding null should throw NullPointerException.");
        assertEquals(0, emptySet.size(), "Size must not change.");
    }

    @Test
    @DisplayName("Test Remove: Removing existing element")
    void testRemoveExisting() {
        emptySet.add(dryer1);
        emptySet.add(fridge1);
        assertTrue(emptySet.remove(dryer1), "Removing an existing element should return true.");
        assertEquals(1, emptySet.size());
        assertFalse(emptySet.contains(dryer1));
        assertTrue(emptySet.contains(fridge1));
    }

    @Test
    @DisplayName("Test Remove: Removing non-existing element")
    void testRemoveNonExisting() {
        emptySet.add(dryer1);
        assertFalse(emptySet.remove(laptop1), "Removing a non-existing element should return false.");
        assertEquals(1, emptySet.size(), "Size must not change.");
    }

    @Test
    @DisplayName("Test Remove: Removing element and checking array shift")
    void testRemoveAndShift() {
        emptySet.add(fridge1);
        emptySet.add(dryer1);
        emptySet.add(laptop1); // Last element at index 2 (size 3)

        // Remove element in the middle (dryer1, index 1)
        emptySet.remove(dryer1);

        assertEquals(2, emptySet.size());
        assertTrue(emptySet.contains(fridge1));
        assertTrue(emptySet.contains(laptop1), "Laptop1 must still be present after middle element removal.");
    }

    @Test
    @DisplayName("Test Contains: Check presence of various elements")
    void testContains() {
        emptySet.add(fridge1);
        assertTrue(emptySet.contains(fridge1));
        assertFalse(emptySet.contains(laptop1));
        // Check contains with duplicate object instance
        assertTrue(emptySet.contains(new Refrigerator("Samsung Fridge", 150, 0.8, true)));
    }

    @Test
    @DisplayName("Test Clear: Clearing a non-empty set")
    void testClear() {
        emptySet.add(fridge1);
        emptySet.add(dryer1);
        assertFalse(emptySet.isEmpty());
        emptySet.clear();
        assertTrue(emptySet.isEmpty());
        assertEquals(0, emptySet.size());
        assertFalse(emptySet.contains(fridge1), "Set should not contain cleared elements.");
    }

    @Test
    @DisplayName("Test Capacity: Initial capacity and no resize")
    void testCapacityNoResize() {
        for (int i = 0; i < 15; i++) {
            emptySet.add(new Laptop("Test Laptop " + i, 10, 0.1, 13));
        }
        assertEquals(15, emptySet.size(), "Set should hold up to INITIAL_CAPACITY.");
    }

    @Test
    @DisplayName("Test Capacity: Forcing a resize (15 -> 19)")
    void testCapacityForcedResize() {
        // Fill initial capacity
        for (int i = 0; i < 15; i++) {
            emptySet.add(new Refrigerator("Unit " + i, 10, 0.1, false));
        }
        // Force resize to 15 * 1.3 = 19.5 -> 19 (first resize)
        assertTrue(emptySet.add(new Refrigerator("Unit 15", 10, 0.1, false)));
        assertEquals(16, emptySet.size(), "Size must be 16 after first element added post-resize.");

        // Fill until next resize (19)
        for (int i = 16; i < 20; i++) {
            emptySet.add(new Refrigerator("Unit " + i, 10, 0.1, false));
        }
        assertEquals(20, emptySet.size());
    }

    // --- 4. Batch Operations Tests (addAll, removeAll, containsAll, retainAll) ---

    @Test
    @DisplayName("Test AddAll: Adding a collection with mixed new and duplicate elements")
    void testAddAllMixed() {
        emptySet.add(fridge1); // Size 1
        List<ElectricAppliance> toAdd = Arrays.asList(dryer1, laptop1, fridge1); // 2 new, 1 duplicate

        assertTrue(emptySet.addAll(toAdd), "Set should be modified.");
        assertEquals(3, emptySet.size());
        assertTrue(emptySet.contains(dryer1));
        assertTrue(emptySet.contains(laptop1));
    }

    @Test
    @DisplayName("Test ContainsAll: Subcollection check")
    void testContainsAll() {
        emptySet.add(fridge1);
        emptySet.add(dryer1);
        Collection<ElectricAppliance> subCollection = Arrays.asList(fridge1, dryer1);
        assertTrue(emptySet.containsAll(subCollection));
        assertTrue(emptySet.contains(fridge1));
        assertFalse(emptySet.containsAll(Arrays.asList(fridge1, laptop1))); // laptop1 is missing
    }

    @Test
    @DisplayName("Test RemoveAll: Removing a subcollection")
    void testRemoveAll() {
        emptySet.add(fridge1);
        emptySet.add(dryer1);
        emptySet.add(laptop1); // Size 3

        // fridge1, laptop1 are removed
        Collection<ElectricAppliance> toRemove = Arrays.asList(fridge1, laptop2_duplicate, fridge2);
        assertTrue(emptySet.removeAll(toRemove), "Set should be modified.");
        assertEquals(1, emptySet.size());
        assertTrue(emptySet.contains(dryer1), "Dryer1 should be the only remaining element.");
        assertFalse(emptySet.contains(fridge1));
    }

    @Test
    @DisplayName("Test RemoveAll: Removing all elements")
    void testRemoveAllFull() {
        emptySet.add(fridge1);
        emptySet.add(dryer1);
        assertTrue(emptySet.removeAll(Arrays.asList(fridge1, dryer1)));
        assertTrue(emptySet.isEmpty());
    }

    @Test
    @DisplayName("Test RetainAll: Retaining intersection of two sets")
    void testRetainAllIntersection() {
        emptySet.add(fridge1);
        emptySet.add(dryer1);
        emptySet.add(laptop1); // Size 3

        Collection<ElectricAppliance> toRetain = Arrays.asList(dryer1, fridge2); // Only dryer1 is common
        assertTrue(emptySet.retainAll(toRetain), "Set should be modified.");
        assertEquals(1, emptySet.size());
        assertTrue(emptySet.contains(dryer1), "Only dryer1 should remain.");
        assertFalse(emptySet.contains(fridge1));
        assertFalse(emptySet.contains(laptop1));
    }

    @Test
    @DisplayName("Test RetainAll: Retaining all elements (no change)")
    void testRetainAllNoChange() {
        emptySet.add(fridge1);
        emptySet.add(dryer1);
        Collection<ElectricAppliance> superSet = Arrays.asList(fridge1, dryer1, laptop1);

        assertFalse(emptySet.retainAll(superSet), "Set should not be modified.");
        assertEquals(2, emptySet.size());
    }

    @Test
    @DisplayName("Test RetainAll: Retaining none (clear)")
    void testRetainAllClear() {
        emptySet.add(fridge1);
        assertTrue(emptySet.retainAll(new ArrayList<>()), "Set should be modified.");
        assertTrue(emptySet.isEmpty());
    }

    @Test
    @DisplayName("Test RetainAll: Null collection throws exception")
    void testRetainAllNull() {
        emptySet.add(fridge1);
        assertThrows(NullPointerException.class, () -> emptySet.retainAll(null));
    }

    @Test
    @DisplayName("Test Iterator: next() and hasNext()")
    void testIteratorNextHasNext() {
        emptySet.add(fridge1);
        emptySet.add(dryer1);
        Iterator<ElectricAppliance> iterator = emptySet.iterator();

        assertTrue(iterator.hasNext());
        assertNotNull(iterator.next());
        assertTrue(iterator.hasNext());
        assertNotNull(iterator.next());
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    @DisplayName("Test Iterator: remove() after next()")
    void testIteratorRemoveAfterNext() {
        emptySet.add(fridge1);
        emptySet.add(dryer1);
        Iterator<ElectricAppliance> iterator = emptySet.iterator();

        iterator.next(); // Position 0 (fridge1)
        iterator.remove(); // Remove fridge1
        assertEquals(1, emptySet.size());
        assertFalse(emptySet.contains(fridge1));

        iterator.next(); // Position 0 (dryer1)
        iterator.remove(); // Remove dryer1
        assertTrue(emptySet.isEmpty());
    }

    @Test
    @DisplayName("Test Iterator: remove() without prior next() throws IllegalStateException")
    void testIteratorRemoveWithoutNext() {
        emptySet.add(fridge1);
        Iterator<ElectricAppliance> iterator = emptySet.iterator();
        assertThrows(IllegalStateException.class, iterator::remove);
    }

    @Test
    @DisplayName("Test Iterator: double remove() throws IllegalStateException")
    void testIteratorDoubleRemove() {
        emptySet.add(fridge1);
        Iterator<ElectricAppliance> iterator = emptySet.iterator();
        iterator.next();
        iterator.remove();
        assertThrows(IllegalStateException.class, iterator::remove);
    }

    @Test
    @DisplayName("Test toArray(): Correct size and content for Object array")
    void testToArrayObject() {
        emptySet.add(fridge1);
        emptySet.add(dryer1);
        Object[] array = emptySet.toArray();

        assertEquals(2, array.length);
        assertTrue(Arrays.asList(array).contains(fridge1));
        assertTrue(Arrays.asList(array).contains(dryer1));
        // Check that the returned array is truly Object[] (or ElectricAppliance[]), not a specific subclass
        assertEquals(ElectricAppliance[].class, array.getClass());
    }

    @Test
    @DisplayName("Test toArray(T[] a): Provided array is oversized")
    void testToArrayOversized() {
        emptySet.add(fridge1);
        ElectricAppliance[] a = new ElectricAppliance[3];
        ElectricAppliance[] result = emptySet.toArray(a);

        assertSame(a, result, "Should return the same provided array.");
        assertEquals(fridge1, result[0]);
        assertNull(result[1], "Element after last item should be null.");
    }

    @Test
    @DisplayName("Test toArray(T[] a): Provided array is undersized (forces copy)")
    void testToArrayUndersized() {
        emptySet.add(fridge1);
        ElectricAppliance[] a = new ElectricAppliance[0];
        ElectricAppliance[] result = emptySet.toArray(a);

        assertNotSame(a, result, "Should return a new array.");
        assertEquals(1, result.length);
        assertEquals(fridge1, result[0]);
    }
}
