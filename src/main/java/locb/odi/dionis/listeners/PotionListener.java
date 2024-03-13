package locb.odi.dionis.listeners;

import locb.odi.dionis.AlcoMaster;
import locb.odi.dionis.Dionis;
import locb.odi.dionis.craft.CraftMaster;
import locb.odi.dionis.craft.Drink;
import locb.odi.dionis.craft.DrinkEffect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.Map;

public class PotionListener implements Listener {

    private final Dionis pl;
    private final AlcoMaster alcoMaster;

    private final Map<String, Drink> drinksMap = CraftMaster.getDrinksMap();

    public PotionListener(Dionis pl) {
        this.pl = pl;
        this.alcoMaster = pl.getAlcoMaster();
    }

    @EventHandler
    public void onPotionSplash(PlayerItemConsumeEvent e) {

        if(e.getItem().getType() == Material.POTION){
            for(String title : drinksMap.keySet()) {
                if(e.getItem().getItemMeta().getDisplayName().equals(title)) {
                    Player p = e.getPlayer();
                    Drink drink = drinksMap.get(title);
                    for( DrinkEffect drinkEffect : drink.getDrinkEffects()) {
                        drinkEffect.addPlayerEffect(p);
                    }
                    alcoMaster.takeDrink(e.getPlayer());
                }
            }
            if(e.getItem().getItemMeta().getDisplayName().equals("Винозельце")) {
                alcoMaster.takeMedicine(e.getPlayer());
            }
        }
        /*if (e.getEntity().getShooter() instanceof Player){

            for(String title : driksMap.keySet()) {
                if(e.getPotion().getItem().getItemMeta().getDisplayName().equals(title)) {
                    System.out.println(title);
                }
            }


        }*/

    }
}
