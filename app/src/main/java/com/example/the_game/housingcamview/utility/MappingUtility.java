package com.example.the_game.housingcamview.utility;

import android.graphics.Point;

import com.example.the_game.housingcamview.beans.Flats;
import com.example.the_game.housingcamview.beans.Points;
import com.example.the_game.housingcamview.FlatStructures.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

/**
 * Created by Locker on 16/10/15.
 */
public class MappingUtility {

  public final String TAG = "MappingUtility";
  public final int R = 6371;

  public void updateAngleAndDistance(ArrayList<PropertyStructure> arrayList,Points origin){

    updateAngle(arrayList,origin);
    updateDistance(arrayList,origin);
  }

  public void updateAngle(ArrayList<PropertyStructure> arrayList, Points origin) {

    Iterator<PropertyStructure> iterator = arrayList.iterator();
    ArrayList<Double> angleList = new ArrayList<Double>();
    while (iterator.hasNext()) {

      PropertyStructure current = iterator.next();
      double lat = current.getLatitude();
      double lon = current.getLongitude();
      double angle = getAngle(new Points(lat, lon), origin);
      current.setAngle(angle);
    }
  }

  public void updateDistance(ArrayList<PropertyStructure> arrayList, Points origin) {

    for (PropertyStructure current : arrayList) {
      double lat = current.getLatitude();
      double lon = current.getLongitude();
      double dis = getDistance(new Points(lat, lon), origin);
      current.setDistance(dis);
    }
  }


  protected double getDistance(Points source, Points origin) {

    double diffLat = Math.toRadians(source.getLat() - origin.getLat());
    double diffLon = Math.toRadians(source.getLon() - origin.getLon());
    double a = Math.sin(diffLat / 2) * Math.sin(diffLat / 2)
        + Math.cos(Math.toRadians(origin.getLat())) * Math.sin(diffLon / 2) * Math.sin(diffLon / 2);
    double distance = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)) * R * 1000;
    return distance;
  }


  protected double getAngle(Points p1, Points p2) {

    double angle = Math.toDegrees(Math.atan2((p2.getLon() - p1.getLon()), p2.getLat() - p1.getLat()));
    if (angle < 0) {
      angle += 360;
    }
    return angle;
  }
}
