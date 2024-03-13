package locb.odi.dionis.service;

import locb.odi.dionis.Dionis;
import locb.odi.dionis.data.Database;
import locb.odi.dionis.data.SQLiteDatabase;
import locb.odi.dionis.model.Drinker;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class AlcoService {

    private final Database db;


    public AlcoService(){
        try {
            db = new SQLiteDatabase(new File(Dionis.getInstance().getDataFolder(), "database.db"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void AddDrinker(String pname) {
        /*Drinker drinker = Drinker.create(pname);
        async(() -> {
            db.saveDrinker(drinker);
        });*/
    }

    public void addOrUpdateDrinker(Drinker drinker) {
        async(() -> {
            db.saveDrinker(drinker);
        });
    }

    public void removeDrinker(Player player, Consumer<Boolean> callback) {
        async(() -> {
            boolean result = db.deleteDrinker(player.getUniqueId());
            sync( () -> callback.accept((result)) );
        });
    }


    public void getDrinker(UUID uuid, Consumer<Drinker> callback) {
        async(() -> {
            Drinker drinker = db.findDrinkerByUUD(uuid);
            sync( () -> callback.accept((drinker)) );
        });
    }


    public void async(Runnable runnable){
        Bukkit.getScheduler().runTaskAsynchronously(Dionis.getInstance(), ()-> {
            synchronized (this) {
                runnable.run();
            }
        });
    }

    public void sync(Runnable runnable){
        Bukkit.getScheduler().scheduleSyncDelayedTask(Dionis.getInstance(), runnable);
    }

    public void close(){
        if(db != null) db.close();
    }


    public Database getDb() {
        return db;
    }

}
