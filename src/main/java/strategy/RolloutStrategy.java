package strategy;

import model.DeviceDetails;

public interface RolloutStrategy
{
    boolean isEligible(DeviceDetails device);
    String describe();

}
