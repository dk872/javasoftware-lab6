# Java_software-lab6

## Description
The core purpose of this project is to model a hierarchy of `Electric Appliances` and, crucially, to implement a custom array-based collection (`CustomApplianceSet`) that fully adheres to the `java.util.Set` interface.

The solution utilizes a base class (`ElectricAppliance`) and concrete derived classes (`Refrigerator`, `Laptop`, `HairDryer`) to manage specific characteristics. A dedicated container class (`ApartmentApplianceManager`) is used to handle a collection of these objects, performing key operations such as power calculation, sorting, and specific property searches. A strong emphasis is placed on exception handling to manage invalid data and search failures

The custom collection utilizes the `ElectricAppliance` hierarchy as its generic element type, demonstrating robust collection management, dynamic resizing, and strict adherence to the Set contract (uniqueness of elements).

## Calculation of the task variant
Number in the group list: **15**;

C2 = 15 % 2 = **1**
C3 = 15 % 3 = **0**.

## Features
### Appliance Hierarchy
- **Appliance Hierarchy**: implements a clean inheritance structure starting from the abstract ElectricAppliance base class.
- **Power State Management**: provides methods (`plugIn()`, `unplug()`) to simulate connecting appliances to a socket.
- **Consumption Calculation**: accurately calculates the total power consumed only by the appliances currently plugged in.
- **Data Sorting**: implements sorting of all appliances based on their power consumption (in Watts).
- **Advanced Search**: features a search method (`findByRadiationRange`) to find appliances within a specified range of Electromagnetic Radiation (EMR) levels.
- **Robust Exception Handling**: thoroughly validates input parameters (e.g., non-positive power, invalid search ranges) and handles logical failures (e.g., search returning no results) using checked and unchecked exceptions.
- **Javadoc Documentation**: includes comprehensive Javadoc comments for all classes, fields, and methods, adhering to coding standards.

### Custom Set Collection
- **Interface Implemented**: `java.util.Set<ElectricAppliance>`.
- **Internal Structure**: array (`ElectricAppliance[]`) for storing elements.
- **Initial Capacity**: the array is initialized with 15 elements.
- **Dynamic Resizing**: the array capacity automatically increases by 30% when the current capacity is exhausted, implemented in ensureCapacity().
- **Constructors**: implemented three mandatory constructors: empty constructor, constructor accepting a single `ElectricAppliance` object, constructor accepting a standard `Collection` of objects.
- **Full Contract Implementation**: implements all `Set` methods, including:
  - *Basic operations*: `add` (ensuring uniqueness), `remove`, `contains`.
  - *Batch operations*: `addAll`, `removeAll`, `containsAll`.
  - *Complex operation*: `retainAll` (implemented using an efficient two-pointer approach).
  - *Iterator*: implemented as a private inner class with full support for `hasNext()`, `next()`, and `remove()`.

## How to run
First, clone the repository and navigate into the project directory:
```
git clone https://github.com/dk872/javasoftware-lab6
```
```
cd javasoftware-lab6
```

Compile the code:
```
javac src/main/java/org/example/*.java
```

Run the program:
```
java -cp src/main/java org.example.Main
```

## Unit tests
This project includes **50** unit tests using JUnit 5 to ensure the reliability and correctness of the core functionalities. The tests cover:

- **ElectricApplianceTest**: checks core state management (`plugIn`/`unplug`), initialization, and `toString` formatting.
- **ApplianceCreationTest**: validates constructors, ensuring `IllegalArgumentException` is thrown for invalid power or radiation inputs.
- **ApartmentApplianceManagerTest**: tests complex logic: correct power calculation, ascending sorting, and reliable EMR range search (including `RuntimeException` for no results).
- **CustomApplianceSetTest**: thoroughly validates the `Set` contract: *uniqueness*, *correct size management*, *correct array element shifting after removal*, *all three constructors*, *all batch operations* (`addAll`, `removeAll`, `retainAll`), and tests *the complex `Iterator` functionality* (`next`, `hasNext`, `remove`, and associated exceptions).

### How to run tests
Make sure you have JUnit 5 configured, then run the tests with your preferred method:
  - From command line
  ```
  mvn test
  ```
  - In an IDE like IntelliJ IDEA or Eclipse using the test runner.

### Documentation
The project includes generated **Javadoc documentation**.  
You can browse it here: [Project Documentation](https://dk872.github.io/javasoftware-lab6/)

To regenerate the documentation, run:
```
javadoc -d docs -sourcepath src/main/java -subpackages org.example -private
```

## Author info
Dmytro Kulyk, a student of group IM-32.
