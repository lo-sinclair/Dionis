package locb.odi.dionis;

import org.bukkit.ChatColor;

public class Messages {

    private static Dionis pl = Dionis.getInstance();

    public static String getPrefix(){
        String msg = pl.getConfig().getString("messages.plugin_prefix") + " ";
        return ChatColor.translateAlternateColorCodes('&', msg) + ChatColor.RESET;
    }

    public static String getNotice(String path){
        String msg = pl.getConfig().getString(path);
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static String getErrorMessage(String path) {
        String msg = path;
        return ChatColor.RED + msg;
    }

}
