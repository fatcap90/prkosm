package com.example.bronze56k.prkosm;

import android.provider.SyncStateContract;
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
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;



public class MainActivity extends AppCompatActivity
{
    private MyLocationNewOverlay  myLocationoverlay;
    private MapController mapController;
    private Location currentLocation = null;

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

        // Отображение с зеркала OSM
        /*String[] urls = {"СЮДА ВСТАВИТЬ ССЫЛКУ"};
        XYTileSource MapSource = new XYTileSource("OSM", 0, 1, 18, ".png", urls);
        map.setTileSource(MapSource);*/

        mapController = (MapController) map.getController();
        mapController.setZoom(14);
        // GeoPoint startPoint = new GeoPoint(currentLocation.getLatitude(), currentLocation.getLongitude());
        GeoPoint startPoint = new GeoPoint(53.1970, 45.0194);
        mapController.setCenter(startPoint);

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
