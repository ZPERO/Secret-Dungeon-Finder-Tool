package com.airbnb.lottie.parser;

import android.graphics.Color;
import android.graphics.Rect;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableTextFrame;
import com.airbnb.lottie.model.animatable.AnimatableTextProperties;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.model.layer.Layer.LayerType;
import com.airbnb.lottie.model.layer.Layer.MatteType;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.parser.moshi.JsonReader.Options;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.Keyframe;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LayerParser
{
  private static final JsonReader.Options EFFECTS_NAMES = JsonReader.Options.init(new String[] { "nm" });
  private static final JsonReader.Options NAMES = JsonReader.Options.init(new String[] { "nm", "ind", "refId", "ty", "parent", "sw", "sh", "sc", "ks", "tt", "masksProperties", "shapes", "t", "ef", "sr", "st", "w", "h", "ip", "op", "tm", "cl", "hd" });
  private static final JsonReader.Options TEXT_NAMES = JsonReader.Options.init(new String[] { "d", "a" });
  
  private LayerParser() {}
  
  public static Layer parse(LottieComposition paramLottieComposition)
  {
    Rect localRect = paramLottieComposition.getBounds();
    return new Layer(Collections.emptyList(), paramLottieComposition, "__container", -1L, Layer.LayerType.PRE_COMP, -1L, null, Collections.emptyList(), new AnimatableTransform(), 0, 0, 0, 0.0F, 0.0F, localRect.width(), localRect.height(), null, null, Collections.emptyList(), Layer.MatteType.NONE, null, false);
  }
  
  public static Layer parse(JsonReader paramJsonReader, LottieComposition paramLottieComposition)
    throws IOException
  {
    Layer.MatteType localMatteType = Layer.MatteType.NONE;
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    paramJsonReader.beginObject();
    Float localFloat1 = Float.valueOf(1.0F);
    Float localFloat2 = Float.valueOf(0.0F);
    Layer.LayerType localLayerType = null;
    String str3 = null;
    AnimatableTransform localAnimatableTransform = null;
    AnimatableTextFrame localAnimatableTextFrame = null;
    AnimatableTextProperties localAnimatableTextProperties = null;
    AnimatableFloatValue localAnimatableFloatValue = null;
    long l2 = -1L;
    float f4 = 0.0F;
    float f3 = 0.0F;
    float f2 = 1.0F;
    int n = 0;
    int m = 0;
    int k = 0;
    int j = 0;
    int i = 0;
    float f1 = 0.0F;
    boolean bool = false;
    long l1 = 0L;
    String str2 = null;
    String str1 = "UNSET";
    while (paramJsonReader.hasNext())
    {
      Object localObject;
      int i1;
      switch (paramJsonReader.selectName(NAMES))
      {
      default: 
        paramJsonReader.skipName();
        paramJsonReader.skipValue();
        break;
      case 22: 
        bool = paramJsonReader.nextBoolean();
        break;
      case 21: 
        str2 = paramJsonReader.nextString();
        break;
      case 20: 
        localAnimatableFloatValue = AnimatableValueParser.parseFloat(paramJsonReader, paramLottieComposition, false);
        break;
      case 19: 
        f3 = (float)paramJsonReader.nextDouble();
        break;
      case 18: 
        f4 = (float)paramJsonReader.nextDouble();
        break;
      case 17: 
        i = (int)(paramJsonReader.nextInt() * Utils.dpScale());
        break;
      case 16: 
        j = (int)(paramJsonReader.nextInt() * Utils.dpScale());
        break;
      case 15: 
        f1 = (float)paramJsonReader.nextDouble();
        break;
      case 14: 
        f2 = (float)paramJsonReader.nextDouble();
        break;
      case 13: 
        paramJsonReader.beginArray();
        localObject = new ArrayList();
        while (paramJsonReader.hasNext())
        {
          paramJsonReader.beginObject();
          while (paramJsonReader.hasNext()) {
            if (paramJsonReader.selectName(EFFECTS_NAMES) != 0)
            {
              paramJsonReader.skipName();
              paramJsonReader.skipValue();
            }
            else
            {
              ((List)localObject).add(paramJsonReader.nextString());
            }
          }
          paramJsonReader.endObject();
        }
        paramJsonReader.endArray();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Lottie doesn't support layer effects. If you are using them for  fills, strokes, trim paths etc. then try adding them directly as contents  in your shape. Found: ");
        localStringBuilder.append(localObject);
        paramLottieComposition.addWarning(localStringBuilder.toString());
        break;
      case 12: 
        paramJsonReader.beginObject();
        while (paramJsonReader.hasNext())
        {
          i1 = paramJsonReader.selectName(TEXT_NAMES);
          if (i1 != 0)
          {
            if (i1 != 1)
            {
              paramJsonReader.skipName();
              paramJsonReader.skipValue();
            }
            else
            {
              paramJsonReader.beginArray();
              if (paramJsonReader.hasNext()) {
                localAnimatableTextProperties = AnimatableTextPropertiesParser.parse(paramJsonReader, paramLottieComposition);
              }
              while (paramJsonReader.hasNext()) {
                paramJsonReader.skipValue();
              }
              paramJsonReader.endArray();
            }
          }
          else {
            localAnimatableTextFrame = AnimatableValueParser.parseDocumentData(paramJsonReader, paramLottieComposition);
          }
        }
        paramJsonReader.endObject();
        break;
      case 11: 
        paramJsonReader.beginArray();
        while (paramJsonReader.hasNext())
        {
          localObject = ContentModelParser.parse(paramJsonReader, paramLottieComposition);
          if (localObject != null) {
            localArrayList2.add(localObject);
          }
        }
        paramJsonReader.endArray();
        break;
      case 10: 
        paramJsonReader.beginArray();
        while (paramJsonReader.hasNext()) {
          localArrayList1.add(MaskParser.parse(paramJsonReader, paramLottieComposition));
        }
        paramLottieComposition.incrementMatteOrMaskCount(localArrayList1.size());
        paramJsonReader.endArray();
        break;
      case 9: 
        localMatteType = Layer.MatteType.values()[paramJsonReader.nextInt()];
        paramLottieComposition.incrementMatteOrMaskCount(1);
        break;
      case 8: 
        localAnimatableTransform = AnimatableTransformParser.parse(paramJsonReader, paramLottieComposition);
        break;
      case 7: 
        k = Color.parseColor(paramJsonReader.nextString());
        break;
      case 6: 
        m = (int)(paramJsonReader.nextInt() * Utils.dpScale());
        break;
      case 5: 
        n = (int)(paramJsonReader.nextInt() * Utils.dpScale());
        break;
      case 4: 
        l2 = paramJsonReader.nextInt();
        break;
      case 3: 
        i1 = paramJsonReader.nextInt();
        if (i1 < Layer.LayerType.UNKNOWN.ordinal()) {
          localLayerType = Layer.LayerType.values()[i1];
        } else {
          localLayerType = Layer.LayerType.UNKNOWN;
        }
        break;
      case 2: 
        str3 = paramJsonReader.nextString();
        break;
      case 1: 
        l1 = paramJsonReader.nextInt();
        break;
      case 0: 
        str1 = paramJsonReader.nextString();
      }
    }
    paramJsonReader.endObject();
    f4 /= f2;
    f3 /= f2;
    paramJsonReader = new ArrayList();
    if (f4 > 0.0F) {
      paramJsonReader.add(new Keyframe(paramLottieComposition, localFloat2, localFloat2, null, 0.0F, Float.valueOf(f4)));
    }
    if (f3 <= 0.0F) {
      f3 = paramLottieComposition.getEndFrame();
    }
    paramJsonReader.add(new Keyframe(paramLottieComposition, localFloat1, localFloat1, null, f4, Float.valueOf(f3)));
    paramJsonReader.add(new Keyframe(paramLottieComposition, localFloat2, localFloat2, null, f3, Float.valueOf(Float.MAX_VALUE)));
    if ((str1.endsWith(".ai")) || ("ai".equals(str2))) {
      paramLottieComposition.addWarning("Convert your Illustrator layers to shape layers.");
    }
    return new Layer(localArrayList2, paramLottieComposition, str1, l1, localLayerType, l2, str3, localArrayList1, localAnimatableTransform, n, m, k, f2, f1, j, i, localAnimatableTextFrame, localAnimatableTextProperties, paramJsonReader, localMatteType, localAnimatableFloatValue, bool);
  }
}
