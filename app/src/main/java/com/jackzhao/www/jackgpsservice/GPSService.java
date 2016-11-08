package com.jackzhao.www.jackgpsservice;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;


/**
 * Created by Jack Zhao on 11/8/2016.
 */

@SuppressWarnings("MissingPermission")
public class GPSService extends Service {

    private LocationListener locationListener;
    private LocationManager locationManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                Intent intent = new Intent("LOCATION_UPDATE");
                intent.putExtra("LATITUDE",location.getLatitude());
                intent.putExtra("LONGITUDE",location.getLongitude());

                sendBroadcast(intent);

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startService(intent);
            }
        };

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        //noinspection MissingPermission
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }
}
