package com.example.bronze56k.prkosm;

import android.support.v7.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.tileprovider.tilesource.XYTileSource;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;



public class MainActivity extends AppCompatActivity
{
    private MyLocationNewOverlay  myLocationoverlay;
    private MapView  mMapView;
    private MapController mapController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        setContentView(R.layout.activity_main);
        MapView map = (MapView) findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setClickable(true);
        map.setMultiTouchControls(true);
        //final float scale = getResources().getDisplayMetrics().density;
       // final int newScale = (int) (256 * scale);
        ///String[] OSMSource = new String[1];
        //OSMSource[0] = "http://a.tile.openstreetmap.org/";
       // XYTileSource MapSource = new XYTileSource("OSM", null, 1, 18, newScale, ".png", OSMSource);
       // map.setTileSource(MapSource);
        mapController = (MapController) map.getController();
        mapController.setZoom(14);
// My Location Overlay
        myLocationoverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this), map);
        myLocationoverlay.enableMyLocation();
        myLocationoverlay.setDrawAccuracyEnabled(true);
        map.getOverlays().add(myLocationoverlay);
        myLocationoverlay.runOnFirstFix(new Runnable() {
            public void run() {
                mapController.animateTo(myLocationoverlay.getMyLocation());
           }
      });
    }

    @Override
    protected void onResume() {
        super.onResume();
        myLocationoverlay.enableMyLocation();
        myLocationoverlay.enableFollowLocation();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
