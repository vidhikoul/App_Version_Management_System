package strategy;

import model.DeviceDetails;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PercentageRolloutTest
{
    void shouldRespectPercentage()
    {
        PercentageRollout rollout = new PercentageRollout(100);
        assertTrue(rollout.isEligible(new DeviceDetails("any", "Pixel", 29, "1.0.0")));
        PercentageRollout zero = new PercentageRollout(0);
        assertFalse(zero.isEligible(new DeviceDetails("any", "Pixel", 29, "1.0.0")));
    }

    @Test
    void shouldThrowOnInvalidPercentage()
    {
        assertThrows(IllegalArgumentException.class, () -> new PercentageRollout(-1));
        assertThrows(IllegalArgumentException.class, () -> new PercentageRollout(101));
    }

}
