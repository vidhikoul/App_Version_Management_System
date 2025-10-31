package strategy;

import model.DeviceDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class BetaRollout implements RolloutStrategy
{
    private Set<String> modelsAllowed;
    public BetaRollout(Collection<String> models)
    {
        this.modelsAllowed = new HashSet<String>(models);
        for(String m:models)
        {
            modelsAllowed.add(m.toLowerCase());

        }

    }

    @Override
    public boolean isEligible(DeviceDetails device)
    {
        return modelsAllowed.contains(device.getModel().toLowerCase());
    }

    @Override
    public String describe()
    {
        return "BetaRollout for models: "+modelsAllowed;
    }

}
