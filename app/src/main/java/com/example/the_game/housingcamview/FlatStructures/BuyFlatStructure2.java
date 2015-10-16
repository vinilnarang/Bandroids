package com.example.the_game.housingcamview.FlatStructures;

/**
 * Created by the_game on 17/10/15.
 */
public class BuyFlatStructure2 {
  public String formatted_price;
  public String type;
  public String title;
  public double latitude;
  public double longitude;
  public String name;
  public int id;
  public BuyFlatStructure2(String formatted_price,
                    String type,
                    String title,
                    double latitude,
                    double longitude,
                    String name,
                    int id){
    this.formatted_price = formatted_price;
    this.type = type;
    this.title = title;
    this.latitude = latitude;
    this.longitude = longitude;
    this.name = name;
    this.id = id;
  }
}
