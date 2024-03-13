package locb.odi.dionis.listeners;

import locb.odi.dionis.AlcoMaster;
import locb.odi.dionis.Dionis;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
    private final Dionis pl;
    private final AlcoMaster alcoMaster;


    public PlayerListener(Dionis pl) {
        this.pl = pl;
        this.alcoMaster = pl.getAlcoMaster();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        alcoMaster.checkPlayer(e.getPlayer());
    }
}
