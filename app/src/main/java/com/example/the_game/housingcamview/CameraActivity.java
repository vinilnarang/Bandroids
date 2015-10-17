package com.example.the_game.housingcamview;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.the_game.housingcamview.FlatStructures.PropertyData;
import com.example.the_game.housingcamview.FlatStructures.PropertyStructure;

import java.util.ArrayList;

public class CameraActivity extends AppCompatActivity implements SensorEventListener {

  private final static String DEBUG_TAG = "MakePhotoActivity";
  private Camera camera = null;
  private int cameraId = 0;
  private CameraPreview mPreview;
  private SensorManager sensorManager;
  private Sensor accelerometer;
  private Sensor magnetometer;
  private float[] mGravity;
  private float[] mGeomagnetic;
  private HorizontalScrollView flatWorldHolder;
  private int flatWorldWidth;
  private TextView tv;
  private double screenWidth, screenHeight;
  private RelativeLayout flatWorld = null;
  ArrayList<? extends PropertyStructure> propertyList = new ArrayList<>();
  private LinearLayout propertyListView;
  private double maxDistance = PropertyData.getInstance().maxDistance;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_camera);

    Intent intent = getIntent();
    String service = intent.getStringExtra("service");


    switch (service) {
      case "buy":
        propertyList = PropertyData.getInstance().getBuyProperties();
        break;
      case "rent":
        propertyList = PropertyData.getInstance().getRentProperties();
        break;
      case "pg":
        propertyList = PropertyData.getInstance().getPgProperties();
        break;
    }

    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    flatWorldHolder = (HorizontalScrollView) findViewById(R.id.flat_world_holder);
    tv = (TextView) findViewById(R.id.tv);
    propertyListView = (LinearLayout) findViewById(R.id.property_list);

    sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    if (accelerometer == null) {
      Log.d("TAG", "accelerometer is null");
    }
    magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    if (magnetometer == null) {
      Log.d("TAG", "magnetometer is null");
    }

    // do we have a camera?
    if (!getPackageManager()
        .hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
      Toast.makeText(this, "No camera on this device", Toast.LENGTH_LONG)
          .show();
    } else {
      cameraId = findFrontFacingCamera();
      if (cameraId < 0) {
        Toast.makeText(this, "No front facing camera found.",
            Toast.LENGTH_LONG).show();
      } else {
        camera = Camera.open(cameraId);
        mPreview = new CameraPreview(this, camera);
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(mPreview);
        Camera.Parameters p = camera.getParameters();
        //double thetaV = Math.toRadians(p.getVerticalViewAngle());
        double thetaH = p.getHorizontalViewAngle();
        screenWidth = getScreenWidthUsingDisplayMetrics();
        screenHeight = getScreenHeightUsingDisplayMetrics();
        flatWorldWidth = (int) (360 / thetaH * screenWidth);
        //flatWorldWidth = (int) (4 * screenWidth);
        flatWorld = (RelativeLayout) findViewById(R.id.flat_world);
        flatWorld.post(new Runnable() {
          @Override
          public void run() {
            final FrameLayout.LayoutParams flatWorldLayoutParams = (FrameLayout.LayoutParams) flatWorld.getLayoutParams();
            flatWorldLayoutParams.width = flatWorldWidth;
            flatWorld.setLayoutParams(flatWorldLayoutParams);
            plotProperties(propertyList);

          }
        });
      }
    }
  }

  private void plotProperties(ArrayList<? extends PropertyStructure> propertyList) {
    int i = 1;
    for (PropertyStructure property : propertyList) {
      addViewToFlatWorld(property, i);
      i++;
    }
  }


  private int findFrontFacingCamera() {
    int cameraId = -1;
    // Search for the front facing camera
    int numberOfCameras = Camera.getNumberOfCameras();
    for (int i = 0; i < numberOfCameras; i++) {
      Camera.CameraInfo info = new Camera.CameraInfo();
      Camera.getCameraInfo(i, info);
      if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
        Log.d(DEBUG_TAG, "Camera found");
        cameraId = i;
        break;
      }
    }
    return cameraId;
  }

  /**
   * gets screen width in pixels, Application Context should be used
   */
  public int getScreenWidthUsingDisplayMetrics() {
    DisplayMetrics displaymetrics = new DisplayMetrics();
    WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
    wm.getDefaultDisplay().getMetrics(displaymetrics);
    return displaymetrics.widthPixels;
  }

  /**
   * gets screen height in pixels, Application Context should be used
   */
  public int getScreenHeightUsingDisplayMetrics() {
    DisplayMetrics displaymetrics = new DisplayMetrics();
    WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
    wm.getDefaultDisplay().getMetrics(displaymetrics);
    return displaymetrics.heightPixels;
  }

  @Override
  protected void onPause() {
    if (camera != null) {
      camera.release();
      camera = null;
    }
    super.onPause();
    sensorManager.unregisterListener(this);
  }

  @Override
  public void onSensorChanged(SensorEvent sensorEvent) {
    if (sensorEvent.values == null) {
      Log.w("TAG", "event.values is null");
      return;
    }
    int sensorType = sensorEvent.sensor.getType();
    switch (sensorType) {
      case Sensor.TYPE_ACCELEROMETER:
        mGravity = sensorEvent.values;
        break;
      case Sensor.TYPE_MAGNETIC_FIELD:
        mGeomagnetic = sensorEvent.values;
        break;
      default:
        Log.w("TAG", "Unknown sensor type " + sensorType);
        return;
    }
    if (mGravity == null) {
      Log.w("TAG", "mGravity is null");
      return;
    }
    if (mGeomagnetic == null) {
      Log.w("TAG", "mGeomagnetic is null");
      return;
    }
    float R[] = new float[9];
    if (!SensorManager.getRotationMatrix(R, null, mGravity, mGeomagnetic)) {
      Log.w("TAG", "getRotationMatrix() failed");
      return;
    }

    float orientation[] = new float[9];
    SensorManager.getOrientation(R, orientation);
    // Orientation contains: azimuth, pitch and roll - we'll use roll
    float azimuthVal = orientation[0];
    float pitchVal = orientation[1];
    float roll = orientation[2];
    int azimuthDeg = (int) Math.round(Math.toDegrees(azimuthVal)) + 90;
    if (azimuthDeg > 180) {
      azimuthDeg = (360 - azimuthDeg) * -1;
    }
    int azimuth = degreesToPower(azimuthDeg);
    int pitchDeg = (int) Math.round(Math.toDegrees(pitchVal));
    int pitch = degreesToPower(pitchDeg);
    int rollDeg = (int) Math.round(Math.toDegrees(roll));
    int power = degreesToPower(rollDeg);
    int scrollAmount;
    double flatWorldMinusSwByTwo = (flatWorldWidth - screenWidth) / 2;
    if (azimuthDeg < 0) {
      //scrollAmount = (int) ((1 + azimuthDeg / 180) * (flatWorldWidth - screenWidth) / 2);
      scrollAmount = (int) (((180 + azimuthDeg) / 180f) * (flatWorldMinusSwByTwo));
    } else {
      scrollAmount = (int) (flatWorldMinusSwByTwo * (1 + azimuthDeg / 180f)) % flatWorldWidth;
    }

    tv.setText("azimuth " + String.valueOf(azimuthDeg) + "scroll amount" + scrollAmount + "pitch " + String.valueOf(pitch) + "roll " + String.valueOf(power));
    flatWorldHolder.smoothScrollTo(scrollAmount, flatWorldHolder.getScrollY());

  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int i) {

  }

  public void addViewToFlatWorld(PropertyStructure propertyStructure, int idx) {
    View propView = getLayoutInflater().inflate(R.layout.property_view_layout, flatWorld, false);
    //flatWorld.addView(propView);
    View dotView = getLayoutInflater().inflate(R.layout.prop_dot_view, flatWorld, false);
    flatWorld.addView(dotView);

    //((TextView) dotView.findViewById(R.id.tag_dot)).setText(idx + "");
    TextView dotTextView = (TextView) dotView.findViewById(R.id.tag_dot);
    dotTextView.setText(Html.fromHtml("<a href=\"" + propertyStructure.getURL() + "\">" + idx + "</a>"));
    dotTextView.setMovementMethod(LinkMovementMethod.getInstance());

    propertyListView.addView(propView);
    //((TextView) propView.findViewById(R.id.property_name)).setText(idx + " " + propertyStructure.getDisplayName());

    ((TextView) propView.findViewById(R.id.property_name)).setText(Html.fromHtml("<a style=\"text-decoration:none\" href=\"" + propertyStructure.getURL() + "\">" + idx + ".  " + propertyStructure.getDisplayName() + "</a>"));
    ((TextView) propView.findViewById(R.id.property_name)).setMovementMethod(LinkMovementMethod.getInstance());
    ((TextView) propView.findViewById(R.id.property_price)).setText("Rs." + propertyStructure.getDisplayPrice());
    ((TextView) propView.findViewById(R.id.property_distance)).setText(String.format("%.2f", propertyStructure.getDistance() / 1000) + " Km");
    double angleRatio = propertyStructure.getAngleRatio();
    float leftMargin = 0;
    if (angleRatio < 0.5) {
      leftMargin = (int) (((angleRatio + 1) * flatWorldWidth / 2) % flatWorldWidth);
    } else {
      leftMargin = (float) (flatWorldWidth / 2 * (angleRatio));
    }
    dotView.setTranslationX(leftMargin - propView.getWidth() / 2);
    dotView.setTranslationY((float) ((1 - propertyStructure.getDistance() / maxDistance) * screenHeight * 1.2));
    dotView.setScaleX(1.5f - (float) (propertyStructure.getDistance() / maxDistance));
    dotView.setScaleY(1.5f - (float) (propertyStructure.getDistance() / maxDistance));
    dotView.findViewById(R.id.red_circle).setAlpha((float) (propertyStructure.getDistance() / maxDistance));
    dotView.findViewById(R.id.green_circle).setAlpha((float) (1 - propertyStructure.getDistance() / maxDistance));
    Log.d(propertyStructure.getDisplayName(), propertyStructure.getAngleRatio() + " " + leftMargin);
    //propView.setLayoutParams((new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)));
    /*
    ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(100, 100);
    layoutParams.leftMargin = 200;
    layoutParams.bottomMargin = 400;
    //propView.setLayoutParams(layoutParams);
    //flatWorld.addView(propView, layoutParams);
    propView.setLayoutParams(layoutParams);

    Button btn = new Button(this);
    btn.setText(propertyStructure.getDisplayName());
    btn.setPadding(10, 0, 0, 0);*/
    // btn.setBackgroundColor(Integer.parseInt("#32000000"));
  }

  @Override
  protected void onResume() {
    super.onResume();
    sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
    sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
  }

  private int degreesToPower(int degrees) {
    // Tilted back towards user more than -90 deg
    if (degrees < -90) {
      degrees = -90;
    }
    // Tilted forward past 0 deg
    else if (degrees > 0) {
      degrees = 0;
    }
    // Normalize into a positive value
    degrees *= -1;
    // Invert from 90-0 to 0-90
    degrees = 90 - degrees;
    // Convert to scale of 0-100
    float degFloat = degrees / 90f * 100f;
    return (int) degFloat;
  }


}
