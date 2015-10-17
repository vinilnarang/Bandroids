package com.example.the_game.housingcamview;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by the_game on 16/10/15.
 */
public class FlatsListFetchAsyncTask extends AsyncTask<UrlServiceParams, Void, ResultServiceParams> {

  private ProgressDialog dialog;
  private Context mContext;

  public FlatsListFetchAsyncTask(Context context) throws IOException {
    this.mContext = context;
    dialog = new ProgressDialog(mContext);
  }

  @Override
  protected void onPreExecute() {
    dialog.setMessage("Fetching data for you! Please wait.");
    dialog.show();
  }

  @Override
  protected ResultServiceParams doInBackground(UrlServiceParams... params) {
    String url = params[0].url;
    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder().url(url).build();
    Response response = null;
    try {
      response = client.newCall(request).execute();
      return new ResultServiceParams(response.body().string(), params[0].service);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  protected void onPostExecute(ResultServiceParams resultServiceParams) {
    Intent intent = new Intent(mContext, DisplayFlatsList.class);
    intent.putExtra("result", resultServiceParams.result);
    intent.putExtra("service", resultServiceParams.service);
    mContext.startActivity(intent);
    dialog.dismiss();
  }
}
