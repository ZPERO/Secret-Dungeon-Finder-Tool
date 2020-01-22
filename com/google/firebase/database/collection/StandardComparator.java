package com.google.firebase.database.collection;

import java.util.Comparator;

public class StandardComparator<A extends Comparable<A>>
  implements Comparator<A>
{
  private static final StandardComparator<?> zzac = new StandardComparator();
  
  private StandardComparator() {}
  
  public static StandardComparator getComparator(Class paramClass)
  {
    return zzac;
  }
  
  public int compare(Comparable paramComparable1, Comparable paramComparable2)
  {
    return paramComparable1.compareTo(paramComparable2);
  }
}
