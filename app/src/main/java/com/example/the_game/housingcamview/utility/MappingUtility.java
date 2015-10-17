package com.example.the_game.housingcamview.utility;

import android.content.Context;

import com.example.the_game.housingcamview.FlatStructures.PropertyStructure;
import com.example.the_game.housingcamview.GPSTracker;
import com.example.the_game.housingcamview.beans.Points;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Locker on 16/10/15.
 */
public class MappingUtility {

  public static final int R = 6371;

  public static void updateAngleAndDistance(ArrayList<? extends PropertyStructure> arrayList, Points origin) {

    updateAngle(arrayList, origin);
    updateDistance(arrayList, origin);
  }

  public static void updateAngle(ArrayList<? extends PropertyStructure> arrayList, Points origin) {

    Iterator<? extends PropertyStructure> iterator = arrayList.iterator();
    ArrayList<Double> angleList = new ArrayList<Double>();
    while (iterator.hasNext()) {

      PropertyStructure current = iterator.next();
      double lat = current.getLatitude();
      double lon = current.getLongitude();
      double angle = getAngle(origin, new Points(lat, lon));
      current.setAngle(angle);
    }
  }

  public static void updateDistance(ArrayList<? extends PropertyStructure> arrayList, Points origin) {

    for (PropertyStructure current : arrayList) {
      double lat = current.getLatitude();
      double lon = current.getLongitude();
      double dis = getDistance(new Points(lat, lon), origin);
      current.setDistance(dis);
    }
  }


  public static double getDistance(Points source, Points origin) {

    double diffLat = Math.toRadians(source.getLat() - origin.getLat());
    double diffLon = Math.toRadians(source.getLon() - origin.getLon());
    double a = Math.sin(diffLat / 2) * Math.sin(diffLat / 2)
        + Math.cos(Math.toRadians(origin.getLat())) * Math.sin(diffLon / 2) * Math.sin(diffLon / 2);
    double distance = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)) * R * 1000;
    return distance;
  }


  public static double getAngle(Points p1, Points p2) {

    double angle = Math.toDegrees(Math.atan2((p2.getLon() - p1.getLon()), p2.getLat() - p1.getLat()));
    if (angle < 0) {
      angle += 360;
    }
    return angle;
  }

  public static double[] getLocationCoords(Context context) {
    GPSTracker gpsTracker = new GPSTracker(context);
    double[] coordinates = new double[2];
    coordinates[0] = 0;
    coordinates[1] = 0;
    if (gpsTracker.getIsGPSTrackingEnabled()) {
      coordinates[0] = gpsTracker.getLatitude();
      coordinates[1] = gpsTracker.getLongitude();
      return coordinates;
    } else {
      gpsTracker.showSettingsAlert();
      coordinates[0] = -1;
      coordinates[1] = -1;
      return coordinates;
    }
  }
}
