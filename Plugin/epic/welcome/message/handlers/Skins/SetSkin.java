package epic.welcome.message.handlers.Skins;

import epic.welcome.message.Main.Main;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SetSkin implements Listener {

    private static final HashMap<String, PlayerInfo> INFOS = new HashMap<>();

    public static void changeSkin(Player p, String name) {
        GameProfile skingp;
        CraftPlayer cp = (CraftPlayer) p;

        try {
            skingp = ProfileBuilder.fetch(UUIDFetcher.getUUID(name));
        } catch (IOException e) {
            try {
                skingp = ProfileBuilder.fetch(UUIDFetcher.getUUID(name));
            } catch (IOException ex) {
                try {
                    skingp = ProfileBuilder.fetch(UUIDFetcher.getUUID(name));
                } catch (IOException exc) {
                    try {
                        skingp = ProfileBuilder.fetch(UUIDFetcher.getUUID(name));
                    } catch (IOException exce) {
                        try {
                            skingp = ProfileBuilder.fetch(UUIDFetcher.getUUID(name));
                        } catch (IOException excep) {
                            try {
                                skingp = ProfileBuilder.fetch(UUIDFetcher.getUUID(name));
                            } catch (IOException except) {
                                try {
                                    skingp = ProfileBuilder.fetch(UUIDFetcher.getUUID(name));
                                } catch (IOException excepti) {
                                    try {
                                        skingp = ProfileBuilder.fetch(UUIDFetcher.getUUID(name));
                                    } catch (IOException exceptio) {
                                        try {
                                            skingp = ProfileBuilder.fetch(UUIDFetcher.getUUID(name));
                                        } catch (IOException exception) {
                                            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&bEpicCome &7>> &4" +
                                                    "Couldn't apply skin change for &b" + p));
                                            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4[&cReason&4] &7>> &6" +
                                                    exception.getCause()));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return;
        }

        Collection<Property> props = skingp.getProperties().get("textures");

        cp.getProfile().getProperties().removeAll("textures");
        cp.getProfile().getProperties().putAll("textures", props);

        HashMap<Integer, ItemStack> stacks = new HashMap<>();

        for (ItemStack is : p.getInventory().getContents()) {
            if (is == null) continue;

            stacks.put(p.getInventory().first(is), is);
        }

        INFOS.put(p.getName(), new PlayerInfo(p.getHealth(), p.getFoodLevel(), p.getLocation().clone().add(0, 1, 0), stacks));

        sendPackets(new PacketPlayOutEntityDestroy(cp.getEntityId()));
        sendPackets(new PacketPlayOutPlayerInfo(
                PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, cp.getHandle()));

        new BukkitRunnable() {
            public void run() {
                p.spigot().respawn();

                PlayerInfo info = INFOS.get(p.getName());

                p.setHealth(info.getHealth());
                p.setFoodLevel(info.getFood());
                p.teleport(info.getLoc());

                for (ItemStack is : info.getItems().values()) {
                    p.getInventory().setItem(getKeyByValue(info.getItems(), is), is);
                }

                INFOS.remove(p.getName());

                sendPackets(new PacketPlayOutPlayerInfo(
                        PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, cp.getHandle()));
                sendPacketsNotFor(p.getName(), new PacketPlayOutNamedEntitySpawn(cp.getHandle()));
            }
        }.runTaskLater(Main.getInst(), 2);
    }

    private static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    private static void sendPackets(Packet... packets) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            for (Packet packet : packets) {
                EntityPlayer nmsPlayer = ((CraftPlayer) p).getHandle();

                nmsPlayer.playerConnection.sendPacket(packet);
            }
        }
    }

    private static void sendPacketsNotFor(String notFor, Packet... packets) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.getName().equals(notFor)) {
                for (Packet packet : packets) {
                    EntityPlayer nmsPlayer = ((CraftPlayer) p).getHandle();

                    nmsPlayer.playerConnection.sendPacket(packet);
                }
            }
        }
    }
}

class PlayerInformation {

}

class PlayerInfo {

    private double health;
    private int food;
    private Location loc;
    private HashMap<Integer, ItemStack> items = new HashMap<>();

    public PlayerInfo(double health, int food, Location loc, HashMap<Integer, ItemStack> items) {
        this.health = health;
        this.food = food;
        this.loc = loc;
        this.items = items;
    }

    public double getHealth() {
        return health;
    }

    public int getFood() {
        return food;
    }

    public Location getLoc() {
        return loc;
    }

    public HashMap<Integer, ItemStack> getItems() {
        return items;
    }
}
