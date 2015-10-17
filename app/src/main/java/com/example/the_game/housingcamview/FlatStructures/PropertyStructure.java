package com.example.the_game.housingcamview.FlatStructures;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Locker on 17/10/15.
 */
abstract public class PropertyStructure implements Serializable {

  public void setDistance(double distance) {
    this.distance = distance;
  }

  public void setAngle(double angle) {
    this.angle = angle;
  }

  public double getDistance() {

    return distance;
  }

  public double getAngleRatio() {
    return angle/360;
  }

  double distance;
  double angle;

  abstract public double getLatitude() ;
  abstract public double getLongitude();
  abstract public String getDisplayName();
  abstract public String getDisplayPrice();

}
