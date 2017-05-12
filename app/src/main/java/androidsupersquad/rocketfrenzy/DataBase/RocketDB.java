package androidsupersquad.rocketfrenzy.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * Created by Christian Blydt-Hansen
 *
 * This class creates the database which stores elements
 * of the game and of the user
 */

public class RocketDB extends SQLiteOpenHelper {
    //database attributes
    private static final String ID_COLUMN = "_id";
    public static final String DATABASE_TABLE = "UserInfo";
    public static final int DATABASE_VERSION = 10;
    //user attributes
    public static final String USER_NAME_COLUMN = "username";
    public static final String COIN_AMOUNT_COLUMN = "coinamount";
    public static final String ROCKETS_OWNED_COLUMN = "rocketsowned";
    public static final String ITEMS_OWNED_COLUMN = "itemsowned";
    public static final String BLEACH_AMOUNT_COLUMN = "bleachamount";
    public static final String PLAYER_ICON_COLUMN = "playericon";

    //sql to create the table that the database uses
    private static String createTable = "CREATE TABLE " + DATABASE_TABLE + "(" +
            ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            USER_NAME_COLUMN + " STRING, " +
            COIN_AMOUNT_COLUMN + " INT, " +
            ROCKETS_OWNED_COLUMN + " BLOB, " +
            ITEMS_OWNED_COLUMN + " BLOB, " +
            BLEACH_AMOUNT_COLUMN + " INT," +
            PLAYER_ICON_COLUMN + " STRING" +
            ")";

    /**
     * Rocket constructor
     *
     * @param context
     */
    public RocketDB(Context context) {

        super(context, DATABASE_TABLE, null, DATABASE_VERSION);
    }

    /**
     * Ran to create the database
     *
     * @param db database tp be created
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(createTable);
    }

    /**
     * Ran when the database is upgraded
     *
     * @param db database to be upgraded
     * @param oldVersion old database version
     * @param newVersion new database version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE); //drop current table
            onCreate(db); //create new one
        }
    }

    /**
     * Inserts a player into the database
     *
     * @param values player attributes to be inserted
     * @return result of insert
     */
    public long insertPlayer(ContentValues values)
    {
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(DATABASE_TABLE, null, values);
    }

    /**
     * Updates certain values in the database
     *
     * @param uri Uri
     * @param values updates attributes
     * @param selection specifies WHERE clause
     * @param selectionArgs replaces "?" in WHERE clause
     * @return
     */
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        SQLiteDatabase db = getWritableDatabase();
        return db.update(DATABASE_TABLE, values, selection, selectionArgs);
    }

    /**
     * Query all players of the database
     *
     * @return a Cursor with the query
     */
    public Cursor getAllPlayers() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query(DATABASE_TABLE, new String[] {USER_NAME_COLUMN, COIN_AMOUNT_COLUMN, ROCKETS_OWNED_COLUMN, ITEMS_OWNED_COLUMN, BLEACH_AMOUNT_COLUMN, PLAYER_ICON_COLUMN}, null, null, null, null, null);
    }

    /**
     * Deletes all information from the database
     *
     * @return result of the deletion
     */
    public int deleteAllInformation()
    {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(DATABASE_TABLE, null, null);
    }
}
