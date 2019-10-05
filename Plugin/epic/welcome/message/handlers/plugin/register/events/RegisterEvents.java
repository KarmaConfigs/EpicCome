package epic.welcome.message.handlers.plugin.register.events;

import epic.welcome.message.Main.Main;
import epic.welcome.message.handlers.events.*;
import epic.welcome.message.handlers.plugin.register.Configs.RegisterConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;

public class RegisterEvents implements Listener {

    FileConfiguration GetConfigs = RegisterConfig.CONFIG;

    public RegisterEvents() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &8&l>> &aEvents registered successfully"));
        Bukkit.getPluginManager().registerEvents(new OnPlayerJoin(Main.getInst()), Main.getInst());
        Bukkit.getPluginManager().registerEvents(new OnPlayerLeave(Main.getInst()), Main.getInst());

        if (GetConfigs.getBoolean("ManageLogin")) {
            Bukkit.getPluginManager().registerEvents(new HookPlayerMove(), Main.getInst());
            Bukkit.getPluginManager().registerEvents(new HookPlayerCommand(), Main.getInst());
            Bukkit.getPluginManager().registerEvents(new HookPlayerChat(), Main.getInst());
        } else {
        }
    }
}
