package androidsupersquad.rocketfrenzy.DataBase;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Blob;
import java.util.ArrayList;

import androidsupersquad.rocketfrenzy.Fragments.Models.ShopItems;

/**
 * Created by Christian on 5/9/2017.
 */

public class ByteArrayConverter {
    public static byte[] ObjectToByteArray(Object o)
    {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;

        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(o);
            byte[] bList = baos.toByteArray();
            return bList;
        } catch (Exception e)
        {
            Log.d("ERROR", "CANNOT CONVERT TO BYTE ARRAY");
            e.printStackTrace();
            return null;
        } finally {
            try {
                baos.close();
                oos.close();
            } catch (IOException e)
            {
                Log.d("IO EXCEPTION ERROR","OBJECT TO BYTE");
            }
        }
    }

    public static Object ByteArrayToObject(byte[] list)
    {
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            bais = new ByteArrayInputStream(list);
            ois = new ObjectInputStream(bais);
            Object o = ois.readObject();
            return o;
        } catch(Exception e) {
            Log.d("ERROR", "CANNOT CONVERT TO OBJECT");
            e.printStackTrace();
            return null;
        } finally {
            try
            {
                bais.close();
                ois.close();
            } catch (IOException e)
            {
                Log.d("IO EXCEPTION ERROR","BYTE TO OBJECT");

            }
        }
    }
}
