package com.airbnb.lottie.parser;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.content.ShapeTrimPath;
import com.airbnb.lottie.model.content.ShapeTrimPath.Type;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.parser.moshi.JsonReader.Options;
import java.io.IOException;

class ShapeTrimPathParser
{
  private static JsonReader.Options NAMES = JsonReader.Options.init(new String[] { "s", "e", "o", "nm", "m", "hd" });
  
  private ShapeTrimPathParser() {}
  
  static ShapeTrimPath parse(JsonReader paramJsonReader, LottieComposition paramLottieComposition)
    throws IOException
  {
    String str = null;
    ShapeTrimPath.Type localType = null;
    AnimatableFloatValue localAnimatableFloatValue3 = null;
    AnimatableFloatValue localAnimatableFloatValue2 = null;
    AnimatableFloatValue localAnimatableFloatValue1 = null;
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
              if (i != 4)
              {
                if (i != 5) {
                  paramJsonReader.skipValue();
                } else {
                  bool = paramJsonReader.nextBoolean();
                }
              }
              else {
                localType = ShapeTrimPath.Type.forId(paramJsonReader.nextInt());
              }
            }
            else {
              str = paramJsonReader.nextString();
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
        localAnimatableFloatValue3 = AnimatableValueParser.parseFloat(paramJsonReader, paramLottieComposition, false);
      }
    }
    return new ShapeTrimPath(str, localType, localAnimatableFloatValue3, localAnimatableFloatValue2, localAnimatableFloatValue1, bool);
  }
}
