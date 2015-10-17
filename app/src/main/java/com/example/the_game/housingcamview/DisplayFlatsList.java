package com.example.the_game.housingcamview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.the_game.housingcamview.FlatStructures.BuyFlatStructure;
import com.example.the_game.housingcamview.FlatStructures.PgFlatStructure;
import com.example.the_game.housingcamview.FlatStructures.PropertyData;
import com.example.the_game.housingcamview.FlatStructures.RentFlatStructure;
import com.example.the_game.housingcamview.beans.Points;
import com.example.the_game.housingcamview.utility.MappingUtility;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DisplayFlatsList extends AppCompatActivity {

  ListView listView;
  Gson gson;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_display_flats_list);
    listView = (ListView) findViewById(R.id.listView);
    gson = new Gson();
    Intent intent = getIntent();
    String service = intent.getStringExtra("service");
    String result = intent.getStringExtra("result");
    parseData(service, result);
    PropertyData.init();
  }

  public void parseData(String service, String result) {
    double[] coordinates = MappingUtility.getLocationCoords(this);
    Points points = new Points(coordinates[0], coordinates[1]);
    Intent intent = new Intent(this, CameraActivity.class);
    intent.putExtra("service", service);
    switch (service) {
      case "buy":
        ArrayList<BuyFlatStructure> buyFlatStructureArrayList = parseBuyData(result);
        MappingUtility.updateAngleAndDistance(buyFlatStructureArrayList, points);
        PropertyData.getInstance().setBuyProperties(buyFlatStructureArrayList);
        break;
      case "rent":
        ArrayList<RentFlatStructure> rentFlatStructureArrayList = parseRentData(result);
        MappingUtility.updateAngleAndDistance(rentFlatStructureArrayList, points);
        PropertyData.getInstance().setRentProperties(rentFlatStructureArrayList);
        break;
      case "pg":
        ArrayList<PgFlatStructure> pgFlatStructureArrayList = parsePgData(result);
        MappingUtility.updateAngleAndDistance(pgFlatStructureArrayList, points);
        PropertyData.getInstance().setPgProperties(pgFlatStructureArrayList);
        break;
    }
    startActivity(intent);

  }

  public ArrayList<BuyFlatStructure> parseBuyData(String result) {
    try {
      JSONObject jsonObject = new JSONObject(result);
      JSONArray jsonArray = jsonObject.getJSONArray("hits");
      ArrayList<BuyFlatStructure> list = new ArrayList<BuyFlatStructure>();
      for (int i = 0; i < jsonArray.length(); i++) {
        String str = jsonArray.getString(i);
        BuyFlatStructure buyFlatStructure = gson.fromJson(str, BuyFlatStructure.class);
        list.add(buyFlatStructure);
      }
      return list;
      //ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
      //listView.setAdapter(adapter);
    } catch (JSONException e) {
      Toast.makeText(getApplicationContext(), "Error parsing data! Please try again in sometime.", Toast.LENGTH_LONG).show();
      e.printStackTrace();
      return null;
    }
  }

  public ArrayList<RentFlatStructure> parseRentData(String result) {
    try {
      JSONObject jsonObject = new JSONObject(result);
      JSONObject jsonObject1 = jsonObject.getJSONObject("hits");

      JSONArray jsonArray = jsonObject1.getJSONArray("hits");
      ArrayList<RentFlatStructure> list = new ArrayList<RentFlatStructure>();
      for (int i = 0; i < jsonArray.length(); i++) {
        String str = jsonArray.getJSONObject(i).getString("_source");
        RentFlatStructure rentFlatStructure = gson.fromJson(str, RentFlatStructure.class);
        list.add(rentFlatStructure);
      }
      return list;
    } catch (JSONException e) {
      Toast.makeText(getApplicationContext(), "Error parsing data! Please try again in sometime.", Toast.LENGTH_LONG).show();
      e.printStackTrace();
      return null;
    }
  }

  public ArrayList<PgFlatStructure> parsePgData(String result) {
    try {
      JSONObject jsonObject = new JSONObject(result);
      JSONObject jsonObject1 = jsonObject.getJSONObject("hits");

      JSONArray jsonArray = jsonObject1.getJSONArray("hits");
      ArrayList<PgFlatStructure> list = new ArrayList<PgFlatStructure>();
      for (int i = 0; i < jsonArray.length(); i++) {
        String str = jsonArray.getJSONObject(i).getString("_source");
        PgFlatStructure pgFlatStructure = gson.fromJson(str, PgFlatStructure.class);
        list.add(pgFlatStructure);
      }
      return list;
    } catch (JSONException e) {
      Toast.makeText(getApplicationContext(), "Error parsing data! Please try again in sometime.", Toast.LENGTH_LONG).show();
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_display_flats_list, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
