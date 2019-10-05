package epic.welcome.message.handlers.Updater;

import epic.welcome.message.Main.Main;
import epic.welcome.message.handlers.plugin.register.Messages.RegisterMessages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Updater
{

    SpigotPluginUpdater spu = new SpigotPluginUpdater(Main.getInst(), "http://karmatikuadeconfigs.000webhostapp.com/Plugins/EpicCome/plugin.html");
    FileConfiguration GetMessages = RegisterMessages.Message;

    public void StartUpdater() {
        String CurrVersion = Main.getInst().getDescription().getVersion();

        if (this.spu.needsUpdate()) {
            this.spu.update();
            for (Player online : Bukkit.getOnlinePlayers()) {
                online.kickPlayer(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("ServerStopMessage")));
            }
            Bukkit.shutdown();
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&bEpicCome &7>> &aYou're running latest version of &4EpicCome &d( &5" + CurrVersion + " &d)"));
        }
    }

    public void CheckUpdate() {
        if (this.spu.needsUpdate()) {
            this.spu.update();
            for (Player online : Bukkit.getOnlinePlayers()) {
                online.kickPlayer(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("ServerStopMessage")));
            }
            Bukkit.shutdown();
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&bEpicCome &7>> &aPlugin is up to date"));
        }
    }
}
