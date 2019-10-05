package epic.welcome.message.handlers.plugin.Updater;

import epic.welcome.message.Main.Main;
import epic.welcome.message.handlers.Updater.SpigotPluginUpdater;
import epic.welcome.message.handlers.Updater.Updater;
import epic.welcome.message.handlers.plugin.register.Configs.RegisterConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

public class MakeUpdater implements Listener {

    FileConfiguration GetConfigs = RegisterConfig.CONFIG;
    SpigotPluginUpdater spu = new SpigotPluginUpdater(Main.getInst(), "http://karmatikuadeconfigs.000webhostapp.com/Plugins/EpicCome/plugin.html");

    public MakeUpdater() {
        if (GetConfigs.getBoolean("AutoUpdates")) {
            new Updater().StartUpdater();
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&bEpicCome &7>> &aAuto updates are now working"));
        } else {
            if (!GetConfigs.getBoolean("AutoUpdates")) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> &cAuto updates function disabled, we recommend to enable it" +
                        " to fix some possible bugs"));
            }
        }
    }
}
