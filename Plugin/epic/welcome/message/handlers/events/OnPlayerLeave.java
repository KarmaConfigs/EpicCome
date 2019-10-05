package epic.welcome.message.handlers.events;

import epic.welcome.message.Main.Main;
import epic.welcome.message.handlers.Config.ConfigManager;
import epic.welcome.message.handlers.plugin.register.Configs.RegisterConfig;
import epic.welcome.message.handlers.plugin.register.Messages.RegisterMessages;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OnPlayerLeave implements Listener {

    private Main plugin;

    public OnPlayerLeave(Main plugin) {
        this.plugin = plugin;
    }

    FileConfiguration GetConfigs = RegisterConfig.CONFIG;

    @EventHandler
    public void LeavePlayer(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        FileConfiguration GetMessages = RegisterMessages.Message;

        event.setQuitMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("Leave")).replace("{player}", player.getName()));

        ConfigManager cm = new ConfigManager(plugin, player);

        Date Disconnect = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        FileConfiguration f = cm.getConfig();

        f.set("Disconnected", format.format(Disconnect));

        if (GetConfigs.getBoolean("ManageLogin")) {

            f.set("Logged", false);

        } else {
        }

        cm.saveConfig();
    }
}
