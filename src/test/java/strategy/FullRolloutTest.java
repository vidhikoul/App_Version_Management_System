package strategy;

import model.DeviceDetails;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FullRolloutTest {
    @Test
    void shouldAlwaysBeEligible()
    {
        FullRollout rollout = new FullRollout();
        DeviceDetails device = new DeviceDetails("d1", "Pixel4", 30, "1.0.0");
        assertTrue(rollout.isEligible(device));
    }

}
