package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Main class to demonstrate the functionality of the ElectricAppliance hierarchy
 * and the CustomApplianceSet collection. The main method is broken down into
 * smaller, logical functions for better readability and modularity.
 */
public class Main {
    /** Unique appliance: Samsung Fridge. */
    private static ElectricAppliance fridge1;
    /** Unique appliance: MacBook Pro Laptop (original instance). */
    private static ElectricAppliance laptop1;
    /** Unique appliance: Philips Hair Dryer. */
    private static ElectricAppliance dryer1;
    /** Unique appliance: Mini-Bar Refrigerator. */
    private static ElectricAppliance fridge2;
    /** Appliance instance that is logically a duplicate of laptop1 (used for uniqueness tests). */
    private static ElectricAppliance laptop2_duplicate;
    /** A CustomApplianceSet initialized using the empty constructor. */
    private static CustomApplianceSet setEmpty;
    /** A CustomApplianceSet initialized using the collection constructor (used for core tests). */
    private static Set<ElectricAppliance> setCollection;

    /**
     * Initializes all test fixtures (ElectricAppliance objects).
     */
    private static void initializeFixtures() {
        System.out.println("--- 1. Initializing Appliances for Testing ---");

        fridge1 = new Refrigerator("Samsung Fridge", 150, 0.8, true);
        laptop1 = new Laptop("MacBook Pro", 60, 0.2, 16);
        dryer1 = new HairDryer("Philips Dryer", 1800, 5.5, 3);
        fridge2 = new Refrigerator("Mini-Bar", 80, 0.5, false);
        laptop2_duplicate = new Laptop("MacBook Pro", 60, 0.2, 16);
        setEmpty = new CustomApplianceSet();
    }

    /**
     * Demonstrates the three constructors and checks basic state.
     */
    private static void demonstrateConstructors() {
        System.out.println("\n--- 2. Testing CustomApplianceSet Constructors and Basic Operations ---");

        // Constructor 1: Empty Constructor
        System.out.println("\n[SET Empty]: Size = " + setEmpty.size() + ", Initial capacity = 15.");
        setEmpty.add(fridge2); // Use setEmpty for a basic operation
        System.out.println("SET Empty: Added Fridge2. Size: " + setEmpty.size());
        setEmpty.remove(fridge2);
        System.out.println("SET Empty: Removed Fridge2. Size: " + setEmpty.size());

        // Constructor 2: Single Object Constructor
        Set<ElectricAppliance> setSingle = new CustomApplianceSet(dryer1);
        System.out.println("[SET Single]: Size = " + setSingle.size() + ". Contains Dryer? " + setSingle.contains(dryer1));
        setSingle.add(fridge2); // Use setSingle for a basic operation
        System.out.println("SET Single: Added Fridge2. Size: " + setSingle.size());

        // Constructor 3: Collection Constructor
        List<ElectricAppliance> initialList = new ArrayList<>(Arrays.asList(fridge1, laptop1, dryer1, fridge1, laptop2_duplicate));

        // The cast is necessary here to avoid ambiguity with the single-element constructor
        setCollection = new CustomApplianceSet(initialList);
        System.out.println("[SET Collection]: Source list size = " + initialList.size()
                + ", Final Set size (unique) = " + setCollection.size()); // Size: 3
    }

    /**
     * Demonstrates sequential Add, Remove, and Uniqueness properties.
     */
    private static void demonstrateCoreSetOperations() {
        System.out.println("\n--- 3. Testing Add, Remove, and Uniqueness ---");

        setCollection.add(fridge2);
        System.out.println("Added Fridge2. New size: " + setCollection.size()); // Size: 4

        boolean addedDuplicate = setCollection.add(fridge1);
        System.out.println("Attempted to add Fridge1 (duplicate). Was added? " + addedDuplicate + ". Final size: "
                + setCollection.size());

        boolean removedLaptop = setCollection.remove(laptop1);
        System.out.println("Removed Laptop1. Was removed? " + removedLaptop + ". New size: "
                + setCollection.size()); // Size: 3

        boolean removedDryer = setCollection.remove(dryer1);
        System.out.println("Removed Dryer1. Was removed? " + removedDryer + ". Final size: "
                + setCollection.size()); // Size: 2

        boolean removedAgain = setCollection.remove(laptop1);
        System.out.println("Attempted to remove Laptop1 again. Was removed? " + removedAgain);
    }

