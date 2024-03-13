package locb.odi.dionis.listeners;

import locb.odi.dionis.Dionis;
import locb.odi.dionis.craft.Ingredient;
import locb.odi.dionis.tools.Tools;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.TripwireHook;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Attachable;

public class InteractListener implements Listener {

    private final Dionis pl;
    public InteractListener(Dionis pl) {
        this.pl = pl;
    }

    @EventHandler
    public void onRightClick (PlayerInteractEvent e) {
        Player p = (Player) e.getPlayer();
        World world = p.getWorld();
        Block block = e.getClickedBlock();

        //Берёзовый сок
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType() == Material.TRIPWIRE_HOOK) {
            TripwireHook hook = (TripwireHook) (e.getClickedBlock().getBlockData());
            if ( e.getClickedBlock().getRelative(hook.getFacing().getOppositeFace()).getType()== Material.BIRCH_LOG){
                p = e.getPlayer();
                ItemStack itemInMainHand = p.getInventory().getItemInMainHand();
                if(itemInMainHand.getType() == Material.GLASS_BOTTLE){
                    if(itemInMainHand.getAmount()==1){
                        p.getInventory().setItemInMainHand(new ItemStack(Ingredient.BIRCH_JUICE.getItemStack()));
                    }
                    else{
                        p.getInventory().addItem(Ingredient.BIRCH_JUICE.getItemStack());
                        itemInMainHand.setAmount(itemInMainHand.getAmount() - 1);
                    }
                }
            }
        }
    }
}
