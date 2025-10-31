package strategy;

import model.DeviceDetails;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BetaRolloutTest
{
    @Test
    void shouldAllowOnlySpecificModels() {
        BetaRollout rollout = new BetaRollout(Arrays.asList("Pixel4", "Pixel5"));
        DeviceDetails pixel4 = new DeviceDetails("d1", "Pixel4", 29, "1.0.0");
        DeviceDetails samsung = new DeviceDetails("d2", "Samsung", 29, "1.0.0");

        assertTrue(rollout.isEligible(pixel4));
        assertFalse(rollout.isEligible(samsung));
    }
}
