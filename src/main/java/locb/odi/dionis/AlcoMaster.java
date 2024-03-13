package locb.odi.dionis;

import locb.odi.dionis.service.AlcoService;
import locb.odi.dionis.model.Drinker;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class AlcoMaster {

    final int DRINK_LIMIT = 10;
    final int TIME_LIMIT = 60*20;

    private final Dionis pl = Dionis.getInstance();

    private HashMap<UUID, Drinker> drinkerMap = new HashMap<>();

    private HashMap<UUID, Integer> alcoholicTasks = new HashMap<>();
    private HashMap<UUID, Integer> treatedTasks = new HashMap<>();


    private AlcoService alcoService = new AlcoService();

    public HashMap<UUID, Drinker> getDrinkerMap() {
        return drinkerMap;
    }


    public AlcoMaster() {

    }

    public void readFromStorage(){

    }

    public void checkPlayer(Player player) {

        alcoService.getDrinker(player.getUniqueId(), drinker -> {
            if(drinker != null)
            {
                if(isActual(drinker) || isAlcoholic(drinker)) {
                    if (!drinkerMap.containsKey(player.getUniqueId())) {
                        drinkerMap.put(player.getUniqueId(), drinker);
                    }
                    if(isAlcoholic(drinker)) {
                        if(!alcoholicTasks.containsKey(player.getUniqueId())) {
                            alcoholismStart(drinker);
                        }
                    }
                }
                else {
                    if (drinkerMap.containsKey(player.getUniqueId())) {
                        drinkerMap.remove(player.getUniqueId());
                    }
                    alcoService.removeDrinker(player, aBoolean -> {});
                }

            }
        });
//        System.out.println(drinkerMap);
    }

    private boolean isAlcoholic(Drinker drinker) {
//        System.out.println("IS_ALCOHOLIC");
//        System.out.println(drinker.getCount());
        if(drinker.getCount() >= DRINK_LIMIT ) {
            return true;
        }
        else return false;
    }

    private boolean isActual(Drinker drinker){
        OfflinePlayer player = Bukkit.getOfflinePlayer(drinker.getUuid());
//        System.out.println("IS_ACTUAL");
//        System.out.println((player.getStatistic(Statistic.TOTAL_WORLD_TIME) - drinker.getTime()) / 20);
        if ((player.getStatistic(Statistic.TOTAL_WORLD_TIME) - drinker.getTime()) >= 20 * TIME_LIMIT) {
            return false;
        }
        return true;
    }


    public void takeDrink(Player player) {
        Drinker drinker;
        if (drinkerMap.containsKey(player.getUniqueId())) {
            drinker = drinkerMap.get(player.getUniqueId());

            if (drinker.getCount() >= 1 && drinker.getCount() < DRINK_LIMIT-1) {
                if (isActual(drinker)) {
                    drinker.setCount(drinker.getCount() + 1);

                } else {
                    drinker.setCount(1);
                    drinker.setTime(player.getStatistic(Statistic.TOTAL_WORLD_TIME));
                }
            }
            else {
                drinker.setCount(drinker.getCount() + 1);
                drinker.setTime(player.getStatistic(Statistic.TOTAL_WORLD_TIME));
                if (drinker.getCount() == DRINK_LIMIT) {
                    player.sendMessage(Messages.getPrefix() + Messages.getNotice("messages.sinyak_on"));
                }
                takeAwaySquirrel(player);

                alcoholismStart(drinker);
            }
        } else {
            drinker = new Drinker(player.getUniqueId(), player.getName(), player.getStatistic(Statistic.TOTAL_WORLD_TIME), 1);
            drinkerMap.put(player.getUniqueId(), drinker);
        }
        alcoService.addOrUpdateDrinker(drinker);
//        System.out.println(drinkerMap.keySet());
//        System.out.println(drinkerMap);
    }


    public void takeMedicine(Player player) {

        if (drinkerMap.containsKey(player.getUniqueId())) {
            Drinker drinker = drinkerMap.get(player.getUniqueId());
            if (drinker.getCount() >= DRINK_LIMIT) {
                player.getWorld().spawnParticle(Particle.ELECTRIC_SPARK, player.getLocation(), 32, 0.6, 0.8, 0.6);
                AtomicInteger c = new AtomicInteger();

                int i = Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, () -> {
                    if((c.get() < 2 || c.get() % 2 == 0) && !(c.get() > 7) ) {
                        player.damage(1);
                    }
                    if(c.get() < 8 ) {
                        player.getWorld().spawnParticle(Particle.BUBBLE_POP, player.getLocation(), 32, 0.6, 0.8, 0.6);
                    }

                    if(c.get() == 9) {
                        player.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, player.getLocation(), 32, 0.6, 0.8, 0.6);
                        player.sendMessage(Messages.getPrefix() + Messages.getNotice("messages.sinyak_off"));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 20*5, 0, false, true));
                        Bukkit.getScheduler().cancelTask(treatedTasks.get(player.getUniqueId()));
                        takeAwaySquirrel(player);
                    }
                    c.getAndIncrement();
                }, 40L, 20L);
                treatedTasks.put(player.getUniqueId(), i);

                drinkerMap.remove(player.getUniqueId());
                alcoService.removeDrinker(player, (res) -> {});
            }
        }
    }

    public void alcoholismStart(Drinker drinker){
        Player player = Bukkit.getPlayer(drinker.getUuid());


        int alcoScheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(pl, () -> {
            if (!player.isOnline()) {
                Bukkit.getScheduler().cancelTask(alcoholicTasks.get(drinker.getUuid()));
                alcoholicTasks.remove(player.getUniqueId());
                return;
            }

            if(!isActual(drinker)) {
                giveSquirrel(player);
                Bukkit.getScheduler().cancelTask(alcoholicTasks.get(player.getUniqueId()));
                alcoholicTasks.remove(player.getUniqueId());
            }

        }, 0, 20L);
        alcoholicTasks.put(player.getUniqueId(), alcoScheduler);
    }

    public void giveSquirrel(Player player) {
        if(Bukkit.getPlayer(player.getUniqueId()) != null) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, Integer.MAX_VALUE, 0, false, false));
        }
    }

    public void takeAwaySquirrel(Player player) {
        if (player.hasPotionEffect(PotionEffectType.DARKNESS)) {
            player.removePotionEffect(PotionEffectType.DARKNESS);
        }
        if(alcoholicTasks.containsKey(player.getUniqueId())) {
            Bukkit.getScheduler().cancelTask(alcoholicTasks.get(player.getUniqueId()));
            alcoholicTasks.remove(player.getUniqueId());
        }
    }

    public void finishedWork() {
        alcoService.close();
    }
}