package epic.welcome.message.handlers.plugin.register.commands;

import epic.welcome.message.Main.Main;
import epic.welcome.message.handlers.Commands.LoginSystem.LoginCommand;
import epic.welcome.message.handlers.Commands.LoginSystem.RegisterCommand;
import epic.welcome.message.handlers.Commands.PluginCommands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;

import java.io.File;

public class RegisterCommands implements Listener {

    public static File CommandFile = new File(Main.getInst().getDataFolder(), "commands.yml");

    public static FileConfiguration CMDS = new YamlConfiguration().loadConfiguration(CommandFile);

    public RegisterCommands() {
        Main.getInst().getCommand("ecome").setExecutor(new PluginCommands());
        Main.getInst().getCommand("register").setExecutor(new RegisterCommand());
        Main.getInst().getCommand("login").setExecutor(new LoginCommand());

        if (!CommandFile.exists()) {
            Main.getInst().saveResource("commands.yml", true);
        }

        if (!CMDS.isSet("enabled") || !CMDS.isSet("messageprefix") || !CMDS.isSet("prefix") || !CMDS.isSet("commands.console")
        || !CMDS.isSet("commands.player") || !CMDS.isSet("commands.message")) {
            Main.getInst().saveResource("commands.yml", true);
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&bEpicCome &7>> &cThere was an" +
                    " internal error on your commands.yml and have been restored"));
        } else {
        }

        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> &aCommands registered successfully"));
    }
}
