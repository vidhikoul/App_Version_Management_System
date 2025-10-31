package service;

import model.App;
import model.AppVersion;
import model.CheckForUpdates;
import model.DeviceDetails;
import storage.InMemoryStorage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class VersionManagement
{
    private InMemoryStorage file=new InMemoryStorage();
    private Map<String,App> apps=new HashMap<String,App>();
    public App ensureApp(String appId, String name)
    {
        return apps.computeIfAbsent(appId, k -> new App(appId, name));

    }
    public AppVersion uploadNewVersion(String appId,String appName,String version,int minAndroidVersion,String description,byte[] apkBytes)
    {
//        System.out.println("inside uploadNewVersion");
        App app=ensureApp(appId,appName);
        String apkUrl=file.uploadFile("apk-"+version,apkBytes);
        AppVersion av=new AppVersion(version,minAndroidVersion,description,apkUrl);
        app.addVersion(av);
        return av;

    }
    public String createUpdatePatch(String appId,String fromVersion,String toVersion)
    {
        App app=apps.get(appId);
        Optional<AppVersion> from=app.getVersion(fromVersion);
        Optional<AppVersion> to=app.getVersion(toVersion);
        return to.get().createDiff(from.orElse(null),file);

    }
    public void releaseVersion(String appId, String version, strategy.RolloutStrategy strategy)
    {
//        System.out.println("inside releaseVersion");
        App app = apps.get(appId);
        Optional<AppVersion> av = app.getVersion(version);
        av.get().release(strategy);
    }

    public boolean isAppVersionSupported(String appId, String targetVersion, DeviceDetails device)
    {
        return apps.get(appId)
                .getVersion(targetVersion)
                .map(v -> v.isSupportedOn(device))
                .orElse(false);
    }

    public Optional<CheckForUpdates> checkForUpdates(String appId, DeviceDetails device)
    {

        App app = apps.get(appId);
        for (AppVersion av: app.allVersionsDesc())
        {
            if(device.getCurrentAppVersion()==null) {
                if (av.isSupportedOn(device))
                {
                    System.out.println(device.getCurrentAppVersion());
                    System.out.println("app id:"+appId);
                    System.out.println("version:"+av.getVersion());
                    return Optional.of(new CheckForUpdates(device.getCurrentAppVersion(),
                            av.getVersion(), av.getApkUrl(), null));

                }

            }

           else if (av.isSupportedOn(device) && av.getVersion().compareTo(device.getCurrentAppVersion()) > 0) {
                String diffUrl = app.getVersion(device.getCurrentAppVersion())
                        .map(from -> av.createDiff(from, file))
                        .orElse(null);
                return Optional.of(new CheckForUpdates(device.getCurrentAppVersion(),
                        av.getVersion(), av.getApkUrl(), diffUrl));
            }
        }
        System.out.println("app id:"+appId);
        System.out.println("version:"+device.getCurrentAppVersion());

        return Optional.empty();
    }
    public void executeTask(DeviceDetails device, CheckForUpdates updateTask)
    {
        if (updateTask == null) {
            System.out.println("[executeTask] No update task to execute.");
            return;
        }
        if (updateTask.getDiffPackUrl() != null)
        {
            System.out.println("[executeTask] Applying DIFF update for device " + device.getDeviceId() +
                    " from " + updateTask.getCurrentVersion() + " → " + updateTask.getNewVersion() +
                    " using patch: " + updateTask.getDiffPackUrl());
            device.setCurrentAppVersion(updateTask.getNewVersion());

        }
        else
        {
            System.out.println("[executeTask] Installing FULL APK for device " + device.getDeviceId() +
                    " version " + updateTask.getNewVersion() +
                    " from: " + updateTask.getApkUrl());
            device.setCurrentAppVersion(updateTask.getNewVersion());
        }
    }








}
