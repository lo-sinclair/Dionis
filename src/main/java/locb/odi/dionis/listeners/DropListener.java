package locb.odi.dionis.listeners;

import locb.odi.dionis.Dionis;
import locb.odi.dionis.craft.Ingredient;
import locb.odi.dionis.tools.Tools;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerHarvestBlockEvent;
import org.bukkit.inventory.ItemStack;

public class DropListener implements Listener {

    private final Dionis pl;


    public DropListener(Dionis pl) {
        this.pl = pl;
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = (Player) e.getPlayer();
        World world = p.getWorld();
        Block block = e.getBlock();

        // Лунный сахар
        if( e.getBlock().getType() == Material.SUGAR_CANE ) {
            long days = world.getFullTime()/24000;
            if ( (days%8) == 0 )  {
                long nowTime = world.getTime();
                if( (nowTime > 13000 && nowTime < 23000) ) {

                    if(Tools.isChans(20)) {
                        ItemStack item = Ingredient.MOON_SUGAR.getItemStack();
//                        e.setDropItems(false);
//                        block.getDrops(item).clear();
                        world.dropItemNaturally(e.getBlock().getLocation(), item);
                     }
                }
            }
        }

        // Раскидистый папоротник и Цветущий папоротник
        if(e.getBlock().getType() == Material.LARGE_FERN) {

            if(e.getPlayer().getEquipment().getItemInMainHand().getType().equals(Material.SHEARS)) {

                long days = world.getFullTime() / 24000;
                if ((days % 8) == 4) {
                    long nowTime = world.getTime();

                    if ((nowTime > 13000 && nowTime < 23000)) {
                        if (Tools.isChans(5)) {
                            ItemStack item = new ItemStack(Material.LARGE_FERN);
                            world.dropItemNaturally(e.getBlock().getLocation(), item);
                            e.setDropItems(false);
                        }
                    }
                }
                if ((days % 8) == 0) {
                    long nowTime = world.getTime();
                    if ((nowTime > 13000 && nowTime < 23000)) {
                        if (Tools.isChans(2)) {
                            ItemStack item = Ingredient.BLOOMING_FERN.getItemStack();
                            world.dropItemNaturally(e.getBlock().getLocation(), item);
                            e.setDropItems(false);
                        }
                    }
                }
            }
        }

        // Высокая трава и Трынь-трава
        if(e.getBlock().getType() == Material.TALL_GRASS) {
            ItemStack itemInMainHand = e.getPlayer().getEquipment().getItemInMainHand();
            if(itemInMainHand.getType() == Material.SHEARS ) {
                if( itemInMainHand.containsEnchantment(Enchantment.SILK_TOUCH) ){
                    ItemStack item = new ItemStack(Material.TALL_GRASS);
                    world.dropItemNaturally(e.getBlock().getLocation(), item);
                    e.setDropItems(false);
                } else {
                    long days = world.getFullTime() / 24000;
                    if ((days % 8) == 2 || (days % 8) == 6 ) {
                        long nowTime = world.getTime();
                        if ((nowTime > 13000 && nowTime < 23000)) {
                            if (Tools.isChans(20)) {
                                ItemStack item = Ingredient.TRIN_TRAVA.getItemStack();
                                world.dropItemNaturally(e.getBlock().getLocation(), item);
                                e.setDropItems(false);
                            }
                        }
                    }
                }
            }
        }

        // Малая бросянка
        if(e.getBlock().getType() == Material.BIG_DRIPLEAF) {
            long nowTime = world.getTime();
            if ((nowTime > 6000 - 20*60 && nowTime < 6000 + 20*60)) {
                if(Tools.isChans(10)) {
                    ItemStack item = new ItemStack(Material.SMALL_DRIPLEAF);
                    world.dropItemNaturally(e.getBlock().getLocation(), item);
                    e.setDropItems(false);
                }
            }
        }
    }


    private void deleteSecondBlock(BlockBreakEvent e, Material type) {
        World world = e.getPlayer().getWorld();
        Location loc = e.getBlock().getLocation();
        Location topLoc = new Location(e.getBlock().getWorld(), loc.getBlockX(), loc.getBlockY()+1, loc.getBlockZ());
        Location bottomLoc = new Location(e.getBlock().getWorld(), loc.getBlockX(), loc.getBlockY()-1, loc.getBlockZ());
        if (world.getBlockAt(topLoc).getType() == type) {
            world.getBlockAt(topLoc).setType(Material.AIR);
        }
        if (world.getBlockAt(bottomLoc).getType() == type) {
            world.getBlockAt(bottomLoc).setType(Material.AIR);
        }
    }


    @EventHandler
    public void onHarvestBreak(PlayerHarvestBlockEvent e) {
        // Пьяные ягоды
        if(e.getHarvestedBlock().getType() == Material.SWEET_BERRY_BUSH) {
            if(Tools.isChans(10)) {
                Player p = (Player) e.getPlayer();
                World world = p.getWorld();
                ItemStack item = Ingredient.DRUNK_BERRIES.getItemStack();
                world.dropItemNaturally(e.getHarvestedBlock().getLocation(), item);

            }
        }
    }

}
