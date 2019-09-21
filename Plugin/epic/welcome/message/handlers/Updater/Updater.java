package epic.welcome.message.handlers.Updater;

import epic.welcome.message.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Updater
{
    SpigotPluginUpdater spu = new SpigotPluginUpdater(Main.getInst(), "http://karmatikuadeconfigs.000webhostapp.com/Plugins/EpicCome/plugin.html");


    public Updater() {
        String CurrVersion = Main.getInst().getDescription().getVersion();

        if (this.spu.needsUpdate()) {
            this.spu.update();
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&bEpicCome &7>> &aYou're running latest version of &4EpicCome &d( &5" + CurrVersion + " &d)"));
        }
    }
}
