package com.airbnb.lottie.parser;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.content.Repeater;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.parser.moshi.JsonReader.Options;
import java.io.IOException;

class RepeaterParser
{
  private static JsonReader.Options NAMES = JsonReader.Options.init(new String[] { "nm", "c", "o", "tr", "hd" });
  
  private RepeaterParser() {}
  
  static Repeater parse(JsonReader paramJsonReader, LottieComposition paramLottieComposition)
    throws IOException
  {
    String str = null;
    AnimatableFloatValue localAnimatableFloatValue2 = null;
    AnimatableFloatValue localAnimatableFloatValue1 = null;
    AnimatableTransform localAnimatableTransform = null;
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
              localAnimatableTransform = AnimatableTransformParser.parse(paramJsonReader, paramLottieComposition);
            }
          }
          else {
            localAnimatableFloatValue1 = AnimatableValueParser.parseFloat(paramJsonReader, paramLottieComposition, false);
          }
        }
        else {
          localAnimatableFloatValue2 = AnimatableValueParser.parseFloat(paramJsonReader, paramLottieComposition, false);
        }
      }
      else {
        str = paramJsonReader.nextString();
      }
    }
    return new Repeater(str, localAnimatableFloatValue2, localAnimatableFloatValue1, localAnimatableTransform, bool);
  }
}
