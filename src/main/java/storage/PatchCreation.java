package storage;

import java.util.ArrayList;
import java.util.List;


public class PatchCreation
{
    public static List<Diff> createDiff(byte[] fromApk, byte[] toApk)
    {
        List<Diff>diffs=new ArrayList<Diff>();
        int maxLen=Math.max(fromApk==null?0:fromApk.length,toApk==null?0:toApk.length);
        for(int i=0;i<maxLen;i++)
        {
            byte fromByte;
            if(fromApk!=null && i<fromApk.length)
            {
                fromByte=fromApk[i];
            }
            else {
                fromByte=-1;
            }
            byte toByte;
            if(toApk!=null && i<toApk.length)
            {
                toByte=toApk[i];
            }
            else {
                toByte=-1;
            }
            if(fromByte!=toByte)
            {
                diffs.add(new Diff(i,toByte));

            }

        }
        return  diffs;

    }
    public static byte[] applyDiff(byte[] fromApk,List<Diff> diffs,int finalLen)
    {
        byte[] result=new byte[finalLen];
        if(fromApk!=null)
        {
            System.arraycopy(fromApk,0,result,0,Math.min(fromApk.length,finalLen));
        }
        for(Diff diff:diffs)
        {
            result[diff.index]=diff.newValue;
        }
        return result;
    }



}
