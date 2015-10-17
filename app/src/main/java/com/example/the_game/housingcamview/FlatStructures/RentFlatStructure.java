package com.example.the_game.housingcamview.FlatStructures;

/**
 * Created by the_game on 17/10/15.
 */

public class RentFlatStructure extends PropertyStructure {
  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setFormatted_rent(String formatted_rent) {
    this.formatted_rent = formatted_rent;
  }

  public void setApartment_type(String apartment_type) {
    this.apartment_type = apartment_type;
  }

  @Override
  public double getLatitude() {

    return latitude;
  }

  @Override
  public double getLongitude() {
    return longitude;
  }

  @Override
  public String getDisplayName() {
    return apartment_type;
  }

  @Override
  public String getDisplayPrice() {
    return formatted_rent;
  }

  public int getId() {
    return id;
  }

  public String getFormatted_rent() {
    return formatted_rent;
  }

  public String getApartment_type() {
    return apartment_type;
  }

  public String getCanonical_url() {
    return canonical_url;
  }

  @Override
  public String getURL() {
    return getCanonical_url();
  }

  double latitude;
  double longitude;
  int id;
  String formatted_rent;
  String apartment_type;
  String canonical_url;
}
