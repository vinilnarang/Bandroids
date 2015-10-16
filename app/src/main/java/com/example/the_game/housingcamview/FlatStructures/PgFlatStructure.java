package com.example.the_game.housingcamview.FlatStructures;

/**
 * Created by the_game on 17/10/15.
 */

public class PgFlatStructure extends PropertyStructure {
  double latitude;
  double longitude;
  int id;
  int number_of_housemates;
  String formatted_min_rent;
  String seo_title;

  @Override
  public double getLatitude() {
    return latitude;
  }

  @Override
  public double getLongitude() {
    return longitude;
  }
}
