package androidsupersquad.rocketfrenzy.DataBase;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by cman4_000 on 4/16/2017.
 */

public class LocationsContentProvider extends ContentProvider{

    private static final String AUTHORITY = "edu.csulb.android.example2.provider";

    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/markers");
    private static final int MARKERS = 1;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, "markers", MARKERS);
    }

    private LocationsDB locDB;

    @Override
    public boolean onCreate() {
        locDB = new LocationsDB(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        if (uriMatcher.match(uri) == MARKERS) {
            return locDB.getAllMarkers();
        }
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

        long id = locDB.insertMarker(values);
        Uri returnUri = null;
        if (id > 0) {
            returnUri = ContentUris.withAppendedId(CONTENT_URI, id);
        }
        return returnUri;

    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int id  = locDB.deleteAllMarkers(selection);
        return id;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
