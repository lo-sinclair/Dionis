package locb.odi.dionis.listeners;

import locb.odi.dionis.Dionis;
import locb.odi.dionis.craft.Ingredient;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class CraftListener implements Listener {

    private final Dionis pl;

    public CraftListener(Dionis pl) {
        this.pl = pl;
    }

    @EventHandler
    public void onCraft (CraftItemEvent e){
//        System.out.println(e.getRecipe());
//        System.out.println(e.getInventory());
    }


}
