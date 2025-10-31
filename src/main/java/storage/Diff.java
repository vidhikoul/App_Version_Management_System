package storage;
import java.io.Serializable;

public class Diff implements Serializable {
    private static final long serialVersionUID = 1L;

    public int index;
    public byte newValue;

    public Diff(int index, byte newValue)
    {
        this.index = index;
        this.newValue = newValue;
    }
}

