package com.squareup.okhttp.internal.framed;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Source;

final class Hpack
{
  private static final Map<ByteString, Integer> NAME_TO_FIRST_INDEX = nameToFirstIndex();
  private static final int PREFIX_4_BITS = 15;
  private static final int PREFIX_5_BITS = 31;
  private static final int PREFIX_6_BITS = 63;
  private static final int PREFIX_7_BITS = 127;
  private static final Header[] STATIC_HEADER_TABLE = { new Header(Header.TARGET_AUTHORITY, ""), new Header(Header.TARGET_METHOD, "GET"), new Header(Header.TARGET_METHOD, "POST"), new Header(Header.TARGET_PATH, "/"), new Header(Header.TARGET_PATH, "/index.html"), new Header(Header.TARGET_SCHEME, "http"), new Header(Header.TARGET_SCHEME, "https"), new Header(Header.RESPONSE_STATUS, "200"), new Header(Header.RESPONSE_STATUS, "204"), new Header(Header.RESPONSE_STATUS, "206"), new Header(Header.RESPONSE_STATUS, "304"), new Header(Header.RESPONSE_STATUS, "400"), new Header(Header.RESPONSE_STATUS, "404"), new Header(Header.RESPONSE_STATUS, "500"), new Header("accept-charset", ""), new Header("accept-encoding", "gzip, deflate"), new Header("accept-language", ""), new Header("accept-ranges", ""), new Header("accept", ""), new Header("access-control-allow-origin", ""), new Header("age", ""), new Header("allow", ""), new Header("authorization", ""), new Header("cache-control", ""), new Header("content-disposition", ""), new Header("content-encoding", ""), new Header("content-language", ""), new Header("content-length", ""), new Header("content-location", ""), new Header("content-range", ""), new Header("content-type", ""), new Header("cookie", ""), new Header("date", ""), new Header("etag", ""), new Header("expect", ""), new Header("expires", ""), new Header("from", ""), new Header("host", ""), new Header("if-match", ""), new Header("if-modified-since", ""), new Header("if-none-match", ""), new Header("if-range", ""), new Header("if-unmodified-since", ""), new Header("last-modified", ""), new Header("link", ""), new Header("location", ""), new Header("max-forwards", ""), new Header("proxy-authenticate", ""), new Header("proxy-authorization", ""), new Header("range", ""), new Header("referer", ""), new Header("refresh", ""), new Header("retry-after", ""), new Header("server", ""), new Header("set-cookie", ""), new Header("strict-transport-security", ""), new Header("transfer-encoding", ""), new Header("user-agent", ""), new Header("vary", ""), new Header("via", ""), new Header("www-authenticate", "") };
  
  private Hpack() {}
  
