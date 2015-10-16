package com.example.the_game.housingcamview.FlatStructures;

/**
 * Created by Locker on 17/10/15.
 */
abstract public class PropertyStructure {

  public void setDistance(double distance) {
    this.distance = distance;
  }

  public void setAngle(double angle) {
    this.angle = angle;
  }

  public double getDistance() {

    return distance;
  }

  public double getAngle() {
    return angle;
  }

  double distance;
  double angle;

  abstract public double getLatitude() ;
  abstract public double getLongitude();

}
