package androidsupersquad.rocketfrenzy;


import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationSource;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.services.android.telemetry.location.LocationEngine;
import com.mapbox.services.android.telemetry.location.LocationEngineListener;
import com.mapbox.services.android.telemetry.permissions.PermissionsListener;
import com.mapbox.services.android.telemetry.permissions.PermissionsManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidsupersquad.rocketfrenzy.DataBase.ByteArrayConverter;
import androidsupersquad.rocketfrenzy.DataBase.RocketContentProvider;
import androidsupersquad.rocketfrenzy.DataBase.RocketDB;
import androidsupersquad.rocketfrenzy.Fragments.DailyTaskFragment;
import androidsupersquad.rocketfrenzy.Fragments.KamikaziFragment;
import androidsupersquad.rocketfrenzy.Fragments.Models.Rocket;
import androidsupersquad.rocketfrenzy.Fragments.Models.ShopItems;
import androidsupersquad.rocketfrenzy.Fragments.ProfileFragment;
import androidsupersquad.rocketfrenzy.Fragments.RocketsFragment;
import androidsupersquad.rocketfrenzy.Fragments.ShopFragment;
import androidsupersquad.rocketfrenzy.MiniGame.Lottery;
import androidsupersquad.rocketfrenzy.MiniGame.ShakeMiniGame;

//import android.support.design.widget.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements PermissionsListener,View.OnClickListener, SensorEventListener {

    private MapView mapView;
    private MapboxMap map;
    private Location userLocation;
    //private com.getbase.floatingactionbutton.FloatingActionsMenu menu;
    private FloatingActionButton menu[];
    private FloatingActionButton floatingActionButton, fab_cancel;
    private LocationEngine locationEngine;
    private LocationEngineListener locationEngineListener;
    private PermissionsManager permissionsManager;
    private ArrayList<Marker> alMarkerGT;
    private Marker marker;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private boolean isMenuOpen = true;
    private float stdY[] = new float[5];
    private FrameLayout fragmentHolder;
    private Fragment[] menuScreens;
    private int ran;
    private Context context;
    private Sensor mPedometer;
    private SensorManager sensorManager;
    private RocketDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        context = getBaseContext();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mPedometer = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        sensorManager.registerListener(MainActivity.this, sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER), SensorManager.SENSOR_DELAY_NORMAL);

        db = new RocketDB(this);

       // insertPlayer("USERNAME");
        //if(getPlayerName()==null){
           //insertPlayer("USERNAME");
        //}
        /*-------------------------begin testing-------------------------//
        Log.d("SENT", "START");
        PlayerDeleteTask task = new PlayerDeleteTask();
        task.execute();
        //----------------- MUST RUN THIS FIRST TIME TO INSERT PLAYER -----------------//
        insertPlayer("USERNAME");
        //----------------- MUST RUN THIS FIRST TIME TO INSERT PLAYER -----------------//

        //PlayerDeleteTask task = new PlayerDeleteTask();
        //task.execute();
        ShopItems item = new ShopItems("item", 0, "it's an item", 100);
        Rocket rocket = new Rocket("rocket", 0, "it's a rocket");
        updatePlayerUsername("JOE", "USERNAME");
//        ArrayList<ShopItems> shopList = new ArrayList<ShopItems>();
//        shopList.add(item);
//        byte[] temp = ByteArrayConverter.ObjectToByteArray(shopList);


        //addItemToPlayer("USERNAME", item);
        //removeAllItemsFromPlayer("USERNAME");
        updatePlayerCoinAmount("USERNAME", 30, true);
        updatePlayerBleachAmount("USERNAME", 5, true);
        //removeAllRocketsFromPlayer("USERNAME");
        //addRocketToPlayer("USERNAME", rocket);
        //addItemToPlayer("USERNAME", item);
        removeItemFromPlayer("USERNAME",item);
        removeRocketFromPlayer("USERNAME", rocket);
        removeRocketFromPlayer("USERNAME", rocket);

        PlayerQueryTask query = new PlayerQueryTask();
        query.execute();
//        ArrayList<ShopItems> newShopList = (ArrayList<ShopItems>) ByteArrayConverter.ByteArrayToObject(temp);
//        ShopItems newItem = newShopList.get(0);
        //Log.d("name,0,it's an item, 10", newItem.getItemName() + "," + newItem.getItemImage() + "," + newItem.getItemDescription() + "," + newItem.getItemCost() + "======");

        updatePlayerCoinAmount("USERNAME", 5, false);
        updatePlayerBleachAmount("USERNAME", -2, false);

        getPlayerCoinAmount("USERNAME");
        getPlayerBleachAmount("USERNAME");
        getPlayerItems("USERNAME");
        getPlayerName();
        //-------------------------done testing-------------------------*/


        //Begin.setOnClickListener(this);

        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
        Mapbox.getInstance(this, getString(R.string.access_token));

        // This contains the MapView in XML and needs to be called after the account manager
        setContentView(R.layout.activity_main);

        // Get the location engine object for later use.
        locationEngine = LocationSource.getLocationEngine(this);
        locationEngine.activate();

        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                map = mapboxMap;
                mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        startGame();
                        return true;
                    }
                });

            }
        });