  private static ByteString checkLowercase(ByteString paramByteString)
    throws IOException
  {
    int j = paramByteString.size();
    int i = 0;
    while (i < j)
    {
      int k = paramByteString.getByte(i);
      if ((k >= 65) && (k <= 90))
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("PROTOCOL_ERROR response malformed: mixed case name: ");
        localStringBuilder.append(paramByteString.utf8());
        throw new IOException(localStringBuilder.toString());
      }
      i += 1;
    }
    return paramByteString;
  }
  
  private static Map nameToFirstIndex()
  {
    LinkedHashMap localLinkedHashMap = new LinkedHashMap(STATIC_HEADER_TABLE.length);
    int i = 0;
    for (;;)
    {
      Header[] arrayOfHeader = STATIC_HEADER_TABLE;
      if (i >= arrayOfHeader.length) {
        break;
      }
      if (!localLinkedHashMap.containsKey(name)) {
        localLinkedHashMap.put(STATIC_HEADER_TABLEname, Integer.valueOf(i));
      }
      i += 1;
    }
    return Collections.unmodifiableMap(localLinkedHashMap);
  }
  
  static final class Reader
  {
    Header[] dynamicTable = new Header[8];
    int dynamicTableByteCount = 0;
    int headerCount = 0;
    private final List<Header> headerList = new ArrayList();
    private int headerTableSizeSetting;
    private int maxDynamicTableByteCount;
    int nextHeaderIndex = dynamicTable.length - 1;
    private final BufferedSource source;
    
    Reader(int paramInt, Source paramSource)
    {
      headerTableSizeSetting = paramInt;
      maxDynamicTableByteCount = paramInt;
      source = Okio.buffer(paramSource);
    }
    
    private void adjustDynamicTableByteCount()
    {
      int i = maxDynamicTableByteCount;
      int j = dynamicTableByteCount;
      if (i < j)
      {
        if (i == 0)
        {
          clearDynamicTable();
          return;
        }
        evictToRecoverBytes(j - i);
      }
    }
    
    private void clearDynamicTable()
    {
      headerList.clear();
      Arrays.fill(dynamicTable, null);
      nextHeaderIndex = (dynamicTable.length - 1);
      headerCount = 0;
      dynamicTableByteCount = 0;
    }
    
    private int dynamicTableIndex(int paramInt)
    {
      return nextHeaderIndex + 1 + paramInt;
    }
    
    private int evictToRecoverBytes(int paramInt)
    {
      int k = 0;
      if (paramInt > 0)
      {
        int i = dynamicTable.length - 1;
        int j = paramInt;
        paramInt = k;
        while ((i >= nextHeaderIndex) && (j > 0))
        {
          j -= dynamicTable[i].hpackSize;
          dynamicTableByteCount -= dynamicTable[i].hpackSize;
          headerCount -= 1;
          paramInt += 1;
          i -= 1;
        }
        Header[] arrayOfHeader = dynamicTable;
        i = nextHeaderIndex;
        System.arraycopy(arrayOfHeader, i + 1, arrayOfHeader, i + 1 + paramInt, headerCount);
        nextHeaderIndex += paramInt;
        return paramInt;
      }
      return 0;
    }
    
    private ByteString getName(int paramInt)
    {
      if (isStaticHeader(paramInt)) {
        return STATIC_HEADER_TABLEname;
      }
      return dynamicTable[dynamicTableIndex(paramInt - Hpack.STATIC_HEADER_TABLE.length)].name;
    }
    
    private void insertIntoDynamicTable(int paramInt, Header paramHeader)
    {
      headerList.add(paramHeader);
      int j = hpackSize;
      int i = j;
      if (paramInt != -1) {
        i = j - dynamicTable[dynamicTableIndex(paramInt)].hpackSize;
      }
      j = maxDynamicTableByteCount;
      if (i > j)
      {
        clearDynamicTable();
        return;
      }
      j = evictToRecoverBytes(dynamicTableByteCount + i - j);
      if (paramInt == -1)
      {
        paramInt = headerCount;
        Header[] arrayOfHeader1 = dynamicTable;
        if (paramInt + 1 > arrayOfHeader1.length)
        {
          Header[] arrayOfHeader2 = new Header[arrayOfHeader1.length * 2];
          System.arraycopy(arrayOfHeader1, 0, arrayOfHeader2, arrayOfHeader1.length, arrayOfHeader1.length);
          nextHeaderIndex = (dynamicTable.length - 1);
          dynamicTable = arrayOfHeader2;
        }
        paramInt = nextHeaderIndex;
        nextHeaderIndex = (paramInt - 1);
        dynamicTable[paramInt] = paramHeader;
        headerCount += 1;
      }
      else
      {
        int k = dynamicTableIndex(paramInt);
        dynamicTable[(paramInt + (k + j))] = paramHeader;
      }
      dynamicTableByteCount += i;
    }
    
    private boolean isStaticHeader(int paramInt)
    {
      return (paramInt >= 0) && (paramInt <= Hpack.STATIC_HEADER_TABLE.length - 1);
    }
    
    private int readByte()
      throws IOException
    {
      return source.readByte() & 0xFF;
    }
    
    private void readIndexedHeader(int paramInt)
      throws IOException
    {
      if (isStaticHeader(paramInt))
      {
        localObject = Hpack.STATIC_HEADER_TABLE[paramInt];
        headerList.add(localObject);
        return;
      }
      int i = dynamicTableIndex(paramInt - Hpack.STATIC_HEADER_TABLE.length);
      if (i >= 0)
      {
        localObject = dynamicTable;
        if (i <= localObject.length - 1)
        {
          headerList.add(localObject[i]);
          return;
        }
      }
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Header index too large ");
      ((StringBuilder)localObject).append(paramInt + 1);
      throw new IOException(((StringBuilder)localObject).toString());
    }
    
    private void readLiteralHeaderWithIncrementalIndexingIndexedName(int paramInt)
      throws IOException
    {
      insertIntoDynamicTable(-1, new Header(getName(paramInt), readByteString()));
    }
    
    private void readLiteralHeaderWithIncrementalIndexingNewName()
      throws IOException
    {
      insertIntoDynamicTable(-1, new Header(Hpack.checkLowercase(readByteString()), readByteString()));
    }
    
    private void readLiteralHeaderWithoutIndexingIndexedName(int paramInt)
      throws IOException
    {
      ByteString localByteString1 = getName(paramInt);
      ByteString localByteString2 = readByteString();
      headerList.add(new Header(localByteString1, localByteString2));
    }
    
    private void readLiteralHeaderWithoutIndexingNewName()
      throws IOException
    {
      ByteString localByteString1 = Hpack.checkLowercase(readByteString());
      ByteString localByteString2 = readByteString();
      headerList.add(new Header(localByteString1, localByteString2));
    }
    
    public List getAndResetHeaderList()
    {
      ArrayList localArrayList = new ArrayList(headerList);
      headerList.clear();
      return localArrayList;
    }
    
    void headerTableSizeSetting(int paramInt)
    {
      headerTableSizeSetting = paramInt;
      maxDynamicTableByteCount = paramInt;
      adjustDynamicTableByteCount();
    }
    
    int maxDynamicTableByteCount()
    {
      return maxDynamicTableByteCount;
    }
    
    ByteString readByteString()
      throws IOException
    {
      int j = readByte();
      int i;
      if ((j & 0x80) == 128) {
        i = 1;
      } else {
        i = 0;
      }
      j = readInt(j, 127);
      if (i != 0) {
        return ByteString.of(Huffman.get().decode(source.readByteArray(j)));
      }
      return source.readByteString(j);
    }
    
    void readHeaders()
      throws IOException
    {
      while (!source.exhausted())
      {
        int i = source.readByte() & 0xFF;
        if (i != 128)
        {
          if ((i & 0x80) == 128)
          {
            readIndexedHeader(readInt(i, 127) - 1);
          }
          else if (i == 64)
          {
            readLiteralHeaderWithIncrementalIndexingNewName();
          }
          else if ((i & 0x40) == 64)
          {
            readLiteralHeaderWithIncrementalIndexingIndexedName(readInt(i, 63) - 1);
          }
          else if ((i & 0x20) == 32)
          {
            maxDynamicTableByteCount = readInt(i, 31);
            i = maxDynamicTableByteCount;
            if ((i >= 0) && (i <= headerTableSizeSetting))
            {
              adjustDynamicTableByteCount();
            }
            else
            {
              StringBuilder localStringBuilder = new StringBuilder();
              localStringBuilder.append("Invalid dynamic table size update ");
              localStringBuilder.append(maxDynamicTableByteCount);
              throw new IOException(localStringBuilder.toString());
            }
          }
          else if ((i != 16) && (i != 0))
          {
            readLiteralHeaderWithoutIndexingIndexedName(readInt(i, 15) - 1);
          }
          else
          {
            readLiteralHeaderWithoutIndexingNewName();
          }
        }
        else {
          throw new IOException("index == 0");
        }
      }
    }
    
    int readInt(int paramInt1, int paramInt2)
      throws IOException
    {
      paramInt1 &= paramInt2;
      if (paramInt1 < paramInt2) {
        return paramInt1;
      }
      paramInt1 = 0;
      int i;
      for (;;)
      {
        i = readByte();
        if ((i & 0x80) == 0) {
          break;
        }
        paramInt2 += ((i & 0x7F) << paramInt1);
        paramInt1 += 7;
      }
      return paramInt2 + (i << paramInt1);
    }
  }
  
  static final class Writer
  {
    private final Buffer out;
    
    Writer(Buffer paramBuffer)
    {
      out = paramBuffer;
    }
    
    void writeByteString(ByteString paramByteString)
      throws IOException
    {
      writeInt(paramByteString.size(), 127, 0);
      out.write(paramByteString);
    }
    
    void writeHeaders(List paramList)
      throws IOException
    {
      int j = paramList.size();
      int i = 0;
      while (i < j)
      {
        ByteString localByteString = getname.toAsciiLowercase();
        Integer localInteger = (Integer)Hpack.NAME_TO_FIRST_INDEX.get(localByteString);
        if (localInteger != null)
        {
          writeInt(localInteger.intValue() + 1, 15, 0);
          writeByteString(getvalue);
        }
        else
        {
          out.writeByte(0);
          writeByteString(localByteString);
          writeByteString(getvalue);
        }
        i += 1;
      }
    }
    
    void writeInt(int paramInt1, int paramInt2, int paramInt3)
      throws IOException
    {
      if (paramInt1 < paramInt2)
      {
        out.writeByte(paramInt1 | paramInt3);
        return;
      }
      out.writeByte(paramInt3 | paramInt2);
      paramInt1 -= paramInt2;
      while (paramInt1 >= 128)
      {
        out.writeByte(0x80 | paramInt1 & 0x7F);
        paramInt1 >>>= 7;
      }
      out.writeByte(paramInt1);
    }
  }
}
