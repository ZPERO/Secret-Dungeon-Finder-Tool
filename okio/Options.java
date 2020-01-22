package okio;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

public final class Options
  extends AbstractList<ByteString>
  implements RandomAccess
{
  final ByteString[] byteStrings;
  final int[] trie;
  
  private Options(ByteString[] paramArrayOfByteString, int[] paramArrayOfInt)
  {
    byteStrings = paramArrayOfByteString;
    trie = paramArrayOfInt;
  }
  
  private static void buildTrieRecursive(long paramLong, Buffer paramBuffer, int paramInt1, List paramList1, int paramInt2, int paramInt3, List paramList2)
  {
    int i = paramInt2;
    if (paramInt2 < paramInt3)
    {
      int j = paramInt2;
      while (j < paramInt3) {
        if (((ByteString)paramList1.get(j)).size() >= paramInt1) {
          j += 1;
        } else {
          throw new AssertionError();
        }
      }
      ByteString localByteString1 = (ByteString)paramList1.get(paramInt2);
      ByteString localByteString2 = (ByteString)paramList1.get(paramInt3 - 1);
      j = -1;
      Object localObject = localByteString1;
      if (paramInt1 == localByteString1.size())
      {
        j = ((Integer)paramList2.get(paramInt2)).intValue();
        i = paramInt2 + 1;
        localObject = (ByteString)paramList1.get(i);
      }
      paramInt2 = i;
      if (((ByteString)localObject).getByte(paramInt1) != localByteString2.getByte(paramInt1))
      {
        i += 1;
        for (k = 1; i < paramInt3; k = m)
        {
          m = k;
          if (((ByteString)paramList1.get(i - 1)).getByte(paramInt1) != ((ByteString)paramList1.get(i)).getByte(paramInt1)) {
            m = k + 1;
          }
          i += 1;
        }
        paramLong = paramLong + intCount(paramBuffer) + 2L + k * 2;
        paramBuffer.writeInt(k);
        paramBuffer.writeInt(j);
        i = paramInt2;
        while (i < paramInt3)
        {
          j = ((ByteString)paramList1.get(i)).getByte(paramInt1);
          if ((i == paramInt2) || (j != ((ByteString)paramList1.get(i - 1)).getByte(paramInt1))) {
            paramBuffer.writeInt(j & 0xFF);
          }
          i += 1;
        }
        localObject = new Buffer();
        for (i = paramInt2; i < paramInt3; i = paramInt2)
        {
          k = ((ByteString)paramList1.get(i)).getByte(paramInt1);
          j = i + 1;
          paramInt2 = j;
          while (paramInt2 < paramInt3)
          {
            if (k != ((ByteString)paramList1.get(paramInt2)).getByte(paramInt1)) {
              break label439;
            }
            paramInt2 += 1;
          }
          paramInt2 = paramInt3;
          label439:
          if ((j == paramInt2) && (paramInt1 + 1 == ((ByteString)paramList1.get(i)).size()))
          {
            paramBuffer.writeInt(((Integer)paramList2.get(i)).intValue());
          }
          else
          {
            paramBuffer.writeInt((int)((intCount((Buffer)localObject) + paramLong) * -1L));
            buildTrieRecursive(paramLong, (Buffer)localObject, paramInt1 + 1, paramList1, i, paramInt2, paramList2);
          }
        }
        paramBuffer.write((Buffer)localObject, ((Buffer)localObject).size());
        return;
      }
      int m = Math.min(((ByteString)localObject).size(), localByteString2.size());
      int k = paramInt1;
      i = 0;
      while ((k < m) && (((ByteString)localObject).getByte(k) == localByteString2.getByte(k)))
      {
        i += 1;
        k += 1;
      }
      paramLong = 1L + (paramLong + intCount(paramBuffer) + 2L + i);
      paramBuffer.writeInt(-i);
      paramBuffer.writeInt(j);
      j = paramInt1;
      for (;;)
      {
        k = paramInt1 + i;
        if (j >= k) {
          break;
        }
        paramBuffer.writeInt(((ByteString)localObject).getByte(j) & 0xFF);
        j += 1;
      }
      if (paramInt2 + 1 == paramInt3)
      {
        if (k == ((ByteString)paramList1.get(paramInt2)).size())
        {
          paramBuffer.writeInt(((Integer)paramList2.get(paramInt2)).intValue());
          return;
        }
        throw new AssertionError();
      }
      localObject = new Buffer();
      paramBuffer.writeInt((int)((intCount((Buffer)localObject) + paramLong) * -1L));
      buildTrieRecursive(paramLong, (Buffer)localObject, k, paramList1, paramInt2, paramInt3, paramList2);
      paramBuffer.write((Buffer)localObject, ((Buffer)localObject).size());
      return;
    }
    paramBuffer = new AssertionError();
    throw paramBuffer;
  }
  
  private static int intCount(Buffer paramBuffer)
  {
    return (int)(paramBuffer.size() / 4L);
  }
  
  public static Options read(ByteString... paramVarArgs)
  {
    int i = paramVarArgs.length;
    int m = 0;
    if (i == 0) {
      return new Options(new ByteString[0], new int[] { 0, -1 });
    }
    Object localObject1 = new ArrayList(Arrays.asList(paramVarArgs));
    Collections.sort((List)localObject1);
    ArrayList localArrayList = new ArrayList();
    i = 0;
    while (i < ((List)localObject1).size())
    {
      localArrayList.add(Integer.valueOf(-1));
      i += 1;
    }
    i = 0;
    while (i < ((List)localObject1).size())
    {
      localArrayList.set(Collections.binarySearch((List)localObject1, paramVarArgs[i]), Integer.valueOf(i));
      i += 1;
    }
    if (((ByteString)((List)localObject1).get(0)).size() != 0)
    {
      int j;
      for (i = 0; i < ((List)localObject1).size(); i = j)
      {
        ByteString localByteString = (ByteString)((List)localObject1).get(i);
        j = i + 1;
        int k = j;
        while (k < ((List)localObject1).size())
        {
          localObject2 = (ByteString)((List)localObject1).get(k);
          if (((ByteString)localObject2).startsWith(localByteString)) {
            if (((ByteString)localObject2).size() != localByteString.size())
            {
              if (((Integer)localArrayList.get(k)).intValue() > ((Integer)localArrayList.get(i)).intValue())
              {
                ((List)localObject1).remove(k);
                localArrayList.remove(k);
              }
              else
              {
                k += 1;
              }
            }
            else
            {
              paramVarArgs = new StringBuilder();
              paramVarArgs.append("duplicate option: ");
              paramVarArgs.append(localObject2);
              throw new IllegalArgumentException(paramVarArgs.toString());
            }
          }
        }
      }
      Object localObject2 = new Buffer();
      buildTrieRecursive(0L, (Buffer)localObject2, 0, (List)localObject1, 0, ((List)localObject1).size(), localArrayList);
      localObject1 = new int[intCount((Buffer)localObject2)];
      i = m;
      while (i < localObject1.length)
      {
        localObject1[i] = ((Buffer)localObject2).readInt();
        i += 1;
      }
      if (((Buffer)localObject2).exhausted()) {
        return new Options((ByteString[])paramVarArgs.clone(), (int[])localObject1);
      }
      throw new AssertionError();
    }
    paramVarArgs = new IllegalArgumentException("the empty byte string is not a supported option");
    throw paramVarArgs;
  }
  
  public ByteString get(int paramInt)
  {
    return byteStrings[paramInt];
  }
  
  public final int size()
  {
    return byteStrings.length;
  }
}
