package androidsupersquad.rocketfrenzy.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by cman4_000 on 4/16/2017.
 */

public class RocketDB extends SQLiteOpenHelper {
    private static final String ID_COLUMN = "_id";
    //for rockets
    public static final String USER_NAME_COLUMN = "username";
    public static final String COIN_AMOUNT_COLUMN = "coinamount";
    public static final String ROCKETS_OWNED_COLUMN = "rocketsowned";
    public static final String ITEMS_OWNED_COLUMN = "itemsowned";
    public static final String BLEACH_AMOUNT_COLUMN = "bleachamount";

    public static final String DATABASE_TABLE = "UserInfo";
    public static final int DATABASE_VERSION = 4;

    private static String createTable = "CREATE TABLE " + DATABASE_TABLE + "(" +
            ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            USER_NAME_COLUMN + " STRING, " +
            COIN_AMOUNT_COLUMN + " INT, " +
            ROCKETS_OWNED_COLUMN + " BLOB, " +
            ITEMS_OWNED_COLUMN + " BLOB, " +
            BLEACH_AMOUNT_COLUMN + " INT" +
            ")";

    public RocketDB(Context context) {

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

    public long insertPlayer(ContentValues values)
    {
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(DATABASE_TABLE, null, values);
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        SQLiteDatabase db = getWritableDatabase();
        return db.update(DATABASE_TABLE, values, selection, selectionArgs);
    }

    public Cursor getAllPlayers() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(DATABASE_TABLE, new String[] {USER_NAME_COLUMN, COIN_AMOUNT_COLUMN, ROCKETS_OWNED_COLUMN, ITEMS_OWNED_COLUMN, BLEACH_AMOUNT_COLUMN}, null, null, null, null, null);
    }

    public int deleteAllInformation(String name)
    {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(DATABASE_TABLE, null, null);
    }
}
