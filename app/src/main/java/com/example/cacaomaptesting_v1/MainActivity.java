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

import net.daum.android.map.coord.MapCoordLatLng;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;
import net.daum.mf.map.api.MapPOIItem;

public class MainActivity extends AppCompatActivity{

        private MapView mMapView;




        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_main);


            MapPoint jije_loc = MapPoint.mapPointWithGeoCoord(37.018787, 127.070442);
            mMapView = (MapView) findViewById(R.id.map_view);
            mMapView.setMapCenterPoint(jije_loc, true);
            mMapView.setZoomLevel(3, true);

            MapPOIItem marker = new MapPOIItem();
            marker.setItemName("Default Marker");
            marker.setTag(0);
            marker.setMapPoint(jije_loc);
            marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
            mMapView.addPOIItem(marker);



//            MapPOIItem customMarker = new MapPOIItem();
//            customMarker.setItemName("Custom Marker");
//            customMarker.setTag(1);
//            customMarker.setMapPoint(jije_loc);
//            customMarker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커타입을 커스텀 마커로 지정.
//            customMarker.setCustomImageResourceId(R.drawable.steam); // 마커 이미지.
//            customMarker.setCustomImageAutoscale(true); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
//            customMarker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
//            mMapView.addPOIItem(customMarker);
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();



        }

}
