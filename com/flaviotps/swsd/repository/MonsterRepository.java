package com.flaviotps.swsd.repository;

import androidx.lifecycle.MutableLiveData;
import com.flaviotps.swsd.enum.MonsterElement;
import com.flaviotps.swsd.model.MonsterModel;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt___CollectionsKt;

@Metadata(bv={1, 0, 3}, d1={"\000\034\n\002\030\002\n\002\030\002\n\002\b\002\n\002\030\002\n\002\020 \n\002\030\002\n\002\b\002\030\000 \0072\0020\001:\001\007B\007\b\002?\006\002\020\002J\022\020\003\032\016\022\n\022\b\022\004\022\0020\0060\0050\004?\006\b"}, d2={"Lcom/flaviotps/swsd/repository/MonsterRepository;", "Lcom/flaviotps/swsd/repository/BaseRepository;", "()V", "getMonsters", "Landroidx/lifecycle/MutableLiveData;", "", "Lcom/flaviotps/swsd/model/MonsterModel;", "Companion", "app_release"}, k=1, mv={1, 1, 16})
public final class MonsterRepository
  extends BaseRepository
{
  public static final Companion Companion = new Companion(null);
  private static MonsterRepository instance;
  
  private MonsterRepository() {}
  
  public final MutableLiveData getMonsters()
  {
    MutableLiveData localMutableLiveData = new MutableLiveData();
    List localList = (List)new ArrayList();
    localList.add(new MonsterModel("Bearman", "", MonsterElement.WATER, 3, "bearman_water"));
    localList.add(new MonsterModel("Garuda", "", MonsterElement.WATER, 2, "garuda_water"));
    localList.add(new MonsterModel("Golem", "", MonsterElement.WATER, 3, "golem_water"));
    localList.add(new MonsterModel("Griffon", "", MonsterElement.WATER, 3, "griffon_water"));
    localList.add(new MonsterModel("Grim Reaper", "", MonsterElement.WATER, 3, "gr_water"));
    localList.add(new MonsterModel("Harpy", "", MonsterElement.WATER, 3, "harpy_water"));
    localList.add(new MonsterModel("Hellhound", "", MonsterElement.WATER, 2, "hellhound_water"));
    localList.add(new MonsterModel("Imp", "", MonsterElement.WATER, 2, "imp_water"));
    localList.add(new MonsterModel("Inferno", "", MonsterElement.WATER, 3, "inferno_water"));
    localList.add(new MonsterModel("Magical Archer", "", MonsterElement.WATER, 3, "ma_water"));
    localList.add(new MonsterModel("Pixie", "", MonsterElement.WATER, 2, "pixie_water"));
    localList.add(new MonsterModel("Salamander", "", MonsterElement.WATER, 2, "salamander_water"));
    localList.add(new MonsterModel("Serpent", "", MonsterElement.WATER, 3, "serpent_water"));
    localList.add(new MonsterModel("Vagabond", "", MonsterElement.WATER, 2, "vagabond_water"));
    localList.add(new MonsterModel("Viking", "", MonsterElement.WATER, 2, "viking_water"));
    localList.add(new MonsterModel("Werewolf", "", MonsterElement.WATER, 3, "werewolf_water"));
    localList.add(new MonsterModel("Bearman", "", MonsterElement.FIRE, 3, "bearman_fire"));
    localList.add(new MonsterModel("Beast Hunter ", "", MonsterElement.FIRE, 3, "bh_fire"));
    localList.add(new MonsterModel("Fairy ", "", MonsterElement.FIRE, 2, "fairy_fire"));
    localList.add(new MonsterModel("Garuda ", "", MonsterElement.FIRE, 2, "garuda_fire"));
    localList.add(new MonsterModel("Griffon ", "", MonsterElement.FIRE, 3, "griffon_fire"));
    localList.add(new MonsterModel("Harpu ", "", MonsterElement.FIRE, 2, "harpu_fire"));
    localList.add(new MonsterModel("Harpy ", "", MonsterElement.FIRE, 3, "harpy_fire"));
    localList.add(new MonsterModel("Howl ", "", MonsterElement.FIRE, 2, "howl_fire"));
    localList.add(new MonsterModel("Imp ", "", MonsterElement.FIRE, 2, "imp_fire"));
    localList.add(new MonsterModel("Magical Archer ", "", MonsterElement.FIRE, 3, "ma_fire"));
    localList.add(new MonsterModel("Pixie ", "", MonsterElement.FIRE, 2, "pixie_fire"));
    localList.add(new MonsterModel("Serpent ", "", MonsterElement.FIRE, 3, "serpent_fire"));
    localList.add(new MonsterModel("Vagabond ", "", MonsterElement.FIRE, 2, "vagabond_fire"));
    localList.add(new MonsterModel("Viking ", "", MonsterElement.FIRE, 2, "viking_fire"));
    localList.add(new MonsterModel("Yeti ", "", MonsterElement.FIRE, 2, "yeti_fire"));
    localList.add(new MonsterModel("Bearman", "", MonsterElement.WIND, 3, "bearman_wind"));
    localList.add(new MonsterModel("Fairy", "", MonsterElement.WIND, 2, "fairy_wind"));
    localList.add(new MonsterModel("Golem", "", MonsterElement.WIND, 3, "golem_wind"));
    localList.add(new MonsterModel("Harpu", "", MonsterElement.WIND, 2, "harpu_wind"));
    localList.add(new MonsterModel("Hellhound ", "", MonsterElement.WIND, 2, "hellhound_wind"));
    localList.add(new MonsterModel("Howl ", "", MonsterElement.WIND, 2, "howl_wind"));
    localList.add(new MonsterModel("Imp ", "", MonsterElement.WIND, 2, "imp_wind"));
    localList.add(new MonsterModel("Inferno ", "", MonsterElement.WIND, 3, "inferno_wind"));
    localList.add(new MonsterModel("Lizardman ", "", MonsterElement.WIND, 3, "lizardman_wind"));
    localList.add(new MonsterModel("Magical Archer ", "", MonsterElement.WIND, 3, "ma_wind"));
    localList.add(new MonsterModel("Pixie ", "", MonsterElement.WIND, 2, "pixie_wind"));
    localList.add(new MonsterModel("Viking ", "", MonsterElement.WIND, 2, "viking_wind"));
    localList.add(new MonsterModel("Warbear ", "", MonsterElement.WIND, 2, "warbear_wind"));
    localList.add(new MonsterModel("Werewolf ", "", MonsterElement.WIND, 3, "werewolf_wind"));
    localList.add(new MonsterModel("Yeti ", "", MonsterElement.WIND, 2, "yeti_wind"));
    localList.add(new MonsterModel("Bearman", "", MonsterElement.LIGHT, 3, "bearman_light"));
    localList.add(new MonsterModel("Cow Girl", "", MonsterElement.LIGHT, 3, "cg_light"));
    localList.add(new MonsterModel("Fairy ", "", MonsterElement.LIGHT, 3, "fairy_light"));
    localList.add(new MonsterModel("Harpu ", "", MonsterElement.LIGHT, 2, "harpu_light"));
    localList.add(new MonsterModel("Hellhound ", "", MonsterElement.LIGHT, 2, "hellhound_light"));
    localList.add(new MonsterModel("Howl ", "", MonsterElement.LIGHT, 2, "howl_light"));
    localList.add(new MonsterModel("Imp ", "", MonsterElement.LIGHT, 2, "imp_light"));
    localList.add(new MonsterModel("Inugami ", "", MonsterElement.LIGHT, 3, "inugami_light"));
    localList.add(new MonsterModel("Pixie ", "", MonsterElement.LIGHT, 2, "pixie_light"));
    localList.add(new MonsterModel("Salamander ", "", MonsterElement.LIGHT, 2, "salamander_light"));
    localList.add(new MonsterModel("Vagabond ", "", MonsterElement.LIGHT, 2, "vagabond_light"));
    localList.add(new MonsterModel("Viking ", "", MonsterElement.LIGHT, 2, "viking_light"));
    localList.add(new MonsterModel("Warbear ", "", MonsterElement.LIGHT, 2, "warbear_light"));
    localList.add(new MonsterModel("Yeti ", "", MonsterElement.LIGHT, 2, "yeti_light"));
    localList.add(new MonsterModel("Bearman", "", MonsterElement.DARK, 3, "bearman_dark"));
    localList.add(new MonsterModel("Fairy ", "", MonsterElement.DARK, 2, "fairy_light"));
    localList.add(new MonsterModel("Garuda ", "", MonsterElement.DARK, 2, "garuda_dark"));
    localList.add(new MonsterModel("Harpu ", "", MonsterElement.DARK, 2, "harpu_dark"));
    localList.add(new MonsterModel("Hellhound ", "", MonsterElement.DARK, 2, "hellhound_dark"));
    localList.add(new MonsterModel("Howl ", "", MonsterElement.DARK, 2, "howl_dark"));
    localList.add(new MonsterModel("Imp ", "", MonsterElement.DARK, 2, "imp_dark"));
    localList.add(new MonsterModel("Inugami ", "", MonsterElement.DARK, 3, "inugami_dark"));
    localList.add(new MonsterModel("Pixie ", "", MonsterElement.DARK, 2, "pixie_dark"));
    localList.add(new MonsterModel("Salamander ", "", MonsterElement.DARK, 2, "salamander_dark"));
    localList.add(new MonsterModel("Vagabond ", "", MonsterElement.DARK, 2, "vagabond_dark"));
    localList.add(new MonsterModel("Viking ", "", MonsterElement.DARK, 2, "viking_dark"));
    localList.add(new MonsterModel("Warbear ", "", MonsterElement.DARK, 2, "warbear_dark"));
    localList.add(new MonsterModel("Yeti ", "", MonsterElement.DARK, 2, "yeti_dark"));
    localMutableLiveData.postValue(CollectionsKt___CollectionsKt.toList((Iterable)localList));
    return localMutableLiveData;
  }
  
  @Metadata(bv={1, 0, 3}, d1={"\000\022\n\002\030\002\n\002\020\000\n\002\b\002\n\002\030\002\n\000\b?\003\030\0002\0020\001B\007\b\002?\006\002\020\002J\006\020\003\032\0020\004R\020\020\003\032\004\030\0010\004X?\016?\006\002\n\000?\006\005"}, d2={"Lcom/flaviotps/swsd/repository/MonsterRepository$Companion;", "", "()V", "instance", "Lcom/flaviotps/swsd/repository/MonsterRepository;", "app_release"}, k=1, mv={1, 1, 16})
  public static final class Companion
  {
    private Companion() {}
    
    public final MonsterRepository instance()
    {
      if (MonsterRepository.access$getInstance$cp() == null) {
        return new MonsterRepository(null);
      }
      MonsterRepository localMonsterRepository = MonsterRepository.access$getInstance$cp();
      if (localMonsterRepository != null) {
        return localMonsterRepository;
      }
      throw new TypeCastException("null cannot be cast to non-null type com.flaviotps.swsd.repository.MonsterRepository");
    }
  }
}
