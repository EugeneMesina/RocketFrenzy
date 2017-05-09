package androidsupersquad.rocketfrenzy.DataBase;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.util.ArrayList;

/**
 * Created by Christian on 5/9/2017.
 */

public class ByteArrayConverter {
    public static byte[] ObjectToByteArray(Object o)
    {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(o);
            byte[] bList = baos.toByteArray();
            return bList;
        } catch (Exception e)
        {
            Log.d("ERROR", "CANNOT CONVERT TO BYTE ARRAY");
            return null;
        }
    }

    public static Object ByteArrayToObject(byte[] list)
    {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(list);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Object o = ois.readObject();
            return o;
        } catch(Exception e) {
            Log.d("ERROR", "CANNOT CONVERT TO OBJECT");
            return null;
        }
    }
}
