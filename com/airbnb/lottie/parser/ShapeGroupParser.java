package com.airbnb.lottie.parser;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.content.ContentModel;
import com.airbnb.lottie.model.content.ShapeGroup;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.parser.moshi.JsonReader.Options;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ShapeGroupParser
{
  private static JsonReader.Options NAMES = JsonReader.Options.init(new String[] { "nm", "hd", "it" });
  
  private ShapeGroupParser() {}
  
  static ShapeGroup parse(JsonReader paramJsonReader, LottieComposition paramLottieComposition)
    throws IOException
  {
    ArrayList localArrayList = new ArrayList();
    String str = null;
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
            paramJsonReader.skipValue();
          }
          else
          {
            paramJsonReader.beginArray();
            while (paramJsonReader.hasNext())
            {
              ContentModel localContentModel = ContentModelParser.parse(paramJsonReader, paramLottieComposition);
              if (localContentModel != null) {
                localArrayList.add(localContentModel);
              }
            }
            paramJsonReader.endArray();
          }
        }
        else {
          bool = paramJsonReader.nextBoolean();
        }
      }
      else {
        str = paramJsonReader.nextString();
      }
    }
    return new ShapeGroup(str, localArrayList, bool);
  }
}
