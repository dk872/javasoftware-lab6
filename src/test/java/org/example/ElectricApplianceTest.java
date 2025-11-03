package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the ElectricAppliance base class and its core methods.
 */
class ElectricApplianceTest {

    /**
     * Helper class for testing abstract ElectricAppliance.
     */
    private static class TestAppliance extends ElectricAppliance {
        public TestAppliance(String name, int powerConsumptionW, double emrLevel) {
            super(name, powerConsumptionW, emrLevel);
        }
    }

    @Test
    void testApplianceInitialization() {
        // Test basic initialization and default state
        ElectricAppliance device = new TestAppliance("Kettle", 2000, 1.2);
        assertEquals("Kettle", device.getName());
        assertEquals(2000, device.getPowerConsumptionW());
        assertEquals(1.2, device.getElectromagneticRadiationLevel(), 0.001);
        assertFalse(device.isPluggedIn());
    }

    @Test
    void testPlugInFunctionality() {
        ElectricAppliance device = new TestAppliance("Toaster", 1000, 0.5);

        // 1. Plug in
        device.plugIn();
        assertTrue(device.isPluggedIn());

        // 2. Try to plug in again (state should remain true)
        device.plugIn();
        assertTrue(device.isPluggedIn());
    }

    @Test
    void testUnplugFunctionality() {
        ElectricAppliance device = new TestAppliance("Vacuum", 1400, 3.1);

        // Setup: plug it in first
        device.plugIn();
        assertTrue(device.isPluggedIn());

        // 1. Unplug
        device.unplug();
        assertFalse(device.isPluggedIn());

        // 2. Try to unplug again (state should remain false)
        device.unplug();
        assertFalse(device.isPluggedIn());
    }

    @Test
    void testToStringFormat() {
        // Check if the toString method generates the expected format
        ElectricAppliance device = new TestAppliance("Mixer", 300, 0.9);
        String expectedUnplugged = "Mixer              | Power:  300W | Plugged: No    | EMR Level: 0.90";
        assertEquals(expectedUnplugged, device.toString().trim());

        device.plugIn();
        String expectedPlugged = "Mixer              | Power:  300W | Plugged: Yes   | EMR Level: 0.90";
        assertEquals(expectedPlugged, device.toString().trim());
    }
}
