package com.airbnb.lottie.parser;

import com.airbnb.lottie.model.content.MergePaths;
import com.airbnb.lottie.model.content.MergePaths.MergePathsMode;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.parser.moshi.JsonReader.Options;
import java.io.IOException;

class MergePathsParser
{
  private static final JsonReader.Options NAMES = JsonReader.Options.init(new String[] { "nm", "mm", "hd" });
  
  private MergePathsParser() {}
  
  static MergePaths parse(JsonReader paramJsonReader)
    throws IOException
  {
    String str = null;
    MergePaths.MergePathsMode localMergePathsMode = null;
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
            paramJsonReader.skipName();
            paramJsonReader.skipValue();
          }
          else
          {
            bool = paramJsonReader.nextBoolean();
          }
        }
        else {
          localMergePathsMode = MergePaths.MergePathsMode.forId(paramJsonReader.nextInt());
        }
      }
      else {
        str = paramJsonReader.nextString();
      }
    }
    return new MergePaths(str, localMergePathsMode, bool);
  }
}
