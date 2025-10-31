package storage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryStorageTest
{
    @Test
    void testUploadAndGetFile()
    {
        InMemoryStorage storage = new InMemoryStorage();
        String url = storage.uploadFile("apk-1.0.0", "APKDATA".getBytes());

        assertNotNull(url);
        byte[] data = storage.get(url);
        assertEquals("APKDATA", new String(data));
    }
}
