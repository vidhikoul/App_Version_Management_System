package storage;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PatchCreationTest {

    @Test
    void testCreateDiffAndApplyDiff() {
        byte[] oldApk = "HELLO".getBytes();
        byte[] newApk = "HELLLOX!".getBytes();
        List<Diff> diffs = PatchCreation.createDiff(oldApk, newApk);
        assertFalse(diffs.isEmpty());
        byte[] patched = PatchCreation.applyDiff(oldApk, diffs, newApk.length);

        assertEquals(new String(newApk), new String(patched));
    }

    @Test
    void testDiffWhenFromApkIsNull() {
        byte[] newApk = "NEWAPK".getBytes();
        List<Diff> diffs = PatchCreation.createDiff(null, newApk);
        assertEquals(newApk.length, diffs.size());
        byte[] patched = PatchCreation.applyDiff(null, diffs, newApk.length);
        assertEquals("NEWAPK", new String(patched));
    }

    @Test
    void testDiffWhenToApkIsNull()
    {
        byte[] oldApk = "OLDAPK".getBytes();
        List<Diff> diffs = PatchCreation.createDiff(oldApk, null);
        assertEquals(oldApk.length, diffs.size());
        byte[] patched = PatchCreation.applyDiff(oldApk, diffs, oldApk.length);
        for (Diff d : diffs)
        {
            assertEquals(-1, d.newValue);
        }
    }
}
