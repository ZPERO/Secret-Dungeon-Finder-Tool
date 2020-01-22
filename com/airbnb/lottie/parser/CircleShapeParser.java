package com.airbnb.lottie.parser;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatablePointValue;
import com.airbnb.lottie.model.animatable.AnimatableValue;
import com.airbnb.lottie.model.content.CircleShape;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.parser.moshi.JsonReader.Options;
import java.io.IOException;

class CircleShapeParser
{
  private static JsonReader.Options NAMES = JsonReader.Options.init(new String[] { "nm", "p", "s", "hd", "d" });
  
  private CircleShapeParser() {}
  
  static CircleShape parse(JsonReader paramJsonReader, LottieComposition paramLottieComposition, int paramInt)
    throws IOException
  {
    boolean bool1;
    if (paramInt == 3) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    String str = null;
    AnimatableValue localAnimatableValue = null;
    AnimatablePointValue localAnimatablePointValue = null;
    boolean bool2 = false;
    while (paramJsonReader.hasNext())
    {
      paramInt = paramJsonReader.selectName(NAMES);
      if (paramInt != 0)
      {
        if (paramInt != 1)
        {
          if (paramInt != 2)
          {
            if (paramInt != 3)
            {
              if (paramInt != 4)
              {
                paramJsonReader.skipName();
                paramJsonReader.skipValue();
              }
              else if (paramJsonReader.nextInt() == 3)
              {
                bool1 = true;
              }
              else
              {
                bool1 = false;
              }
            }
            else {
              bool2 = paramJsonReader.nextBoolean();
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
    return new CircleShape(str, localAnimatableValue, localAnimatablePointValue, bool1, bool2);
  }
}
