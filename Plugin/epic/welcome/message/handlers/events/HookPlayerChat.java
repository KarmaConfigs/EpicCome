package epic.welcome.message.handlers.events;

import epic.welcome.message.Main.Main;
import epic.welcome.message.handlers.Config.ConfigManager;
import epic.welcome.message.handlers.plugin.register.Messages.RegisterMessages;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class HookPlayerChat implements Listener {

    @EventHandler
    public void PlayerChat(PlayerChatEvent e) {
        Player player = e.getPlayer();

        ConfigManager cm = new ConfigManager(Main.getInst(), player);

        FileConfiguration f = cm.getConfig();

        FileConfiguration GetMessages = RegisterMessages.Message;

        if (!f.getBoolean("Logged") && f.getBoolean("Registered")) {

            if (!e.getMessage().contains("/ecome login")) {
                e.setCancelled(true);

                if (!GetMessages.getString("prefix").isEmpty()) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("Login")));
                } else if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("Login")));
                } else if (GetMessages.getString("prefix").isEmpty()) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("Login")));
                }
            } else {
                if (e.getMessage().contains("/ecome login")) {
                    e.setCancelled(false);
                }
            }

            if (!e.getMessage().contains("/login")) {
                e.setCancelled(true);

                if (!GetMessages.getString("prefix").isEmpty()) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("Login")));
                } else if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("Login")));
                } else if (GetMessages.getString("prefix").isEmpty()) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("Login")));
                }
            } else {
                if (e.getMessage().contains("/login")) {
                    e.setCancelled(false);
                }
            }

        } else {
            if (!f.getBoolean("Logged") && !f.getBoolean("Registered")) {

                if (!e.getMessage().contains("/ecome register") || !e.getMessage().contains("/register")) {
                    e.setCancelled(true);

                    if (!GetMessages.getString("prefix").isEmpty()) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("Register")));
                    } else if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("Register")));
                    } else if (GetMessages.getString("prefix").isEmpty()) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("Register")));
                    }
                } else {
                }
            } else {
                if (f.getBoolean("Logged") && f.getBoolean("Registered")) {
                }
            }
        }
    }
}
