package org.example;

/**
 * Represents a HairDryer, a high-power kitchen appliance used intermittently.
 */
public class HairDryer extends ElectricAppliance {
    /** The number of speed settings available on the hair dryer. */
    private final int speedSettings;

    /**
     * Constructor for HairDryer.
     * @param name The name of the hair dryer.
     * @param powerConsumptionW The power consumed in Watts (high).
     * @param electromagneticRadiationLevel The EMR level (can be high).
     * @param speedSettings The number of speed settings available.
     */
    public HairDryer(String name, int powerConsumptionW, double electromagneticRadiationLevel, int speedSettings) {
        super(name, powerConsumptionW, electromagneticRadiationLevel);

        if (speedSettings <= 0) {
            throw new IllegalArgumentException("HairDryer must have at least one speed setting.");
        }
        
        this.speedSettings = speedSettings;
    }

    /**
     * Retrieves the number of speed settings available on the hair dryer.
     * @return The number of speed settings.
     */
    public int getSpeedSettings() {
        return speedSettings;
    }

    /**
     * Provides a string representation of the HairDryer, including speed settings count.
     * @return A formatted string with appliance and HairDryer details.
     */
    @Override
    public String toString() {
        return super.toString() + String.format(" | Type: Hair Dryer (Speeds: %d)", speedSettings);
    }
}
