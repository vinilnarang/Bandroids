package com.example.the_game.housingcamview.FlatStructures;

import java.util.ArrayList;

/**
 * Created by ravikiransahajan on 17/10/15.
 */
public class PropertyData {

  private static PropertyData instance;

  public static synchronized void init() {
    if (instance == null) {
      instance = new PropertyData();
    }
  }

  public static PropertyData getInstance() {
    if (instance == null) {
      init();
    }
    return instance;
  }

  public ArrayList<BuyFlatStructure> getBuyProperties() {
    return buyProperties;
  }

  public ArrayList<PgFlatStructure> getPgProperties() {
    return pgProperties;
  }

  public ArrayList<RentFlatStructure> getRentProperties() {
    return rentProperties;
  }

  ArrayList<BuyFlatStructure> buyProperties = new ArrayList<>();
  ArrayList<PgFlatStructure> pgProperties = new ArrayList<>();
  ArrayList<RentFlatStructure> rentProperties = new ArrayList<>();

  public void setBuyProperties(ArrayList<BuyFlatStructure> buyProperties) {
    this.buyProperties = buyProperties;
  }

  public void setPgProperties(ArrayList<PgFlatStructure> pgProperties) {
    this.pgProperties = pgProperties;
  }

  public void setRentProperties(ArrayList<RentFlatStructure> rentProperties) {
    this.rentProperties = rentProperties;
  }
}
