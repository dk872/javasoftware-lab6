package org.example;

/**
 * Represents a Laptop, a portable appliance with moderate power and low radiation.
 */
public class Laptop extends ElectricAppliance {
    /** The size of the laptop screen in inches. */
    private final int screenSizeInches;

    /**
     * Constructor for Laptop.
     * @param name The name of the laptop.
     * @param powerConsumptionW The power consumed in Watts (when charging).
     * @param electromagneticRadiationLevel The EMR level.
     * @param screenSizeInches The size of the screen in inches.
     */
    public Laptop(String name, int powerConsumptionW, double electromagneticRadiationLevel, int screenSizeInches) {
        super(name, powerConsumptionW, electromagneticRadiationLevel);

        if (screenSizeInches < 5) {
            throw new IllegalArgumentException("Laptop screen size must be at least 5 inches.");
        }
        
        this.screenSizeInches = screenSizeInches;
    }

    /**
     * Retrieves the screen size of the laptop.
     * @return The screen size in inches.
     */
    public int getScreenSizeInches() {
        return screenSizeInches;
    }

    /**
     * Provides a string representation of the Laptop, including screen size.
     * @return A formatted string with appliance and Laptop details.
     */
    @Override
    public String toString() {
        return super.toString() + String.format(" | Type: Laptop (Screen: %d\")", screenSizeInches);
    }
}
