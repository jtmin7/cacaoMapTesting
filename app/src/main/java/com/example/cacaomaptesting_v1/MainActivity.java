package com.example.cacaomaptesting_v1;



import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

public class MainActivity extends AppCompatActivity{

        private static final String LOG_TAG = "MainActivity";

        private MapView mMapView;


        private static final int GPS_ENABLE_REQUEST_CODE = 2001;
        private static final int PERMISSIONS_REQUEST_CODE = 100;
        String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION};




        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_main);

            mMapView = (MapView) findViewById(R.id.map_view);
            //mMapView.setDaumMapApiKey(MapApiConst.DAUM_MAPS_ANDROID_APP_API_KEY);
//            mMapView.setCurrentLocationEventListener(this);
            mMapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.018787, 127.070442), true);
            mMapView.setZoomLevel(3, true);


        }

        @Override
        protected void onDestroy() {
            super.onDestroy();



        }

}
