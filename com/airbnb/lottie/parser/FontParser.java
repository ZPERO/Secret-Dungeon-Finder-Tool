package com.airbnb.lottie.parser;

import com.airbnb.lottie.model.Font;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.parser.moshi.JsonReader.Options;
import java.io.IOException;

class FontParser
{
  private static final JsonReader.Options NAMES = JsonReader.Options.init(new String[] { "fFamily", "fName", "fStyle", "ascent" });
  
  private FontParser() {}
  
  static Font parse(JsonReader paramJsonReader)
    throws IOException
  {
    paramJsonReader.beginObject();
    String str3 = null;
    String str2 = null;
    String str1 = null;
    float f = 0.0F;
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
              paramJsonReader.skipName();
              paramJsonReader.skipValue();
            }
            else
            {
              f = (float)paramJsonReader.nextDouble();
            }
          }
          else {
            str1 = paramJsonReader.nextString();
          }
        }
        else {
          str2 = paramJsonReader.nextString();
        }
      }
      else {
        str3 = paramJsonReader.nextString();
      }
    }
    paramJsonReader.endObject();
    return new Font(str3, str2, str1, f);
  }
}
