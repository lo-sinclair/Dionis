package locb.odi.dionis.data;

import locb.odi.dionis.model.Drinker;

import java.util.List;
import java.util.UUID;

public interface Database {
    //int saveDrinker(Drinker drinker);

    List<Drinker> findAlcoholics();

    void saveDrinker(Drinker drinker);

    Drinker findDrinkerByUUD(UUID uuid);

    boolean deleteDrinker(UUID uuid);

    void close();


}
