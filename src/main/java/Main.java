import model.*;
import service.VersionManagement;
import storage.*;
import strategy.*;
import java.util.Arrays;
import java.util.Optional;

public class Main
{
    public static void main(String[] args)
    {
        VersionManagement versionManagement = new VersionManagement();
//        AppConfigLoader.loadAndInitializeApp(versionManagement);
        String appId="phonepe";

//        DeviceDetails device = new DeviceDetails("A", "Pixel4", 24, "1.0.0");
//
//        Optional<CheckForUpdates> update = versionManagement.checkForUpdates("phonepe", device);
//        update.ifPresent(task -> versionManagement.executeTask(device, task));
//
//        System.out.println("Device is now running version: " + device.getCurrentAppVersion());

//
        versionManagement.uploadNewVersion(appId, "PhonePe", "1.0.0", 21, "Initial release", "APK_V1".getBytes());
        versionManagement.uploadNewVersion(appId, "PhonePe", "1.1.0", 21, "Minor improvements after initial release", "APK_V1_1".getBytes());
        versionManagement.uploadNewVersion(appId, "PhonePe", "2.0.0", 24, "Final release", "APK_V2".getBytes());
//
        versionManagement.releaseVersion(appId, "1.0.0", new FullRollout());
        versionManagement.releaseVersion(appId, "1.1.0", new PercentageRollout(50));
        versionManagement.releaseVersion(appId, "2.0.0", new BetaRollout(Arrays.asList("Pixel4", "Pixel5")));



        DeviceDetails deviceA = new DeviceDetails("A", "Pixel4", 24, "null");

        var update_ = versionManagement.checkForUpdates(appId, deviceA);
        System.out.println(update_);
        update_.ifPresent(task -> versionManagement.executeTask(deviceA, task));
        System.out.println("\nDevice is now running version: " + deviceA.getCurrentAppVersion());
//        System.out.println("Device is now run " + );
        System.out.println("\n\n");



        DeviceDetails deviceB = new DeviceDetails("B", "SamsungS9", 25, "1.0.0");
        var updateB = versionManagement.checkForUpdates(appId, deviceB);
        System.out.println("\nDevice B update check: " + updateB);
        updateB.ifPresent(task -> versionManagement.executeTask(deviceB, task));
        System.out.println("\nDevice B is now running version: " + deviceB.getCurrentAppVersion());
        System.out.println("\n\n");

        DeviceDetails deviceC = new DeviceDetails("C", "Pixel4", 26, "1.0.0");
        var updateC = versionManagement.checkForUpdates(appId, deviceC);
        System.out.println("\nDevice C update check: " + updateC);
        updateC.ifPresent(task -> versionManagement.executeTask(deviceC, task));
        System.out.println("\nDevice C is now running version: " + deviceC.getCurrentAppVersion());
        System.out.println("\n\n");


        DeviceDetails deviceD = new DeviceDetails("D", "Pixel4", 20, "1.0.0");
        var updateD = versionManagement.checkForUpdates(appId, deviceD);
        System.out.println("\nDevice D update check (too old Android): " + updateD);
        System.out.println("\nDevice D remains on: " + deviceD.getCurrentAppVersion());
        System.out.println("\n\n");

        DeviceDetails deviceE = new DeviceDetails("E", "OnePlus9", 29, "1.1.0");
        var updateE = versionManagement.checkForUpdates(appId, deviceE);
        System.out.println("\nDevice E update check (not in beta): " + updateE);
        System.out.println("\n\n");

        DeviceDetails deviceF = new DeviceDetails("F", "Pixel5", 29, "1.1.0");
        var updateF = versionManagement.checkForUpdates(appId, deviceF);
        System.out.println("\nDevice F update check (in beta): " + updateF);
        updateF.ifPresent(task -> versionManagement.executeTask(deviceF, task));
        System.out.println("\nDevice F is now running version: " + deviceF.getCurrentAppVersion());


        for (int i = 1; i <= 5; i++) {
            DeviceDetails testDevice = new DeviceDetails("UUID-" + i, "SamsungS9", 29, "1.0.0");
            var upd = versionManagement.checkForUpdates(appId, testDevice);
            System.out.println("Device " + testDevice.getDeviceId() + " update check: " + upd);
            upd.ifPresent(task -> versionManagement.executeTask(testDevice, task));
        }
        System.out.println("\n\n");

        DeviceDetails deviceG = new DeviceDetails("G", "Pixel4", 29, "2.0.0");
        var updateG = versionManagement.checkForUpdates(appId, deviceG);
        System.out.println("\nDevice G update check (already latest): " + updateG);


    }
}