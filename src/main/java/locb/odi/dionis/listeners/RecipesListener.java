package locb.odi.dionis.listeners;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class RecipesListener implements Listener {

    @EventHandler
    public void onAnvilInventory(PrepareAnvilEvent e) {
        AnvilInventory anvilInv = (AnvilInventory) e.getInventory();

        if(e.getInventory().getItem(0) != null && e.getInventory().getItem(1) != null ) {
            ItemStack result = e.getInventory().getItem(0).clone();
            if(e.getInventory().getItem(1).getType() == Material.ENCHANTED_BOOK
            && ((EnchantmentStorageMeta) e.getInventory().getItem(1).getItemMeta()).getStoredEnchants().containsKey(Enchantment.SILK_TOUCH)) {
               result.addUnsafeEnchantment(Enchantment.SILK_TOUCH, 1);
               e.setResult(result);
               anvilInv.setRepairCost(3);
            }
        }
    }
}