//        Begin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        });

        createMenu();
        fragmentHolder = (FrameLayout) findViewById(R.id.fragmentHolder);
        fragmentManager = getSupportFragmentManager();
        transaction = getSupportFragmentManager().beginTransaction();
        bindMenuScreens();
        setOnClickListeners();
        fab_cancel = (FloatingActionButton) findViewById(R.id.fab_cancel);
        fab_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentHolder.animate().alpha(0F);
                fragmentHolder.setClickable(false);
                transaction = getSupportFragmentManager().beginTransaction();
                Log.d("CURRENT FRAGMENT", "FRAGMENT: " + getCurrentFragment().getTag());
                transaction.remove(getCurrentFragment());
                transaction.commit();
                fragmentManager.popBackStack();
                fragmentManager.executePendingTransactions();
                //fragmentManager.popBackStack();
                //fragmentManager.popBackStackImmediate();
                Log.d("POPPED FROM BACKSTACK", "BACKSTACK COUNT: " + fragmentManager.getBackStackEntryCount());
                //transaction.detach(getSupportFragmentManager().findFragmentByTag("PROFILE"));
                //fragmentHolder.setVisibility(View.GONE);
            }
        });
        /*
        menu[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(AccGame.this, "Inside menu[0] onClick", Toast.LENGTH_SHORT).show();
                if(isMenuOpen) {
                    //Toast.makeText(AccGame.this, "isMenuOpen is true", Toast.LENGTH_SHORT).show();
                    closeMenu();
                } else {
                    //Toast.makeText(AccGame.this, "isMenuOpen is false", Toast.LENGTH_SHORT).show();
                    openMenu();
                }
            }
        });
        menu[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Close Menu
                closeMenu();
                //Reinstantiate transaction
                transaction = getSupportFragmentManager().beginTransaction();
                //Set holder to visible even though alpha = 0
                fragmentHolder.setVisibility(View.VISIBLE);
                //Fade in when loading frame
                fragmentHolder.animate().alpha(1F);
                //Only the current panel is clickable
                fragmentHolder.setClickable(true);
                //Adds Profile Fragment
                transaction.add(R.id.fragmentHolder, new ProfileFragment(), Integer.toString(getFragmentCount()));
                //Adds Profile Fragment ID to backStack
                transaction.addToBackStack(Integer.toString(getFragmentCount()));
                //Finish Changes
                transaction.commit();
                //Execute commits
                fragmentManager.executePendingTransactions();
                //transaction.commit();
                //Log.d("ADDED TO BACKSTACK", "BACKSTACK COUNT: " + fragmentManager.getBackStackEntryCount());
            }
        });
        menu[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AccGame.this, "TODO: Go to inventory", Toast.LENGTH_LONG).show();
                //startActivity(toProfile);
            }
        });
        menu[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AccGame.this, "TODO: Go to shop", Toast.LENGTH_LONG).show();
                //startActivity(toProfile);
            }
        });
        menu[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AccGame.this, "TODO: Go to Daily Tasks", Toast.LENGTH_LONG).show();

                //startActivity(toProfile);
            }
        });*/
        menu[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent x = new Intent(MainActivity.this, ShakeMiniGame.class);
                startActivity(x);
            }
        });

        floatingActionButton = (FloatingActionButton) findViewById(R.id.location_toggle_fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (map != null) {
                    toggleGps(!map.isMyLocationEnabled());

                }
            }
        });
    }

    /**
     * Gets number of backstack entries, named as FragmentCount since only fragments
     * are currently placed on the backstack.
     *
     * @return Number of backstack entries
     */
    protected int getFragmentCount() {
        return getSupportFragmentManager().getBackStackEntryCount();
    }

    /**
     * Fragments are named according to the current number of entries on the backstack. Will
     * get the fragment with the tag specified.
     *
     * @param i Fragment Tag
     * @return The specified fragment
     */
    private Fragment getFragmentAt(int i) {
        return (getFragmentCount() > 0) ? getSupportFragmentManager().findFragmentByTag(Integer.toString(i)) : null;
    }

    /**
     * Gets the top most fragment on the backstack
     *
     * The reason why we are passing count - 1 is that each fragment is named according to current
     * fragment count. So if there are 0 fragments, the tag for the first fragment pushed onto
     * the stack is "0". We then use getFragmentAt(count - 1), so we are getting getFragmentAt(1 - 1),
     * which returns the top most fragment with tag "0".
     *
     * @return The top-most fragment
     */
    protected Fragment getCurrentFragment() {
        return getFragmentAt(getFragmentCount() - 1);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER),SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        // Ensure no memory leak occurs if we register the location listener but the call hasn't
        // been made yet.
        if (locationEngineListener != null) {
            locationEngine.removeLocationEngineListener(locationEngineListener);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private void toggleGps(boolean enableGps) {
        if (enableGps) {
            // Check if user has granted location permission
            permissionsManager = new PermissionsManager(this);
            if (!PermissionsManager.areLocationPermissionsGranted(this)) {
                permissionsManager.requestLocationPermissions(this);
            } else {
                enableLocation(true);
            }
        } else {
            enableLocation(false);
        }
    }

    private void enableLocation(boolean enabled) {
        if (enabled) {
            // If we have the last location of the user, we can move the camera to that position.
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location lastLocation = locationEngine.getLastLocation();
            userLocation = lastLocation;
            if (lastLocation != null) {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastLocation), 16));
            }

            locationEngineListener = new LocationEngineListener() {
                @Override
                public void onConnected() {
                    // No action needed here.
                }

                @Override
                public void onLocationChanged(Location location) {
                    if (location != null) {
                        // Move the map camera to where the user location is and then remove the
                        // listener so the camera isn't constantly updating when the user location
                        // changes. When the user disables and then enables the location again, this
                        // listener is registered again and will adjust the camera once again.

                        CameraPosition position = new CameraPosition.Builder()
                                .target(new LatLng(location)) // Sets the new camera position
                                .zoom(17) // Sets the zoom
                                .bearing(180) // Rotate the camera
                                .tilt(30) // Set the camera tilt
                                .build(); // Creates a CameraPosition from the builder

                        map.animateCamera(CameraUpdateFactory
                                .newCameraPosition(position), 7000);

                    }
                }
            };
            locationEngine.addLocationEngineListener(locationEngineListener);
            floatingActionButton.setImageResource(R.drawable.ic_location_disabled_24dp);
        } else {
            floatingActionButton.setImageResource(R.drawable.ic_my_location_24dp);
        }
        // Enable or disable the location layer on the map
        map.setMyLocationEnabled(enabled);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, "This app needs location permissions in order to show its functionality.",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            enableLocation(true);
        } else {
            Toast.makeText(this, "You didn't grant location permissions.",
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }

    /**
     * Creates and registers menus
     */
    private void createMenu() {
        menu = new FloatingActionButton[6];
        menu[0] = (FloatingActionButton) findViewById(R.id.temp_menu_base);
        menu[1] = (FloatingActionButton) findViewById(R.id.temp_menu_1);
        menu[2] = (FloatingActionButton) findViewById(R.id.temp_menu_2);
        menu[3] = (FloatingActionButton) findViewById(R.id.temp_menu_3);
        menu[4] = (FloatingActionButton) findViewById(R.id.temp_menu_4);
        menu[5] = (FloatingActionButton) findViewById(R.id.temp_menu_5);
    }

    /**
     * Adds onClick methods, animations, etc. for the menu
     */
    private void initMenuFunctionality() {
        bindMenuScreens();
        setOnClickListeners();
    }

    /**
     * Instantiates Fragments
     */
    private void bindMenuScreens() {
        menuScreens = new Fragment[5];
        menuScreens[3] = new DailyTaskFragment();
        menuScreens[4] = new KamikaziFragment();
        menuScreens[2] = new ShopFragment();
        menuScreens[1] = new RocketsFragment();
        menuScreens[0] = new ProfileFragment();
    }

    /**
     * Attaches onClickListeners to menu buttons
     */
    private void setOnClickListeners() {
        for(int ii = 0; ii < menu.length - 1; ii++) {
            if(ii != 0)
                menu[ii].setOnClickListener(setListenerOptions(ii));
            else {
                menu[ii].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(isMenuOpen)
                            closeMenu();
                        else
                            openMenu();
                    }
                });
            }
        }
    }
    private void startGame(){

        Random random = new Random();
        ran = random.nextInt(4)+1;

        new AlertDialog.Builder(this)
                .setTitle("Game Found")
                .setMessage("Do you want to play?" + ran)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    Intent game;
                    public void onClick(DialogInterface dialog, int which) {
                        switch (ran){
                            case 1:
                                //shake game

                                game = new Intent(MainActivity.this, Lottery.class);
                                break;
                            case 2:
                                //lottery slot machine

                                game = new Intent(MainActivity.this, ShakeMiniGame.class);
                                //accgame
                                break;
                            case 3:

                                game = new Intent(MainActivity.this, ShakeMiniGame.class);
                                break;
                                //daniel's game
                            case 4:

                                game = new Intent(MainActivity.this, Lottery.class);
                                break;


                        }
                        startActivity(game);
                        map.clear();
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        map.clear();

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();



    }






    /**
     * Creates an onClickListener for the menu buttons
     *
     * Basically, Java wanted me to use a final variable but I wanted to use a loop, so this is the
     * compromise. I swear this works.
     *
     * @param i The menu option
     * @return An onClickListener for the specified menu button
     */
    private View.OnClickListener setListenerOptions(int i) {
        final int choice = i;
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Close Menu
                closeMenu();
                //Reinstantiate transaction
                transaction = getSupportFragmentManager().beginTransaction();
                //Set holder to visible even though alpha = 0
                fragmentHolder.setVisibility(View.VISIBLE);
                //Fade in when loading frame
                fragmentHolder.animate().alpha(1F);
                //Only the current panel is clickable
                fragmentHolder.setClickable(true);
                //Adds Profile Fragment
                transaction.add(R.id.fragmentHolder, menuScreens[choice - 1], Integer.toString(getFragmentCount()));
                //Adds Profile Fragment ID to backStack
                transaction.addToBackStack(Integer.toString(getFragmentCount()));
                //Finish Changes
                transaction.commit();
                //Execute commits
                fragmentManager.executePendingTransactions();
                //transaction.commit();
            }
        };
    }

    /**
     * Opens menu
     */
    private void openMenu() {
        isMenuOpen = true;
        menu[0].animate().rotation(-60);
        for(int ii = 1; ii < 6; ii++)
            menu[ii].animate().translationY(0);
    }

    /**
     * Closes menu
     */
    private void closeMenu() {
        isMenuOpen = false;
        menu[0].animate().rotation(60);
        int offSet = -225, constantVal = 150, sum = -constantVal;
        for(int ii = 1; ii < 6; ii++)
            menu[ii].animate().translationY(offSet - (sum += constantVal));
        /*
        menu[1].animate().translationY(offSet-0);
        menu[2].animate().translationY(offSet-150);
        menu[3].animate().translationY(offSet-300);
        menu[4].animate().translationY(offSet-450);
        menu[5].animate().translationY(offSet-600);*/
    }

    @Override
    public void onBackPressed() {
        Log.d("FRAGMENT", "" + fragmentHolder.isClickable());
        if(fragmentHolder.isClickable()) {
            fragmentHolder.animate().alpha(0F);
            fragmentHolder.setClickable(false);
            transaction = getSupportFragmentManager().beginTransaction();
            Log.d("CURRENT FRAGMENT", "FRAGMENT: " + getCurrentFragment().getTag());
            transaction.remove(getCurrentFragment());
            transaction.commit();
            fragmentManager.popBackStack();
            fragmentManager.executePendingTransactions();
            Log.d("POPPED FROM BACKSTACK", "BACKSTACK COUNT: " + fragmentManager.getBackStackEntryCount());
        }
    }

    @Override
    public void onClick(View view) {
        //do things
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float steps;
        if(sensorEvent.sensor.getType()==Sensor.TYPE_STEP_COUNTER)
        {
            steps = sensorEvent.values[0];
            System.out.print("WOW"  + steps);
            Log.d("step",Float.toString(steps));
            if(steps%1==0){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //  img.setVisibility(View.GONE);
                        if(userLocation!=null ) {
                            Icon icon = IconFactory.getInstance(MainActivity.this).fromResource(R.drawable.profile);
                            final MarkerOptions gameMarker = new MarkerOptions();
                            map.addMarker( new MarkerOptions()
                                    .position(new LatLng(userLocation))
                                    .title("Game Start")
                                    .snippet("Play Game")
                                    .icon(icon));



                        }
                        else{
                            //do nothing
                        }


                    }
                }, 500);



            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private class PlayerQueryTask extends AsyncTask<ContentValues, Void, Void> {

        @Override
        protected Void doInBackground(ContentValues... contentValues) {
            Cursor cursor = getContentResolver().query(RocketContentProvider.CONTENT_URI, null, null, null, null);
            int username = cursor.getColumnIndex(RocketDB.USER_NAME_COLUMN);
            int coin = cursor.getColumnIndex(RocketDB.COIN_AMOUNT_COLUMN);
            int rockets = cursor.getColumnIndex(RocketDB.ROCKETS_OWNED_COLUMN);
            int items = cursor.getColumnIndex(RocketDB.ITEMS_OWNED_COLUMN);
            int bleach = cursor.getColumnIndex(RocketDB.BLEACH_AMOUNT_COLUMN);
            cursor.moveToFirst();
            //still kind of testing//
            ArrayList<ShopItems> newList = (ArrayList<ShopItems>) ByteArrayConverter.ByteArrayToObject(cursor.getBlob(items));
            String itemString = "\t";
            for(ShopItems si : newList)
            {
                itemString += (si + "\n\t");
            }
            ArrayList<Rocket> newerList = (ArrayList<Rocket>)  ByteArrayConverter.ByteArrayToObject(cursor.getBlob(rockets));
            String rocketString = "\t";
            for(Rocket r : newerList)
            {
                rocketString += (r + "\n\t");
            }
            Log.d("DATABASE_INFO", "Username: " + cursor.getString(username) + "\nCoin amount: " + cursor.getInt(coin) + "\nBleach amount: " + cursor.getInt(bleach) + "\nRockets Owned: " + rocketString + "\nItems Owned: " + itemString);
            return null;
        }
    }

    private class PlayerInsertTask extends AsyncTask<ContentValues, Void, Void> {
        @Override
        protected Void doInBackground(ContentValues... contentValues) {
            getContentResolver().insert(RocketContentProvider.CONTENT_URI, contentValues[0]);
            return null;
        }
    }

    private class PlayerDeleteTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            getContentResolver().delete(RocketContentProvider.CONTENT_URI, null, null);
            return null;
        }
    }

