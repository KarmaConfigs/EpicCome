package epic.welcome.message.handlers.plugin.Updater;

import epic.welcome.message.Main.Main;
import epic.welcome.message.handlers.Updater.Updater;
import epic.welcome.message.handlers.plugin.register.Configs.RegisterConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class AutoChecks implements Listener {

    FileConfiguration GetConfigs = RegisterConfig.CONFIG;

    public AutoChecks() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (GetConfigs.getBoolean("AutoUpdates")) {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&bEpicCome &7>> Checking for updates..."));
                    new Updater().CheckUpdate();
                } else {
                }
            }
        }.runTaskTimer(Main.getInst(), 0, 20*300);
    }
}
