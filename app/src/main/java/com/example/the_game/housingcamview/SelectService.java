package com.example.the_game.housingcamview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class SelectService extends AppCompatActivity implements View.OnClickListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_select_service);
    Button buyButton = (Button) findViewById(R.id.buyButton);
    Button rentButton = (Button) findViewById(R.id.rentButton);
    Button pgButton = (Button) findViewById(R.id.pgButton);
    buyButton.setOnClickListener(this);
    rentButton.setOnClickListener(this);
    pgButton.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.buyButton:
        getFlatsList("buy");
        break;
      case R.id.rentButton:
        getFlatsList("rent");
        break;
      case R.id.pgButton:
        getFlatsList("pg");
        break;
    }
  }

  public void getFlatsList(String service) {
    double[] coordinates = getLocationCoords();
    if (coordinates[0] == (0) && coordinates[1] == 0) {
      Toast.makeText(this, "OOPS, Something happened. Please try later!", Toast.LENGTH_SHORT).show();
      return;
    }

    getPolygonUUID(coordinates, service);
  }

  public void getPolygonUUID(double[] coordinates, String service) {
    String url = "https://regions.housing.com/api/v2/polygon/near_me/?lat=" + coordinates[0] + "&lng=" + coordinates[1];
    try {
      PolygonUUIDFetchAsyncTask polygonUUIDFetchAsyncTask = new PolygonUUIDFetchAsyncTask(this);
      polygonUUIDFetchAsyncTask.execute(new UrlServiceParams(url,service));
    } catch (IOException e) {
      Toast.makeText(this, "Error fetching data! Please try again in sometime.", Toast.LENGTH_LONG).show();
      e.printStackTrace();
      return;
    }
    return;
  }

  public double[] getLocationCoords() {
    GPSTracker gpsTracker = new GPSTracker(this);
    double[] coordinates = new double[2];
    coordinates[0] = 0;
    coordinates[1] = 0;
    if (gpsTracker.getIsGPSTrackingEnabled()) {
      coordinates[0] = gpsTracker.latitude;
      coordinates[1] = gpsTracker.longitude;
      return coordinates;
    } else {
      gpsTracker.showSettingsAlert();
      coordinates[0] = -1;
      coordinates[1] = -1;
      return coordinates;
    }
  }

}
