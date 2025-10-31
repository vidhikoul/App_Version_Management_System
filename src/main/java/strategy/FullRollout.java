package strategy;

import model.DeviceDetails;

public class FullRollout implements  RolloutStrategy
{
    @Override
    public boolean isEligible(DeviceDetails device)
    {
        return true;
    }

    @Override
    public String describe()
    {
        return "Full Rollout";
    }
}
