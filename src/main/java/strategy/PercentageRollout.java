package strategy;

import model.DeviceDetails;

public class PercentageRollout implements RolloutStrategy
{
    private  double percentage;
    public PercentageRollout(double percentage)
    {
        if(percentage<0 || percentage>100)
        {
            throw new IllegalArgumentException("Percentage must be between 0 and 100");
        }
        this.percentage = percentage;
    }

    @Override
    public boolean isEligible(DeviceDetails device)
    {
        double ans=Math.abs(device.getDeviceId().hashCode())%100;
        if(ans<percentage)
        {
            return true;
        }
        return false;
    }

    @Override
    public String describe()
    {
        return "PercentageRollout: "+percentage;
    }
}
