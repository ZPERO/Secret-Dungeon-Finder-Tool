package com.airbnb.lottie.parser;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableGradientColorValue;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.animatable.AnimatablePointValue;
import com.airbnb.lottie.model.content.GradientStroke;
import com.airbnb.lottie.model.content.GradientType;
import com.airbnb.lottie.model.content.ShapeStroke.LineCapType;
import com.airbnb.lottie.model.content.ShapeStroke.LineJoinType;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.parser.moshi.JsonReader.Options;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class GradientStrokeParser
{
  private static final JsonReader.Options DASH_PATTERN_NAMES = JsonReader.Options.init(new String[] { "n", "v" });
  private static final JsonReader.Options GRADIENT_NAMES;
  private static JsonReader.Options NAMES = JsonReader.Options.init(new String[] { "nm", "g", "o", "t", "s", "e", "w", "lc", "lj", "ml", "hd", "d" });
  
  static
  {
    GRADIENT_NAMES = JsonReader.Options.init(new String[] { "p", "k" });
  }
  
  private GradientStrokeParser() {}
  
  static GradientStroke parse(JsonReader paramJsonReader, LottieComposition paramLottieComposition)
    throws IOException
  {
    ArrayList localArrayList = new ArrayList();
    String str1 = null;
    Object localObject3 = null;
    AnimatableGradientColorValue localAnimatableGradientColorValue = null;
    AnimatableIntegerValue localAnimatableIntegerValue = null;
    AnimatablePointValue localAnimatablePointValue2 = null;
    AnimatablePointValue localAnimatablePointValue1 = null;
    AnimatableFloatValue localAnimatableFloatValue = null;
    ShapeStroke.LineCapType localLineCapType = null;
    ShapeStroke.LineJoinType localLineJoinType = null;
    float f = 0.0F;
    Object localObject1 = null;
    boolean bool = false;
    while (paramJsonReader.hasNext())
    {
      Object localObject2;
      int i;
      switch (paramJsonReader.selectName(NAMES))
      {
      default: 
        paramJsonReader.skipName();
        paramJsonReader.skipValue();
        break;
      case 11: 
        paramJsonReader.beginArray();
        while (paramJsonReader.hasNext())
        {
          paramJsonReader.beginObject();
          String str2 = null;
          localObject2 = null;
          while (paramJsonReader.hasNext())
          {
            i = paramJsonReader.selectName(DASH_PATTERN_NAMES);
            if (i != 0)
            {
              if (i != 1)
              {
                paramJsonReader.skipName();
                paramJsonReader.skipValue();
              }
              for (;;)
              {
                break;
                localObject2 = AnimatableValueParser.parseFloat(paramJsonReader, paramLottieComposition);
              }
            }
            str2 = paramJsonReader.nextString();
          }
          paramJsonReader.endObject();
          if (str2.equals("o"))
          {
            localObject1 = localObject2;
          }
          else
          {
            if ((!str2.equals("d")) && (!str2.equals("g"))) {
              break label315;
            }
            paramLottieComposition.setHasDashPattern(true);
            localArrayList.add(localObject2);
          }
        }
        paramJsonReader.endArray();
        if (localArrayList.size() == 1) {
          localArrayList.add(localArrayList.get(0));
        }
        break;
      case 10: 
        bool = paramJsonReader.nextBoolean();
        break;
      case 9: 
        f = (float)paramJsonReader.nextDouble();
        break;
      case 8: 
        localLineJoinType = ShapeStroke.LineJoinType.values()[(paramJsonReader.nextInt() - 1)];
        break;
      case 7: 
        localLineCapType = ShapeStroke.LineCapType.values()[(paramJsonReader.nextInt() - 1)];
        break;
      case 6: 
        localAnimatableFloatValue = AnimatableValueParser.parseFloat(paramJsonReader, paramLottieComposition);
        break;
      case 5: 
        localAnimatablePointValue1 = AnimatableValueParser.parsePoint(paramJsonReader, paramLottieComposition);
        break;
      case 4: 
        localAnimatablePointValue2 = AnimatableValueParser.parsePoint(paramJsonReader, paramLottieComposition);
        break;
      case 3: 
        if (paramJsonReader.nextInt() == 1) {
          localObject2 = GradientType.LINEAR;
        } else {
          localObject2 = GradientType.RADIAL;
        }
        localObject3 = localObject2;
        break;
      case 2: 
        localAnimatableIntegerValue = AnimatableValueParser.parseInteger(paramJsonReader, paramLottieComposition);
        break;
      case 1: 
        i = -1;
        paramJsonReader.beginObject();
        if (paramJsonReader.hasNext())
        {
          int j = paramJsonReader.selectName(GRADIENT_NAMES);
          if (j != 0) {
            if (j != 1)
            {
              paramJsonReader.skipName();
              paramJsonReader.skipValue();
            }
          }
          for (;;)
          {
            break;
            localAnimatableGradientColorValue = AnimatableValueParser.parseGradientColor(paramJsonReader, paramLottieComposition, i);
            break;
            i = paramJsonReader.nextInt();
          }
        }
        paramJsonReader.endObject();
        break;
      case 0: 
        label315:
        str1 = paramJsonReader.nextString();
      }
    }
    return new GradientStroke(str1, localObject3, localAnimatableGradientColorValue, localAnimatableIntegerValue, localAnimatablePointValue2, localAnimatablePointValue1, localAnimatableFloatValue, localLineCapType, localLineJoinType, f, localArrayList, localObject1, bool);
  }
}
