package com.genymotion.binocle;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GpsSampleFragment extends Fragment {

    public static final String TAG = "Gps";
    private static final int EARTH_R = 6371; //km
    private TextView tvGpsWarning;
    private TextView tvGpsCurrentLat;
    private TextView tvGpsCurrentLng;
    private TextView tvGpsCurrentAlt;
    private TextView tvGpsCurrentBearing;
    private TextView tvGpsCurrentAccuracy;
    private TextView tvGpsDistanceToDalvik;
    private LocationManager locationManager;
    private LocationListener locationListener;

    /*
    Calculate distance in km between two coordinates using HaverSine method.
     */
    private static double getHaverSineDistance(double latSrc, double lngSrc, double latDst, double lngDst) {
        double dLat = Math.toRadians(latDst - latSrc);
        double dLon = Math.toRadians(lngDst - lngSrc);
        double latSrcRad = Math.toRadians(latSrc);
        double latDstRad = Math.toRadians(latDst);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(latSrcRad) * Math.cos(latDstRad);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_R * c;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationManager = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new MyLocationListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gps_sample, container, false);

        //retrieve widgets
        tvGpsWarning = (TextView) rootView.findViewById(R.id.tv_gpsWarning);
        tvGpsCurrentLat = (TextView) rootView.findViewById(R.id.tv_gpsCurrentLat);
        tvGpsCurrentLng = (TextView) rootView.findViewById(R.id.tv_gpsCurrentLng);
        tvGpsCurrentAlt = (TextView) rootView.findViewById(R.id.tv_gpsCurrentAlt);
        tvGpsCurrentBearing = (TextView) rootView.findViewById(R.id.tv_gpsCurrentBearing);
        tvGpsCurrentAccuracy = (TextView) rootView.findViewById(R.id.tv_gpsCurrentAccuracy);
        tvGpsDistanceToDalvik = (TextView) rootView.findViewById(R.id.tv_gpsDistanceToDalvik);

        //default values
        tvGpsWarning.setVisibility(View.GONE);
        tvGpsCurrentLat.setVisibility(View.INVISIBLE);
        tvGpsCurrentLng.setVisibility(View.INVISIBLE);
        tvGpsDistanceToDalvik.setVisibility(View.INVISIBLE);
        tvGpsCurrentAlt.setVisibility(View.INVISIBLE);
        tvGpsCurrentBearing.setVisibility(View.INVISIBLE);
        tvGpsCurrentAccuracy.setVisibility(View.INVISIBLE);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    @Override
    public void onPause() {
        // Unregister
        locationManager.removeUpdates(locationListener);

        super.onPause();
    }

    public class MyLocationListener implements LocationListener {

        static final double DALVIK_LAT = 65.966667;
        static final double DALVIK_LNG = -18.533327;

        @Override
        public void onLocationChanged(Location loc) {
            final double latitude = loc.getLatitude();
            final double longitude = loc.getLongitude();
            final double altitude = loc.getAltitude();
            final float bearing = loc.getBearing();
            final float accuracy = loc.getAccuracy();

            tvGpsCurrentLat.setText(getResources().getString(R.string.gps_currentLat, latitude));
            tvGpsCurrentLng.setText(getResources().getString(R.string.gps_currentLng, longitude));
            tvGpsCurrentAlt.setText(getResources().getString(R.string.gps_currentAltitude, altitude));
            tvGpsCurrentBearing.setText(getResources().getString(R.string.gps_currentBearing, bearing));
            tvGpsCurrentAccuracy.setText(getResources().getString(R.string.gps_currentAccuracy, accuracy));

            // Calculate distance using haversine
            double distance = GpsSampleFragment.getHaverSineDistance(latitude, longitude, DALVIK_LAT, DALVIK_LNG);
            tvGpsDistanceToDalvik.setText(getResources().getString(R.string.gps_distanceToDalvik, (int) distance));

            // Ensure labels are shown
            tvGpsCurrentLat.setVisibility(View.VISIBLE);
            tvGpsCurrentLng.setVisibility(View.VISIBLE);
            tvGpsDistanceToDalvik.setVisibility(View.VISIBLE);
            tvGpsCurrentAlt.setVisibility(View.VISIBLE);
            tvGpsCurrentBearing.setVisibility(View.VISIBLE);
            tvGpsCurrentAccuracy.setVisibility(View.VISIBLE);

            if (distance <= 100) {
                Log.d(GpsSampleFragment.TAG, "Show location warning");
                tvGpsWarning.setVisibility(View.VISIBLE);
            } else {
                Log.d(GpsSampleFragment.TAG, "Hide location warning");
                tvGpsWarning.setVisibility(View.GONE);
            }

        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    }
}
