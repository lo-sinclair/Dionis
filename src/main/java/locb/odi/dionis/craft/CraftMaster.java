package locb.odi.dionis.craft;

import locb.odi.dionis.Dionis;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;

public class CraftMaster {

    private static Map<String, ItemStack> craftItems = new HashMap<>();
    private static Map<String, Drink> drinksMap = new HashMap<>();

    public CraftMaster() {
        setupDrinks();
        setReceipts();
    }

    public void setReceipts(){
        waterSeedsReceipt();
        susloReceipt();
        woodSaltReceipt();
        slivkiReceipt();
        vinozelceReceipt();
    }

    public static Map<String, Drink> getDrinksMap() {
        return drinksMap;
    }

    private void waterSeedsReceipt(){
        NamespacedKey key = new NamespacedKey(Dionis.getInstance(), "water_seeds");

        //ShapedRecipe recipe = new ShapedRecipe(key, Ingredient.WATER_SEEDS.getItemStack());

        ShapelessRecipe recipe = new ShapelessRecipe(key, Ingredient.WATER_SEEDS.getItemStack());

        ItemStack waterBottleItem = new ItemStack(Material.POTION, 1);
        ItemMeta waterBottleMeta = waterBottleItem.getItemMeta();
        PotionMeta pmeta = (PotionMeta) waterBottleMeta;
        PotionData pdata = new PotionData(PotionType.WATER);
        pmeta.setBasePotionData(pdata);
        waterBottleItem.setItemMeta(pmeta);

        RecipeChoice ingredientStack = new RecipeChoice.ExactChoice(waterBottleItem);

        recipe.addIngredient(Material.WHEAT_SEEDS);
        recipe.addIngredient(ingredientStack);

        Bukkit.addRecipe(recipe);
    }

    private void vinozelceReceipt(){
        NamespacedKey key = new NamespacedKey(Dionis.getInstance(), "vinozelce");
        ShapelessRecipe recipe = new ShapelessRecipe(key, Ingredient.VINOZELCE.getItemStack());
        recipe.addIngredient(Material.CLAY_BALL);
        recipe.addIngredient(new RecipeChoice.ExactChoice(Ingredient.SLIVKI.getItemStack()));
        recipe.addIngredient(Material.HANGING_ROOTS);
        recipe.addIngredient(Material.MANGROVE_PROPAGULE);

        Bukkit.addRecipe(recipe);
    }


    private void susloReceipt(){
        NamespacedKey key = new NamespacedKey(Dionis.getInstance(), "suslo");
        RecipeChoice ingredientStack = new RecipeChoice.ExactChoice(Ingredient.WATER_SEEDS.getItemStack());

        Bukkit.addRecipe(new CampfireRecipe(key, Ingredient.SUSLO.getItemStack(), ingredientStack, 1, 20*60));
    }

    private void woodSaltReceipt(){
        NamespacedKey key;
        RecipeChoice ingredientStack;

        key = new NamespacedKey(Dionis.getInstance(), "wood_salt1");
        ingredientStack = new RecipeChoice.ExactChoice(new ItemStack(Material.OAK_LOG));
        Bukkit.addRecipe(new CampfireRecipe(key, Ingredient.WOOD_SALT.getItemStack(), ingredientStack, 1, 20*60));

        key = new NamespacedKey(Dionis.getInstance(), "wood_salt2");
        ingredientStack = new RecipeChoice.ExactChoice(new ItemStack(Material.BIRCH_LOG));
        Bukkit.addRecipe(new CampfireRecipe(key, Ingredient.WOOD_SALT.getItemStack(), ingredientStack, 1, 20*60));
    }


    private void slivkiReceipt(){
        NamespacedKey key = new NamespacedKey(Dionis.getInstance(), "cream");
        ShapelessRecipe recipe = new ShapelessRecipe(key, Ingredient.SLIVKI.getItemStack());

        recipe.addIngredient(Material.MILK_BUCKET);
        recipe.addIngredient(Material.GLASS_BOTTLE);

        Bukkit.addRecipe(recipe);

    }

    private void setupDrinks(){
        JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader(Dionis.getInstance().getDataFolder() + File.separator + "drinks.json")) {

            JSONArray drinksArray = (JSONArray) parser.parse(reader);
            String[] ingKeys = new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I"};

            drinksArray.forEach(item -> {
                JSONObject jsonObject = (JSONObject)item;

                String title = (String)jsonObject.get("title");
                NamespacedKey key = new NamespacedKey(Dionis.getInstance(), (String)jsonObject.get("key"));
                long color = (long) jsonObject.get("color");
                JSONArray craft = (JSONArray)jsonObject.get("craft");
                JSONArray effects = (JSONArray)jsonObject.get("effect");

                ItemStack itemStack = new ItemStack(Material.POTION);
                PotionMeta meta = (PotionMeta)itemStack.getItemMeta();
                meta.setDisplayName(ChatColor.RESET + title);
                itemStack.setAmount(3);

                meta.setColor(Color.fromRGB((int) color));
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
                itemStack.setItemMeta(meta);

                List<DrinkEffect> drinkEffects = new ArrayList<>();
                effects.forEach(item_effect -> {
                    JSONObject effect = (JSONObject) item_effect;
                    String type = (String) effect.get("type");
                    long amplifier = (long) effect.get("amplifier");
                    long duration = (long) effect.get("duration");

                    drinkEffects.add(new DrinkEffect(type, (int) duration * 20 * 60, (int) amplifier ));

                });

                // Рецепты
                ShapedRecipe recipe = new ShapedRecipe(key, itemStack);
                Map<String, String> ingMap = new HashMap<>();

                int kIndex = 0;

                String[] shapeLines = new String[3];
                for (int i=0; i < craft.size(); i++) {
                    JSONArray line = (JSONArray) craft.get(i);
                    String shapeLine = "";
                    for (int j=0; j < line.size(); j++) {
                        String el = (String) line.get(j);
                        if(ingMap.get(el) == null) {
                            ingMap.put(el, ingKeys[kIndex]);
                            kIndex++;
                        }
                        shapeLine += ingMap.get(el);
                    }
                    shapeLines[i] = shapeLine;
                }

                recipe.shape(shapeLines[0], shapeLines[1], shapeLines[2]);

                char sym;
                for( Map.Entry<String, String> entry : ingMap.entrySet()){
                    sym = entry.getValue().charAt(0);
                    if( Material.getMaterial(entry.getKey()) != null ) {
                        Material material =  Material.getMaterial(entry.getKey());
                        recipe.setIngredient(sym, material);
                    }
                   else if (Ingredient.getIngrediet(entry.getKey()) != null) {
                        RecipeChoice ingredientStack = new RecipeChoice.ExactChoice(Ingredient.valueOf(entry.getKey()).getItemStack());
                        recipe.setIngredient(sym, ingredientStack);
                    }
                }

                Bukkit.addRecipe(recipe);

                // Добавить в карту
                drinksMap.put(title, new Drink(
                       title,
                       itemStack,
                       drinkEffects
                ));

                // Do stuff
            });


        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            throw new RuntimeException(e);
        }
    }


}
