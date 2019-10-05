package epic.welcome.message.handlers.Commands.LoginSystem;

import epic.welcome.message.Main.Main;
import epic.welcome.message.handlers.Config.ConfigManager;
import epic.welcome.message.handlers.auth.PasswordHashv2;
import epic.welcome.message.handlers.events.Utils.EmulateJoinEvent;
import epic.welcome.message.handlers.plugin.register.Configs.RegisterConfig;
import epic.welcome.message.handlers.plugin.register.Messages.RegisterMessages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.io.File;

@SuppressWarnings("all")
public class RegisterCommand implements CommandExecutor {

    public static File CommandFile = new File(Main.getInst().getDataFolder(), "commands.yml");

    FileConfiguration GetMessages = RegisterMessages.Message;
    FileConfiguration GetConfigs = RegisterConfig.CONFIG;
    public static FileConfiguration CMDS = new YamlConfiguration().loadConfiguration(CommandFile);

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String str, String[] args) {

        if (s instanceof Player) {

            Player player = (Player) s;
            String PlayerName = player.getName();
            String ServerName = Bukkit.getServerName();
            int OnlinePlayers = Bukkit.getOnlinePlayers().size();
            int MaxPlayers = Bukkit.getMaxPlayers();
            ConfigManager cm = new ConfigManager(Main.getInst(), ((Player) s));

            if (args.length == 1) {

                if (GetConfigs.getBoolean("ManageLogin")) {

                    FileConfiguration f = cm.getConfig();

                    if (f.getString("Password").isEmpty()) {

                        if (!player.isOnGround()) {
                            player.setAllowFlight(false);
                            player.setFlying(false);
                            player.removePotionEffect(PotionEffectType.BLINDNESS);
                            player.setGameMode(GameMode.SURVIVAL);
                            new EmulateJoinEvent((Player) s);
                        } else {
                            player.removePotionEffect(PotionEffectType.BLINDNESS);
                            player.setGameMode(GameMode.SURVIVAL);
                            new EmulateJoinEvent((Player) s);
                        }

                        f.set("Password", new PasswordHashv2().hash(args[0]));

                        f.set("Registered", true);
                        f.set("Logged", true);

                        cm.saveConfig();


                        if (CMDS.getBoolean("enabled")) {
                            for (String command : CMDS.getStringList("commands.console")) {
                                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ChatColor.translateAlternateColorCodes('&', command).replace("{player}", PlayerName).replace("{server}", ServerName).replace("{online}", String.valueOf(OnlinePlayers)).replace("{max}", String.valueOf(MaxPlayers)));
                            }
                            for (String commands : CMDS.getStringList("commands.player")) {
                                player.performCommand(commands);
                            }
                            for (String messages : CMDS.getStringList("commands.message")) {
                                if (CMDS.getBoolean("messageprefix")) {
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', CMDS.getString("prefix") + messages).replace("{player}", PlayerName).replace("{server}", ServerName).replace("{online}", String.valueOf(OnlinePlayers)).replace("{max}", String.valueOf(MaxPlayers)));
                                } else {
                                    if (!CMDS.getBoolean("messageprefix")) {
                                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', messages).replace("{player}", PlayerName).replace("{server}", ServerName).replace("{online}", String.valueOf(OnlinePlayers)).replace("{max}", String.valueOf(MaxPlayers)));
                                    }
                                }
                            }
                        } else {
                        }

                        if (!GetMessages.getString("prefix").isEmpty()) {
                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("Registered")));
                        } else if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("Registered")));
                        } else if (GetMessages.getString("prefix").isEmpty()) {
                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("Registered")));
                        }
                    } else {
                        if (!f.getString("Password").isEmpty()) {
                            if (!GetMessages.getString("prefix").isEmpty()) {
                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("AlreadyRegister")));
                            } else if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("AlreadyRegister")));
                            } else if (GetMessages.getString("prefix").isEmpty()) {
                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("AlreadyRegister")));
                            }
                        }
                    }
                } else {
                    if (!GetMessages.getString("prefix").isEmpty()) {
                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("LogDisabled")));
                    } else if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("LogDisabled")));
                    } else if (GetMessages.getString("prefix").isEmpty()) {
                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("LogDisabled")));
                    }
                }
            } else {
                if (!GetMessages.getString("prefix").isEmpty()) {
                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("help")));
                } else {
                    if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("help")));
                    } else {
                        if (GetMessages.getString("prefix").isEmpty()) {
                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("help")));
                        }
                    }
                }
            }
        } else {
            if (s instanceof ConsoleCommandSender) {

                ConsoleCommandSender ccs = (ConsoleCommandSender) s;

                ccs.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Console commands: &become reload &7| &become reloadcfg &7| &become reloadmsg | &become setskin <player> <skin>"));
            }
        }
        return false;
    }
}
