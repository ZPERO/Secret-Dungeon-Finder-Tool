package com.airbnb.lottie.parser;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableColorValue;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableTextProperties;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.parser.moshi.JsonReader.Options;
import java.io.IOException;

public class AnimatableTextPropertiesParser
{
  private static JsonReader.Options ANIMATABLE_PROPERTIES_NAMES = JsonReader.Options.init(new String[] { "fc", "sc", "sw", "t" });
  private static JsonReader.Options PROPERTIES_NAMES = JsonReader.Options.init(new String[] { "a" });
  
  private AnimatableTextPropertiesParser() {}
  
  public static AnimatableTextProperties parse(JsonReader paramJsonReader, LottieComposition paramLottieComposition)
    throws IOException
  {
    paramJsonReader.beginObject();
    AnimatableTextProperties localAnimatableTextProperties = null;
    while (paramJsonReader.hasNext()) {
      if (paramJsonReader.selectName(PROPERTIES_NAMES) != 0)
      {
        paramJsonReader.skipName();
        paramJsonReader.skipValue();
      }
      else
      {
        localAnimatableTextProperties = parseAnimatableTextProperties(paramJsonReader, paramLottieComposition);
      }
    }
    paramJsonReader.endObject();
    paramJsonReader = localAnimatableTextProperties;
    if (localAnimatableTextProperties == null) {
      paramJsonReader = new AnimatableTextProperties(null, null, null, null);
    }
    return paramJsonReader;
  }
  
  private static AnimatableTextProperties parseAnimatableTextProperties(JsonReader paramJsonReader, LottieComposition paramLottieComposition)
    throws IOException
  {
    paramJsonReader.beginObject();
    AnimatableColorValue localAnimatableColorValue2 = null;
    AnimatableColorValue localAnimatableColorValue1 = null;
    AnimatableFloatValue localAnimatableFloatValue2 = null;
    AnimatableFloatValue localAnimatableFloatValue1 = null;
    while (paramJsonReader.hasNext())
    {
      int i = paramJsonReader.selectName(ANIMATABLE_PROPERTIES_NAMES);
      if (i != 0)
      {
        if (i != 1)
        {
          if (i != 2)
          {
            if (i != 3)
            {
              paramJsonReader.skipName();
              paramJsonReader.skipValue();
            }
            else
            {
              localAnimatableFloatValue1 = AnimatableValueParser.parseFloat(paramJsonReader, paramLottieComposition);
            }
          }
          else {
            localAnimatableFloatValue2 = AnimatableValueParser.parseFloat(paramJsonReader, paramLottieComposition);
          }
        }
        else {
          localAnimatableColorValue1 = AnimatableValueParser.parseColor(paramJsonReader, paramLottieComposition);
        }
      }
      else {
        localAnimatableColorValue2 = AnimatableValueParser.parseColor(paramJsonReader, paramLottieComposition);
      }
    }
    paramJsonReader.endObject();
    return new AnimatableTextProperties(localAnimatableColorValue2, localAnimatableColorValue1, localAnimatableFloatValue2, localAnimatableFloatValue1);
  }
}
