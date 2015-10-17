package com.example.the_game.housingcamview.utility;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

/**
 * This class returns a font from the cache if available, else create a font,
 * adds to the cache and returns it
 *
 * @author gayathri_nair
 */
public class FontCache {

  private static HashMap<String, Typeface> cache = new HashMap<String, Typeface>();
  private static final String RUPEE_FONT = "fonts/Rupee_Foradian.ttf";

  public static Typeface get(String name, Context context) {
    Typeface typeFace = cache.get(name);
    if (typeFace == null) {
      try {
        typeFace = Typeface.createFromAsset(context.getAssets(), name);
        cache.put(name, typeFace);
      } catch (Exception e) {
        return Typeface.DEFAULT;
      }
    }
    return typeFace;
  }

  public static Typeface getRupeeTypeFace(Context context) {
    return get(RUPEE_FONT, context);
  }
}

