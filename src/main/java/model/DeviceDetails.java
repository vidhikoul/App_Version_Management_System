package model;

public class DeviceDetails {
    private final String deviceId;
    private int androidVersion;
    private String currentAppVersion;
    private final String model;
    private String emp_Id;

    public DeviceDetails(String deviceId, String model, int androidVersion, String appVersion)
    {
        this.deviceId = deviceId;
        this.androidVersion = androidVersion;
        this.currentAppVersion = appVersion;
        this.model = model;

    }
    public String getDeviceId()
    {
        return deviceId;

    }
    public int getAndroidVersion()
    {
        return androidVersion;
    }
    public String getCurrentAppVersion()
    {
        return currentAppVersion;
    }
    public  String getModel()
    {
        return model;
    }
    public void setCurrentAppVersion(String newVersion)
    {
        this.currentAppVersion = newVersion;
    }


}
