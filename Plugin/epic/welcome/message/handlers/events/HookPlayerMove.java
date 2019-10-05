package epic.welcome.message.handlers.events;

import epic.welcome.message.Main.Main;
import epic.welcome.message.handlers.Config.ConfigManager;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HookPlayerMove implements Listener {

    @EventHandler
    public void OnPlayerMove(PlayerMoveEvent e) {

        Player player = e.getPlayer();

        ConfigManager cm = new ConfigManager(Main.getInst(), player);

        FileConfiguration f = cm.getConfig();

        World PlayerWorld = player.getWorld();

        double X = player.getLocation().getX();
        double Y = player.getLocation().getY();
        double Z = player.getLocation().getZ();

        Location SpawnLoc = new Location(PlayerWorld, X, Y, Z);

        float Yaw = player.getLocation().getYaw();
        float Pitch = player.getLocation().getPitch();

        PotionEffect effect = new PotionEffect(PotionEffectType.BLINDNESS, 100000000, 4);

        if (!e.getTo().equals(SpawnLoc)) {
            if (!f.getBoolean("Logged") && f.getBoolean("Registered")) {
                if (!player.isOnGround()) {
                    if (!player.isFlying()) {
                        player.setAllowFlight(true);
                        player.setFlying(true);
                    } else {
                    }
                    e.setCancelled(true);
                } else {
                    e.setCancelled(true);
                }
                player.addPotionEffect(effect);
                player.setGameMode(GameMode.SPECTATOR);
            } else {
                if (!f.getBoolean("Logged") && !f.getBoolean("Registered")) {
                    if (!player.isOnGround()) {
                        if (!player.isFlying()) {
                            player.setAllowFlight(true);
                            player.setFlying(true);
                        } else {
                        }
                        e.setCancelled(true);
                    } else {
                        e.setCancelled(true);
                    }
                    player.addPotionEffect(effect);
                    player.setGameMode(GameMode.SPECTATOR);
                } else {
                    if (f.getBoolean("Logged") && f.getBoolean("Registered")) {
                    }
                }
            }
            if (e.getTo().getPitch() != Pitch) {
                e.setCancelled(false);
            } else {
                if (e.getTo().getYaw() != Yaw) {
                    e.setCancelled(false);
                }
            }
        }
    }
}
