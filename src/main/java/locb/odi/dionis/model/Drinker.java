package locb.odi.dionis.model;

import java.util.UUID;

public class Drinker {
    private UUID uuid;
    private String name;
    private int time;
    private int count;

    public Drinker(UUID uuid, String name, int time, int count) {
        this.uuid = uuid;
        this.name = name;
        this.time = time;
        this.count = count;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String nick) {
        this.name = nick;
    }

    public long getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Drinker{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", count=" + count +
                '}';
    }
}


