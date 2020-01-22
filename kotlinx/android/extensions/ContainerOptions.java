package kotlinx.android.extensions;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import kotlin.Metadata;

@Retention(RetentionPolicy.RUNTIME)
@Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\020\033\n\000\n\002\030\002\n\002\b\002\b?\002\030\0002\0020\001B\n\022\b\b\002\020\002\032\0020\003R\017\020\002\032\0020\003?\006\006\032\004\b\002\020\004?\006\005"}, d2={"Lkotlinx/android/extensions/ContainerOptions;", "", "cache", "Lkotlinx/android/extensions/CacheImplementation;", "()Lkotlinx/android/extensions/CacheImplementation;", "kotlin-android-extensions-runtime"}, k=1, mv={1, 1, 15})
public @interface ContainerOptions
{
  CacheImplementation cache();
}
