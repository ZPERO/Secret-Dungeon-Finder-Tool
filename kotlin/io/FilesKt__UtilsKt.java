package kotlin.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.sequences.Sequence;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlin.text.StringsKt__StringsKt;

@Metadata(bv={1, 0, 3}, d1={"\000<\n\000\n\002\020\016\n\002\030\002\n\002\b\f\n\002\020\013\n\002\b\003\n\002\030\002\n\002\030\002\n\002\030\002\n\002\b\002\n\002\020\b\n\002\b\004\n\002\020 \n\000\n\002\030\002\n\002\b\f\032(\020\t\032\0020\0022\b\b\002\020\n\032\0020\0012\n\b\002\020\013\032\004\030\0010\0012\n\b\002\020\f\032\004\030\0010\002\032(\020\r\032\0020\0022\b\b\002\020\n\032\0020\0012\n\b\002\020\013\032\004\030\0010\0012\n\b\002\020\f\032\004\030\0010\002\0328\020\016\032\0020\017*\0020\0022\006\020\020\032\0020\0022\b\b\002\020\021\032\0020\0172\032\b\002\020\022\032\024\022\004\022\0020\002\022\004\022\0020\024\022\004\022\0020\0250\023\032&\020\026\032\0020\002*\0020\0022\006\020\020\032\0020\0022\b\b\002\020\021\032\0020\0172\b\b\002\020\027\032\0020\030\032\n\020\031\032\0020\017*\0020\002\032\022\020\032\032\0020\017*\0020\0022\006\020\033\032\0020\002\032\022\020\032\032\0020\017*\0020\0022\006\020\033\032\0020\001\032\n\020\034\032\0020\002*\0020\002\032\035\020\034\032\b\022\004\022\0020\0020\035*\b\022\004\022\0020\0020\035H\002?\006\002\b\036\032\021\020\034\032\0020\037*\0020\037H\002?\006\002\b\036\032\022\020 \032\0020\002*\0020\0022\006\020!\032\0020\002\032\024\020\"\032\004\030\0010\002*\0020\0022\006\020!\032\0020\002\032\022\020#\032\0020\002*\0020\0022\006\020!\032\0020\002\032\022\020$\032\0020\002*\0020\0022\006\020%\032\0020\002\032\022\020$\032\0020\002*\0020\0022\006\020%\032\0020\001\032\022\020&\032\0020\002*\0020\0022\006\020%\032\0020\002\032\022\020&\032\0020\002*\0020\0022\006\020%\032\0020\001\032\022\020'\032\0020\017*\0020\0022\006\020\033\032\0020\002\032\022\020'\032\0020\017*\0020\0022\006\020\033\032\0020\001\032\022\020(\032\0020\001*\0020\0022\006\020!\032\0020\002\032\033\020)\032\004\030\0010\001*\0020\0022\006\020!\032\0020\002H\002?\006\002\b*\"\025\020\000\032\0020\001*\0020\0028F?\006\006\032\004\b\003\020\004\"\025\020\005\032\0020\001*\0020\0028F?\006\006\032\004\b\006\020\004\"\025\020\007\032\0020\001*\0020\0028F?\006\006\032\004\b\b\020\004?\006+"}, d2={"extension", "", "Ljava/io/File;", "getExtension", "(Ljava/io/File;)Ljava/lang/String;", "invariantSeparatorsPath", "getInvariantSeparatorsPath", "nameWithoutExtension", "getNameWithoutExtension", "createTempDir", "prefix", "suffix", "directory", "createTempFile", "copyRecursively", "", "target", "overwrite", "onError", "Lkotlin/Function2;", "Ljava/io/IOException;", "Lkotlin/io/OnErrorAction;", "copyTo", "bufferSize", "", "deleteRecursively", "endsWith", "other", "normalize", "", "normalize$FilesKt__UtilsKt", "Lkotlin/io/FilePathComponents;", "relativeTo", "base", "relativeToOrNull", "relativeToOrSelf", "resolve", "relative", "resolveSibling", "startsWith", "toRelativeString", "toRelativeStringOrNull", "toRelativeStringOrNull$FilesKt__UtilsKt", "kotlin-stdlib"}, k=5, mv={1, 1, 15}, xi=1, xs="kotlin/io/FilesKt")
class FilesKt__UtilsKt
  extends FilesKt__FileTreeWalkKt
{
  public FilesKt__UtilsKt() {}
  
  public static final boolean copyRecursively(File paramFile1, File paramFile2, boolean paramBoolean, Function2 paramFunction2)
  {
    Intrinsics.checkParameterIsNotNull(paramFile1, "$this$copyRecursively");
    Intrinsics.checkParameterIsNotNull(paramFile2, "target");
    Intrinsics.checkParameterIsNotNull(paramFunction2, "onError");
    if (!paramFile1.exists()) {
      return (OnErrorAction)paramFunction2.invoke(paramFile1, new NoSuchFileException(paramFile1, null, "The source file doesn't exist.", 2, null)) != OnErrorAction.TERMINATE;
    }
    try
    {
      Object localObject1 = FilesKt__FileTreeWalkKt.walkTopDown(paramFile1);
      Object localObject2 = new Lambda(paramFunction2)
      {
        public final void invoke(File paramAnonymousFile, IOException paramAnonymousIOException)
        {
          Intrinsics.checkParameterIsNotNull(paramAnonymousFile, "f");
          Intrinsics.checkParameterIsNotNull(paramAnonymousIOException, "e");
          if ((OnErrorAction)FilesKt__UtilsKt.this.invoke(paramAnonymousFile, paramAnonymousIOException) != OnErrorAction.TERMINATE) {
            return;
          }
          throw ((Throwable)new TerminateException(paramAnonymousFile));
        }
      };
      localObject2 = (Function2)localObject2;
      localObject1 = ((FileTreeWalk)localObject1).onFail((Function2)localObject2).iterator();
      label280:
      label283:
      do
      {
        long l1;
        long l2;
        do
        {
          Object localObject3;
          for (;;)
          {
            boolean bool = ((Iterator)localObject1).hasNext();
            if (!bool) {
              break label408;
            }
            localObject2 = ((Iterator)localObject1).next();
            localObject2 = (File)localObject2;
            bool = ((File)localObject2).exists();
            if (!bool)
            {
              localObject2 = paramFunction2.invoke(localObject2, new NoSuchFileException((File)localObject2, null, "The source file doesn't exist.", 2, null));
              if ((OnErrorAction)localObject2 == OnErrorAction.TERMINATE) {
                return false;
              }
            }
            else
            {
              localObject3 = toRelativeString((File)localObject2, paramFile1);
              localObject3 = new File(paramFile2, (String)localObject3);
              bool = ((File)localObject3).exists();
              if (bool)
              {
                bool = ((File)localObject2).isDirectory();
                if (bool)
                {
                  bool = ((File)localObject3).isDirectory();
                  if (bool) {}
                }
                else
                {
                  if (!paramBoolean) {}
                  do
                  {
                    for (;;)
                    {
                      i = 1;
                      break label283;
                      bool = ((File)localObject3).isDirectory();
                      if (!bool) {
                        break;
                      }
                      bool = deleteRecursively((File)localObject3);
                      if (bool) {
                        break label280;
                      }
                    }
                    bool = ((File)localObject3).delete();
                  } while (!bool);
                  int i = 0;
                  if (i != 0)
                  {
                    localObject2 = paramFunction2.invoke(localObject3, new FileAlreadyExistsException((File)localObject2, (File)localObject3, "The destination file already exists."));
                    if ((OnErrorAction)localObject2 != OnErrorAction.TERMINATE) {
                      continue;
                    }
                    return false;
                  }
                }
              }
              bool = ((File)localObject2).isDirectory();
              if (!bool) {
                break;
              }
              ((File)localObject3).mkdirs();
            }
          }
          l1 = copyTo$default((File)localObject2, (File)localObject3, paramBoolean, 0, 4, null).length();
          l2 = ((File)localObject2).length();
        } while (l1 == l2);
        localObject2 = paramFunction2.invoke(localObject2, new IOException("Source file wasn't copied completely, length of destination file differs."));
      } while ((OnErrorAction)localObject2 != OnErrorAction.TERMINATE);
      return false;
      label408:
      return true;
    }
    catch (TerminateException paramFile1) {}
    return false;
  }
  
  public static final File copyTo(File paramFile1, File paramFile2, boolean paramBoolean, int paramInt)
  {
    Intrinsics.checkParameterIsNotNull(paramFile1, "$this$copyTo");
    Intrinsics.checkParameterIsNotNull(paramFile2, "target");
    if (paramFile1.exists())
    {
      if (paramFile2.exists()) {
        if (paramBoolean)
        {
          if (!paramFile2.delete()) {
            throw ((Throwable)new FileAlreadyExistsException(paramFile1, paramFile2, "Tried to overwrite the destination, but failed to delete it."));
          }
        }
        else {
          throw ((Throwable)new FileAlreadyExistsException(paramFile1, paramFile2, "The destination file already exists."));
        }
      }
      if (paramFile1.isDirectory())
      {
        if (paramFile2.mkdirs()) {
          return paramFile2;
        }
        throw ((Throwable)new FileSystemException(paramFile1, paramFile2, "Failed to create target directory."));
      }
      Object localObject = paramFile2.getParentFile();
      if (localObject != null) {
        ((File)localObject).mkdirs();
      }
      paramFile1 = (Closeable)new FileInputStream(paramFile1);
      try
      {
        FileInputStream localFileInputStream = (FileInputStream)paramFile1;
        localObject = (Closeable)new FileOutputStream(paramFile2);
        try
        {
          FileOutputStream localFileOutputStream = (FileOutputStream)localObject;
          ByteStreamsKt.copyTo((InputStream)localFileInputStream, (OutputStream)localFileOutputStream, paramInt);
          CloseableKt.closeFinally((Closeable)localObject, null);
          CloseableKt.closeFinally(paramFile1, null);
          return paramFile2;
        }
        catch (Throwable paramFile2)
        {
          try
          {
            throw paramFile2;
          }
          catch (Throwable localThrowable2)
          {
            CloseableKt.closeFinally((Closeable)localObject, paramFile2);
            throw localThrowable2;
          }
        }
        throw ((Throwable)new NoSuchFileException(paramFile1, null, "The source file doesn't exist.", 2, null));
      }
      catch (Throwable paramFile2)
      {
        try
        {
          throw paramFile2;
        }
        catch (Throwable localThrowable1)
        {
          CloseableKt.closeFinally(paramFile1, paramFile2);
          throw localThrowable1;
        }
      }
    }
  }
  
  public static final File createTempDir(String paramString1, String paramString2, File paramFile)
  {
    Intrinsics.checkParameterIsNotNull(paramString1, "prefix");
    paramString1 = File.createTempFile(paramString1, paramString2, paramFile);
    paramString1.delete();
    if (paramString1.mkdir())
    {
      Intrinsics.checkExpressionValueIsNotNull(paramString1, "dir");
      return paramString1;
    }
    paramString2 = new StringBuilder();
    paramString2.append("Unable to create temporary directory ");
    paramString2.append(paramString1);
    paramString2.append('.');
    throw ((Throwable)new IOException(paramString2.toString()));
  }
  
  public static final File createTempFile(String paramString1, String paramString2, File paramFile)
  {
    Intrinsics.checkParameterIsNotNull(paramString1, "prefix");
    paramString1 = File.createTempFile(paramString1, paramString2, paramFile);
    Intrinsics.checkExpressionValueIsNotNull(paramString1, "File.createTempFile(prefix, suffix, directory)");
    return paramString1;
  }
  
  public static final boolean deleteRecursively(File paramFile)
  {
    Intrinsics.checkParameterIsNotNull(paramFile, "$this$deleteRecursively");
    paramFile = ((Sequence)FilesKt__FileTreeWalkKt.walkBottomUp(paramFile)).iterator();
    for (boolean bool = true;; bool = false)
    {
      if (!paramFile.hasNext()) {
        return bool;
      }
      File localFile = (File)paramFile.next();
      if (((localFile.delete()) || (!localFile.exists())) && (bool)) {
        break;
      }
    }
    return bool;
  }
  
  public static final boolean endsWith(File paramFile1, File paramFile2)
  {
    Intrinsics.checkParameterIsNotNull(paramFile1, "$this$endsWith");
    Intrinsics.checkParameterIsNotNull(paramFile2, "other");
    FilePathComponents localFilePathComponents1 = FilesKt__FilePathComponentsKt.toComponents(paramFile1);
    FilePathComponents localFilePathComponents2 = FilesKt__FilePathComponentsKt.toComponents(paramFile2);
    if (localFilePathComponents2.isRooted()) {
      return Intrinsics.areEqual(paramFile1, paramFile2);
    }
    int i = localFilePathComponents1.getSize() - localFilePathComponents2.getSize();
    if (i < 0) {
      return false;
    }
    return localFilePathComponents1.getSegments().subList(i, localFilePathComponents1.getSize()).equals(localFilePathComponents2.getSegments());
  }
  
  public static final boolean endsWith(File paramFile, String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramFile, "$this$endsWith");
    Intrinsics.checkParameterIsNotNull(paramString, "other");
    return endsWith(paramFile, new File(paramString));
  }
  
  public static final String getExtension(File paramFile)
  {
    Intrinsics.checkParameterIsNotNull(paramFile, "$this$extension");
    paramFile = paramFile.getName();
    Intrinsics.checkExpressionValueIsNotNull(paramFile, "name");
    return StringsKt__StringsKt.substringAfterLast(paramFile, '.', "");
  }
  
  public static final String getInvariantSeparatorsPath(File paramFile)
  {
    Intrinsics.checkParameterIsNotNull(paramFile, "$this$invariantSeparatorsPath");
    if (File.separatorChar != '/')
    {
      paramFile = paramFile.getPath();
      Intrinsics.checkExpressionValueIsNotNull(paramFile, "path");
      return StringsKt__StringsJVMKt.replace$default(paramFile, File.separatorChar, '/', false, 4, null);
    }
    paramFile = paramFile.getPath();
    Intrinsics.checkExpressionValueIsNotNull(paramFile, "path");
    return paramFile;
  }
  
  public static final String getNameWithoutExtension(File paramFile)
  {
    Intrinsics.checkParameterIsNotNull(paramFile, "$this$nameWithoutExtension");
    paramFile = paramFile.getName();
    Intrinsics.checkExpressionValueIsNotNull(paramFile, "name");
    return StringsKt__StringsKt.substringBeforeLast$default(paramFile, ".", null, 2, null);
  }
  
  public static final File normalize(File paramFile)
  {
    Intrinsics.checkParameterIsNotNull(paramFile, "$this$normalize");
    Object localObject = FilesKt__FilePathComponentsKt.toComponents(paramFile);
    paramFile = ((FilePathComponents)localObject).getRoot();
    localObject = (Iterable)normalize$FilesKt__UtilsKt(((FilePathComponents)localObject).getSegments());
    String str = File.separator;
    Intrinsics.checkExpressionValueIsNotNull(str, "File.separator");
    return resolve(paramFile, CollectionsKt___CollectionsKt.joinToString$default((Iterable)localObject, (CharSequence)str, null, null, 0, null, null, 62, null));
  }
  
  private static final List normalize$FilesKt__UtilsKt(List paramList)
  {
    List localList = (List)new ArrayList(paramList.size());
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      File localFile = (File)paramList.next();
      String str = localFile.getName();
      if (str != null)
      {
        int i = str.hashCode();
        if (i != 46)
        {
          if ((i == 1472) && (str.equals("..")))
          {
            if ((!localList.isEmpty()) && ((Intrinsics.areEqual(((File)CollectionsKt___CollectionsKt.last(localList)).getName(), "..") ^ true)))
            {
              localList.remove(localList.size() - 1);
              continue;
            }
            localList.add(localFile);
          }
        }
        else {
          if (str.equals(".")) {
            continue;
          }
        }
      }
      localList.add(localFile);
    }
    return localList;
  }
  
  private static final FilePathComponents normalize$FilesKt__UtilsKt(FilePathComponents paramFilePathComponents)
  {
    return new FilePathComponents(paramFilePathComponents.getRoot(), normalize$FilesKt__UtilsKt(paramFilePathComponents.getSegments()));
  }
  
  public static final File relativeTo(File paramFile1, File paramFile2)
  {
    Intrinsics.checkParameterIsNotNull(paramFile1, "$this$relativeTo");
    Intrinsics.checkParameterIsNotNull(paramFile2, "base");
    return new File(toRelativeString(paramFile1, paramFile2));
  }
  
  public static final File relativeToOrNull(File paramFile1, File paramFile2)
  {
    Intrinsics.checkParameterIsNotNull(paramFile1, "$this$relativeToOrNull");
    Intrinsics.checkParameterIsNotNull(paramFile2, "base");
    paramFile1 = toRelativeStringOrNull$FilesKt__UtilsKt(paramFile1, paramFile2);
    if (paramFile1 != null) {
      return new File(paramFile1);
    }
    return null;
  }
  
  public static final File relativeToOrSelf(File paramFile1, File paramFile2)
  {
    Intrinsics.checkParameterIsNotNull(paramFile1, "$this$relativeToOrSelf");
    Intrinsics.checkParameterIsNotNull(paramFile2, "base");
    paramFile2 = toRelativeStringOrNull$FilesKt__UtilsKt(paramFile1, paramFile2);
    if (paramFile2 != null) {
      paramFile1 = new File(paramFile2);
    }
    return paramFile1;
  }
  
  public static final File resolve(File paramFile1, File paramFile2)
  {
    Intrinsics.checkParameterIsNotNull(paramFile1, "$this$resolve");
    Intrinsics.checkParameterIsNotNull(paramFile2, "relative");
    if (FilesKt__FilePathComponentsKt.isRooted(paramFile2)) {
      return paramFile2;
    }
    paramFile1 = paramFile1.toString();
    Intrinsics.checkExpressionValueIsNotNull(paramFile1, "this.toString()");
    Object localObject = (CharSequence)paramFile1;
    int i;
    if (((CharSequence)localObject).length() == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if ((i == 0) && (!StringsKt__StringsKt.endsWith$default((CharSequence)localObject, File.separatorChar, false, 2, null)))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(paramFile1);
      ((StringBuilder)localObject).append(File.separatorChar);
      ((StringBuilder)localObject).append(paramFile2);
      return new File(((StringBuilder)localObject).toString());
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append(paramFile1);
    ((StringBuilder)localObject).append(paramFile2);
    return new File(((StringBuilder)localObject).toString());
  }
  
  public static final File resolve(File paramFile, String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramFile, "$this$resolve");
    Intrinsics.checkParameterIsNotNull(paramString, "relative");
    return resolve(paramFile, new File(paramString));
  }
  
  public static final File resolveSibling(File paramFile1, File paramFile2)
  {
    Intrinsics.checkParameterIsNotNull(paramFile1, "$this$resolveSibling");
    Intrinsics.checkParameterIsNotNull(paramFile2, "relative");
    FilePathComponents localFilePathComponents = FilesKt__FilePathComponentsKt.toComponents(paramFile1);
    if (localFilePathComponents.getSize() == 0) {
      paramFile1 = new File("..");
    } else {
      paramFile1 = localFilePathComponents.subPath(0, localFilePathComponents.getSize() - 1);
    }
    return resolve(resolve(localFilePathComponents.getRoot(), paramFile1), paramFile2);
  }
  
  public static final File resolveSibling(File paramFile, String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramFile, "$this$resolveSibling");
    Intrinsics.checkParameterIsNotNull(paramString, "relative");
    return resolveSibling(paramFile, new File(paramString));
  }
  
  public static final boolean startsWith(File paramFile1, File paramFile2)
  {
    Intrinsics.checkParameterIsNotNull(paramFile1, "$this$startsWith");
    Intrinsics.checkParameterIsNotNull(paramFile2, "other");
    paramFile1 = FilesKt__FilePathComponentsKt.toComponents(paramFile1);
    paramFile2 = FilesKt__FilePathComponentsKt.toComponents(paramFile2);
    if ((Intrinsics.areEqual(paramFile1.getRoot(), paramFile2.getRoot()) ^ true)) {
      return false;
    }
    if (paramFile1.getSize() < paramFile2.getSize()) {
      return false;
    }
    return paramFile1.getSegments().subList(0, paramFile2.getSize()).equals(paramFile2.getSegments());
  }
  
  public static final boolean startsWith(File paramFile, String paramString)
  {
    Intrinsics.checkParameterIsNotNull(paramFile, "$this$startsWith");
    Intrinsics.checkParameterIsNotNull(paramString, "other");
    return startsWith(paramFile, new File(paramString));
  }
  
  public static final String toRelativeString(File paramFile1, File paramFile2)
  {
    Intrinsics.checkParameterIsNotNull(paramFile1, "$this$toRelativeString");
    Intrinsics.checkParameterIsNotNull(paramFile2, "base");
    Object localObject = toRelativeStringOrNull$FilesKt__UtilsKt(paramFile1, paramFile2);
    if (localObject != null) {
      return localObject;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("this and base files have different roots: ");
    ((StringBuilder)localObject).append(paramFile1);
    ((StringBuilder)localObject).append(" and ");
    ((StringBuilder)localObject).append(paramFile2);
    ((StringBuilder)localObject).append('.');
    throw ((Throwable)new IllegalArgumentException(((StringBuilder)localObject).toString()));
  }
  
  private static final String toRelativeStringOrNull$FilesKt__UtilsKt(File paramFile1, File paramFile2)
  {
    Object localObject = normalize$FilesKt__UtilsKt(FilesKt__FilePathComponentsKt.toComponents(paramFile1));
    paramFile2 = normalize$FilesKt__UtilsKt(FilesKt__FilePathComponentsKt.toComponents(paramFile2));
    if ((Intrinsics.areEqual(((FilePathComponents)localObject).getRoot(), paramFile2.getRoot()) ^ true)) {
      return null;
    }
    int k = paramFile2.getSize();
    int m = ((FilePathComponents)localObject).getSize();
    int i = 0;
    int j = Math.min(m, k);
    while ((i < j) && (Intrinsics.areEqual((File)((FilePathComponents)localObject).getSegments().get(i), (File)paramFile2.getSegments().get(i)))) {
      i += 1;
    }
    paramFile1 = new StringBuilder();
    j = k - 1;
    if (j >= i) {
      for (;;)
      {
        if (Intrinsics.areEqual(((File)paramFile2.getSegments().get(j)).getName(), "..")) {
          return null;
        }
        paramFile1.append("..");
        if (j != i) {
          paramFile1.append(File.separatorChar);
        }
        if (j == i) {
          break;
        }
        j -= 1;
      }
    }
    if (i < m)
    {
      if (i < k) {
        paramFile1.append(File.separatorChar);
      }
      paramFile2 = (Iterable)CollectionsKt___CollectionsKt.drop((Iterable)((FilePathComponents)localObject).getSegments(), i);
      localObject = (Appendable)paramFile1;
      String str = File.separator;
      Intrinsics.checkExpressionValueIsNotNull(str, "File.separator");
      CollectionsKt___CollectionsKt.joinTo$default(paramFile2, (Appendable)localObject, (CharSequence)str, null, null, 0, null, null, 124, null);
    }
    return paramFile1.toString();
  }
}
