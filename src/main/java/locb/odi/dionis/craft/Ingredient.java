package locb.odi.dionis.craft;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

public enum Ingredient {

    SUSLO(suslo()),
    WATER_SEEDS(waterSeeds()),
    MOON_SUGAR(moonSugar()),
    DRUNK_BERRIES(drunkBerries()),
    WOOD_SALT(woodSalt()),
    BIRCH_JUICE(birchJuice()),
    SLIVKI(slivki()),
    VINOZELCE(vinozelce()),
    BLOOMING_FERN(bloomingfern()),
    TRIN_TRAVA(trintrava());

    private final ItemStack itemStack;


    Ingredient(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }


    public static Ingredient getIngrediet(String str) {
        for (Ingredient ingredient : Ingredient.values()) {
            if (ingredient.name().equalsIgnoreCase(str))
                return ingredient;
        }
        return null;
    }

    private static ItemStack suslo(){
        ItemStack item = new ItemStack(Material.POTION);

        PotionMeta meta = (PotionMeta)item.getItemMeta();
        meta.setDisplayName(ChatColor.RESET+"Сусло");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setColor(Color.fromRGB(16766044));

        item.setItemMeta(meta);

        return item;
    }

    private static ItemStack vinozelce(){
        ItemStack item = new ItemStack(Material.POTION);

        PotionMeta meta = (PotionMeta)item.getItemMeta();
        meta.setDisplayName(ChatColor.RESET+"Винозельце");
        //meta.addEnchant(Enchantment.OXYGEN, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        meta.setColor(Color.fromRGB(5557967));

        item.setItemMeta(meta);

        return item;
    }

    private static ItemStack waterSeeds(){
        ItemStack item = new ItemStack(Material.POTION);

        PotionMeta meta = (PotionMeta)item.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "Вода с семенами");
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setColor(Color.fromRGB(4285087));
        //meta.removeCustomEffect(PotionType.POISON.getEffectType());
        item.setItemMeta(meta);


        return item;
    }

    private static ItemStack moonSugar(){
        ItemStack item = new ItemStack(Material.SUGAR);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "" + ChatColor.WHITE + "Лунный сахар");
        meta.addEnchant(Enchantment.OXYGEN, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);

        return item;
    }

    private static ItemStack drunkBerries(){
        ItemStack item = new ItemStack(Material.SWEET_BERRIES);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "" + ChatColor.WHITE + "Пьяные ягоды");
        meta.addEnchant(Enchantment.OXYGEN, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);

        return item;
    }


    private static ItemStack birchJuice(){
        ItemStack item = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta)item.getItemMeta();
        meta.setDisplayName(ChatColor.RESET+"Берёзовый сок");
        meta.setColor(Color.fromRGB(15120813));
        item.setItemMeta(meta);

        return item;
    }


    private static ItemStack woodSalt(){
        ItemStack item = new ItemStack(Material.SUGAR);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.RESET+"Древесная соль");

        item.setItemMeta(meta);

        return item;
    }

    private static ItemStack slivki(){
        ItemStack item = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta)item.getItemMeta();
        meta.setDisplayName(ChatColor.RESET+"Сливки");
        meta.setColor(Color.fromRGB(16776427));
        item.setItemMeta(meta);

        return item;
    }

    private static ItemStack bloomingfern(){
        ItemStack item = new ItemStack(Material.LARGE_FERN);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "" + ChatColor.WHITE + "Цветущий раскидистый папоротник");
        meta.addEnchant(Enchantment.OXYGEN, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);

        return item;
    }

    private static ItemStack trintrava(){
        ItemStack item = new ItemStack(Material.SHORT_GRASS);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + "" + ChatColor.WHITE + "Трынь-трава");
        meta.addEnchant(Enchantment.OXYGEN, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);

        return item;
    }
}
