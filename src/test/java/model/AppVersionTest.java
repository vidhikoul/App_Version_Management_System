package model;

import org.junit.jupiter.api.Test;
import storage.InMemoryStorage;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AppVersionTest
{
    @Test
    void shouldCreateDiffBetweenVersions()
    {
        InMemoryStorage store = new InMemoryStorage();
        String urlV1 = store.uploadFile( "v1","apk1".getBytes());
        String urlV2 = store.uploadFile( "v2","apk2".getBytes());

        AppVersion v1 = new AppVersion("1.0.0", 21, "desc1", urlV1);
        AppVersion v2 = new AppVersion("2.0.0", 21, "desc2", urlV2);

        String diffUrl = v2.createDiff(v1, store);
        assertNotNull(diffUrl);
        assertNotNull(store.get(diffUrl));
    }


}
