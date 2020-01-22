package com.airbnb.lottie.parser;

import android.graphics.Path.FillType;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableColorValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.content.ShapeFill;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.parser.moshi.JsonReader.Options;
import java.io.IOException;

class ShapeFillParser
{
  private static final JsonReader.Options NAMES = JsonReader.Options.init(new String[] { "nm", "c", "o", "fillEnabled", "r", "hd" });
  
  private ShapeFillParser() {}
  
  static ShapeFill parse(JsonReader paramJsonReader, LottieComposition paramLottieComposition)
    throws IOException
  {
    String str = null;
    AnimatableColorValue localAnimatableColorValue = null;
    AnimatableIntegerValue localAnimatableIntegerValue = null;
    int i = 1;
    boolean bool2 = false;
    boolean bool1 = false;
    while (paramJsonReader.hasNext())
    {
      int j = paramJsonReader.selectName(NAMES);
      if (j != 0)
      {
        if (j != 1)
        {
          if (j != 2)
          {
            if (j != 3)
            {
              if (j != 4)
              {
                if (j != 5)
                {
                  paramJsonReader.skipName();
                  paramJsonReader.skipValue();
                }
                else
                {
                  bool1 = paramJsonReader.nextBoolean();
                }
              }
              else {
                i = paramJsonReader.nextInt();
              }
            }
            else {
              bool2 = paramJsonReader.nextBoolean();
            }
          }
          else {
            localAnimatableIntegerValue = AnimatableValueParser.parseInteger(paramJsonReader, paramLottieComposition);
          }
        }
        else {
          localAnimatableColorValue = AnimatableValueParser.parseColor(paramJsonReader, paramLottieComposition);
        }
      }
      else {
        str = paramJsonReader.nextString();
      }
    }
    if (i == 1) {
      paramJsonReader = Path.FillType.WINDING;
    } else {
      paramJsonReader = Path.FillType.EVEN_ODD;
    }
    return new ShapeFill(str, bool2, paramJsonReader, localAnimatableColorValue, localAnimatableIntegerValue, bool1);
  }
}
