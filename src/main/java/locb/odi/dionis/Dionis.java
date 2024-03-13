package locb.odi.dionis;

import locb.odi.dionis.craft.CraftMaster;
import locb.odi.dionis.listeners.*;
import locb.odi.dionis.service.AlcoService;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Dionis extends JavaPlugin {

    private static Dionis instance;

    Logger log = Logger.getLogger("Minecraft");

    private static CraftMaster craftMaster;

    //private List<Drink> drinkList = new ArrayList<>();
    //private File path = new File(getDataFolder() + File.separator + "drinks.json");
    private Map<String, Integer> drinkersMap = new HashMap<>();

    private AlcoService alcoService;

    private AlcoMaster alcoMaster;

    @Override
    public void onEnable() {
        instance = this;
        craftMaster = new CraftMaster();
        alcoService = new AlcoService();
        alcoMaster = new AlcoMaster();

        ConsoleCommandSender console = getServer().getConsoleSender();
        console.sendMessage("\n");
        console.sendMessage(ChatColor.GRAY + "Odisseya server plugin");
        console.sendMessage(ChatColor.BLUE + "|~~~~~~~~~~~~~|");
        console.sendMessage(ChatColor.BLUE + "| D i o n i s |");
        console.sendMessage(ChatColor.BLUE + "|~~~~~~~~~~~~~|");
        console.sendMessage(ChatColor.GRAY + "[codded by locb_km]");
        console.sendMessage(ChatColor.GRAY + "spetional for ode.l :p");
        console.sendMessage(ChatColor.GRAY + "with love \u2764");
        console.sendMessage(ChatColor.GRAY + "idea author & screenwriter: LeonBattist");
        console.sendMessage("\n");

        Bukkit.getPluginManager().registerEvents(new DropListener(this), this);
        Bukkit.getPluginManager().registerEvents(new InteractListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PotionListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
        Bukkit.getPluginManager().registerEvents(new RecipesListener(), this);

        //Bukkit.getPluginManager().registerEvents(new CraftListener(this), this);

        File config = new File(getDataFolder() + File.separator + "config.yml" );
        if(!config.exists()) {
            getLogger().info("Creating new config file...");
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }

        File drinks_file = new File(getDataFolder() + File.separator + "drinks.json");
        System.out.println(drinks_file);
        if(!drinks_file.exists()) saveResource(drinks_file.getName(), false);

    }

    @Override
    public void onDisable() {
        if(alcoService != null) alcoService.close();
    }


    public AlcoMaster getAlcoMaster() {
        return alcoMaster;
    }

    public AlcoService getAlcoService() {
        return alcoService;
    }

    public static Dionis getInstance() {
        return instance;
    }

    public static void error(String msg) {
        instance.getLogger().severe(msg);
    }

    public static void error(String msg, Throwable e) {
        instance.getLogger().log(Level.SEVERE, msg, e);
    }

    public static void log(String msg) {
        instance.getLogger().info(msg);
    }
}
