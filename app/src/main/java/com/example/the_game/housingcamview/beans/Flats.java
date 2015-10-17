package com.example.the_game.housingcamview.beans;

/**
 * Created by Locker on 17/10/15.
 */
public class Flats {

  String type;
  String title;
  String location_coordinates;
  String name;
  String id;

  public Flats(String type, String title, String location_coordinates, String name, String id) {
    this.type = type;
    this.title = title;
    this.location_coordinates = location_coordinates;
    this.name = name;
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public String getTitle() {
    return title;
  }

  public String getLocation_coordinates() {
    return location_coordinates;
  }

  public String getName() {
    return name;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setLocation_coordinates(String location_coordinates) {
    this.location_coordinates = location_coordinates;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {

    return id;
  }

}