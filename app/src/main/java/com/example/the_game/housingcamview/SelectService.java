package com.example.the_game.housingcamview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.the_game.housingcamview.utility.MappingUtility;

import java.io.IOException;

public class SelectService extends AppCompatActivity implements View.OnClickListener {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_select_service);
    ImageButton buyButton = (ImageButton) findViewById(R.id.buyButton);
    ImageButton rentButton = (ImageButton) findViewById(R.id.rentButton);
    ImageButton pgButton = (ImageButton) findViewById(R.id.pgButton);
    final TextView buyTextView = (TextView) findViewById(R.id.buyTextView);
    final TextView rentTextView = (TextView) findViewById(R.id.rentTextView);
    final TextView pgTextView = (TextView) findViewById(R.id.pgTextView);
    final View buyHighlighter = (View) findViewById(R.id.buyHighlighter);
    final View rentHighlighter = (View) findViewById(R.id.rentHighlighter);
    final View pgHighlighter = (View) findViewById(R.id.pgHighlighter);
    final ViewGroup.LayoutParams buyLayoutParams = buyHighlighter.getLayoutParams();
    final ViewGroup.LayoutParams rentLayoutParams = rentHighlighter.getLayoutParams();
    final ViewGroup.LayoutParams pgLayoutParams = pgHighlighter.getLayoutParams();
    buyTextView.post(new Runnable() {
      @Override
      public void run() {
        buyLayoutParams.width = buyTextView.getWidth();
        buyHighlighter.setLayoutParams(buyLayoutParams);
        rentLayoutParams.width = rentTextView.getWidth();
        rentHighlighter.setLayoutParams(rentLayoutParams);
        pgLayoutParams.width = pgTextView.getWidth();
        pgHighlighter.setLayoutParams(pgLayoutParams);
      }
    });
    buyTextView.setOnClickListener(this);
    rentTextView.setOnClickListener(this);
    pgTextView.setOnClickListener(this);

  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.buyTextView:
        getFlatsList("buy");
        break;
      case R.id.rentTextView:
        getFlatsList("rent");
        break;
      case R.id.pgTextView:
        getFlatsList("pg");
        break;
    }
  }

  public void getFlatsList(String service) {
    double[] coordinates = MappingUtility.getLocationCoords(this);
    if (coordinates[0] == (0) && coordinates[1] == 0) {
      Toast.makeText(this, "OOPS, Unable to fetch location. Please try later!", Toast.LENGTH_SHORT).show();
      return;
    }

    getPolygonUUID(coordinates, service);
  }

  public void getPolygonUUID(double[] coordinates, String service) {
    String url = "https://regions.housing.com/api/v2/polygon/near_me/?lat=" + coordinates[0] + "&lng=" + coordinates[1];
    try {
      PolygonUUIDFetchAsyncTask polygonUUIDFetchAsyncTask = new PolygonUUIDFetchAsyncTask(this);
      polygonUUIDFetchAsyncTask.execute(new UrlServiceParams(url, service));
    } catch (IOException e) {
      Toast.makeText(this, "Error fetching data! Please try again in sometime.", Toast.LENGTH_LONG).show();
      e.printStackTrace();
      return;
    }
    return;
  }


}
