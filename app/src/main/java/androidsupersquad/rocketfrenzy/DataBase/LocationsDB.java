package androidsupersquad.rocketfrenzy.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;

import java.util.ArrayList;

/**
 * Created by cman4_000 on 4/16/2017.
 */

public class LocationsDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "markersDB";
    private static final String ID_COLUMN = "_id";
    private static final String NAME_COLUMN = "name";
    private static final String LATITUDE_COLUMN = "latitude";
    private static final String LONGITUDE_COLUMN = "longitude";
    private static final String ZOOM_LEVEL_COLUMN = "zoomLevel";

    private static final String DATABASE_TABLE = "Map";
    public static final int DATABASE_VERSION = 1;
    private int zoomLevel;

    private static String createTable = "CREATE TABLE " + DATABASE_TABLE + "(" +
            ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            LATITUDE_COLUMN + " FLOAT, " +
            LONGITUDE_COLUMN + " FLOAT, " +
            ZOOM_LEVEL_COLUMN + " FLOAT" +
            ")";

    public LocationsDB(Context context) {

        super(context, DATABASE_TABLE, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       if(oldVersion != newVersion) {
           db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
           onCreate(db);
       }
    }

    public long insertMarker(ContentValues values)
    {
        SQLiteDatabase db = getWritableDatabase();
                return db.insert(DATABASE_TABLE, null, values);
    }

    public Cursor getAllMarkers() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(DATABASE_TABLE, new String[] {LATITUDE_COLUMN, LONGITUDE_COLUMN, ZOOM_LEVEL_COLUMN}, null, null, null, null, null);
    }

    public int deleteAllMarkers(String name)
    {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(DATABASE_TABLE, null, null);
    }

//    public void readAll()
//    {
//        ArrayList values = new ArrayList();
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor c = db.rawQuery("SELECT * FROM table ", null);
//        if(c.moveToFirst()) {
//            do{
//                String name = c.getString(0);
//                int lat = c.getInt(1);
//                int lon = c.getInt(2);
//                values.add(name);
//                values.add(lat);
//                values.add(lon);
//            }while(c.moveToNext());
//        }
//        c.close();
//        db.close();
//    }
}
