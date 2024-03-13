package locb.odi.dionis.craft;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class Drink {
    private String title;

    private ItemStack itemStack;
    private List<DrinkEffect> drinkEffects = new ArrayList<>();


    public Drink(String title, ItemStack itemStack, List<DrinkEffect> drinkEffects) {
        this.title = title;
        this.itemStack = itemStack;
        this.drinkEffects = drinkEffects;
    }

    public String getTitle() {
        return title;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public List<DrinkEffect> getDrinkEffects() {
        return drinkEffects;
    }
}
