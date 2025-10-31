package storage;

import java.util.HashMap;
import java.util.Map;

public class InMemoryStorage
{
    private Map<String, byte[]> storage=new HashMap<>();
    public int counter=1;
    public String uploadFile(String fileName,byte[] data)
    {
        String url="inmemory://"+fileName+"/"+(counter++);
        storage.put(url,data);
        return url;
    }
    public byte[] get(String url)
    {
        return storage.get(url);
    }


}
