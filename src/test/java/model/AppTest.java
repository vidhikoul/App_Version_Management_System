package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    private App app;

    @BeforeEach
    void setUp() {
        app = new App("id1", "PhonePe");
    }

    @Test
    void testAddAndGetVersion()
    {
        AppVersion v1 = new AppVersion("1.0.0", 21, "Initial", "url1");
        app.addVersion(v1);

        Optional<AppVersion> found = app.getVersion("1.0.0");
        assertTrue(found.isPresent());
        assertEquals("1.0.0", found.get().getVersion());
    }

    @Test
    void testAddVersionReplacesExisting()
    {
        AppVersion v1 = new AppVersion("1.0.0", 21, "Initial", "url1");
        AppVersion v1Updated = new AppVersion("1.0.0", 21, "Updated", "url2");

        app.addVersion(v1);
        app.addVersion(v1Updated);

        Optional<AppVersion> found = app.getVersion("1.0.0");
        assertTrue(found.isPresent());
        assertEquals("url2", found.get().getApkUrl());
    }

    @Test
    void testAllVersionsDescOrder()
    {
        app.addVersion(new AppVersion("1.0.0", 21, "Initial", "url1"));
        app.addVersion(new AppVersion("2.0.0", 21, "Big update", "url2"));
        app.addVersion(new AppVersion("1.1.0", 21, "Minor", "url3"));

        var versions = app.allVersionsDesc();
        assertEquals("2.0.0", versions.get(0).getVersion());
        assertEquals("1.0.0", versions.get(2).getVersion());
    }
}
