package com.airbnb.lottie.parser;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableIntegerValue;
import com.airbnb.lottie.model.animatable.AnimatableShapeValue;
import com.airbnb.lottie.model.content.Mask;
import com.airbnb.lottie.model.content.Mask.MaskMode;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.Logger;
import java.io.IOException;

class MaskParser
{
  private MaskParser() {}
  
  static Mask parse(JsonReader paramJsonReader, LottieComposition paramLottieComposition)
    throws IOException
  {
    paramJsonReader.beginObject();
    Object localObject = null;
    AnimatableShapeValue localAnimatableShapeValue = null;
    AnimatableIntegerValue localAnimatableIntegerValue = null;
    boolean bool = false;
    while (paramJsonReader.hasNext())
    {
      String str = paramJsonReader.nextName();
      int i = str.hashCode();
      int j = -1;
      if (i != 111)
      {
        if (i != 3588)
        {
          if (i != 104433)
          {
            if ((i == 3357091) && (str.equals("mode")))
            {
              i = 0;
              break label127;
            }
          }
          else if (str.equals("inv"))
          {
            i = 3;
            break label127;
          }
        }
        else if (str.equals("pt"))
        {
          i = 1;
          break label127;
        }
      }
      else if (str.equals("o"))
      {
        i = 2;
        break label127;
      }
      i = -1;
      label127:
      if (i != 0)
      {
        if (i != 1)
        {
          if (i != 2)
          {
            if (i != 3) {
              paramJsonReader.skipValue();
            } else {
              bool = paramJsonReader.nextBoolean();
            }
          }
          else {
            localAnimatableIntegerValue = AnimatableValueParser.parseInteger(paramJsonReader, paramLottieComposition);
          }
        }
        else {
          localAnimatableShapeValue = AnimatableValueParser.parseShapeData(paramJsonReader, paramLottieComposition);
        }
      }
      else
      {
        localObject = paramJsonReader.nextString();
        i = ((String)localObject).hashCode();
        if (i != 97)
        {
          if (i != 105)
          {
            if (i != 110)
            {
              if (i != 115)
              {
                i = j;
              }
              else
              {
                i = j;
                if (((String)localObject).equals("s")) {
                  i = 1;
                }
              }
            }
            else
            {
              i = j;
              if (((String)localObject).equals("n")) {
                i = 2;
              }
            }
          }
          else
          {
            i = j;
            if (((String)localObject).equals("i")) {
              i = 3;
            }
          }
        }
        else
        {
          i = j;
          if (((String)localObject).equals("a")) {
            i = 0;
          }
        }
        if (i != 0)
        {
          if (i != 1)
          {
            if (i != 2)
            {
              if (i != 3)
              {
                localObject = new StringBuilder();
                ((StringBuilder)localObject).append("Unknown mask mode ");
                ((StringBuilder)localObject).append(str);
                ((StringBuilder)localObject).append(". Defaulting to Add.");
                Logger.warning(((StringBuilder)localObject).toString());
                localObject = Mask.MaskMode.MASK_MODE_ADD;
              }
              else
              {
                paramLottieComposition.addWarning("Animation contains intersect masks. They are not supported but will be treated like add masks.");
                localObject = Mask.MaskMode.MASK_MODE_INTERSECT;
              }
            }
            else {
              localObject = Mask.MaskMode.MASK_MODE_NONE;
            }
          }
          else {
            localObject = Mask.MaskMode.MASK_MODE_SUBTRACT;
          }
        }
        else {
          localObject = Mask.MaskMode.MASK_MODE_ADD;
        }
      }
    }
    paramJsonReader.endObject();
    return new Mask((Mask.MaskMode)localObject, localAnimatableShapeValue, localAnimatableIntegerValue, bool);
  }
}