    /**
     * Demonstrates the retainAll batch operation.
     */
    private static void demonstrateRetainAll() {
        System.out.println("\n--- 4. Testing retainAll() Functionality ---");

        // Create a temporary set to test retainAll
        Set<ElectricAppliance> setTemp = new CustomApplianceSet(Arrays.asList(dryer1, fridge2, laptop1));
        System.out.println("SET Temp before retainAll: Size " + setTemp.size() + ", Elements: Dryer1, Fridge2, Laptop1");

        // Only retain fridge2 and a non-existent item
        Collection<ElectricAppliance> retainOnlyFridge2 = Arrays.asList(fridge2, new HairDryer("Ghost Dryer",
                100, 0.0, 1));

        boolean modified = setTemp.retainAll(retainOnlyFridge2);
        System.out.println("SET Temp after retainAll: Changed? " + modified + ", New size: " + setTemp.size());

        // Output remaining element(s)
        System.out.print("Remaining elements: ");
        setTemp.forEach(a -> System.out.print(a.getName() + " "));
        System.out.println();
    }

    /**
     * Demonstrates capacity increase logic and iterator functionality.
     */
    private static void demonstrateCapacityAndIterator() {
        System.out.println("\n--- 5. Testing Iterator and Capacity Increase ---");

        // Demonstrate Capacity Increase
        CustomApplianceSet largeSet = new CustomApplianceSet();
        for (int i = 0; i < 20; i++) {
            largeSet.add(new Laptop("Laptop-" + i, 50 + i, 0.1, 13));
        }
        System.out.println("Large set size after 20 unique adds: " + largeSet.size());

        // Demonstrate Iterator (including remove via iterator)
        Iterator<ElectricAppliance> it = largeSet.iterator();
        System.out.println("Iterating over large set (first 5 items):");
        int count = 0;
        while (it.hasNext() && count < 5) {
            ElectricAppliance app = it.next();
            System.out.println(" - " + app.getName());
            if (count == 2) {
                it.remove(); // Remove the 3rd item (e.g., Laptop-2)
                System.out.println("   -> Removed item via iterator. Size: " + largeSet.size());
            }
            count++;
        }
    }

    /**
     * Demonstrates exception handling for critical methods.
     */
    private static void demonstrateExceptionHandling() {
        System.out.println("\n--- 6. Testing Exception Handling ---");

        // Test NullPointerException
        try {
            System.out.println("Attempting to add a null appliance (expected failure)...");
            setCollection.add(null);
        } catch (NullPointerException e) {
            System.err.println("Successfully handled NullPointerException: " + e.getMessage());
        }

        // Test NoSuchElementException (for Iterator.next())
        try {
            Iterator<ElectricAppliance> emptyIt = setEmpty.iterator();
            System.out.println("Attempting to call next() on an empty iterator (expected failure)...");
            emptyIt.next();
        } catch (NoSuchElementException e) {
            System.err.println("Successfully handled NoSuchElementException: " + e.getClass().getSimpleName());
        }
    }

    /**
     * The main entry point for the application.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        // Run all demonstration methods sequentially
        initializeFixtures();
        demonstrateConstructors();
        demonstrateCoreSetOperations();
        demonstrateRetainAll();
        demonstrateCapacityAndIterator();
        demonstrateExceptionHandling();

        // Final cleanup
        System.out.println("\n--- 7. Final Cleanup ---");
        System.out.println("Clearing setCollection. Size before: " + setCollection.size());
        setCollection.clear();
        System.out.println("Size after clear: " + setCollection.size() + ", Empty? " + setCollection.isEmpty());
    }
}
