package com.airbnb.lottie.parser;

import android.graphics.Rect;
import androidx.collection.LongSparseArray;
import androidx.collection.SparseArrayCompat;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieImageAsset;
import com.airbnb.lottie.model.Font;
import com.airbnb.lottie.model.FontCharacter;
import com.airbnb.lottie.model.Marker;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.model.layer.Layer.LayerType;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.parser.moshi.JsonReader.Options;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.utils.Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LottieCompositionMoshiParser
{
  static JsonReader.Options ASSETS_NAMES = JsonReader.Options.init(new String[] { "id", "layers", "w", "h", "p", "u" });
  private static final JsonReader.Options FONT_NAMES = JsonReader.Options.init(new String[] { "list" });
  private static final JsonReader.Options MARKER_NAMES = JsonReader.Options.init(new String[] { "cm", "tm", "dr" });
  private static final JsonReader.Options NAMES = JsonReader.Options.init(new String[] { "w", "h", "ip", "op", "fr", "v", "layers", "assets", "fonts", "chars", "markers" });
  
  public LottieCompositionMoshiParser() {}
  
  public static LottieComposition parse(JsonReader paramJsonReader)
    throws IOException
  {
    float f4 = Utils.dpScale();
    LongSparseArray localLongSparseArray = new LongSparseArray();
    ArrayList localArrayList1 = new ArrayList();
    HashMap localHashMap1 = new HashMap();
    HashMap localHashMap2 = new HashMap();
    HashMap localHashMap3 = new HashMap();
    ArrayList localArrayList2 = new ArrayList();
    SparseArrayCompat localSparseArrayCompat = new SparseArrayCompat();
    LottieComposition localLottieComposition = new LottieComposition();
    paramJsonReader.beginObject();
    int j = 0;
    int i = 0;
    float f3 = 0.0F;
    float f2 = 0.0F;
    float f1 = 0.0F;
    for (;;)
    {
      Object localObject = paramJsonReader;
      if (!paramJsonReader.hasNext()) {
        break;
      }
      switch (((JsonReader)localObject).selectName(NAMES))
      {
      default: 
        paramJsonReader.skipName();
        paramJsonReader.skipValue();
        break;
      case 10: 
        parseMarkers((JsonReader)localObject, localLottieComposition, localArrayList2);
        break;
      case 9: 
        parseChars((JsonReader)localObject, localLottieComposition, localSparseArrayCompat);
        break;
      case 8: 
        parseFonts((JsonReader)localObject, localHashMap3);
        break;
      case 7: 
        parseAssets((JsonReader)localObject, localLottieComposition, localHashMap1, localHashMap2);
        break;
      case 6: 
        parseLayers((JsonReader)localObject, localLottieComposition, localArrayList1, localLongSparseArray);
        break;
      case 5: 
        localObject = paramJsonReader.nextString().split("\\.");
        if (!Utils.isAtLeastVersion(Integer.parseInt(localObject[0]), Integer.parseInt(localObject[1]), Integer.parseInt(localObject[2]), 4, 4, 0)) {
          localLottieComposition.addWarning("Lottie only supports bodymovin >= 4.4.0");
        }
        break;
      case 4: 
        f1 = (float)paramJsonReader.nextDouble();
        break;
      case 3: 
        f2 = (float)paramJsonReader.nextDouble() - 0.01F;
        break;
      case 2: 
        f3 = (float)paramJsonReader.nextDouble();
        break;
      case 1: 
        i = paramJsonReader.nextInt();
        break;
      }
      j = paramJsonReader.nextInt();
    }
    localLottieComposition.init(new Rect(0, 0, (int)(j * f4), (int)(i * f4)), f3, f2, f1, localArrayList1, localLongSparseArray, localHashMap1, localHashMap2, localSparseArrayCompat, localHashMap3, localArrayList2);
    return localLottieComposition;
  }
  
  private static void parseAssets(JsonReader paramJsonReader, LottieComposition paramLottieComposition, Map paramMap1, Map paramMap2)
    throws IOException
  {
    paramJsonReader.beginArray();
    while (paramJsonReader.hasNext())
    {
      ArrayList localArrayList = new ArrayList();
      LongSparseArray localLongSparseArray = new LongSparseArray();
      paramJsonReader.beginObject();
      String str2 = null;
      String str1 = null;
      Object localObject = null;
      int j = 0;
      int i = 0;
      while (paramJsonReader.hasNext())
      {
        int k = paramJsonReader.selectName(ASSETS_NAMES);
        if (k != 0)
        {
          if (k != 1)
          {
            if (k != 2)
            {
              if (k != 3)
              {
                if (k != 4)
                {
                  if (k != 5)
                  {
                    paramJsonReader.skipName();
                    paramJsonReader.skipValue();
                  }
                  else
                  {
                    localObject = paramJsonReader.nextString();
                  }
                }
                else {
                  str1 = paramJsonReader.nextString();
                }
              }
              else {
                i = paramJsonReader.nextInt();
              }
            }
            else {
              j = paramJsonReader.nextInt();
            }
          }
          else
          {
            paramJsonReader.beginArray();
            while (paramJsonReader.hasNext())
            {
              Layer localLayer = LayerParser.parse(paramJsonReader, paramLottieComposition);
              localLongSparseArray.put(localLayer.getId(), localLayer);
              localArrayList.add(localLayer);
            }
            paramJsonReader.endArray();
          }
        }
        else {
          str2 = paramJsonReader.nextString();
        }
      }
      paramJsonReader.endObject();
      if (str1 != null)
      {
        localObject = new LottieImageAsset(j, i, str2, str1, (String)localObject);
        paramMap2.put(((LottieImageAsset)localObject).getId(), localObject);
      }
      else
      {
        paramMap1.put(str2, localArrayList);
      }
    }
    paramJsonReader.endArray();
  }
  
  private static void parseChars(JsonReader paramJsonReader, LottieComposition paramLottieComposition, SparseArrayCompat paramSparseArrayCompat)
    throws IOException
  {
    paramJsonReader.beginArray();
    while (paramJsonReader.hasNext())
    {
      FontCharacter localFontCharacter = FontCharacterParser.parse(paramJsonReader, paramLottieComposition);
      paramSparseArrayCompat.put(localFontCharacter.hashCode(), localFontCharacter);
    }
    paramJsonReader.endArray();
  }
  
  private static void parseFonts(JsonReader paramJsonReader, Map paramMap)
    throws IOException
  {
    paramJsonReader.beginObject();
    while (paramJsonReader.hasNext()) {
      if (paramJsonReader.selectName(FONT_NAMES) != 0)
      {
        paramJsonReader.skipName();
        paramJsonReader.skipValue();
      }
      else
      {
        paramJsonReader.beginArray();
        while (paramJsonReader.hasNext())
        {
          Font localFont = FontParser.parse(paramJsonReader);
          paramMap.put(localFont.getName(), localFont);
        }
        paramJsonReader.endArray();
      }
    }
    paramJsonReader.endObject();
  }
  
  private static void parseLayers(JsonReader paramJsonReader, LottieComposition paramLottieComposition, List paramList, LongSparseArray paramLongSparseArray)
    throws IOException
  {
    paramJsonReader.beginArray();
    int i = 0;
    while (paramJsonReader.hasNext())
    {
      Object localObject = LayerParser.parse(paramJsonReader, paramLottieComposition);
      int j = i;
      if (((Layer)localObject).getLayerType() == Layer.LayerType.IMAGE) {
        j = i + 1;
      }
      paramList.add(localObject);
      paramLongSparseArray.put(((Layer)localObject).getId(), localObject);
      i = j;
      if (j > 4)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("You have ");
        ((StringBuilder)localObject).append(j);
        ((StringBuilder)localObject).append(" images. Lottie should primarily be used with shapes. If you are using Adobe Illustrator, convert the Illustrator layers to shape layers.");
        Logger.warning(((StringBuilder)localObject).toString());
        i = j;
      }
    }
    paramJsonReader.endArray();
  }
  
  private static void parseMarkers(JsonReader paramJsonReader, LottieComposition paramLottieComposition, List paramList)
    throws IOException
  {
    paramJsonReader.beginArray();
    while (paramJsonReader.hasNext())
    {
      paramLottieComposition = null;
      paramJsonReader.beginObject();
      float f2 = 0.0F;
      float f1 = 0.0F;
      while (paramJsonReader.hasNext())
      {
        int i = paramJsonReader.selectName(MARKER_NAMES);
        if (i != 0)
        {
          if (i != 1)
          {
            if (i != 2)
            {
              paramJsonReader.skipName();
              paramJsonReader.skipValue();
            }
            else
            {
              f1 = (float)paramJsonReader.nextDouble();
            }
          }
          else {
            f2 = (float)paramJsonReader.nextDouble();
          }
        }
        else {
          paramLottieComposition = paramJsonReader.nextString();
        }
      }
      paramJsonReader.endObject();
      paramList.add(new Marker(paramLottieComposition, f2, f1));
    }
    paramJsonReader.endArray();
  }
}
