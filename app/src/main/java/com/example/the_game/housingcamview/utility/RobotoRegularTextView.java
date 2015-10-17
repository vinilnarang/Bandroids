package com.example.the_game.housingcamview.utility;

import android.content.Context;
import android.util.AttributeSet;

/**
 * This custom text view class has to be used instead of the <TextView/> element in an xml wherever
 * this font is needed.
 * Created by gayathri_nair on 12/03/15.
 */
public class RobotoRegularTextView extends CustomTextView {

  public static final String FONT_NAME = "fonts/Roboto-Bold.ttf";

  public RobotoRegularTextView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public RobotoRegularTextView(Context context, AttributeSet attrs) {
    super(context, attrs);

  }

  public RobotoRegularTextView(Context context) {
    super(context);
  }


  @Override
  protected String getFontName() {
    return FONT_NAME;
  }
}

