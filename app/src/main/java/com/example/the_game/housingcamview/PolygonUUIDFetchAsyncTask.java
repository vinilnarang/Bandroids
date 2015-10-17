package com.example.the_game.housingcamview;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by the_game on 16/10/15.
 */
public class PolygonUUIDFetchAsyncTask extends AsyncTask<UrlServiceParams, Void, UuidServiceParams> {

  private ProgressDialog dialog;
  private Context mContext;

  public PolygonUUIDFetchAsyncTask(Context context) throws IOException {
    this.mContext = context;
    dialog = new ProgressDialog(mContext);
  }

  @Override
  protected void onPreExecute() {
    dialog.setMessage("Fetching data for you! Please wait.");
    dialog.show();
  }

  @Override
  protected UuidServiceParams doInBackground(UrlServiceParams... params) {
    String url = params[0].url;
    String service = params[0].service;
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder().url(url).build();
    Response response = null;
    try {
      response = client.newCall(request).execute();
      return new UuidServiceParams(response.body().string(), service);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  protected void onPostExecute(UuidServiceParams result) {
    dialog.dismiss();
    if (result == null) {
      Toast.makeText(mContext, "Error fetching data! Please try again in sometime.", Toast.LENGTH_LONG).show();
    } else {
      try {
        JSONObject jsonObject = new JSONObject(result.uuid);
        String uuid = jsonObject.getString("uuid");
        String url = buildURLForFlatsListFetching(uuid, result.service);
        try {
          FlatsListFetchAsyncTask flatsListFetchAsyncTask = new FlatsListFetchAsyncTask(mContext);
          UrlServiceParams urlServiceParams = new UrlServiceParams(url, result.service);
          flatsListFetchAsyncTask.execute(urlServiceParams);
        } catch (IOException e) {
          Toast.makeText(mContext, "Error fetching data! Please try again in sometime.", Toast.LENGTH_LONG).show();
          e.printStackTrace();
          return;
        }
      } catch (JSONException e) {
        Toast.makeText(mContext, "Error parsing data! Please try again in sometime.", Toast.LENGTH_LONG).show();
        e.printStackTrace();
      }
    }
  }

  public String buildURLForFlatsListFetching(String uuid, String service) {
    String url = "";
    switch (service) {
      case "buy":
        url = "https://buy.housing.com/api/v1/buy/index/filter?sort_key=relevance&poly=" + uuid + "&results_per_page=1000";
        break;
      case "rent":
        url = "https://rails.housing.com//api/v3/rent/filter?poly=" + uuid + "&poly_q=true&details=true&sort_key=relevance&sort_order=ASC";
        break;
      case "pg":
        url = "https://pg.housing.com/api/v3/pg//filter?poly=" + uuid + "3&poly_q=true&sort_key=relevance&sort_order=ASC&details=true";
        break;
    }
    return url;
  }

}
