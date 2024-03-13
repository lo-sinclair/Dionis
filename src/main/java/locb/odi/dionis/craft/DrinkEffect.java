package locb.odi.dionis.craft;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class DrinkEffect {
    private String name;
    private int duration;
    private int amplifier;

    public DrinkEffect(String name, int duration, int amplifier) {
        this.name = name;
        this.duration = duration;
        this.amplifier = amplifier;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public int getAmplifier() {
        return amplifier;
    }


    public void addPlayerEffect(Player p){
        if( PotionEffectType.getByName(name) != null ) {
            p.addPotionEffect(new PotionEffect(PotionEffectType.getByName(name), duration, amplifier));
        }
        else {
            if(name.equals("FREEZE")) {
                p.setFreezeTicks(duration);
            }
        }
    }

}
