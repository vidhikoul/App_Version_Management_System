package service;

import model.*;
import strategy.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class VersionManagerTest {
    private VersionManagement vm;
    private final String appId = "phonepe";

    @BeforeEach
    void setup()
    {
        vm = new VersionManagement();
        vm.uploadNewVersion(appId, "PhonePe", "1.0.0", 21, "init", "apk1".getBytes());
        vm.uploadNewVersion(appId, "PhonePe", "1.1.0", 21, "update", "apk2".getBytes());
        vm.uploadNewVersion(appId, "PhonePe", "2.0.0", 24, "major", "apk3".getBytes());
    }

    @Test
    void shouldReleaseAndSupportVersion()
    {
        vm.releaseVersion(appId, "1.0.0", new FullRollout());

        DeviceDetails device = new DeviceDetails("d1", "Pixel4", 29, "0.9.0");
        assertTrue(vm.isAppVersionSupported(appId, "1.0.0", device));
    }

    @Test
    void shouldReturnUpdateIfEligible()
    {
        vm.releaseVersion(appId, "1.0.0", new FullRollout());
        vm.releaseVersion(appId, "1.1.0", new FullRollout());
        DeviceDetails device = new DeviceDetails("d1", "Pixel4", 29, "1.0.0");
        Optional<CheckForUpdates> res = vm.checkForUpdates(appId, device);
        assertTrue(res.isPresent());
        assertEquals("1.1.0", res.get().getNewVersion());
    }

    @Test
    void shouldNotReturnUpdateIfBelowMinAndroid()
    {
        vm.releaseVersion(appId, "2.0.0", new FullRollout());
        DeviceDetails oldDevice = new DeviceDetails("d1", "Pixel4", 20, "1.0.0");
        Optional<CheckForUpdates> res = vm.checkForUpdates(appId, oldDevice);
        assertTrue(res.isEmpty());
    }

    @Test
    void shouldReturnDiffPackWhenAvailable()
    {
        vm.releaseVersion(appId, "1.0.0", new FullRollout());
        vm.releaseVersion(appId, "2.0.0", new FullRollout());
        DeviceDetails device = new DeviceDetails("d1", "Pixel4", 29, "1.0.0");
        Optional<CheckForUpdates> res = vm.checkForUpdates(appId, device);
        assertTrue(res.isPresent());
        assertNotNull(res.get().getDiffPackUrl());
    }
    @Test
    void executeTask_ShouldDoNothing_WhenNullTask() {
        DeviceDetails device = new DeviceDetails("d1", "Pixel4", 29, "1.0.0");
        vm.executeTask(device, null);
        assertEquals("1.0.0", device.getCurrentAppVersion());
    }

    @Test
    void executeTask_ShouldUpdateDevice_WithFullInstall() {
        vm.releaseVersion(appId, "1.0.0", new FullRollout());
        vm.releaseVersion(appId, "1.1.0", new FullRollout());
        DeviceDetails device = new DeviceDetails("d1", "Pixel4", 29, "1.0.0");
        Optional<CheckForUpdates> res = vm.checkForUpdates(appId, device);
        assertTrue(res.isPresent());
        vm.executeTask(device, res.get());
        assertEquals("1.1.0", device.getCurrentAppVersion());
    }

    @Test
    void executeTask_ShouldUpdateDevice_WithDiffPatch() {
        vm.releaseVersion(appId, "1.0.0", new FullRollout());
        vm.releaseVersion(appId, "2.0.0", new FullRollout());
        DeviceDetails device = new DeviceDetails("d1", "Pixel4", 29, "1.0.0");
        Optional<CheckForUpdates> res = vm.checkForUpdates(appId, device);
        assertTrue(res.isPresent());
        assertNotNull(res.get().getDiffPackUrl(), "Diff patch should be available");
        vm.executeTask(device, res.get());
        assertEquals("2.0.0", device.getCurrentAppVersion());
    }

}