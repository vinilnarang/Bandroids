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
import com.example.the_game.housingcamview.FlatStructures.RentFlatStructure;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DisplayFlatsList extends AppCompatActivity {

  ListView listView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_display_flats_list);
    listView = (ListView) findViewById(R.id.listView);
    Intent intent = getIntent();
    String service = intent.getStringExtra("service");
    String result = intent.getStringExtra("result");
    parseData(service, result);

  }

  public void parseData(String service, String result) {
    switch (service) {
      case "buy":
        ArrayList<BuyFlatStructure> buyFlatStructureArrayList = parseBuyData(result);
        break;
      case "rent":
        ArrayList<RentFlatStructure> rentFlatStructureArrayList = parseRentData(result);
        break;
      case "pg":
        ArrayList<PgFlatStructure> pgFlatStructureArrayList = parsePgData(result);
        break;
    }
  }

  public ArrayList<BuyFlatStructure> parseBuyData(String result) {
    try {
      JSONObject jsonObject = new JSONObject(result);
      JSONArray jsonArray = jsonObject.getJSONArray("hits");
      Gson gson = new Gson();
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

      //String result1 = jsonObject.getString("hits");
      //JSONObject jsonObject1 = new JSONObject(result1);
      JSONArray jsonArray = jsonObject1.getJSONArray("hits");
      Gson gson = new Gson();
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

      //String result1 = jsonObject.getString("hits");
      //JSONObject jsonObject1 = new JSONObject(result1);
      JSONArray jsonArray = jsonObject1.getJSONArray("hits");
      Gson gson = new Gson();
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
