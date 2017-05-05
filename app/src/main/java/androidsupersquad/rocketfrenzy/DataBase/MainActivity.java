package androidsupersquad.rocketfrenzy.DataBase;

import android.Manifest;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

//import com.google.android.gms.maps.CameraUpdate;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapFragment;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;

//public class MainActivity extends FragmentActivity implements OnMapReadyCallback, LoaderManager.LoaderCallbacks<Cursor> {
//
//    private final LatLng LOCATION_UNIV = new LatLng(33.783768, -118.114336);
//    private final LatLng LOCATION_ECS = new LatLng(33.782777, -118.111868);
//
//    private final int MY_PERMISSION_GET_LOCATION_PERMISSIONS = 0;
//    private static final int LOADER_ID = 0;
//
//    private GoogleMap map;
//    private LocationsDB db;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_GET_LOCATION_PERMISSIONS);
//        }
//
//        MapFragment mf = ((MapFragment) getFragmentManager().findFragmentById(R.id.map));
//        mf.getMapAsync(this);
//        db = new LocationsDB(this);
//
//        LoaderManager loaderManager = getLoaderManager();
//        loaderManager.initLoader(LOADER_ID, null, this);
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_GET_LOCATION_PERMISSIONS);
//        }
//        map = googleMap;
//        map.setMyLocationEnabled(true);
//        map.addMarker(new MarkerOptions().position(LOCATION_ECS).title("Find me here!"));
//
//        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                map.addMarker(new MarkerOptions().position(latLng));
//                ContentValues contentValues = new ContentValues();
//                contentValues.put("latitude", latLng.latitude);
//                contentValues.put("longitude", latLng.longitude);
//                contentValues.put("zoomLevel", map.getCameraPosition().zoom);
//
//                LocationInsertTask insertTask = new LocationInsertTask();
//                insertTask.execute(contentValues);
//                Toast.makeText(getBaseContext(), "Marker has been added to the map.", Toast.LENGTH_LONG).show();
//            }
//        });
//
//        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
//            @Override
//            public void onMapLongClick(LatLng latLng) {
//                map.clear();
//                LocationDeleteTask deleteTask = new LocationDeleteTask();
//                deleteTask.execute();
//                Toast.makeText(getBaseContext(), "All markers have been removed.", Toast.LENGTH_LONG).show();
//            }
//        });
//    }
//
//    public void onClick_ECS(View v)
//    {
//        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
//        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_UNIV, 14);
//        map.animateCamera(update);
//    }
//
//    public void onClick_LongBeachUniv(View v)
//    {
//        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_ECS, 16);
//        map.animateCamera(update);
//    }
//
//    public void onClick_City(View v)
//    {
//        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_UNIV, 9);
//        map.animateCamera(update);
//    }
//
//    @Override
//    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
//        return new CursorLoader(MainActivity.this, LocationsContentProvider.CONTENT_URI, null, null, null, null);
//    }
//
//    @Override
//    public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
//        int locationCount = 0;
//        double lat = 0;
//        double lng = 0;
//        float zoom = 0;
//        // Number of locations available in the SQLite database table
//        if (arg1 != null) {
//            locationCount = arg1.getCount();
//        }
//        else {
//            locationCount = 0;
//        }
//        arg1.moveToFirst();
//        for (int i = 0; i < locationCount; i++) {
//            lat = arg1.getDouble(arg1.getColumnIndex("latitude"));
//            lng = arg1.getDouble(arg1.getColumnIndex("longitude"));
//            zoom = arg1.getFloat(arg1.getColumnIndex("zoomLevel"));
//            LatLng latLng = new LatLng(lat, lng);
//            map.addMarker(new MarkerOptions().position(latLng));
//            arg1.moveToNext();
//        }
//        if (locationCount > 0) {
//            map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng)));
//            map.animateCamera(CameraUpdateFactory.zoomTo(zoom));
//        }
//
//    }
//
//    @Override
//    public void onLoaderReset(android.content.Loader<Cursor> loader) {
//        //does nothing
//    }
//
//    private class LocationInsertTask extends AsyncTask<ContentValues, Void, Void> {
//        @Override
//        protected Void doInBackground(ContentValues... contentValues) {
//            getContentResolver().insert(LocationsContentProvider.CONTENT_URI, contentValues[0]);
//            return null;
//        }
//    }
//
//    private class LocationDeleteTask extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected Void doInBackground(Void... params) {
//            getContentResolver().delete(LocationsContentProvider.CONTENT_URI, null, null);
//            return null;
//        }
//    }
//}
