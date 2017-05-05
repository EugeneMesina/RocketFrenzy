package androidsupersquad.rocketfrenzy;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
//import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import android.support.design.widget.FloatingActionButton;
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

import androidsupersquad.rocketfrenzy.DataBase.LocationsDB;
import androidsupersquad.rocketfrenzy.Fragments.DailyTaskFragment;
import androidsupersquad.rocketfrenzy.Fragments.KamikaziFragment;
import androidsupersquad.rocketfrenzy.Fragments.ProfileFragment;
import androidsupersquad.rocketfrenzy.Fragments.RocketsFragment;
import androidsupersquad.rocketfrenzy.Fragments.ShopFragment;
import androidsupersquad.rocketfrenzy.MiniGame.ShakeMiniGame;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PermissionsListener,View.OnClickListener {

    private MapView mapView;
    private MapboxMap map;
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


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
//        final Button Begin = (Button) findViewById(R.id.button);

        LocationsDB db = new LocationsDB(getBaseContext());
        db.
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
                Icon icon = IconFactory.getInstance(MainActivity.this).fromResource(R.drawable.rocket);

                // Add the custom icon marker to the map
                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(33.7878, -118.1134))
                        .title("The Pyramid")
                        .snippet("Cause it's a pyramid :)")
                        .icon(icon));
                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(33.783303 ,  -118.113731))
                        .title("Horn Center")
                        .snippet("Computer and Crap")
                        .icon(icon));
                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(33.78252527744477 ,  -118.11507797188824))
                        .title("Brotman Hall ")
                        .snippet("Admins")
                        .icon(icon));
                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(33.783264 ,  -118.110649
                        ))
                        .title("Engineering")
                        .snippet("Nerds, Stay Away Normies")
                        .icon(icon));
                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(33.781035626614724 ,  -118.11357647180557))
                        .title("USU")
                        .snippet("Grab food")
                        .icon(icon));

            }
        });

//        Begin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//
//
//
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
                //Toast.makeText(MainActivity.this, "Inside menu[0] onClick", Toast.LENGTH_SHORT).show();
                if(isMenuOpen) {
                    //Toast.makeText(MainActivity.this, "isMenuOpen is true", Toast.LENGTH_SHORT).show();
                    closeMenu();
                } else {
                    //Toast.makeText(MainActivity.this, "isMenuOpen is false", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MainActivity.this, "TODO: Go to inventory", Toast.LENGTH_LONG).show();
                //startActivity(toProfile);
            }
        });
        menu[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "TODO: Go to shop", Toast.LENGTH_LONG).show();
                //startActivity(toProfile);
            }
        });
        menu[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "TODO: Go to Daily Tasks", Toast.LENGTH_LONG).show();

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

//    @Override
//    public boolean onMarkerClick(@NonNull Marker marker) {
//        //do things
//
//        return false;
//    }
}