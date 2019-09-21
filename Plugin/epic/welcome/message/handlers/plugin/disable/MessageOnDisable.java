package epic.welcome.message.handlers.plugin.disable;

import epic.welcome.message.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

public class MessageOnDisable implements Listener {

    public MessageOnDisable() {

        CommandSender ConsoleMessage = Bukkit.getConsoleSender();
        String LargeLine = ChatColor.translateAlternateColorCodes('&', "&8------------------------------");
        String Spacer = ChatColor.translateAlternateColorCodes('&', "");

        String Name = Main.getInst().getDescription().getName();
        String Version = Main.getInst().getDescription().getVersion();

        boolean IsAlpha = Main.getInst().getDescription().getVersion().contains("Alpha");
        boolean IsBeta = Main.getInst().getDescription().getVersion().contains("Beta");
        boolean IsNotAlpha = !Main.getInst().getDescription().getVersion().contains("Alpha");
        boolean IsNotBeta = !Main.getInst().getDescription().getVersion().contains("Beta");

        if (IsAlpha) {
            ConsoleMessage.sendMessage(LargeLine);
            ConsoleMessage.sendMessage(Spacer);
            ConsoleMessage.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b" + Name + " &ahas been disabled"));
            ConsoleMessage.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aVersion:" + " &c" + Version.replace("Alpha", "&4Alpha&c")));
            ConsoleMessage.sendMessage(Spacer);
            ConsoleMessage.sendMessage(LargeLine);
        } else {
            if (IsBeta) {
                ConsoleMessage.sendMessage(LargeLine);
                ConsoleMessage.sendMessage(Spacer);
                ConsoleMessage.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b" + Name + " &ahas been disabled"));
                ConsoleMessage.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aVersion:" + " &7" + Version.replace("Beta", "&bBeta&7")));
                ConsoleMessage.sendMessage(Spacer);
                ConsoleMessage.sendMessage(LargeLine);
            } else {
                if (IsNotAlpha && IsNotBeta) {
                    ConsoleMessage.sendMessage(LargeLine);
                    ConsoleMessage.sendMessage(Spacer);
                    ConsoleMessage.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b" + Name + " &ahas been disabled"));
                    ConsoleMessage.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aVersion:" + " &a" + Version));
                    ConsoleMessage.sendMessage(Spacer);
                    ConsoleMessage.sendMessage(LargeLine);
                } else {
                    if (!Name.equals("BubbaxCome")) {
                        Bukkit.getPluginManager().disablePlugin(Main.getInst());
                    }
                }
            }
        }
    }
}
