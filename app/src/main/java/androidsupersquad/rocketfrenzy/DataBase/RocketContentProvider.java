package androidsupersquad.rocketfrenzy.DataBase;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Allows the rocket database to be accessed throughout the
 * application using a content provider
 * <p>
 * Created by: Christian Blydt-Hansen
 */
public class RocketContentProvider extends ContentProvider {
    private static final String AUTHORITY = "androidsupersquad.rocketfrenzy.DataBase";
    //content location
    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/players");
    private static final int PLAYERS = 1; //for once player
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, "players", PLAYERS);
    }

    private RocketDB rocketDB;

    /**
     * Creates the database with the given context
     *
     * @return results of the creation
     */
    @Override
    public boolean onCreate() {
        rocketDB = new RocketDB(getContext());
        return true;
    }

    /**
     * @param uri           matches with player Uri
     * @param projection    data protections
     * @param selection     specifies WHERE clause
     * @param selectionArgs replaces "?" in WHERE clause
     * @param sortOrder     order
     * @return null
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        if (uriMatcher.match(uri) == PLAYERS) {
            Log.d("QUERY", "MATCH");
            return rocketDB.getAllPlayers();
        }
        Log.d("QUERY", "NO MATCH");
        return null;
    }

    /**
     * No utilized
     *
     * @param uri Uri
     * @return null
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        return null;
    }

    /**
     * Insert an element into the database
     *
     * @param uri    matches with player Uri
     * @param values attributes to insert
     * @return
     */
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        long id = rocketDB.insertPlayer(values);
        Uri returnUri = null;
        if (id > 0) {
            returnUri = ContentUris.withAppendedId(CONTENT_URI, id);
        }
        return returnUri;

    }

    /**
     * Deletes all information from the database
     *
     * @param uri           Uri
     * @param selection     specifies WHERE clause
     * @param selectionArgs replaces "?" in WHERE clause
     * @return result of the deletion
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int id = rocketDB.deleteAllInformation();
        return id;
    }

    /**
     * Updates a selection of the database
     *
     * @param uri           Uri
     * @param values        updates attributes
     * @param selection     specifies WHERE clause
     * @param selectionArgs replaces "?" in WHERE clause
     * @return result of the update
     */
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int id = rocketDB.update(uri, values, selection, selectionArgs);
        return id;
    }
}
