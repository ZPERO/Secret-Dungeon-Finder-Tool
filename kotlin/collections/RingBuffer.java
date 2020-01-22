package kotlin.collections;

import java.util.Arrays;
import java.util.Iterator;
import java.util.RandomAccess;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;

@Metadata(bv={1, 0, 3}, d1={"\000>\n\002\030\002\n\000\n\002\030\002\n\002\030\002\n\002\030\002\n\000\n\002\020\b\n\002\b\002\n\002\020\021\n\002\020\000\n\002\b\t\n\002\020\002\n\002\b\b\n\002\020\013\n\000\n\002\020(\n\002\b\b\b\002\030\000*\004\b\000\020\0012\b\022\004\022\002H\0010\0022\0060\003j\002`\004B\017\b\026\022\006\020\005\032\0020\006?\006\002\020\007B\035\022\016\020\b\032\n\022\006\022\004\030\0010\n0\t\022\006\020\013\032\0020\006?\006\002\020\fJ\023\020\023\032\0020\0242\006\020\025\032\0028\000?\006\002\020\026J\024\020\027\032\b\022\004\022\0028\0000\0002\006\020\030\032\0020\006J\026\020\031\032\0028\0002\006\020\032\032\0020\006H?\002?\006\002\020\033J\006\020\034\032\0020\035J\017\020\036\032\b\022\004\022\0028\0000\037H?\002J\016\020 \032\0020\0242\006\020!\032\0020\006J\025\020\"\032\n\022\006\022\004\030\0010\n0\tH\024?\006\002\020#J'\020\"\032\b\022\004\022\002H\0010\t\"\004\b\001\020\0012\f\020$\032\b\022\004\022\002H\0010\tH\024?\006\002\020%J\025\020&\032\0020\006*\0020\0062\006\020!\032\0020\006H?\bR\030\020\b\032\n\022\006\022\004\030\0010\n0\tX?\004?\006\004\n\002\020\rR\016\020\005\032\0020\006X?\004?\006\002\n\000R\036\020\017\032\0020\0062\006\020\016\032\0020\006@RX?\016?\006\b\n\000\032\004\b\020\020\021R\016\020\022\032\0020\006X?\016?\006\002\n\000?\006'"}, d2={"Lkotlin/collections/RingBuffer;", "T", "Lkotlin/collections/AbstractList;", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "capacity", "", "(I)V", "buffer", "", "", "filledSize", "([Ljava/lang/Object;I)V", "[Ljava/lang/Object;", "<set-?>", "size", "getSize", "()I", "startIndex", "add", "", "element", "(Ljava/lang/Object;)V", "expanded", "maxCapacity", "get", "index", "(I)Ljava/lang/Object;", "isFull", "", "iterator", "", "removeFirst", "n", "toArray", "()[Ljava/lang/Object;", "array", "([Ljava/lang/Object;)[Ljava/lang/Object;", "forward", "kotlin-stdlib"}, k=1, mv={1, 1, 15})
final class RingBuffer<T>
  extends AbstractList<T>
  implements RandomAccess
{
  private final Object[] buffer;
  private final int capacity;
  private int size;
  private int startIndex;
  
  public RingBuffer(int paramInt)
  {
    this(new Object[paramInt], 0);
  }
  
  public RingBuffer(Object[] paramArrayOfObject, int paramInt)
  {
    buffer = paramArrayOfObject;
    int j = 1;
    int i;
    if (paramInt >= 0) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      if (paramInt <= buffer.length) {
        i = j;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        capacity = buffer.length;
        size = paramInt;
        return;
      }
      paramArrayOfObject = new StringBuilder();
      paramArrayOfObject.append("ring buffer filled size: ");
      paramArrayOfObject.append(paramInt);
      paramArrayOfObject.append(" cannot be larger than the buffer size: ");
      paramArrayOfObject.append(buffer.length);
      throw ((Throwable)new IllegalArgumentException(paramArrayOfObject.toString().toString()));
    }
    paramArrayOfObject = new StringBuilder();
    paramArrayOfObject.append("ring buffer filled size should not be negative but it is ");
    paramArrayOfObject.append(paramInt);
    throw ((Throwable)new IllegalArgumentException(paramArrayOfObject.toString().toString()));
  }
  
  private final int forward(int paramInt1, int paramInt2)
  {
    return (paramInt1 + paramInt2) % access$getCapacity$p(this);
  }
  
  public final void add(Object paramObject)
  {
    if (!isFull())
    {
      buffer[((startIndex + size()) % access$getCapacity$p(this))] = paramObject;
      size = (size() + 1);
      return;
    }
    throw ((Throwable)new IllegalStateException("ring buffer is full"));
  }
  
  public final RingBuffer expanded(int paramInt)
  {
    int i = capacity;
    paramInt = RangesKt___RangesKt.coerceAtMost(i + (i >> 1) + 1, paramInt);
    Object[] arrayOfObject1;
    if (startIndex == 0)
    {
      Object[] arrayOfObject2 = Arrays.copyOf(buffer, paramInt);
      arrayOfObject1 = arrayOfObject2;
      Intrinsics.checkExpressionValueIsNotNull(arrayOfObject2, "java.util.Arrays.copyOf(this, newSize)");
    }
    else
    {
      arrayOfObject1 = toArray(new Object[paramInt]);
    }
    return new RingBuffer(arrayOfObject1, size());
  }
  
  public Object get(int paramInt)
  {
    AbstractList.Companion.checkElementIndex$kotlin_stdlib(paramInt, size());
    return buffer[((startIndex + paramInt) % access$getCapacity$p(this))];
  }
  
  public int getSize()
  {
    return size;
  }
  
  public final boolean isFull()
  {
    return size() == capacity;
  }
  
  public Iterator iterator()
  {
    (Iterator)new AbstractIterator()
    {
      private int count = size();
      private int index = RingBuffer.access$getStartIndex$p(RingBuffer.this);
      
      protected void computeNext()
      {
        if (count == 0)
        {
          done();
          return;
        }
        setNext(RingBuffer.access$getBuffer$p(RingBuffer.this)[index]);
        RingBuffer localRingBuffer = RingBuffer.this;
        index = ((index + 1) % RingBuffer.access$getCapacity$p(localRingBuffer));
        count -= 1;
      }
    };
  }
  
  public final void removeFirst(int paramInt)
  {
    int j = 1;
    int i;
    if (paramInt >= 0) {
      i = 1;
    } else {
      i = 0;
    }
    StringBuilder localStringBuilder;
    if (i != 0)
    {
      if (paramInt <= size()) {
        i = j;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        if (paramInt > 0)
        {
          i = startIndex;
          j = (i + paramInt) % access$getCapacity$p(this);
          if (i > j)
          {
            ArraysKt___ArraysJvmKt.fill(buffer, null, i, capacity);
            ArraysKt___ArraysJvmKt.fill(buffer, null, 0, j);
          }
          else
          {
            ArraysKt___ArraysJvmKt.fill(buffer, null, i, j);
          }
          startIndex = j;
          size = (size() - paramInt);
        }
      }
      else
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("n shouldn't be greater than the buffer size: n = ");
        localStringBuilder.append(paramInt);
        localStringBuilder.append(", size = ");
        localStringBuilder.append(size());
        throw ((Throwable)new IllegalArgumentException(localStringBuilder.toString().toString()));
      }
    }
    else
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("n shouldn't be negative but it is ");
      localStringBuilder.append(paramInt);
      throw ((Throwable)new IllegalArgumentException(localStringBuilder.toString().toString()));
    }
  }
  
  public Object[] toArray()
  {
    return toArray(new Object[size()]);
  }
  
  public Object[] toArray(Object[] paramArrayOfObject)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "array");
    Object[] arrayOfObject = paramArrayOfObject;
    if (paramArrayOfObject.length < size())
    {
      arrayOfObject = Arrays.copyOf(paramArrayOfObject, size());
      paramArrayOfObject = arrayOfObject;
      Intrinsics.checkExpressionValueIsNotNull(arrayOfObject, "java.util.Arrays.copyOf(this, newSize)");
      arrayOfObject = paramArrayOfObject;
    }
    int i1 = size();
    int n = 0;
    int j = startIndex;
    int i = 0;
    int k;
    int m;
    for (;;)
    {
      k = n;
      m = i;
      if (i >= i1) {
        break;
      }
      k = n;
      m = i;
      if (j >= capacity) {
        break;
      }
      arrayOfObject[i] = buffer[j];
      i += 1;
      j += 1;
    }
    while (m < i1)
    {
      arrayOfObject[m] = buffer[k];
      m += 1;
      k += 1;
    }
    if (arrayOfObject.length > size()) {
      arrayOfObject[size()] = null;
    }
    if (arrayOfObject != null) {
      return arrayOfObject;
    }
    paramArrayOfObject = new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
    throw paramArrayOfObject;
  }
}
