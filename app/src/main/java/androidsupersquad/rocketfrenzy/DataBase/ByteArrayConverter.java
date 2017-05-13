package androidsupersquad.rocketfrenzy.DataBase;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Converts Object to byte steams and vice versa
 * to be able to store serializable objects into
 * the database using BLOBs
 *
 * <p>Created by Christian Blydt-Hansen</p>
 */
public class ByteArrayConverter {
    /**
     * Converts an Object to a byte array
     *
     * @param o Object to be converted
     * @return byte array represention of the o
     */
    public static byte[] ObjectToByteArray(Object o) {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        //attempt to convert
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(o);
            byte[] bList = baos.toByteArray();
            return bList;
        } catch (Exception e) {
            Log.d("ERROR", "CANNOT CONVERT TO BYTE ARRAY");
            e.printStackTrace();
            return null;
        } finally {
            try {
                //close streams
                baos.close();
                oos.close();
            } catch (IOException e) {
                Log.d("IO EXCEPTION ERROR", "OBJECT TO BYTE");
            }
        }
    }

    /**
     * Converts a byte array to an Object
     *
     * @param list byte array to be converted
     * @return an Object representation of list
     */
    public static Object ByteArrayToObject(byte[] list) {
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        //attempt to convert
        try {
            bais = new ByteArrayInputStream(list);
            ois = new ObjectInputStream(bais);
            Object o = ois.readObject();
            return o;
        } catch (Exception e) {
            Log.d("ERROR", "CANNOT CONVERT TO OBJECT");
            e.printStackTrace();
            return null;
        } finally {
            try {
                //close streams
                bais.close();
                ois.close();
            } catch (IOException e) {
                Log.d("IO EXCEPTION ERROR", "BYTE TO OBJECT");

            }
        }
    }
}