//    @Override
//    public boolean onMarkerClick(@NonNull Marker marker) {
//        //do things
//
//        return false;
//    }

    private String getPlayerName()
    {
        Cursor cursor = getContentResolver().query(RocketContentProvider.CONTENT_URI, null, null, null, null);
        int username = cursor.getColumnIndex(RocketDB.USER_NAME_COLUMN);
        cursor.moveToFirst();
        //still kind of testing//
         String name = cursor.getString(username);

        Log.d("PLAYER_NAME_INFO", "Username: " + name);
        return name;
    }

    private int updatePlayerUsername(String playerName, String newName)
    {
        String whereClause = RocketDB.USER_NAME_COLUMN + "= ?";
        String[] whereArgs = {playerName};
        ContentValues newValues = new ContentValues();
        newValues.put(RocketDB.USER_NAME_COLUMN, newName);
        return getContentResolver().update(RocketContentProvider.CONTENT_URI, newValues, whereClause, whereArgs);
    }

    private int getPlayerCoinAmount(String playerName)
    {
        String where = RocketDB.USER_NAME_COLUMN + "= ?";
        String whereArgs[] = {playerName};
        String[] resultColumns = {RocketDB.COIN_AMOUNT_COLUMN};
        Cursor cursor = getContentResolver().query(RocketContentProvider.CONTENT_URI, resultColumns, where, whereArgs, null);
        int coin = cursor.getColumnIndex(RocketDB.COIN_AMOUNT_COLUMN);
        cursor.moveToFirst();
        int coinAmount = cursor.getInt(coin);
        Log.d("COIN_INFO", "Username: " + playerName + "\nCoin amount: " + coinAmount);
        return coinAmount;
    }

    private int updatePlayerCoinAmount(String playerName, int coinAmount, boolean set)
    {
        String whereClause = RocketDB.USER_NAME_COLUMN + "= ?";
        String[] whereArgs = {playerName};
        int newCoinAmount = 0;
        ContentValues newValues = new ContentValues();
        if(set) {
            newCoinAmount = coinAmount;
        } else {
            int currentCoins = getPlayerCoinAmount(playerName);
            newCoinAmount = currentCoins + coinAmount;
        }
        newValues.put(RocketDB.COIN_AMOUNT_COLUMN, newCoinAmount);
        return getContentResolver().update(RocketContentProvider.CONTENT_URI, newValues, whereClause, whereArgs);
    }

    private int getPlayerBleachAmount(String playerName)
    {
        String where = RocketDB.USER_NAME_COLUMN + "= ?";
        String whereArgs[] = {playerName};
        String[] resultColumns = {RocketDB.BLEACH_AMOUNT_COLUMN};
        Cursor cursor = getContentResolver().query(RocketContentProvider.CONTENT_URI, resultColumns, where, whereArgs, null);
        int bleach = cursor.getColumnIndex(RocketDB.BLEACH_AMOUNT_COLUMN);
        cursor.moveToFirst();
        int bleachAmount = cursor.getInt(bleach);
        Log.d("BLEACH_INFO", "Username: " + playerName + "\nBleach amount: " + bleachAmount);
        return bleachAmount;
    }

    private int updatePlayerBleachAmount(String playerName, int bleachAmount, boolean set)
    {
        String whereClause = RocketDB.USER_NAME_COLUMN + "= ?";
        String[] whereArgs = {playerName};
        int newBleachAmount = 0;
        ContentValues newValues = new ContentValues();
        if(set) {
            newBleachAmount = bleachAmount;
        } else {
            int currentBleach = getPlayerBleachAmount(playerName);
            newBleachAmount = currentBleach + bleachAmount;
        }
        newValues.put(RocketDB.BLEACH_AMOUNT_COLUMN, newBleachAmount);
        return getContentResolver().update(RocketContentProvider.CONTENT_URI, newValues, whereClause, whereArgs);
    }

    //items
    private ArrayList<ShopItems> getPlayerItems(String playerName)
    {
        String where = RocketDB.USER_NAME_COLUMN + "= ?";
        String whereArgs[] = {playerName};
        String[] resultColumns = {RocketDB.ITEMS_OWNED_COLUMN};
        Cursor cursor = getContentResolver().query(RocketContentProvider.CONTENT_URI, resultColumns, where, whereArgs, null);
        int items = cursor.getColumnIndex(RocketDB.ITEMS_OWNED_COLUMN);
        cursor.moveToFirst();
        try {
            ArrayList<ShopItems> itemArray = (ArrayList<ShopItems>) ByteArrayConverter.ByteArrayToObject(cursor.getBlob(items));
            String itemString = "\t";

            for (ShopItems si : itemArray) {
                itemString += (si + "\n\t");
            }
            Log.d("ITEM_INFO", itemString);
            return itemArray;
        } catch (Exception e)
        {
            Log.d("ITEM_INFO", "Username: " + playerName + "\nItem names: null");
            return null;
        }
    }

    private int addItemToPlayer(String playerName, ShopItems item)
    {
        String whereClause = RocketDB.USER_NAME_COLUMN + "= ?";
        String[] whereArgs = {playerName};
        ArrayList<ShopItems> currentItems = getPlayerItems(playerName);
        if(currentItems == null)
        {
            currentItems = new ArrayList<ShopItems>();
        }
        currentItems.add(item);
        byte[]bytes = ByteArrayConverter.ObjectToByteArray(currentItems);
        ContentValues values = new ContentValues();
        values.put(RocketDB.ITEMS_OWNED_COLUMN, bytes);
        return getContentResolver().update(RocketContentProvider.CONTENT_URI, values, whereClause, whereArgs);
    }

    private int removeItemFromPlayer(String playerName, ShopItems item)
    {
        String whereClause = RocketDB.USER_NAME_COLUMN + "= ?";
        String[] whereArgs = {playerName};
        ArrayList<ShopItems> currentItems = getPlayerItems(playerName);
        if(currentItems == null)
        {
            currentItems = new ArrayList<ShopItems>();
        }
        for(int i = 0; i < currentItems.size(); i++)
        {
            if(currentItems.get(i).getItemName().equals(item.getItemName()))
            {
                currentItems.remove(i);
                i = currentItems.size();
                Log.d("REMOVAL", "SUCCESSFUL: " + item.getItemName() + " " + item.getItemCost());
            }
        }
        byte[]bytes = ByteArrayConverter.ObjectToByteArray(currentItems);
        ContentValues values = new ContentValues();
        values.put(RocketDB.ITEMS_OWNED_COLUMN, bytes);
        return getContentResolver().update(RocketContentProvider.CONTENT_URI, values, whereClause, whereArgs);
    }

    private int removeAllItemsFromPlayer(String playerName)
    {
        String whereClause = RocketDB.USER_NAME_COLUMN + "= ?";
        String[] whereArgs = {playerName};
        ArrayList<ShopItems>items = new ArrayList<ShopItems>();
        byte[]bytes = ByteArrayConverter.ObjectToByteArray(items);
        ContentValues values = new ContentValues();
        values.put(RocketDB.ITEMS_OWNED_COLUMN, bytes);
        return getContentResolver().update(RocketContentProvider.CONTENT_URI, values, whereClause, whereArgs);
    }

    //rockets

    private ArrayList<Rocket> getPlayerRockets(String playerName)
    {
        String where = RocketDB.USER_NAME_COLUMN + "= ?";
        String whereArgs[] = {playerName};
        String[] resultColumns = {RocketDB.ROCKETS_OWNED_COLUMN};
        Cursor cursor = getContentResolver().query(RocketContentProvider.CONTENT_URI, resultColumns, where, whereArgs, null);
        int rockets = cursor.getColumnIndex(RocketDB.ROCKETS_OWNED_COLUMN);
        cursor.moveToFirst();
        try {
            ArrayList<Rocket> rocketArray = (ArrayList<Rocket>) ByteArrayConverter.ByteArrayToObject(cursor.getBlob(rockets));
            String rocketString = "\t";

            for (Rocket r : rocketArray) {
                rocketString += (r + "\n\t");
            }
            Log.d("ROCKET_INFO", rocketString);
            return rocketArray;
        } catch (Exception e)
        {
            Log.d("ROCKET_INFO", "Username: " + playerName + "\nRocket names: null");
            return null;
        }
    }

    private int addRocketToPlayer(String playerName, Rocket rocket)
    {
        String whereClause = RocketDB.USER_NAME_COLUMN + "= ?";
        String[] whereArgs = {playerName};
        ArrayList<Rocket> currentRockets = getPlayerRockets(playerName);
        if(currentRockets == null)
        {
            currentRockets = new ArrayList<Rocket>();
        }
        currentRockets.add(rocket);
        byte[]bytes = ByteArrayConverter.ObjectToByteArray(currentRockets);
        ContentValues values = new ContentValues();
        values.put(RocketDB.ROCKETS_OWNED_COLUMN, bytes);
        return getContentResolver().update(RocketContentProvider.CONTENT_URI, values, whereClause, whereArgs);
    }

    private int removeRocketFromPlayer(String playerName, Rocket rocket)
    {
        String whereClause = RocketDB.USER_NAME_COLUMN + "= ?";
        String[] whereArgs = {playerName};
        ArrayList<Rocket> currentRockets = getPlayerRockets(playerName);
        if(currentRockets == null)
        {
            currentRockets = new ArrayList<Rocket>();
        }
        for(int i = 0; i < currentRockets.size(); i++)
        {
            if(currentRockets.get(i).getName().equals(rocket.getName()))
            {
                currentRockets.remove(i);
                i = currentRockets.size();
                Log.d("REMOVAL", "SUCCESSFUL: " + rocket.getName());
            }
        }
        byte[]bytes = ByteArrayConverter.ObjectToByteArray(currentRockets);
        ContentValues values = new ContentValues();
        values.put(RocketDB.ROCKETS_OWNED_COLUMN, bytes);
        return getContentResolver().update(RocketContentProvider.CONTENT_URI, values, whereClause, whereArgs);
    }

    private int removeAllRocketsFromPlayer(String playerName)
    {
        String whereClause = RocketDB.USER_NAME_COLUMN + "= ?";
        String[] whereArgs = {playerName};
        ArrayList<Rocket>rockets = new ArrayList<Rocket>();
        byte[]bytes = ByteArrayConverter.ObjectToByteArray(rockets);
        ContentValues values = new ContentValues();
        values.put(RocketDB.ROCKETS_OWNED_COLUMN, bytes);
        return getContentResolver().update(RocketContentProvider.CONTENT_URI, values, whereClause, whereArgs);
    }

    private void insertPlayer(String userName)
    {
        try {
            ContentValues values = new ContentValues();
            values.put(RocketDB.USER_NAME_COLUMN, userName);
            values.put(RocketDB.COIN_AMOUNT_COLUMN, 0);
            values.put(RocketDB.ROCKETS_OWNED_COLUMN, ByteArrayConverter.ObjectToByteArray(new ArrayList<Rocket>()));
            values.put(RocketDB.ITEMS_OWNED_COLUMN, ByteArrayConverter.ObjectToByteArray(new ArrayList<ShopItems>()));
            values.put(RocketDB.BLEACH_AMOUNT_COLUMN, 0);
            getContentResolver().insert(RocketContentProvider.CONTENT_URI, values);
            Log.d("SENT", "CORRECTLY");
        } catch(Exception e)
        {
            Log.d("SENT", "INCORRECTLY");
            e.printStackTrace();
        }
    }
}