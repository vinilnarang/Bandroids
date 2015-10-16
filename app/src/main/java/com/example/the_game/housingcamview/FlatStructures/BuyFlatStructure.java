package com.example.the_game.housingcamview.FlatStructures;

/**
 * Created by the_game on 17/10/15.
 */
public class BuyFlatStructure extends PropertyStructure {
  String formatted_price;
  String type;
  String title;
  String location_coordinates;
  String name;
  int id;

  @Override
  public double getLatitude() {

    String[] latlon = location_coordinates.split(",");
    double lat=Double.parseDouble(latlon[0]);
    return lat;
  }

  @Override
  public double getLongitude() {
    String[] latlon = location_coordinates.split(",");
    double lon=Double.parseDouble(latlon[1]);
    return lon;
  }
}


