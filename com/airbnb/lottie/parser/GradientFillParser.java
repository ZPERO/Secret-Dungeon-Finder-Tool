package com.airbnb.lottie.parser;

import android.graphics.Path.FillType;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableGradientColorValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.animatable.AnimatablePointValue;
import com.airbnb.lottie.model.content.GradientFill;
import com.airbnb.lottie.model.content.GradientType;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.parser.moshi.JsonReader.Options;
import java.io.IOException;

class GradientFillParser
{
  private static final JsonReader.Options GRADIENT_NAMES = JsonReader.Options.init(new String[] { "p", "k" });
  private static final JsonReader.Options NAMES = JsonReader.Options.init(new String[] { "nm", "g", "o", "t", "s", "e", "r", "hd" });
  
  private GradientFillParser() {}
  
  static GradientFill parse(JsonReader paramJsonReader, LottieComposition paramLottieComposition)
    throws IOException
  {
    Object localObject3 = Path.FillType.WINDING;
    String str = null;
    Object localObject2 = null;
    AnimatableGradientColorValue localAnimatableGradientColorValue = null;
    AnimatableIntegerValue localAnimatableIntegerValue = null;
    AnimatablePointValue localAnimatablePointValue2 = null;
    AnimatablePointValue localAnimatablePointValue1 = null;
    boolean bool = false;
    while (paramJsonReader.hasNext())
    {
      Object localObject1;
      switch (paramJsonReader.selectName(NAMES))
      {
      default: 
        paramJsonReader.skipName();
        paramJsonReader.skipValue();
        break;
      case 7: 
        bool = paramJsonReader.nextBoolean();
        break;
      case 6: 
        if (paramJsonReader.nextInt() == 1) {
          localObject1 = Path.FillType.WINDING;
        } else {
          localObject1 = Path.FillType.EVEN_ODD;
        }
        localObject3 = localObject1;
        break;
      case 5: 
        localAnimatablePointValue1 = AnimatableValueParser.parsePoint(paramJsonReader, paramLottieComposition);
        break;
      case 4: 
        localAnimatablePointValue2 = AnimatableValueParser.parsePoint(paramJsonReader, paramLottieComposition);
        break;
      case 3: 
        if (paramJsonReader.nextInt() == 1) {
          localObject1 = GradientType.LINEAR;
        } else {
          localObject1 = GradientType.RADIAL;
        }
        localObject2 = localObject1;
        break;
      case 2: 
        localAnimatableIntegerValue = AnimatableValueParser.parseInteger(paramJsonReader, paramLottieComposition);
        break;
      case 1: 
        int i = -1;
        paramJsonReader.beginObject();
        while (paramJsonReader.hasNext())
        {
          int j = paramJsonReader.selectName(GRADIENT_NAMES);
          if (j != 0)
          {
            if (j != 1)
            {
              paramJsonReader.skipName();
              paramJsonReader.skipValue();
            }
            else
            {
              localAnimatableGradientColorValue = AnimatableValueParser.parseGradientColor(paramJsonReader, paramLottieComposition, i);
            }
          }
          else {
            i = paramJsonReader.nextInt();
          }
        }
        paramJsonReader.endObject();
        break;
      case 0: 
        str = paramJsonReader.nextString();
      }
    }
    return new GradientFill(str, localObject2, (Path.FillType)localObject3, localAnimatableGradientColorValue, localAnimatableIntegerValue, localAnimatablePointValue2, localAnimatablePointValue1, null, null, bool);
  }
}
