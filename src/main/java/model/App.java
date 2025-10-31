package model;
import java.util.*;

public class App
{
    private final String appId;
    private String name;
    private final NavigableSet<AppVersion> versions=new TreeSet<>();
    public  App(String appId, String name)
    {
        this.appId = appId;
        this.name = name;
    }
    public void addVersion(AppVersion v)
    {
        versions.removeIf(existing -> existing.getVersion().equals(v.getVersion()));
        versions.add(v);
    }
    public Optional<AppVersion> getVersion(String version)
    {
        return versions.stream().filter(v -> v.getVersion().equals(version)).findFirst();
    }

    public List<AppVersion> allVersionsDesc()
    {
        List<AppVersion> list = new ArrayList<>(versions);
        Collections.reverse(list);
        return list;
    }


}
