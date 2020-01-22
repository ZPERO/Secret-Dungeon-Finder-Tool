package androidx.versionedparcelable;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
@Target({java.lang.annotation.ElementType.TYPE})
public @interface VersionedParcelize
{
  boolean allowSerialization();
  
  int[] deprecatedIds();
  
  Class factory();
  
  boolean ignoreParcelables();
  
  boolean isCustom();
  
  String jetifyAs();
}