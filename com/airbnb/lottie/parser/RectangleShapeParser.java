package com.airbnb.lottie.parser;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatablePointValue;
import com.airbnb.lottie.model.animatable.AnimatableValue;
import com.airbnb.lottie.model.content.RectangleShape;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.parser.moshi.JsonReader.Options;
import java.io.IOException;

class RectangleShapeParser
{
  private static JsonReader.Options NAMES = JsonReader.Options.init(new String[] { "nm", "p", "s", "r", "hd" });
  
  private RectangleShapeParser() {}
  
  static RectangleShape parse(JsonReader paramJsonReader, LottieComposition paramLottieComposition)
    throws IOException
  {
    String str = null;
    AnimatableValue localAnimatableValue = null;
    AnimatablePointValue localAnimatablePointValue = null;
    AnimatableFloatValue localAnimatableFloatValue = null;
    boolean bool = false;
    while (paramJsonReader.hasNext())
    {
      int i = paramJsonReader.selectName(NAMES);
      if (i != 0)
      {
        if (i != 1)
        {
          if (i != 2)
          {
            if (i != 3)
            {
              if (i != 4) {
                paramJsonReader.skipValue();
              } else {
                bool = paramJsonReader.nextBoolean();
              }
            }
            else {
              localAnimatableFloatValue = AnimatableValueParser.parseFloat(paramJsonReader, paramLottieComposition);
            }
          }
          else {
            localAnimatablePointValue = AnimatableValueParser.parsePoint(paramJsonReader, paramLottieComposition);
          }
        }
        else {
          localAnimatableValue = AnimatablePathValueParser.parseSplitPath(paramJsonReader, paramLottieComposition);
        }
      }
      else {
        str = paramJsonReader.nextString();
      }
    }
    return new RectangleShape(str, localAnimatableValue, localAnimatablePointValue, localAnimatableFloatValue, bool);
  }
}
