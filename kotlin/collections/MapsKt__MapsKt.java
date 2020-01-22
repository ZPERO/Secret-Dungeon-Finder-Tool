package kotlin.collections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.sequences.Sequence;

@Metadata(bv={1, 0, 3}, d1={"\000~\n\000\n\002\020\b\n\000\n\002\020$\n\002\b\003\n\002\030\002\n\002\030\002\n\000\n\002\020\021\n\002\030\002\n\002\b\002\n\002\030\002\n\002\030\002\n\002\b\006\n\002\020%\n\000\n\002\020&\n\002\b\003\n\002\020\013\n\002\030\002\n\002\b\007\n\002\030\002\n\002\b\f\n\002\030\002\n\002\b\n\n\002\020(\n\002\020)\n\002\020'\n\002\b\n\n\002\020\034\n\002\030\002\n\000\n\002\020\002\n\002\b\026\032\036\020\002\032\016\022\004\022\002H\004\022\004\022\002H\0050\003\"\004\b\000\020\004\"\004\b\001\020\005\0321\020\006\032\036\022\004\022\002H\004\022\004\022\002H\0050\007j\016\022\004\022\002H\004\022\004\022\002H\005`\b\"\004\b\000\020\004\"\004\b\001\020\005H?\b\032_\020\006\032\036\022\004\022\002H\004\022\004\022\002H\0050\007j\016\022\004\022\002H\004\022\004\022\002H\005`\b\"\004\b\000\020\004\"\004\b\001\020\0052*\020\t\032\026\022\022\b\001\022\016\022\004\022\002H\004\022\004\022\002H\0050\0130\n\"\016\022\004\022\002H\004\022\004\022\002H\0050\013?\006\002\020\f\0321\020\r\032\036\022\004\022\002H\004\022\004\022\002H\0050\016j\016\022\004\022\002H\004\022\004\022\002H\005`\017\"\004\b\000\020\004\"\004\b\001\020\005H?\b\032_\020\r\032\036\022\004\022\002H\004\022\004\022\002H\0050\016j\016\022\004\022\002H\004\022\004\022\002H\005`\017\"\004\b\000\020\004\"\004\b\001\020\0052*\020\t\032\026\022\022\b\001\022\016\022\004\022\002H\004\022\004\022\002H\0050\0130\n\"\016\022\004\022\002H\004\022\004\022\002H\0050\013?\006\002\020\020\032\020\020\021\032\0020\0012\006\020\022\032\0020\001H\001\032!\020\023\032\016\022\004\022\002H\004\022\004\022\002H\0050\003\"\004\b\000\020\004\"\004\b\001\020\005H?\b\032O\020\023\032\016\022\004\022\002H\004\022\004\022\002H\0050\003\"\004\b\000\020\004\"\004\b\001\020\0052*\020\t\032\026\022\022\b\001\022\016\022\004\022\002H\004\022\004\022\002H\0050\0130\n\"\016\022\004\022\002H\004\022\004\022\002H\0050\013?\006\002\020\024\032!\020\025\032\016\022\004\022\002H\004\022\004\022\002H\0050\026\"\004\b\000\020\004\"\004\b\001\020\005H?\b\032O\020\025\032\016\022\004\022\002H\004\022\004\022\002H\0050\026\"\004\b\000\020\004\"\004\b\001\020\0052*\020\t\032\026\022\022\b\001\022\016\022\004\022\002H\004\022\004\022\002H\0050\0130\n\"\016\022\004\022\002H\004\022\004\022\002H\0050\013?\006\002\020\024\032*\020\027\032\002H\004\"\004\b\000\020\004\"\004\b\001\020\005*\016\022\004\022\002H\004\022\004\022\002H\0050\030H?\n?\006\002\020\031\032*\020\032\032\002H\005\"\004\b\000\020\004\"\004\b\001\020\005*\016\022\004\022\002H\004\022\004\022\002H\0050\030H?\n?\006\002\020\031\0329\020\033\032\0020\034\"\t\b\000\020\004?\006\002\b\035\"\004\b\001\020\005*\020\022\006\b\001\022\002H\004\022\004\022\002H\0050\0032\006\020\036\032\002H\004H?\n?\006\002\020\037\0321\020 \032\0020\034\"\t\b\000\020\004?\006\002\b\035*\016\022\006\b\001\022\002H\004\022\002\b\0030\0032\006\020\036\032\002H\004H?\b?\006\002\020\037\0327\020!\032\0020\034\"\004\b\000\020\004\"\t\b\001\020\005?\006\002\b\035*\016\022\004\022\002H\004\022\004\022\002H\0050\0032\006\020\"\032\002H\005H?\b?\006\002\020\037\032S\020#\032\016\022\004\022\002H\004\022\004\022\002H\0050\003\"\004\b\000\020\004\"\004\b\001\020\005*\020\022\006\b\001\022\002H\004\022\004\022\002H\0050\0032\036\020$\032\032\022\020\022\016\022\004\022\002H\004\022\004\022\002H\0050\030\022\004\022\0020\0340%H?\b\032G\020&\032\016\022\004\022\002H\004\022\004\022\002H\0050\003\"\004\b\000\020\004\"\004\b\001\020\005*\020\022\006\b\001\022\002H\004\022\004\022\002H\0050\0032\022\020$\032\016\022\004\022\002H\004\022\004\022\0020\0340%H?\b\032S\020'\032\016\022\004\022\002H\004\022\004\022\002H\0050\003\"\004\b\000\020\004\"\004\b\001\020\005*\020\022\006\b\001\022\002H\004\022\004\022\002H\0050\0032\036\020$\032\032\022\020\022\016\022\004\022\002H\004\022\004\022\002H\0050\030\022\004\022\0020\0340%H?\b\032n\020(\032\002H)\"\004\b\000\020\004\"\004\b\001\020\005\"\030\b\002\020)*\022\022\006\b\000\022\002H\004\022\006\b\000\022\002H\0050\026*\020\022\006\b\001\022\002H\004\022\004\022\002H\0050\0032\006\020*\032\002H)2\036\020$\032\032\022\020\022\016\022\004\022\002H\004\022\004\022\002H\0050\030\022\004\022\0020\0340%H?\b?\006\002\020+\032n\020,\032\002H)\"\004\b\000\020\004\"\004\b\001\020\005\"\030\b\002\020)*\022\022\006\b\000\022\002H\004\022\006\b\000\022\002H\0050\026*\020\022\006\b\001\022\002H\004\022\004\022\002H\0050\0032\006\020*\032\002H)2\036\020$\032\032\022\020\022\016\022\004\022\002H\004\022\004\022\002H\0050\030\022\004\022\0020\0340%H?\b?\006\002\020+\032G\020-\032\016\022\004\022\002H\004\022\004\022\002H\0050\003\"\004\b\000\020\004\"\004\b\001\020\005*\020\022\006\b\001\022\002H\004\022\004\022\002H\0050\0032\022\020$\032\016\022\004\022\002H\005\022\004\022\0020\0340%H?\b\032;\020.\032\004\030\001H\005\"\t\b\000\020\004?\006\002\b\035\"\004\b\001\020\005*\020\022\006\b\001\022\002H\004\022\004\022\002H\0050\0032\006\020\036\032\002H\004H?\n?\006\002\020/\032@\0200\032\002H\005\"\004\b\000\020\004\"\004\b\001\020\005*\016\022\004\022\002H\004\022\004\022\002H\0050\0032\006\020\036\032\002H\0042\f\0201\032\b\022\004\022\002H\00502H?\b?\006\002\0203\032@\0204\032\002H\005\"\004\b\000\020\004\"\004\b\001\020\005*\016\022\004\022\002H\004\022\004\022\002H\0050\0032\006\020\036\032\002H\0042\f\0201\032\b\022\004\022\002H\00502H?\b?\006\002\0203\032@\0205\032\002H\005\"\004\b\000\020\004\"\004\b\001\020\005*\016\022\004\022\002H\004\022\004\022\002H\0050\0262\006\020\036\032\002H\0042\f\0201\032\b\022\004\022\002H\00502H?\b?\006\002\0203\0321\0206\032\002H\005\"\004\b\000\020\004\"\004\b\001\020\005*\016\022\004\022\002H\004\022\004\022\002H\0050\0032\006\020\036\032\002H\004H\007?\006\002\020/\032<\0207\032\002H8\"\024\b\000\020)*\n\022\002\b\003\022\002\b\0030\003*\002H8\"\004\b\001\0208*\002H)2\f\0201\032\b\022\004\022\002H802H?\b?\006\002\0209\032'\020:\032\0020\034\"\004\b\000\020\004\"\004\b\001\020\005*\020\022\006\b\001\022\002H\004\022\004\022\002H\0050\003H?\b\032:\020;\032\0020\034\"\004\b\000\020\004\"\004\b\001\020\005*\022\022\006\b\001\022\002H\004\022\004\022\002H\005\030\0010\003H?\b?\002\016\n\f\b\000\022\002\030\001\032\004\b\003\020\000\0329\020<\032\024\022\020\022\016\022\004\022\002H\004\022\004\022\002H\0050\0300=\"\004\b\000\020\004\"\004\b\001\020\005*\020\022\006\b\001\022\002H\004\022\004\022\002H\0050\003H?\n\032<\020<\032\024\022\020\022\016\022\004\022\002H\004\022\004\022\002H\0050?0>\"\004\b\000\020\004\"\004\b\001\020\005*\016\022\004\022\002H\004\022\004\022\002H\0050\026H?\n?\006\002\b@\032Y\020A\032\016\022\004\022\002H8\022\004\022\002H\0050\003\"\004\b\000\020\004\"\004\b\001\020\005\"\004\b\002\0208*\020\022\006\b\001\022\002H\004\022\004\022\002H\0050\0032\036\020B\032\032\022\020\022\016\022\004\022\002H\004\022\004\022\002H\0050\030\022\004\022\002H80%H?\b\032t\020C\032\002H)\"\004\b\000\020\004\"\004\b\001\020\005\"\004\b\002\0208\"\030\b\003\020)*\022\022\006\b\000\022\002H8\022\006\b\000\022\002H\0050\026*\020\022\006\b\001\022\002H\004\022\004\022\002H\0050\0032\006\020*\032\002H)2\036\020B\032\032\022\020\022\016\022\004\022\002H\004\022\004\022\002H\0050\030\022\004\022\002H80%H?\b?\006\002\020+\032Y\020D\032\016\022\004\022\002H\004\022\004\022\002H80\003\"\004\b\000\020\004\"\004\b\001\020\005\"\004\b\002\0208*\020\022\006\b\001\022\002H\004\022\004\022\002H\0050\0032\036\020B\032\032\022\020\022\016\022\004\022\002H\004\022\004\022\002H\0050\030\022\004\022\002H80%H?\b\032t\020E\032\002H)\"\004\b\000\020\004\"\004\b\001\020\005\"\004\b\002\0208\"\030\b\003\020)*\022\022\006\b\000\022\002H\004\022\006\b\000\022\002H80\026*\020\022\006\b\001\022\002H\004\022\004\022\002H\0050\0032\006\020*\032\002H)2\036\020B\032\032\022\020\022\016\022\004\022\002H\004\022\004\022\002H\0050\030\022\004\022\002H80%H?\b?\006\002\020+\032@\020F\032\016\022\004\022\002H\004\022\004\022\002H\0050\003\"\004\b\000\020\004\"\004\b\001\020\005*\020\022\006\b\001\022\002H\004\022\004\022\002H\0050\0032\006\020\036\032\002H\004H?\002?\006\002\020G\032H\020F\032\016\022\004\022\002H\004\022\004\022\002H\0050\003\"\004\b\000\020\004\"\004\b\001\020\005*\020\022\006\b\001\022\002H\004\022\004\022\002H\0050\0032\016\020H\032\n\022\006\b\001\022\002H\0040\nH?\002?\006\002\020I\032A\020F\032\016\022\004\022\002H\004\022\004\022\002H\0050\003\"\004\b\000\020\004\"\004\b\001\020\005*\020\022\006\b\001\022\002H\004\022\004\022\002H\0050\0032\f\020H\032\b\022\004\022\002H\0040JH?\002\032A\020F\032\016\022\004\022\002H\004\022\004\022\002H\0050\003\"\004\b\000\020\004\"\004\b\001\020\005*\020\022\006\b\001\022\002H\004\022\004\022\002H\0050\0032\f\020H\032\b\022\004\022\002H\0040KH?\002\0322\020L\032\0020M\"\004\b\000\020\004\"\004\b\001\020\005*\016\022\004\022\002H\004\022\004\022\002H\0050\0262\006\020\036\032\002H\004H?\n?\006\002\020N\032:\020L\032\0020M\"\004\b\000\020\004\"\004\b\001\020\005*\016\022\004\022\002H\004\022\004\022\002H\0050\0262\016\020H\032\n\022\006\b\001\022\002H\0040\nH?\n?\006\002\020O\0323\020L\032\0020M\"\004\b\000\020\004\"\004\b\001\020\005*\016\022\004\022\002H\004\022\004\022\002H\0050\0262\f\020H\032\b\022\004\022\002H\0040JH?\n\0323\020L\032\0020M\"\004\b\000\020\004\"\004\b\001\020\005*\016\022\004\022\002H\004\022\004\022\002H\0050\0262\f\020H\032\b\022\004\022\002H\0040KH?\n\0320\020P\032\016\022\004\022\002H\004\022\004\022\002H\0050\003\"\004\b\000\020\004\"\004\b\001\020\005*\016\022\004\022\002H\004\022\004\022\002H\0050\003H\000\0323\020Q\032\016\022\004\022\002H\004\022\004\022\002H\0050\003\"\004\b\000\020\004\"\004\b\001\020\005*\020\022\004\022\002H\004\022\004\022\002H\005\030\0010\003H?\b\032T\020R\032\016\022\004\022\002H\004\022\004\022\002H\0050\003\"\004\b\000\020\004\"\004\b\001\020\005*\020\022\006\b\001\022\002H\004\022\004\022\002H\0050\0032\032\020\t\032\026\022\022\b\001\022\016\022\004\022\002H\004\022\004\022\002H\0050\0130\nH?\002?\006\002\020S\032G\020R\032\016\022\004\022\002H\004\022\004\022\002H\0050\003\"\004\b\000\020\004\"\004\b\001\020\005*\020\022\006\b\001\022\002H\004\022\004\022\002H\0050\0032\022\020T\032\016\022\004\022\002H\004\022\004\022\002H\0050\013H?\002\032M\020R\032\016\022\004\022\002H\004\022\004\022\002H\0050\003\"\004\b\000\020\004\"\004\b\001\020\005*\020\022\006\b\001\022\002H\004\022\004\022\002H\0050\0032\030\020\t\032\024\022\020\022\016\022\004\022\002H\004\022\004\022\002H\0050\0130JH?\002\032I\020R\032\016\022\004\022\002H\004\022\004\022\002H\0050\003\"\004\b\000\020\004\"\004\b\001\020\005*\020\022\006\b\001\022\002H\004\022\004\022\002H\0050\0032\024\020U\032\020\022\006\b\001\022\002H\004\022\004\022\002H\0050\003H?\002\032M\020R\032\016\022\004\022\002H\004\022\004\022\002H\0050\003\"\004\b\000\020\004\"\004\b\001\020\005*\020\022\006\b\001\022\002H\004\022\004\022\002H\0050\0032\030\020\t\032\024\022\020\022\016\022\004\022\002H\004\022\004\022\002H\0050\0130KH?\002\032J\020V\032\0020M\"\004\b\000\020\004\"\004\b\001\020\005*\022\022\006\b\000\022\002H\004\022\006\b\000\022\002H\0050\0262\032\020\t\032\026\022\022\b\001\022\016\022\004\022\002H\004\022\004\022\002H\0050\0130\nH?\n?\006\002\020W\032=\020V\032\0020M\"\004\b\000\020\004\"\004\b\001\020\005*\022\022\006\b\000\022\002H\004\022\006\b\000\022\002H\0050\0262\022\020T\032\016\022\004\022\002H\004\022\004\022\002H\0050\013H?\n\032C\020V\032\0020M\"\004\b\000\020\004\"\004\b\001\020\005*\022\022\006\b\000\022\002H\004\022\006\b\000\022\002H\0050\0262\030\020\t\032\024\022\020\022\016\022\004\022\002H\004\022\004\022\002H\0050\0130JH?\n\032=\020V\032\0020M\"\004\b\000\020\004\"\004\b\001\020\005*\022\022\006\b\000\022\002H\004\022\006\b\000\022\002H\0050\0262\022\020U\032\016\022\004\022\002H\004\022\004\022\002H\0050\003H?\n\032C\020V\032\0020M\"\004\b\000\020\004\"\004\b\001\020\005*\022\022\006\b\000\022\002H\004\022\006\b\000\022\002H\0050\0262\030\020\t\032\024\022\020\022\016\022\004\022\002H\004\022\004\022\002H\0050\0130KH?\n\032G\020X\032\0020M\"\004\b\000\020\004\"\004\b\001\020\005*\022\022\006\b\000\022\002H\004\022\006\b\000\022\002H\0050\0262\032\020\t\032\026\022\022\b\001\022\016\022\004\022\002H\004\022\004\022\002H\0050\0130\n?\006\002\020W\032@\020X\032\0020M\"\004\b\000\020\004\"\004\b\001\020\005*\022\022\006\b\000\022\002H\004\022\006\b\000\022\002H\0050\0262\030\020\t\032\024\022\020\022\016\022\004\022\002H\004\022\004\022\002H\0050\0130J\032@\020X\032\0020M\"\004\b\000\020\004\"\004\b\001\020\005*\022\022\006\b\000\022\002H\004\022\006\b\000\022\002H\0050\0262\030\020\t\032\024\022\020\022\016\022\004\022\002H\004\022\004\022\002H\0050\0130K\032;\020Y\032\004\030\001H\005\"\t\b\000\020\004?\006\002\b\035\"\004\b\001\020\005*\020\022\006\b\001\022\002H\004\022\004\022\002H\0050\0262\006\020\036\032\002H\004H?\b?\006\002\020/\032:\020Z\032\0020M\"\004\b\000\020\004\"\004\b\001\020\005*\016\022\004\022\002H\004\022\004\022\002H\0050\0262\006\020\036\032\002H\0042\006\020\"\032\002H\005H?\n?\006\002\020[\032;\020\\\032\016\022\004\022\002H\004\022\004\022\002H\0050\003\"\004\b\000\020\004\"\004\b\001\020\005*\026\022\022\b\001\022\016\022\004\022\002H\004\022\004\022\002H\0050\0130\n?\006\002\020\024\032Q\020\\\032\002H)\"\004\b\000\020\004\"\004\b\001\020\005\"\030\b\002\020)*\022\022\006\b\000\022\002H\004\022\006\b\000\022\002H\0050\026*\026\022\022\b\001\022\016\022\004\022\002H\004\022\004\022\002H\0050\0130\n2\006\020*\032\002H)?\006\002\020]\0324\020\\\032\016\022\004\022\002H\004\022\004\022\002H\0050\003\"\004\b\000\020\004\"\004\b\001\020\005*\024\022\020\022\016\022\004\022\002H\004\022\004\022\002H\0050\0130J\032O\020\\\032\002H)\"\004\b\000\020\004\"\004\b\001\020\005\"\030\b\002\020)*\022\022\006\b\000\022\002H\004\022\006\b\000\022\002H\0050\026*\024\022\020\022\016\022\004\022\002H\004\022\004\022\002H\0050\0130J2\006\020*\032\002H)?\006\002\020^\0322\020\\\032\016\022\004\022\002H\004\022\004\022\002H\0050\003\"\004\b\000\020\004\"\004\b\001\020\005*\020\022\006\b\001\022\002H\004\022\004\022\002H\0050\003H\007\032M\020\\\032\002H)\"\004\b\000\020\004\"\004\b\001\020\005\"\030\b\002\020)*\022\022\006\b\000\022\002H\004\022\006\b\000\022\002H\0050\026*\020\022\006\b\001\022\002H\004\022\004\022\002H\0050\0032\006\020*\032\002H)H\007?\006\002\020_\0324\020\\\032\016\022\004\022\002H\004\022\004\022\002H\0050\003\"\004\b\000\020\004\"\004\b\001\020\005*\024\022\020\022\016\022\004\022\002H\004\022\004\022\002H\0050\0130K\032O\020\\\032\002H)\"\004\b\000\020\004\"\004\b\001\020\005\"\030\b\002\020)*\022\022\006\b\000\022\002H\004\022\006\b\000\022\002H\0050\026*\024\022\020\022\016\022\004\022\002H\004\022\004\022\002H\0050\0130K2\006\020*\032\002H)?\006\002\020`\0322\020a\032\016\022\004\022\002H\004\022\004\022\002H\0050\026\"\004\b\000\020\004\"\004\b\001\020\005*\020\022\006\b\001\022\002H\004\022\004\022\002H\0050\003H\007\0321\020b\032\016\022\004\022\002H\004\022\004\022\002H\0050\013\"\004\b\000\020\004\"\004\b\001\020\005*\016\022\004\022\002H\004\022\004\022\002H\0050\030H?\b\"\016\020\000\032\0020\001X?T?\006\002\n\000?\006c"}, d2={"INT_MAX_POWER_OF_TWO", "", "emptyMap", "", "K", "V", "hashMapOf", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "pairs", "", "Lkotlin/Pair;", "([Lkotlin/Pair;)Ljava/util/HashMap;", "linkedMapOf", "Ljava/util/LinkedHashMap;", "Lkotlin/collections/LinkedHashMap;", "([Lkotlin/Pair;)Ljava/util/LinkedHashMap;", "mapCapacity", "expectedSize", "mapOf", "([Lkotlin/Pair;)Ljava/util/Map;", "mutableMapOf", "", "component1", "", "(Ljava/util/Map$Entry;)Ljava/lang/Object;", "component2", "contains", "", "Lkotlin/internal/OnlyInputTypes;", "key", "(Ljava/util/Map;Ljava/lang/Object;)Z", "containsKey", "containsValue", "value", "filter", "predicate", "Lkotlin/Function1;", "filterKeys", "filterNot", "filterNotTo", "M", "destination", "(Ljava/util/Map;Ljava/util/Map;Lkotlin/jvm/functions/Function1;)Ljava/util/Map;", "filterTo", "filterValues", "get", "(Ljava/util/Map;Ljava/lang/Object;)Ljava/lang/Object;", "getOrElse", "defaultValue", "Lkotlin/Function0;", "(Ljava/util/Map;Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "getOrElseNullable", "getOrPut", "getValue", "ifEmpty", "R", "(Ljava/util/Map;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isNotEmpty", "isNullOrEmpty", "iterator", "", "", "", "mutableIterator", "mapKeys", "transform", "mapKeysTo", "mapValues", "mapValuesTo", "minus", "(Ljava/util/Map;Ljava/lang/Object;)Ljava/util/Map;", "keys", "(Ljava/util/Map;[Ljava/lang/Object;)Ljava/util/Map;", "", "Lkotlin/sequences/Sequence;", "minusAssign", "", "(Ljava/util/Map;Ljava/lang/Object;)V", "(Ljava/util/Map;[Ljava/lang/Object;)V", "optimizeReadOnlyMap", "orEmpty", "plus", "(Ljava/util/Map;[Lkotlin/Pair;)Ljava/util/Map;", "pair", "map", "plusAssign", "(Ljava/util/Map;[Lkotlin/Pair;)V", "putAll", "remove", "set", "(Ljava/util/Map;Ljava/lang/Object;Ljava/lang/Object;)V", "toMap", "([Lkotlin/Pair;Ljava/util/Map;)Ljava/util/Map;", "(Ljava/lang/Iterable;Ljava/util/Map;)Ljava/util/Map;", "(Ljava/util/Map;Ljava/util/Map;)Ljava/util/Map;", "(Lkotlin/sequences/Sequence;Ljava/util/Map;)Ljava/util/Map;", "toMutableMap", "toPair", "kotlin-stdlib"}, k=5, mv={1, 1, 15}, xi=1, xs="kotlin/collections/MapsKt")
class MapsKt__MapsKt
  extends MapsKt__MapsJVMKt
{
  private static final int INT_MAX_POWER_OF_TWO = 1073741824;
  
  public MapsKt__MapsKt() {}
  
  private static final Object component1(Map.Entry paramEntry)
  {
    Intrinsics.checkParameterIsNotNull(paramEntry, "$this$component1");
    return paramEntry.getKey();
  }
  
  private static final Object component2(Map.Entry paramEntry)
  {
    Intrinsics.checkParameterIsNotNull(paramEntry, "$this$component2");
    return paramEntry.getValue();
  }
  
  private static final boolean contains(Map paramMap, Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$contains");
    return paramMap.containsKey(paramObject);
  }
  
  private static final boolean containsKey(Map paramMap, Object paramObject)
  {
    if (paramMap != null) {
      return paramMap.containsKey(paramObject);
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Map<K, *>");
  }
  
  private static final boolean containsValue(Map paramMap, Object paramObject)
  {
    return paramMap.containsValue(paramObject);
  }
  
  public static final Map emptyMap()
  {
    EmptyMap localEmptyMap = EmptyMap.INSTANCE;
    if (localEmptyMap != null) {
      return (Map)localEmptyMap;
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.Map<K, V>");
  }
  
  public static final Map filter(Map paramMap, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$filter");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    Map localMap = (Map)new LinkedHashMap();
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramMap.next();
      if (((Boolean)paramFunction1.invoke(localEntry)).booleanValue()) {
        localMap.put(localEntry.getKey(), localEntry.getValue());
      }
    }
    return localMap;
  }
  
  public static final Map filterKeys(Map paramMap, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$filterKeys");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    LinkedHashMap localLinkedHashMap = new LinkedHashMap();
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramMap.next();
      if (((Boolean)paramFunction1.invoke(localEntry.getKey())).booleanValue()) {
        localLinkedHashMap.put(localEntry.getKey(), localEntry.getValue());
      }
    }
    return (Map)localLinkedHashMap;
  }
  
  public static final Map filterNot(Map paramMap, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$filterNot");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    Map localMap = (Map)new LinkedHashMap();
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramMap.next();
      if (!((Boolean)paramFunction1.invoke(localEntry)).booleanValue()) {
        localMap.put(localEntry.getKey(), localEntry.getValue());
      }
    }
    return localMap;
  }
  
  public static final Map filterNotTo(Map paramMap1, Map paramMap2, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramMap1, "$this$filterNotTo");
    Intrinsics.checkParameterIsNotNull(paramMap2, "destination");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    paramMap1 = paramMap1.entrySet().iterator();
    while (paramMap1.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramMap1.next();
      if (!((Boolean)paramFunction1.invoke(localEntry)).booleanValue()) {
        paramMap2.put(localEntry.getKey(), localEntry.getValue());
      }
    }
    return paramMap2;
  }
  
  public static final Map filterTo(Map paramMap1, Map paramMap2, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramMap1, "$this$filterTo");
    Intrinsics.checkParameterIsNotNull(paramMap2, "destination");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    paramMap1 = paramMap1.entrySet().iterator();
    while (paramMap1.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramMap1.next();
      if (((Boolean)paramFunction1.invoke(localEntry)).booleanValue()) {
        paramMap2.put(localEntry.getKey(), localEntry.getValue());
      }
    }
    return paramMap2;
  }
  
  public static final Map filterValues(Map paramMap, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$filterValues");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "predicate");
    LinkedHashMap localLinkedHashMap = new LinkedHashMap();
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramMap.next();
      if (((Boolean)paramFunction1.invoke(localEntry.getValue())).booleanValue()) {
        localLinkedHashMap.put(localEntry.getKey(), localEntry.getValue());
      }
    }
    return (Map)localLinkedHashMap;
  }
  
  private static final Object getOrElse(Map paramMap, Object paramObject, Function0 paramFunction0)
  {
    paramMap = paramMap.get(paramObject);
    if (paramMap != null) {
      return paramMap;
    }
    return paramFunction0.invoke();
  }
  
  public static final Object getOrElseNullable(Map paramMap, Object paramObject, Function0 paramFunction0)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$getOrElseNullable");
    Intrinsics.checkParameterIsNotNull(paramFunction0, "defaultValue");
    Object localObject = paramMap.get(paramObject);
    if ((localObject == null) && (!paramMap.containsKey(paramObject))) {
      return paramFunction0.invoke();
    }
    return localObject;
  }
  
  private static final Object getOrPut(Map paramMap, Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$get");
    return paramMap.get(paramObject);
  }
  
  public static final Object getOrPut(Map paramMap, Object paramObject, Function0 paramFunction0)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$getOrPut");
    Intrinsics.checkParameterIsNotNull(paramFunction0, "defaultValue");
    Object localObject2 = paramMap.get(paramObject);
    Object localObject1 = localObject2;
    if (localObject2 == null)
    {
      localObject1 = paramFunction0.invoke();
      paramMap.put(paramObject, localObject1);
    }
    return localObject1;
  }
  
  public static final Object getValue(Map paramMap, Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$getValue");
    return MapsKt__MapWithDefaultKt.getOrImplicitDefaultNullable(paramMap, paramObject);
  }
  
  private static final HashMap hashMapOf()
  {
    return new HashMap();
  }
  
  public static final HashMap hashMapOf(Pair... paramVarArgs)
  {
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "pairs");
    HashMap localHashMap = new HashMap(mapCapacity(paramVarArgs.length));
    putAll((Map)localHashMap, paramVarArgs);
    return localHashMap;
  }
  
  private static final Object ifEmpty(Map paramMap, Function0 paramFunction0)
  {
    if (paramMap.isEmpty()) {
      return paramFunction0.invoke();
    }
    return paramMap;
  }
  
  private static final boolean isNotEmpty(Map paramMap)
  {
    return paramMap.isEmpty() ^ true;
  }
  
  private static final boolean isNullOrEmpty(Map paramMap)
  {
    return (paramMap == null) || (paramMap.isEmpty());
  }
  
  private static final Iterator iterator(Map paramMap)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$iterator");
    return paramMap.entrySet().iterator();
  }
  
  private static final LinkedHashMap linkedMapOf()
  {
    return new LinkedHashMap();
  }
  
  public static final LinkedHashMap linkedMapOf(Pair... paramVarArgs)
  {
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "pairs");
    return (LinkedHashMap)toMap(paramVarArgs, (Map)new LinkedHashMap(mapCapacity(paramVarArgs.length)));
  }
  
  public static final int mapCapacity(int paramInt)
  {
    if (paramInt < 3) {
      return paramInt + 1;
    }
    if (paramInt < 1073741824) {
      return paramInt + paramInt / 3;
    }
    return Integer.MAX_VALUE;
  }
  
  public static final Map mapKeys(Map paramMap, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$mapKeys");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "transform");
    Map localMap = (Map)new LinkedHashMap(mapCapacity(paramMap.size()));
    paramMap = ((Iterable)paramMap.entrySet()).iterator();
    while (paramMap.hasNext())
    {
      Object localObject = paramMap.next();
      localMap.put(paramFunction1.invoke(localObject), ((Map.Entry)localObject).getValue());
    }
    return localMap;
  }
  
  public static final Map mapKeysTo(Map paramMap1, Map paramMap2, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramMap1, "$this$mapKeysTo");
    Intrinsics.checkParameterIsNotNull(paramMap2, "destination");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "transform");
    paramMap1 = ((Iterable)paramMap1.entrySet()).iterator();
    while (paramMap1.hasNext())
    {
      Object localObject = paramMap1.next();
      paramMap2.put(paramFunction1.invoke(localObject), ((Map.Entry)localObject).getValue());
    }
    return paramMap2;
  }
  
  private static final Map mapOf()
  {
    return emptyMap();
  }
  
  public static final Map mapOf(Pair... paramVarArgs)
  {
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "pairs");
    if (paramVarArgs.length > 0) {
      return toMap(paramVarArgs, (Map)new LinkedHashMap(mapCapacity(paramVarArgs.length)));
    }
    return emptyMap();
  }
  
  public static final Map mapValues(Map paramMap, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$mapValues");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "transform");
    Map localMap = (Map)new LinkedHashMap(mapCapacity(paramMap.size()));
    paramMap = ((Iterable)paramMap.entrySet()).iterator();
    while (paramMap.hasNext())
    {
      Object localObject = paramMap.next();
      localMap.put(((Map.Entry)localObject).getKey(), paramFunction1.invoke(localObject));
    }
    return localMap;
  }
  
  public static final Map mapValuesTo(Map paramMap1, Map paramMap2, Function1 paramFunction1)
  {
    Intrinsics.checkParameterIsNotNull(paramMap1, "$this$mapValuesTo");
    Intrinsics.checkParameterIsNotNull(paramMap2, "destination");
    Intrinsics.checkParameterIsNotNull(paramFunction1, "transform");
    paramMap1 = ((Iterable)paramMap1.entrySet()).iterator();
    while (paramMap1.hasNext())
    {
      Object localObject = paramMap1.next();
      paramMap2.put(((Map.Entry)localObject).getKey(), paramFunction1.invoke(localObject));
    }
    return paramMap2;
  }
  
  public static final Map minus(Map paramMap, Iterable paramIterable)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$minus");
    Intrinsics.checkParameterIsNotNull(paramIterable, "keys");
    paramMap = toMutableMap(paramMap);
    CollectionsKt__MutableCollectionsKt.removeAll((Collection)paramMap.keySet(), paramIterable);
    return optimizeReadOnlyMap(paramMap);
  }
  
  public static final Map minus(Map paramMap, Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$minus");
    paramMap = toMutableMap(paramMap);
    paramMap.remove(paramObject);
    return optimizeReadOnlyMap(paramMap);
  }
  
  public static final Map minus(Map paramMap, Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$minus");
    Intrinsics.checkParameterIsNotNull(paramSequence, "keys");
    paramMap = toMutableMap(paramMap);
    CollectionsKt__MutableCollectionsKt.removeAll((Collection)paramMap.keySet(), paramSequence);
    return optimizeReadOnlyMap(paramMap);
  }
  
  public static final Map minus(Map paramMap, Object[] paramArrayOfObject)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$minus");
    Intrinsics.checkParameterIsNotNull(paramArrayOfObject, "keys");
    paramMap = toMutableMap(paramMap);
    CollectionsKt__MutableCollectionsKt.removeAll((Collection)paramMap.keySet(), paramArrayOfObject);
    return optimizeReadOnlyMap(paramMap);
  }
  
  private static final void minusAssign(Map paramMap, Iterable paramIterable)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$minusAssign");
    CollectionsKt__MutableCollectionsKt.removeAll((Collection)paramMap.keySet(), paramIterable);
  }
  
  private static final void minusAssign(Map paramMap, Object paramObject)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$minusAssign");
    paramMap.remove(paramObject);
  }
  
  private static final void minusAssign(Map paramMap, Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$minusAssign");
    CollectionsKt__MutableCollectionsKt.removeAll((Collection)paramMap.keySet(), paramSequence);
  }
  
  private static final void minusAssign(Map paramMap, Object[] paramArrayOfObject)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$minusAssign");
    CollectionsKt__MutableCollectionsKt.removeAll((Collection)paramMap.keySet(), paramArrayOfObject);
  }
  
  private static final Iterator mutableIterator(Map paramMap)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$iterator");
    return paramMap.entrySet().iterator();
  }
  
  private static final Map mutableMapOf()
  {
    return (Map)new LinkedHashMap();
  }
  
  public static final Map mutableMapOf(Pair... paramVarArgs)
  {
    Intrinsics.checkParameterIsNotNull(paramVarArgs, "pairs");
    Map localMap = (Map)new LinkedHashMap(mapCapacity(paramVarArgs.length));
    putAll(localMap, paramVarArgs);
    return localMap;
  }
  
  public static final Map optimizeReadOnlyMap(Map paramMap)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$optimizeReadOnlyMap");
    int i = paramMap.size();
    if (i != 0)
    {
      if (i != 1) {
        return paramMap;
      }
      return MapsKt__MapsJVMKt.toSingletonMap(paramMap);
    }
    return emptyMap();
  }
  
  private static final Map orEmpty(Map paramMap)
  {
    if (paramMap != null) {
      return paramMap;
    }
    return emptyMap();
  }
  
  public static final Map plus(Map paramMap, Iterable paramIterable)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$plus");
    Intrinsics.checkParameterIsNotNull(paramIterable, "pairs");
    if (paramMap.isEmpty()) {
      return toMap(paramIterable);
    }
    paramMap = (Map)new LinkedHashMap(paramMap);
    putAll(paramMap, paramIterable);
    return paramMap;
  }
  
  public static final Map plus(Map paramMap1, Map paramMap2)
  {
    Intrinsics.checkParameterIsNotNull(paramMap1, "$this$plus");
    Intrinsics.checkParameterIsNotNull(paramMap2, "map");
    paramMap1 = new LinkedHashMap(paramMap1);
    paramMap1.putAll(paramMap2);
    return (Map)paramMap1;
  }
  
  public static final Map plus(Map paramMap, Pair paramPair)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$plus");
    Intrinsics.checkParameterIsNotNull(paramPair, "pair");
    if (paramMap.isEmpty()) {
      return MapsKt__MapsJVMKt.mapOf(paramPair);
    }
    paramMap = new LinkedHashMap(paramMap);
    paramMap.put(paramPair.getFirst(), paramPair.getSecond());
    return (Map)paramMap;
  }
  
  public static final Map plus(Map paramMap, Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$plus");
    Intrinsics.checkParameterIsNotNull(paramSequence, "pairs");
    paramMap = (Map)new LinkedHashMap(paramMap);
    putAll(paramMap, paramSequence);
    return optimizeReadOnlyMap(paramMap);
  }
  
  public static final Map plus(Map paramMap, Pair[] paramArrayOfPair)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$plus");
    Intrinsics.checkParameterIsNotNull(paramArrayOfPair, "pairs");
    if (paramMap.isEmpty()) {
      return toMap(paramArrayOfPair);
    }
    paramMap = (Map)new LinkedHashMap(paramMap);
    putAll(paramMap, paramArrayOfPair);
    return paramMap;
  }
  
  private static final void plus(Map paramMap, Object paramObject1, Object paramObject2)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$set");
    paramMap.put(paramObject1, paramObject2);
  }
  
  private static final void plusAssign(Map paramMap, Iterable paramIterable)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$plusAssign");
    putAll(paramMap, paramIterable);
  }
  
  private static final void plusAssign(Map paramMap1, Map paramMap2)
  {
    Intrinsics.checkParameterIsNotNull(paramMap1, "$this$plusAssign");
    paramMap1.putAll(paramMap2);
  }
  
  private static final void plusAssign(Map paramMap, Pair paramPair)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$plusAssign");
    paramMap.put(paramPair.getFirst(), paramPair.getSecond());
  }
  
  private static final void plusAssign(Map paramMap, Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$plusAssign");
    putAll(paramMap, paramSequence);
  }
  
  private static final void plusAssign(Map paramMap, Pair[] paramArrayOfPair)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$plusAssign");
    putAll(paramMap, paramArrayOfPair);
  }
  
  public static final void putAll(Map paramMap, Iterable paramIterable)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$putAll");
    Intrinsics.checkParameterIsNotNull(paramIterable, "pairs");
    paramIterable = paramIterable.iterator();
    while (paramIterable.hasNext())
    {
      Pair localPair = (Pair)paramIterable.next();
      paramMap.put(localPair.component1(), localPair.component2());
    }
  }
  
  public static final void putAll(Map paramMap, Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$putAll");
    Intrinsics.checkParameterIsNotNull(paramSequence, "pairs");
    paramSequence = paramSequence.iterator();
    while (paramSequence.hasNext())
    {
      Pair localPair = (Pair)paramSequence.next();
      paramMap.put(localPair.component1(), localPair.component2());
    }
  }
  
  public static final void putAll(Map paramMap, Pair[] paramArrayOfPair)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$putAll");
    Intrinsics.checkParameterIsNotNull(paramArrayOfPair, "pairs");
    int j = paramArrayOfPair.length;
    int i = 0;
    while (i < j)
    {
      Pair localPair = paramArrayOfPair[i];
      paramMap.put(localPair.component1(), localPair.component2());
      i += 1;
    }
  }
  
  private static final Object remove(Map paramMap, Object paramObject)
  {
    if (paramMap != null) {
      return TypeIntrinsics.asMutableMap(paramMap).remove(paramObject);
    }
    throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.MutableMap<K, V>");
  }
  
  public static final Map toMap(Iterable paramIterable)
  {
    Intrinsics.checkParameterIsNotNull(paramIterable, "$this$toMap");
    if ((paramIterable instanceof Collection))
    {
      Collection localCollection = (Collection)paramIterable;
      int i = localCollection.size();
      if (i != 0)
      {
        if (i != 1) {
          return toMap(paramIterable, (Map)new LinkedHashMap(mapCapacity(localCollection.size())));
        }
        if ((paramIterable instanceof List)) {
          paramIterable = ((List)paramIterable).get(0);
        } else {
          paramIterable = paramIterable.iterator().next();
        }
        return MapsKt__MapsJVMKt.mapOf((Pair)paramIterable);
      }
      return emptyMap();
    }
    return optimizeReadOnlyMap(toMap(paramIterable, (Map)new LinkedHashMap()));
  }
  
  public static final Map toMap(Iterable paramIterable, Map paramMap)
  {
    Intrinsics.checkParameterIsNotNull(paramIterable, "$this$toMap");
    Intrinsics.checkParameterIsNotNull(paramMap, "destination");
    putAll(paramMap, paramIterable);
    return paramMap;
  }
  
  public static final Map toMap(Map paramMap)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$toMap");
    int i = paramMap.size();
    if (i != 0)
    {
      if (i != 1) {
        return toMutableMap(paramMap);
      }
      return MapsKt__MapsJVMKt.toSingletonMap(paramMap);
    }
    return emptyMap();
  }
  
  public static final Map toMap(Map paramMap1, Map paramMap2)
  {
    Intrinsics.checkParameterIsNotNull(paramMap1, "$this$toMap");
    Intrinsics.checkParameterIsNotNull(paramMap2, "destination");
    paramMap2.putAll(paramMap1);
    return paramMap2;
  }
  
  public static final Map toMap(Sequence paramSequence)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$toMap");
    return optimizeReadOnlyMap(toMap(paramSequence, (Map)new LinkedHashMap()));
  }
  
  public static final Map toMap(Sequence paramSequence, Map paramMap)
  {
    Intrinsics.checkParameterIsNotNull(paramSequence, "$this$toMap");
    Intrinsics.checkParameterIsNotNull(paramMap, "destination");
    putAll(paramMap, paramSequence);
    return paramMap;
  }
  
  public static final Map toMap(Pair[] paramArrayOfPair)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfPair, "$this$toMap");
    int i = paramArrayOfPair.length;
    if (i != 0)
    {
      if (i != 1) {
        return toMap(paramArrayOfPair, (Map)new LinkedHashMap(mapCapacity(paramArrayOfPair.length)));
      }
      return MapsKt__MapsJVMKt.mapOf(paramArrayOfPair[0]);
    }
    return emptyMap();
  }
  
  public static final Map toMap(Pair[] paramArrayOfPair, Map paramMap)
  {
    Intrinsics.checkParameterIsNotNull(paramArrayOfPair, "$this$toMap");
    Intrinsics.checkParameterIsNotNull(paramMap, "destination");
    putAll(paramMap, paramArrayOfPair);
    return paramMap;
  }
  
  public static final Map toMutableMap(Map paramMap)
  {
    Intrinsics.checkParameterIsNotNull(paramMap, "$this$toMutableMap");
    return (Map)new LinkedHashMap(paramMap);
  }
  
  private static final Pair toPair(Map.Entry paramEntry)
  {
    return new Pair(paramEntry.getKey(), paramEntry.getValue());
  }
}
