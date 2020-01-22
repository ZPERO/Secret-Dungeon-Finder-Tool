package com.airbnb.lottie.animation.content;

import android.graphics.Path;
import android.graphics.Path.Op;
import android.os.Build.VERSION;
import com.airbnb.lottie.model.content.MergePaths;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MergePathsContent
  implements PathContent, GreedyContent
{
  private final Path firstPath = new Path();
  private final MergePaths mergePaths;
  private final String name;
  private final Path path = new Path();
  private final List<PathContent> pathContents = new ArrayList();
  private final Path remainderPath = new Path();
  
  public MergePathsContent(MergePaths paramMergePaths)
  {
    if (Build.VERSION.SDK_INT >= 19)
    {
      name = paramMergePaths.getName();
      mergePaths = paramMergePaths;
      return;
    }
    throw new IllegalStateException("Merge paths are not supported pre-KitKat.");
  }
  
  private void addPaths()
  {
    int i = 0;
    while (i < pathContents.size())
    {
      path.addPath(((PathContent)pathContents.get(i)).getPath());
      i += 1;
    }
  }
  
  private void opFirstPathWithRest(Path.Op paramOp)
  {
    remainderPath.reset();
    firstPath.reset();
    int i = pathContents.size() - 1;
    List localList;
    Path localPath;
    while (i >= 1)
    {
      localObject = (PathContent)pathContents.get(i);
      if ((localObject instanceof ContentGroup))
      {
        localObject = (ContentGroup)localObject;
        localList = ((ContentGroup)localObject).getPathList();
        int j = localList.size() - 1;
        while (j >= 0)
        {
          localPath = ((PathContent)localList.get(j)).getPath();
          localPath.transform(((ContentGroup)localObject).getTransformationMatrix());
          remainderPath.addPath(localPath);
          j -= 1;
        }
      }
      remainderPath.addPath(((PathContent)localObject).getPath());
      i -= 1;
    }
    Object localObject = pathContents;
    i = 0;
    localObject = (PathContent)((List)localObject).get(0);
    if ((localObject instanceof ContentGroup))
    {
      localObject = (ContentGroup)localObject;
      localList = ((ContentGroup)localObject).getPathList();
      while (i < localList.size())
      {
        localPath = ((PathContent)localList.get(i)).getPath();
        localPath.transform(((ContentGroup)localObject).getTransformationMatrix());
        firstPath.addPath(localPath);
        i += 1;
      }
    }
    firstPath.set(((PathContent)localObject).getPath());
    path.op(firstPath, remainderPath, paramOp);
  }
  
  public void absorbContent(ListIterator paramListIterator)
  {
    while ((paramListIterator.hasPrevious()) && (paramListIterator.previous() != this)) {}
    while (paramListIterator.hasPrevious())
    {
      Content localContent = (Content)paramListIterator.previous();
      if ((localContent instanceof PathContent))
      {
        pathContents.add((PathContent)localContent);
        paramListIterator.remove();
      }
    }
  }
  
  public String getName()
  {
    return name;
  }
  
  public Path getPath()
  {
    path.reset();
    if (mergePaths.isHidden()) {
      return path;
    }
    int i = 1.$SwitchMap$com$airbnb$lottie$model$content$MergePaths$MergePathsMode[mergePaths.getMode().ordinal()];
    if (i != 1)
    {
      if (i != 2)
      {
        if (i != 3)
        {
          if (i != 4)
          {
            if (i == 5) {
              opFirstPathWithRest(Path.Op.XOR);
            }
          }
          else {
            opFirstPathWithRest(Path.Op.INTERSECT);
          }
        }
        else {
          opFirstPathWithRest(Path.Op.REVERSE_DIFFERENCE);
        }
      }
      else {
        opFirstPathWithRest(Path.Op.UNION);
      }
    }
    else {
      addPaths();
    }
    return path;
  }
  
  public void setContents(List paramList1, List paramList2)
  {
    int i = 0;
    while (i < pathContents.size())
    {
      ((PathContent)pathContents.get(i)).setContents(paramList1, paramList2);
      i += 1;
    }
  }
}
