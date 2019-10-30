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

// 컬러 클래스 사용
import android.graphics.Color;

import net.daum.android.map.coord.MapCoordLatLng;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapCircle;

// 동적 배열을 이용해 그라디언트 싸이클 만들기
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

        private MapView mMapView;




        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_main);

            double dist = 0.000002 * 500 * 5;
            MapPoint jije_loc = MapPoint.mapPointWithGeoCoord(37.018787, 127.070442);
            MapPoint jije_loc02 = MapPoint.mapPointWithGeoCoord(jije_loc.getMapPointGeoCoord().latitude - dist, jije_loc.getMapPointGeoCoord().longitude - dist);
            mMapView = (MapView) findViewById(R.id.map_view);
            mMapView.setMapCenterPoint(jije_loc, true);
            mMapView.setZoomLevel(3, true);

            MapPOIItem marker = new MapPOIItem();
            marker.setItemName("Default Marker");
            marker.setTag(0);
            marker.setMapPoint(jije_loc02);
            marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
            mMapView.addPOIItem(marker);

            MapPOIItem marker2 = new MapPOIItem();
            marker2.setItemName("Default Marker");
            marker2.setTag(0);
            marker2.setMapPoint(jije_loc);
            marker2.setMarkerType(MapPOIItem.MarkerType.RedPin); // 기본으로 제공하는 BluePin 마커 모양.
            marker2.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
            mMapView.addPOIItem(marker2);


//            MapCircle circle2 = new MapCircle(
//                    jije_loc02, // center
//                    100, // radius
//                    Color.argb(128, 255, 0, 0), // strokeColor
//                    Color.argb(128, 255, 255, 0) // fillColor
//            );
//            circle2.setTag(1235);
//            mMapView.addCircle(circle2);


            int argb = Color.argb(128/4, 255, 255, 0); // fillColor
            makeGradientCircle(mMapView, jije_loc, argb, 1000/2, 8, 5, 2, 0.6f, true, false);

            int argb02 = Color.argb(128/4, 0, 255, 0); // fillColor
            makeGradientCircle(mMapView, jije_loc02, argb, 1000/2, 8, 15, 2, 0.6f, true, false);

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

        static ArrayList<MapCircle> makeGradientCircle(MapView mapview, MapPoint mappoint, int argb, int radius, int density, int tag, int centerdensity, float centerdensityarea, boolean show, boolean bordershow){

            int gap = radius / density;
            ArrayList<MapCircle> circles = new ArrayList<>();
            int alpha;
            if(bordershow){
                alpha = 255;
            }
            else{
                alpha = 0;
            }

            for(int i = 0; i< density; i++){
                circles.add(new MapCircle(
                        mappoint, // center
                        radius - gap * i, // radius
                        Color.argb(alpha, 0, 0, 0), // strokeColor
                        argb// fillColor
                )
                );
                circles.get(i).setTag(tag);
                if(show){
                    mapview.addCircle(circles.get(i));
                }
                if( i == density-1){
                    gap = (int)((radius * centerdensityarea) / (density*centerdensity));
                    for (int j=0; j<density*centerdensity; j++) {
                        circles.add(new MapCircle(
                                        mappoint, // center
                                gap * j, // radius
                                        Color.argb(alpha, 0, 0, 0), // strokeColor
                                        argb// fillColor
                                )
                        );
                        circles.get(i + j+1).setTag(tag);
                        if (show) {
                            mapview.addCircle(circles.get(i + j+1));
                        }
                    }
                }
            }
            return circles;
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();



        }

}
