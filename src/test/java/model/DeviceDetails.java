package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DeviceDetailsTest {

    @Test
    void testDeviceInitialization()
    {
        DeviceDetails device = new DeviceDetails("D1", "Pixel4", 29, "1.0.0");

        assertEquals("D1", device.getDeviceId());
        assertEquals("Pixel4", device.getModel());
        assertEquals(29, device.getAndroidVersion());
        assertEquals("1.0.0", device.getCurrentAppVersion());
    }

    @Test
    void testDifferentDevicesNotEqualByReference()
    {
        DeviceDetails d1 = new DeviceDetails("D1", "Pixel4", 29, "1.0.0");
        DeviceDetails d2 = new DeviceDetails("D1", "Pixel4", 29, "1.0.0");
        assertNotEquals(d1, d2);
    }

    @Test
    void testSameReferenceIsEqual()
    {
        DeviceDetails d1 = new DeviceDetails("D1", "Pixel4", 29, "1.0.0");
        DeviceDetails d2 = d1;
        assertEquals(d1, d2);
    }

    @Test
    void testValuesAreStoredCorrectly()
    {
        DeviceDetails device = new DeviceDetails("ABC123", "SamsungS21", 30, "2.0.0");

        assertAll("device",
                () -> assertEquals("ABC123", device.getDeviceId()),
                () -> assertEquals("SamsungS21", device.getModel()),
                () -> assertEquals(30, device.getAndroidVersion()),
                () -> assertEquals("2.0.0", device.getCurrentAppVersion())
        );
    }
}
