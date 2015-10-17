package com.example.the_game.housingcamview.beans;

/**
 * Created by Locker on 16/10/15.
 */

/*

public class Flats{
  String type;
  String title;
  String location_coordinates;
  String name;
  String id;
}
"19.117023,72.894769"
*/




public class Points {
  public Points(double lat, double lon) {
    this.lat = lat;
    this.lon = lon;
  }

  private double lat;
  private double lon;

  public void setLat(double lat) {
    this.lat = lat;
  }

  public void setLon(double lon) {
    this.lon = lon;
  }

  public double getLat() {

    return lat;
  }

  public double getLon() {
    return lon;
  }
}
