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

public class MainActivity extends FragmentActivity {

//    private RocketDB db;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        db = new RocketDB(this);
//
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_GET_LOCATION_PERMISSIONS);
//        }
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
//
//    private class LocationInsertTask extends AsyncTask<ContentValues, Void, Void> {
//        @Override
//        protected Void doInBackground(ContentValues... contentValues) {
//            getContentResolver().insert(RocketContentProvider.CONTENT_URI, contentValues[0]);
//            return null;
//        }
//    }
//
//    private class LocationDeleteTask extends AsyncTask<Void, Void, Void> {
//        @Override
//        protected Void doInBackground(Void... params) {
//            getContentResolver().delete(RocketContentProvider.CONTENT_URI, null, null);
//            return null;
//        }
//    }
}
