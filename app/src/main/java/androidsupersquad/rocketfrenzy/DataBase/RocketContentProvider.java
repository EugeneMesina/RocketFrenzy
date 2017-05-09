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
 * Created by cman4_000 on 4/16/2017.
 */

public class RocketContentProvider extends ContentProvider{

    private static final String AUTHORITY = "androidsupersquad.rocketfrenzy.DataBase";

    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/players");
    private static final int PLAYERS = 1;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, "players", PLAYERS);
    }

    private RocketDB rocketDB;

    @Override
    public boolean onCreate() {
        rocketDB = new RocketDB(getContext());
        return true;
    }

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

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        return null;
    }

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

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int id  = rocketDB.deleteAllInformation(selection);
        return id;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int id = rocketDB.update(uri, values, selection, selectionArgs);
        return id;
    }
}
