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
  String inventory_canonical_url;
  int id;

  @Override
  public double getLatitude() {

    String[] latlon = location_coordinates.split(",");
    double lat = Double.parseDouble(latlon[0]);
    return lat;
  }

  @Override
  public double getLongitude() {
    String[] latlon = location_coordinates.split(",");
    double lon = Double.parseDouble(latlon[1]);
    return lon;
  }

  @Override
  public String getDisplayName() {
    if (name != null) {
      return name;
    } else {
      return title;
    }
  }

  @Override
  public String getDisplayPrice() {
    return formatted_price;
  }

  public String getInventory_canonical_url() {
    return "https://housing.com/" + inventory_canonical_url;
  }

  @Override
  public String getURL() {
    return getInventory_canonical_url();
  }


}


