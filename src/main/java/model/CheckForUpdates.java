package model;

public class CheckForUpdates {
    private String currentVersion;
    private String newVersion;
    private String apkUrl;
    private String diffPackUrl;

    public CheckForUpdates(String currentVersion, String newVersion, String apkUrl, String diffPackUrl)
    {
//        System.out.println("inside updates");
        this.currentVersion = currentVersion;
        this.newVersion = newVersion;
        this.apkUrl = apkUrl;
        this.diffPackUrl = diffPackUrl;
    }
    public String getCurrentVersion() {
        return currentVersion;
    }
    public String getNewVersion() {
        return newVersion;
    }

    public String getApkUrl()
    {
        return apkUrl;
    }
    public String getDiffPackUrl()
    {
        return diffPackUrl;
    }

    @Override
    public String toString()
    {
        return "CheckForUpdateResult" +
                "{" +
                "currentVersion='" + currentVersion + '\'' +
                ", targetVersion='" + newVersion + '\'' +
                ", apkUrl='" + apkUrl + '\'' +
                ", diffPackUrl='" + diffPackUrl + '\'' +
                '}';
    }

}
