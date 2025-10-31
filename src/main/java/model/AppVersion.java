package model;

import storage.Diff;
import storage.InMemoryStorage;
import storage.PatchCreation;
import strategy.FullRollout;
import strategy.RolloutStrategy;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppVersion implements Comparable<AppVersion>
{
    private final String version;
    private final int minAndroidVersion;
    private final String apkUrl;
    private final String description;
    private boolean released = false;
    private RolloutStrategy rolloutStrategy = new FullRollout();
    private final Map<String, String> diffPacks = new HashMap<>();

    public AppVersion(String version, int minAndroidVersion, String description, String apkUrl)
    {
        this.version = version;
        this.apkUrl = apkUrl;
        this.description = description;
        this.minAndroidVersion = minAndroidVersion;
    }

    public String getVersion()
    {
        return version;
    }

    public String getApkUrl()
    {
        return apkUrl;
    }

    public boolean isReleased()
    {
        return released;
    }

    public int getMinAndroidVersion()
    {
        return minAndroidVersion;
    }

    public void release(RolloutStrategy strategy)
    {
        this.rolloutStrategy = (strategy == null) ? new FullRollout() : strategy;
        this.released = true;
    }

    public boolean isSupportedOn(DeviceDetails device)
    {
        if (device == null)
        {
            return false;
        }
        return released && device.getAndroidVersion() >= minAndroidVersion &&
                rolloutStrategy.isEligible(device);
    }

    public String createDiff(AppVersion from, InMemoryStorage storage)
    {
        if (diffPacks.containsKey(from.version))
        {
            return diffPacks.get(from.version);
        }

        List<Diff> diffs = PatchCreation.createDiff(
                storage.get(from.apkUrl),
                storage.get(this.apkUrl)
        );

        byte[] diffBytes = serializeDiffs(diffs);

        String diffUrl = storage.uploadFile(
                "diff-" + from.version + "-to-" + this.version,
                diffBytes
        );
        diffPacks.put(from.version, diffUrl);
        return diffUrl;
    }

    private byte[] serializeDiffs(List<Diff> diffs)
    {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(bos))
        {
            out.writeObject(diffs);
            return bos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int compareTo(AppVersion other)
    {
        return compareSemanticVersions(this.version, other.version);
    }

    private int compareSemanticVersions(String a, String b)
    {
        String[] as = a.split("\\.");
        String[] bs = b.split("\\.");
        int n = Math.max(as.length, bs.length);
        for (int i = 0; i < n; i++)
        {
            int ai = (i < as.length) ? Integer.parseInt(as[i]) : 0;
            int bi = (i < bs.length) ? Integer.parseInt(bs[i]) : 0;
            if (ai != bi) return Integer.compare(ai, bi);
        }
        return 0;
    }
}
