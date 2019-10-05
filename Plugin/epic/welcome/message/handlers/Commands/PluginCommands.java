package epic.welcome.message.handlers.Commands;

import epic.welcome.message.Main.Main;
import epic.welcome.message.handlers.Config.ConfigManager;
import epic.welcome.message.handlers.Skins.SetSkin;
import epic.welcome.message.handlers.Updater.GetLatestVersion;
import epic.welcome.message.handlers.Updater.SpigotPluginUpdater;
import epic.welcome.message.handlers.auth.PasswordHashv2;
import epic.welcome.message.handlers.events.Utils.EmulateJoinEvent;
import epic.welcome.message.handlers.plugin.register.Configs.RegisterConfig;
import epic.welcome.message.handlers.plugin.register.Messages.RegisterMessages;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;

@SuppressWarnings("all")
public class PluginCommands implements CommandExecutor {

    SpigotPluginUpdater spu = new SpigotPluginUpdater(Main.getInst(), "http://karmatikuadeconfigs.000webhostapp.com/Plugins/EpicCome/plugin.html");

    public boolean isUsernamePremium(String username) throws IOException {
        URL url = new URL("https://api.mojang.com/users/profiles/minecraft/"+username);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String line;
        StringBuilder result = new StringBuilder();
        while ((line = in.readLine())!=null){
            result.append(line);
        }
        return !result.toString().equals("");
    }

    public static File ConfigFile = new File(Main.getInst().getDataFolder(), "config.yml");
    public static File MessageFile = new File(Main.getInst().getDataFolder(), "messages.yml");
    public static File CommandFile = new File(Main.getInst().getDataFolder(), "commands.yml");

    FileConfiguration GetMessages = RegisterMessages.Message;
    FileConfiguration GetConfigs = RegisterConfig.CONFIG;
    public static FileConfiguration CMDS = new YamlConfiguration().loadConfiguration(CommandFile);

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String str, String[] args) {

        if (s instanceof Player) {

            ConfigManager acm = new ConfigManager(Main.getInst(), (Player) s);
            ConfigManager cm = new ConfigManager(Main.getInst(), ((Player) s));

            //For developing, please, don't activate for possible issues

            /*if (args[0].equals("vanishtest")) {
                for(String key : acm.getAllConfigs().getKeys(false)){
                    Bukkit.getConsoleSender().sendMessage(acm.getAllConfigs().getString(key));
                }
            }*/

            Player player = ((Player) s);
            String PlayerName = player.getName();
            String ServerName = Bukkit.getServerName();
            int OnlinePlayers = Bukkit.getOnlinePlayers().size();
            int MaxPlayers = Bukkit.getMaxPlayers();

            if (args.length == 0) {
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
            } else {
                if (args.length == 1) {
                    if (args[0].equals("help")) {
                        if (s.hasPermission(GetConfigs.getString("Permissions.Commands.PageOfHelp"))) {
                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l---------[ &bEpicCome page 1/3&8&l ]---------"));
                            s.sendMessage("");
                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f/ecome help 2 &b- &dShows next page of help"));
                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f/ecome reload &b- &dReload all configs and messages"));
                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f/ecome reload configs &b- &dReload config only"));
                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f/ecome reload messages &b- &dReload messages only"));
                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f/ecome setkin <skin> &b- &dSet your skin"));
                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f/ecome setkin <player> <skin> &b- &dSet player skin"));
                            s.sendMessage("");
                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l-----------[ &bEpicCome page 1/3&8&l ]-----------"));
                        } else {
                            if (GetConfigs.getBoolean("SendCommandFeedback")) {
                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("noperms")));
                            } else {
                            }
                        }
                    } else {
                        if (args[0].equals("reload")) {

                            if (s.hasPermission(GetConfigs.getString("Permissions.Commands.ReloadFull"))) {

                                GetConfigs = YamlConfiguration.loadConfiguration(ConfigFile);
                                GetConfigs.getDefaults();
                                GetMessages = YamlConfiguration.loadConfiguration(MessageFile);
                                GetMessages.getDefaults();
                                CMDS = YamlConfiguration.loadConfiguration(CommandFile);
                                CMDS.getDefaults();

                                if (GetConfigs.getBoolean("AdminCommandFeedback")) {
                                    if (!GetMessages.getString("prefix").isEmpty()) {
                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("reload")));
                                    } else {
                                        if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("reload")));
                                        } else {
                                            if (GetMessages.getString("prefix").isEmpty()) {
                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("reload")));
                                            }
                                        }
                                    }
                                } else {
                                }
                            } else {
                                if (GetConfigs.getBoolean("SendCommandFeedback")) {

                                    if (!GetMessages.getString("prefix").isEmpty()) {
                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("noperms")));
                                    } else {
                                        if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("noperms")));
                                        } else {
                                            if (GetMessages.getString("prefix").isEmpty()) {
                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("noperms")));
                                            }
                                        }
                                    }
                                } else {
                                }
                            }
                        } else {
                            if (args[0].equals("setspawn")) {
                                if (s.hasPermission(GetConfigs.getString("Permissions.Commands.SetSpawn"))) {

                                    if (!SpawnFile.exists()) {
                                        try {
                                            Main.getInst().saveResource("spawn.yml", false);
                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> &cSpawn file doesn't exists, creating one"));
                                        } catch (Error e) {
                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> &cSpawn.yml file doesn't exists and " +
                                                    "plugin couldn't create a new one"));
                                        }
                                    } else {
                                        if (SpawnFile.exists()) {

                                            String PlayerWorld = ((Player) s).getWorld().getName();

                                            double PlayerX = ((Player) s).getLocation().getX();
                                            double PlayerY = ((Player) s).getLocation().getY();
                                            double PlayerZ = ((Player) s).getLocation().getZ();

                                            float PlayerPitch = ((Player) s).getLocation().getPitch();
                                            float PlayerYaw = ((Player) s).getLocation().getYaw();

                                            SPAWN.set("Spawn.World", PlayerWorld);
                                            SPAWN.set("Spawn.X", PlayerX);
                                            SPAWN.set("Spawn.Y", PlayerY);
                                            SPAWN.set("Spawn.Z", PlayerZ);
                                            SPAWN.set("Spawn.Pitch", PlayerPitch);
                                            SPAWN.set("Spawn.Yaw", PlayerYaw);

                                            try {
                                                SPAWN.save(SpawnFile);
                                            } catch (IOException e) {
                                                if (!GetMessages.getString("prefix").isEmpty()) {
                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("ErrorSave")));
                                                }
                                                else if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("ErrorSave")));
                                                }
                                                else if (GetMessages.getString("prefix").isEmpty()) {
                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("ErrorSave")));
                                                }
                                            }
                                            if (!GetMessages.getString("prefix").isEmpty()) {
                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("SetSpawn")));
                                            }
                                            else if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("SetSpawn")));
                                            }
                                            else if (GetMessages.getString("prefix").isEmpty()) {
                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("SetSpawn")));
                                            }
                                        }
                                    }
                                } else {
                                    if (!s.hasPermission(GetConfigs.getString("Permissions.Commands.SetSpawn"))) {
                                       if (GetConfigs.getBoolean("SendCommandFeedback")) {

                                           if (!this.GetMessages.getString("prefix").isEmpty()) {
                                               s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("noperms")));
                                           } else if (this.GetMessages.getString("prefix").equals("NoPrefix")) {
                                               s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("noperms")));
                                           } else if (this.GetMessages.getString("prefix").isEmpty()) {
                                               s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("noperms")));

                                           }
                                       } else {
                                       }
                                   }
                                }
                            } else {
                                if (args[0].equals("delspawn")) {

                                    if (s.hasPermission(GetConfigs.getString("Permissions.Commands.DelSpawn"))) {

                                        if (!SpawnFile.exists()) {
                                            try {
                                                Main.getInst().saveResource("spawn.yml", false);
                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> &cSpawn file doesn't exists, creating one"));
                                            } catch (Error e) {
                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> &cSpawn.yml file doesn't exists and " +
                                                        "plugin couldn't create a new one"));
                                            }
                                        } else {
                                            if (SpawnFile.exists()) {

                                                SPAWN.set("Spawn.World", "");
                                                SPAWN.set("Spawn.X", "");
                                                SPAWN.set("Spawn.Y", "");
                                                SPAWN.set("Spawn.Z", "");
                                                SPAWN.set("Spawn.Pitch", "");
                                                SPAWN.set("Spawn.Yaw", "");

                                                try {
                                                    SPAWN.save(SpawnFile);
                                                } catch (IOException e) {
                                                    if (!GetMessages.getString("prefix").isEmpty()) {
                                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("ErrorSave")));
                                                    }
                                                    else if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("ErrorSave")));
                                                    }
                                                    else if (GetMessages.getString("prefix").isEmpty()) {
                                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("ErrorSave")));
                                                    }
                                                }
                                                if (!GetMessages.getString("prefix").isEmpty()) {
                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + this.GetMessages.getString("DelSpawn")));
                                                }
                                                else if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("DelSpawn")));
                                                }
                                                else if (GetMessages.getString("prefix").isEmpty()) {
                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("DelSpawn")));
                                                }
                                            }
                                        }
                                    } else {
                                        if (!s.hasPermission(GetConfigs.getString("Permissions.Commands.DelSpawn"))) {

                                            if (GetConfigs.getBoolean("SendCommandFeedback")) {

                                                if (!this.GetMessages.getString("prefix").isEmpty()) {
                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("noperms")));
                                                } else if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("noperms")));
                                                } else if (GetMessages.getString("prefix").isEmpty()) {
                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("noperms")));
                                                }
                                            } else {
                                            }
                                        }
                                    }
                                } else {
                                    if (args[0].equals("spawn")) {
                                        if (s.hasPermission(GetConfigs.getString("Permissions.Commands.GoToSpawn"))) {
                                            if (SpawnFile.exists()) {

                                                if (SPAWN.getString("Spawn.World").isEmpty()) {

                                                    if (!GetMessages.getString("prefix").isEmpty()) {
                                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("SpawnNotSet")));
                                                    } else if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("SpawnNotSet")));
                                                    } else if (GetMessages.getString("prefix").isEmpty()) {
                                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("SpawnNotSet")));

                                                    }
                                                } else {
                                                    if (!SPAWN.getString("Spawn.World").isEmpty()) {
                                                        World World = Bukkit.getWorld(SPAWN.getString("Spawn.World"));

                                                        double SX = SPAWN.getDouble("Spawn.X");
                                                        double SY = SPAWN.getDouble("Spawn.Y");
                                                        double SZ = SPAWN.getDouble("Spawn.Z");

                                                        float SYa = (float) SPAWN.getDouble("Spawn.Yaw");
                                                        float SP = (float) SPAWN.getDouble("Spawn.Pitch");


                                                        Location Spawn = new Location(World, SX, SY, SZ);

                                                        Spawn.setYaw(SYa);
                                                        ((Player) s).getLocation().setPitch(SP);

                                                        if (World != null) {
                                                            ((Player) s).teleport(Spawn);
                                                            if (!this.GetMessages.getString("prefix").isEmpty()) {
                                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("Spawn")));
                                                            } else if (this.GetMessages.getString("prefix").equals("NoPrefix")) {
                                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("Spawn")));
                                                            } else if (this.GetMessages.getString("prefix").isEmpty()) {
                                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("Spawn")));

                                                            }

                                                        }
                                                    }
                                                }
                                            } else if (!SpawnFile.exists()) {
                                                if (!GetMessages.getString("prefix").isEmpty()) {
                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("SpawnNotSet")));
                                                } else if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("SpawnNotSet")));
                                                } else if (GetMessages.getString("prefix").isEmpty()) {
                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("SpawnNotSet")));

                                                }
                                            }

                                        } else if (!s.hasPermission(GetConfigs.getString("Permissions.Commands.GoToSpawn")) &&
                                                GetConfigs.getBoolean("SendCommandFeedback")) {

                                            if (!GetMessages.getString("prefix").isEmpty()) {
                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("noperms")));
                                            } else if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("noperms")));
                                            } else if (GetMessages.getString("prefix").isEmpty()) {
                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("noperms")));
                                            }
                                        }
                                    } else if (args[0].equals("update")) {
                                        if (s.hasPermission(GetConfigs.getString("Permissions.Commands.Update"))) {
                                            if (this.spu.needsUpdate()) {
                                                this.spu.UpdateFromPlayer((Player) s);
                                                for (Player online : Bukkit.getOnlinePlayers()) {
                                                    online.kickPlayer(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("ServerStopMessage")));

                                                    FileConfiguration f = cm.getConfig();

                                                    f.set("Logged", false);
                                                }

                                                Bukkit.shutdown();
                                            } else {
                                                if (!GetMessages.getString("prefix").isEmpty()) {
                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + "&cPlugin is up to date"));
                                                } else {
                                                    if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlugin is up to date"));
                                                    } else {
                                                        if (GetMessages.getString("prefix").isEmpty()) {
                                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> &cPlugin is up to date"));
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            if (!s.hasPermission(GetConfigs.getString("Permissions.Commands.Update"))) {
                                                if (!GetMessages.getString("prefix").isEmpty()) {
                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("noperms")));
                                                } else if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("noperms")));
                                                } else if (GetMessages.getString("prefix").isEmpty()) {
                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("noperms")));
                                                }
                                            }
                                        }
                                    } else if (args[0].equals("changelog")) {
                                        if (s.hasPermission(GetConfigs.getString("Permissions.Commands.Update"))) {
                                            this.spu.SendChangelog((Player) s);
                                        } else {
                                            if (!s.hasPermission(GetConfigs.getString("Permissions.Commands.Update"))) {
                                                if (!GetMessages.getString("prefix").isEmpty()) {
                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("noperms")));
                                                } else if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("noperms")));
                                                } else if (GetMessages.getString("prefix").isEmpty()) {
                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("noperms")));
                                                }
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
                                }
                            }
                        }
                    }
                } else {
                    if (args.length == 2) {
                        if (args[0].equals("reload") && args[1].equals("config") || args[1].equals("configs")) {

                            if (s.hasPermission(GetConfigs.getString("Permissions.Commands.ReloadConfig"))) {

                                GetConfigs = YamlConfiguration.loadConfiguration(ConfigFile);
                                GetConfigs.getDefaults();

                                if (GetConfigs.getBoolean("AdminCommandFeedback")) {

                                    if (!GetMessages.getString("prefix").isEmpty()) {
                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("reloadconfig")));
                                    } else {
                                        if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("reloadconfig")));
                                        } else {
                                            if (GetMessages.getString("prefix").isEmpty()) {
                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("reloadconfig")));
                                            }
                                        }
                                    }
                                } else {
                                }
                            } else {
                                if (GetConfigs.getBoolean("SendCommandFeedback")) {

                                    if (!GetMessages.getString("prefix").isEmpty()) {
                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("noperms")));
                                    } else {
                                        if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("noperms")));
                                        } else {
                                            if (GetMessages.getString("prefix").isEmpty()) {
                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("noperms")));
                                            }
                                        }
                                    }
                                } else {
                                }
                            }
                        } else {
                            if (args[0].equals("reload") && args[1].equals("messages") || args[1].equals("message")) {

                                if (s.hasPermission(GetConfigs.getString("Permissions.Commands.ReloadMessages"))) {

                                    GetMessages = YamlConfiguration.loadConfiguration(MessageFile);
                                    GetMessages.getDefaults();

                                    if (GetConfigs.getBoolean("AdminCommandFeedback")) {

                                        if (GetConfigs.getBoolean("AdminCommandFeedback")) {
                                            if (!GetMessages.getString("prefix").isEmpty()) {
                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("reloadmessage")));
                                            } else {
                                                if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("reloadmessage")));
                                                } else {
                                                    if (GetMessages.getString("prefix").isEmpty()) {
                                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("reloadmessage")));
                                                    }
                                                }
                                            }
                                        } else {
                                        }
                                    } else {
                                    }
                                } else {
                                    if (GetConfigs.getBoolean("SendCommandFeedback")) {

                                        if (!GetMessages.getString("prefix").isEmpty()) {
                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("noperms")));
                                        } else {
                                            if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("noperms")));
                                            } else {
                                                if (GetMessages.getString("prefix").isEmpty()) {
                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("noperms")));
                                                }
                                            }
                                        }
                                    } else {
                                    }
                                }
                            } else {
                                if (args[0].equals("setskin")) {
                                    if (s.hasPermission(GetConfigs.getString("Permissions.Commands.SetSelfSkin"))) {
                                        try {
                                            if (isUsernamePremium(args[1])) {
                                                SetSkin.changeSkin(((Player) s), args[1]);

                                                String world = ((Player) s).getWorld().getName() + "_nether";

                                                World OldWorld = ((Player) s).getWorld();
                                                World NetherWorld = Bukkit.getWorld(world);

                                                double x = ((Player) s).getLocation().getX();
                                                double y = ((Player) s).getLocation().getY();
                                                double z = ((Player) s).getLocation().getZ();

                                                Location Nether = new Location(NetherWorld, x, y, z);
                                                Location oldWorld = new Location(OldWorld, x, y, z);

                                                if (Bukkit.getWorld(world) != null) {
                                                    ((Player) s).teleport(Nether);
                                                    ((Player) s).teleport(oldWorld);
                                                    if (!GetMessages.getString("prefix").isEmpty()) {
                                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("PlayerSkin")).replace("{skin}", args[1]));
                                                    } else {
                                                        if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("PlayerSkin")).replace("{skin}", args[1]));
                                                        } else {
                                                            if (GetMessages.getString("prefix").isEmpty()) {
                                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("PlayerSkin")).replace("{skin}", args[1]));
                                                            }
                                                        }
                                                    }
                                                } else {

                                                    List<World> GetWorlds = Bukkit.getWorlds();

                                                    Random rnd = new Random();

                                                    int pos = rnd.nextInt(GetWorlds.size());

                                                    World RandomWorld = GetWorlds.get(pos);

                                                    Location rndWorld = new Location(RandomWorld, x, y, z);

                                                    ((Player) s).teleport(rndWorld);
                                                    ((Player) s).teleport(oldWorld);
                                                    if (!GetMessages.getString("prefix").isEmpty()) {
                                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("PlayerSkin")).replace("{skin}", args[1]));
                                                    } else {
                                                        if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("PlayerSkin")).replace("{skin}", args[1]));
                                                        } else {
                                                            if (GetMessages.getString("prefix").isEmpty()) {
                                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("PlayerSkin")).replace("{skin}", args[1]));
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        } catch (IOException e) {
                                            if (!GetMessages.getString("prefix").isEmpty()) {
                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("NoPremium")));
                                            } else {
                                                if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("NoPremium")));
                                                } else {
                                                    if (GetMessages.getString("prefix").isEmpty()) {
                                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("NoPremium")));
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        if (!s.hasPermission(GetConfigs.getString("Permissions.Commands.SetSelfSkin"))) {
                                            if (GetConfigs.getBoolean("SendCommandFeedback")) {

                                                if (!GetMessages.getString("prefix").isEmpty()) {
                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("noperms")));
                                                } else {
                                                    if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("noperms")));
                                                    } else {
                                                        if (GetMessages.getString("prefix").isEmpty()) {
                                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("noperms")));
                                                        }
                                                    }
                                                }
                                            } else {
                                            }
                                        }
                                    }
                                } else {
                                        if (args[0].equals("help") && args[1].equals("2")) {
                                            if (s.hasPermission(GetConfigs.getString("Permissions.Commands.PageOfHelp"))) {
                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l---------[ &bEpicCome page 2/3&8&l ]---------"));
                                                s.sendMessage("");
                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f/ecome help 2 &b- &dShows this"));
                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f/ecome help 3 &b- &dShows next page of help"));
                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f/ecome update &b- &dComing soon"));
                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f/ecome setspawn &b- &dSet spawn"));
                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f/ecome delspawn &b- &dDelete spawn"));
                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f/ecome spawn &b- &dTeleport to spawn"));
                                                s.sendMessage("");
                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l-----------[ &bEpicCome page 2/3&8&l ]-----------"));
                                            } else {
                                                if (GetConfigs.getBoolean("SendCommandFeedback")) {
                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("noperms")));
                                                } else {
                                                }
                                            }
                                        } else {
                                            if (args[0].equals("help") && args[1].equals("3")) {

                                                if (GetConfigs.getBoolean("ManageLogin")) {

                                                    if (s.hasPermission(GetConfigs.getString("Permissions.Commands.PageOfHelp"))) {
                                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l---------[ &bEpicCome page 3/3&8&l ]---------"));
                                                        s.sendMessage("");
                                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f/ecome help 2 &b- &dGo back"));
                                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f/ecome help 3 &b- &dShows this"));
                                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f/register &b- &dRegister your account"));
                                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f/login &b- &dLogin to play"));
                                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f/ecome changepass &b- &dChange your password"));
                                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f/ecome delpass &b- &dRemove your account"));
                                                        s.sendMessage("");
                                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l-----------[ &bEpicCome page 3/3&8&l ]-----------"));
                                                    } else {
                                                        if (GetConfigs.getBoolean("SendCommandFeedback")) {
                                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("noperms")));
                                                        } else {
                                                        }
                                                    }
                                                } else {
                                                    if (!GetConfigs.getBoolean("ManageLogin")) {
                                                        if (s.hasPermission(GetConfigs.getString("Permissions.Commands.PageOfHelp"))) {
                                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l---------[ &bEpicCome page 3/3&8&l ]---------"));
                                                            s.sendMessage("");
                                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f/ecome help 2 &b- &dGo back"));
                                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f/ecome help 3 &b- &dShows this"));
                                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f/ecome register &b- &cLogin system disabled"));
                                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f/ecome login &b- &cLogin system disabled"));
                                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f/ecome changepass &b- &cLogin system disabled"));
                                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f/ecome delpass &b- &cLogin system disabled"));
                                                            s.sendMessage("");
                                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&l-----------[ &bEpicCome page 3/3&8&l ]-----------"));
                                                        } else {
                                                            if (GetConfigs.getBoolean("SendCommandFeedback")) {
                                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("noperms")));
                                                            } else {
                                                            }
                                                        }
                                                    }
                                                }
                                            } else {
                                                if (args[0].equals("register")) {
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

                                                            f.set("Password", new PasswordHashv2().hash(args[1]));

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
                                                    if (args[0].equals("login")) {

                                                        if (GetConfigs.getBoolean("ManageLogin")) {

                                                            FileConfiguration f = cm.getConfig();

                                                            if (!f.getBoolean("Logged")) {

                                                                if (f.getString("Password").isEmpty()) {
                                                                    if (!this.GetMessages.getString("prefix").isEmpty()) {
                                                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("NotRegistered")));
                                                                    } else if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("NotRegistered")));
                                                                    } else if (GetMessages.getString("prefix").isEmpty()) {
                                                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("NotRegistered")));
                                                                    }
                                                                } else {

                                                                    if (new PasswordHashv2().auth(args[1], f.getString("Password"))) {
                                                                        if (!this.GetMessages.getString("prefix").isEmpty()) {
                                                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("Logged")));
                                                                        } else if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("Logged")));
                                                                        } else if (GetMessages.getString("prefix").isEmpty()) {
                                                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("Logged")));
                                                                        }

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

                                                                        if (this.spu.needsUpdate()) {
                                                                            if (s.hasPermission(GetConfigs.getString("Permissions.UpdateMsg"))) {
                                                                                ((Player) s).sendMessage(ChatColor.translateAlternateColorCodes('&', "&aThere's a new update available &7&o( &f&l{version} &7&o), &ccurrent version: &7&o( &b{current} &7&o)").replace("{current}", Main.getInst().getDescription().getVersion()).replace("{version}", new GetLatestVersion().GetLatest()));
                                                                                ((Player) s).sendMessage(ChatColor.translateAlternateColorCodes('&', "&aUse &b/ecome update &ato update"));
                                                                                ((Player) s).sendMessage(ChatColor.translateAlternateColorCodes('&', "&aUse &b/ecome changelog &ato view changelog"));
                                                                            } else {
                                                                            }
                                                                        } else {
                                                                        }

                                                                    } else {
                                                                        if (!this.GetMessages.getString("prefix").isEmpty()) {
                                                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("IncorrectPassword")));
                                                                        } else if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("IncorrectPassword")));
                                                                        } else if (GetMessages.getString("prefix").isEmpty()) {
                                                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("IncorrectPassword")));
                                                                        }
                                                                    }
                                                                }
                                                            } else {
                                                                if (f.getBoolean("Logged")) {
                                                                    if (!this.GetMessages.getString("prefix").isEmpty()) {
                                                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("AlreadyLogged")));
                                                                    } else if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("AlreadyLogged")));
                                                                    } else if (GetMessages.getString("prefix").isEmpty()) {
                                                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("AlreadyLogged")));
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            if (!this.GetMessages.getString("prefix").isEmpty()) {
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
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                        if (args.length == 3) {

                            Player target = Bukkit.getPlayer(args[1]);

                            if (args[0].equals("setskin")) {

                                if (s.hasPermission(GetConfigs.getString("Permissions.Commands.SetSkin"))) {

                                    if (target != null) {

                                        if (!target.getName().equals(s.getName())) {

                                            try {
                                                if (isUsernamePremium(args[2])) {
                                                    SetSkin.changeSkin(target, args[2]);

                                                    String world = target.getWorld().getName() + "_nether";

                                                    World OldWorld = target.getWorld();
                                                    World NetherWorld = Bukkit.getWorld(world);

                                                    double x = target.getLocation().getX();
                                                    double y = target.getLocation().getY();
                                                    double z = target.getLocation().getZ();

                                                    Location Nether = new Location(NetherWorld, x, y, z);
                                                    Location oldWorld = new Location(OldWorld, x, y, z);

                                                    if (Bukkit.getWorld(world) != null) {
                                                        target.teleport(Nether);
                                                        target.teleport(oldWorld);
                                                        if (!GetMessages.getString("prefix").isEmpty()) {
                                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("SetSkin").replace("{player}", target.getName()).replace("{skin}", args[2])));
                                                        } else {
                                                            if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("SetSkin").replace("{player}", target.getName()).replace("{skin}", args[2])));
                                                            } else {
                                                                if (GetMessages.getString("prefix").isEmpty()) {
                                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("SetSkin").replace("{player}", target.getName()).replace("{skin}", args[2])));
                                                                }
                                                            }
                                                        }

                                                        if (!GetMessages.getString("prefix").isEmpty()) {
                                                            target.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("PlayerSkin")).replace("{skin}", args[2]));
                                                        } else {
                                                            if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                                target.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("PlayerSkin")).replace("{skin}", args[2]));
                                                            } else {
                                                                if (GetMessages.getString("prefix").isEmpty()) {
                                                                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("PlayerSkin")).replace("{skin}", args[2]));
                                                                }
                                                            }
                                                        }
                                                    } else {

                                                        List<World> GetWorlds = Bukkit.getWorlds();

                                                        Random rnd = new Random();

                                                        int pos = rnd.nextInt(GetWorlds.size());

                                                        World RandomWorld = GetWorlds.get(pos);

                                                        Location rndWorld = new Location(RandomWorld, x, y, z);

                                                        target.teleport(rndWorld);
                                                        target.teleport(oldWorld);
                                                        if (!GetMessages.getString("prefix").isEmpty()) {
                                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("SetSkin").replace("{player}", target.getName()).replace("{skin}", args[2])));
                                                        } else {
                                                            if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("SetSkin").replace("{player}", target.getName()).replace("{skin}", args[2])));
                                                            } else {
                                                                if (GetMessages.getString("prefix").isEmpty()) {
                                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("SetSkin").replace("{player}", target.getName()).replace("{skin}", args[2])));
                                                                }
                                                            }
                                                        }

                                                        if (!GetMessages.getString("prefix").isEmpty()) {
                                                            target.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("PlayerSkin")).replace("{skin}", args[2]));
                                                        } else {
                                                            if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                                target.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("PlayerSkin")).replace("{skin}", args[2]));
                                                            } else {
                                                                if (GetMessages.getString("prefix").isEmpty()) {
                                                                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("PlayerSkin")).replace("{skin}", args[2]));
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } catch (IOException e) {
                                                if (!GetMessages.getString("prefix").isEmpty()) {
                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("NoPremium")));
                                                } else {
                                                    if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("NoPremium")));
                                                    } else {
                                                        if (GetMessages.getString("prefix").isEmpty()) {
                                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("NoPremium")));
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            try {
                                                if (isUsernamePremium(args[2])) {
                                                    SetSkin.changeSkin(target, args[2]);

                                                    String world = target.getWorld().getName() + "_nether";

                                                    World OldWorld = target.getWorld();
                                                    World NetherWorld = Bukkit.getWorld(world);

                                                    double x = target.getLocation().getX();
                                                    double y = target.getLocation().getY();
                                                    double z = target.getLocation().getZ();

                                                    Location Nether = new Location(NetherWorld, x, y, z);
                                                    Location oldWorld = new Location(OldWorld, x, y, z);

                                                    if (Bukkit.getWorld(world) != null) {
                                                        target.teleport(Nether);
                                                        target.teleport(oldWorld);
                                                        if (!GetMessages.getString("prefix").isEmpty()) {
                                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("PlayerSkin")).replace("{skin}", args[2]));
                                                        } else {
                                                            if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("PlayerSkin")).replace("{skin}", args[2]));
                                                            } else {
                                                                if (GetMessages.getString("prefix").isEmpty()) {
                                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("PlayerSkin")).replace("{skin}", args[2]));
                                                                }
                                                            }
                                                        }
                                                    } else {

                                                        List<World> GetWorlds = Bukkit.getWorlds();

                                                        Random rnd = new Random();

                                                        int pos = rnd.nextInt(GetWorlds.size());

                                                        World RandomWorld = GetWorlds.get(pos);

                                                        Location rndWorld = new Location(RandomWorld, x, y, z);

                                                        target.teleport(rndWorld);
                                                        target.teleport(oldWorld);
                                                        if (!GetMessages.getString("prefix").isEmpty()) {
                                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("PlayerSkin")).replace("{skin}", args[2]));
                                                        } else {
                                                            if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("PlayerSkin")).replace("{skin}", args[2]));
                                                            } else {
                                                                if (GetMessages.getString("prefix").isEmpty()) {
                                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("PlayerSkin")).replace("{skin}", args[2]));
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            } catch (IOException e) {
                                                if (!GetMessages.getString("prefix").isEmpty()) {
                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("NoPremium")));
                                                } else {
                                                    if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("NoPremium")));
                                                    } else {
                                                        if (GetMessages.getString("prefix").isEmpty()) {
                                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("NoPremium")));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        if (!GetMessages.getString("prefix").isEmpty()) {
                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("SetSkinUsage")));
                                        } else {
                                            if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("SetSkinUsage")));
                                            } else {
                                                if (GetMessages.getString("prefix").isEmpty()) {
                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("SetSkinUsage")));
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    if (!s.hasPermission(GetConfigs.getString("Permissions.Commands.SetSkin"))) {
                                        if (GetConfigs.getBoolean("SendCommandFeedback")) {

                                            if (!GetMessages.getString("prefix").isEmpty()) {
                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("noperms")));
                                            } else {
                                                if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("noperms")));
                                                } else {
                                                    if (GetMessages.getString("prefix").isEmpty()) {
                                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("noperms")));
                                                    }
                                                }
                                            }
                                        } else {
                                        }
                                    }
                                }
                            } else {
                                if (args[0].equals("changepass")) {

                                    if (GetConfigs.getBoolean("ManageLogin")) {

                                        FileConfiguration f = cm.getConfig();

                                        if (new PasswordHashv2().auth(args[1], f.getString("Password")) && !new PasswordHashv2().auth(args[2], f.getString("Password"))) {
                                            f.set("Password", new PasswordHashv2().hash(args[2]));
                                            if (!this.GetMessages.getString("prefix").isEmpty()) {
                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("PassChanged")));
                                            } else if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("PassChanged")));
                                            } else if (GetMessages.getString("prefix").isEmpty()) {
                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("PassChanged")));
                                            }

                                            cm.saveConfig();

                                        } else {
                                            if (new PasswordHashv2().auth(args[1], f.getString("Password")) && new PasswordHashv2().auth(args[2], f.getString("Password"))) {
                                                if (!this.GetMessages.getString("prefix").isEmpty()) {
                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("SamePassOld")));
                                                } else if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("SamePassOld")));
                                                } else if (GetMessages.getString("prefix").isEmpty()) {
                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("SamePassOld")));
                                                }
                                            } else {
                                                if (!new PasswordHashv2().auth(args[1], f.getString("Password")) && new PasswordHashv2().auth(args[2], f.getString("Password"))) {
                                                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> &cIncorrect usage: &b/ecome changepass &3<password> <newpassword>"));
                                                } else {
                                                    if (!new PasswordHashv2().auth(args[1], f.getString("Password")) && !new PasswordHashv2().auth(args[2], f.getString("Password"))) {
                                                        if (!this.GetMessages.getString("prefix").isEmpty()) {
                                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("IncorrectPassword")));
                                                        } else if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("IncorrectPassword")));
                                                        } else if (GetMessages.getString("prefix").isEmpty()) {
                                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("IncorrectPassword")));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        if (!this.GetMessages.getString("prefix").isEmpty()) {
                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("LogDisabled")));
                                        } else if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("LogDisabled")));
                                        } else if (GetMessages.getString("prefix").isEmpty()) {
                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("LogDisabled")));
                                        }
                                    }
                                } else {
                                    if (args[0].equals("delpass")) {

                                        if (GetConfigs.getBoolean("ManageLogin")) {

                                            FileConfiguration f = cm.getConfig();

                                            if (new PasswordHashv2().auth(args[1], f.getString("Password")) && new PasswordHashv2().auth(args[2], f.getString("Password"))) {
                                                f.set("Password", "");
                                                f.set("Registered", false);
                                                f.set("Logged", false);
                                                ((Player) s).kickPlayer(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("Unregistered")));

                                                cm.saveConfig();

                                            } else {
                                                if (new PasswordHashv2().auth(args[1], f.getString("Password")) && !new PasswordHashv2().auth(args[2], f.getString("Password"))) {
                                                    if (!this.GetMessages.getString("prefix").isEmpty()) {
                                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + "&cIncorrect usage: &b/ecome changepass &3<password> <password>"));
                                                    } else if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cIncorrect usage: &b/ecome changepass &3<password> <password>"));
                                                    } else if (GetMessages.getString("prefix").isEmpty()) {
                                                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> &cIncorrect usage: &b/ecome changepass &3<password> <password>"));
                                                    }
                                                } else {
                                                    if (!new PasswordHashv2().auth(args[1], f.getString("Password")) && new PasswordHashv2().auth(args[2], f.getString("Password"))) {
                                                        if (!this.GetMessages.getString("prefix").isEmpty()) {
                                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + "&cIncorrect usage: &b/ecome changepass &3<password> <password>"));
                                                        } else if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cIncorrect usage: &b/ecome changepass &3<password> <password>"));
                                                        } else if (GetMessages.getString("prefix").isEmpty()) {
                                                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> &cIncorrect usage: &b/ecome changepass &3<password> <password>"));
                                                        }
                                                    } else {
                                                        if (!new PasswordHashv2().auth(args[1], f.getString("Password")) && !new PasswordHashv2().auth(args[2], f.getString("Password"))) {
                                                            if (!this.GetMessages.getString("prefix").isEmpty()) {
                                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("IncorrectPassword")));
                                                            } else if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("IncorrectPassword")));
                                                            } else if (GetMessages.getString("prefix").isEmpty()) {
                                                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("IncorrectPassword")));
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            if (!this.GetMessages.getString("prefix").isEmpty()) {
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
                    }
                }
            }
        } else {
            if (s instanceof ConsoleCommandSender) {
                ConsoleCommandSender ccs = (ConsoleCommandSender) s;

                if (args.length == 0) {
                    ccs.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Console commands: &become reload &7| &become reloadcfg &7| &become reloadmsg | &become setskin <player> <skin>"));
                } else {
                    if (args.length == 1) {
                        if (args[0].equals("reload")) {
                            GetConfigs = YamlConfiguration.loadConfiguration(ConfigFile);
                            GetMessages = YamlConfiguration.loadConfiguration(MessageFile);

                            if (!GetMessages.getString("prefix").isEmpty()) {
                                ccs.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("reload")));
                            } else {
                                if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                    ccs.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("reload")));
                                } else {
                                    if (GetMessages.getString("prefix").isEmpty()) {
                                        ccs.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("reload")));
                                    }
                                }
                            }
                        } else {
                            if (args[0].equals("reloadcfg")) {
                                GetConfigs = YamlConfiguration.loadConfiguration(ConfigFile);

                                if (!GetMessages.getString("prefix").isEmpty()) {
                                    ccs.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("reloadconfig")));
                                } else {
                                    if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                        ccs.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("reloadconfig")));
                                    } else {
                                        if (GetMessages.getString("prefix").isEmpty()) {
                                            ccs.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("reloadconfig")));
                                        }
                                    }
                                }
                            } else {
                                if (args[0].equals("reloadmsg")) {
                                    GetMessages = YamlConfiguration.loadConfiguration(MessageFile);

                                    if (!GetMessages.getString("prefix").isEmpty()) {
                                        ccs.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("reloadmessage")));
                                    } else {
                                        if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                            ccs.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("reloadmessage")));
                                        } else {
                                            if (GetMessages.getString("prefix").isEmpty()) {
                                                ccs.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("reloadmessage")));
                                            }
                                        }
                                    }
                                } else {
                                    ccs.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Console commands: &become reload &7| &become reloadcfg &7| &become reloadmsg | &become setskin <player> <skin>"));
                                }
                            }
                        }
                    } else {
                        if (args.length == 3) {

                            Player target = Bukkit.getPlayer(args[1]);

                            if (args[0].equals("setskin")) {

                                if (target != null) {

                                    try {
                                        if (isUsernamePremium(args[2])) {
                                            SetSkin.changeSkin(target, args[2]);

                                            String world = target.getWorld().getName() + "_nether";

                                            World OldWorld = target.getWorld();
                                            World NetherWorld = Bukkit.getWorld(world);

                                            double x = target.getLocation().getX();
                                            double y = target.getLocation().getY();
                                            double z = target.getLocation().getZ();

                                            Location Nether = new Location(NetherWorld, x, y, z);
                                            Location oldWorld = new Location(OldWorld, x, y, z);

                                            if (Bukkit.getWorld(world) != null) {
                                                target.teleport(Nether);
                                                target.teleport(oldWorld);
                                                if (!GetMessages.getString("prefix").isEmpty()) {
                                                    ccs.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("SetSkin").replace("{player}", target.getName()).replace("{skin}", args[2])));
                                                } else {
                                                    if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                        ccs.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("SetSkin").replace("{player}", target.getName()).replace("{skin}", args[2])));
                                                    } else {
                                                        if (GetMessages.getString("prefix").isEmpty()) {
                                                            ccs.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("SetSkin").replace("{player}", target.getName()).replace("{skin}", args[2])));
                                                        }
                                                    }
                                                }

                                                if (!GetMessages.getString("prefix").isEmpty()) {
                                                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("PlayerSkin")).replace("{skin}", args[2]));
                                                } else {
                                                    if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("PlayerSkin")).replace("{skin}", args[2]));
                                                    } else {
                                                        if (GetMessages.getString("prefix").isEmpty()) {
                                                            target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("PlayerSkin")).replace("{skin}", args[2]));
                                                        }
                                                    }
                                                }
                                            } else {

                                                List<World> GetWorlds = Bukkit.getWorlds();

                                                Random rnd = new Random();

                                                int pos = rnd.nextInt(GetWorlds.size());

                                                World RandomWorld = GetWorlds.get(pos);

                                                Location rndWorld = new Location(RandomWorld, x, y, z);

                                                target.teleport(rndWorld);
                                                target.teleport(oldWorld);
                                                if (!GetMessages.getString("prefix").isEmpty()) {
                                                    ccs.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("SetSkin").replace("{player}", target.getName()).replace("{skin}", args[2])));
                                                } else {
                                                    if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                        ccs.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("SetSkin").replace("{player}", target.getName()).replace("{skin}", args[2])));
                                                    } else {
                                                        if (GetMessages.getString("prefix").isEmpty()) {
                                                            ccs.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("SetSkin").replace("{player}", target.getName()).replace("{skin}", args[2])));
                                                        }
                                                    }
                                                }

                                                if (!GetMessages.getString("prefix").isEmpty()) {
                                                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("PlayerSkin")).replace("{skin}", args[2]));
                                                } else {
                                                    if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                        target.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("PlayerSkin")).replace("{skin}", args[2]));
                                                    } else {
                                                        if (GetMessages.getString("prefix").isEmpty()) {
                                                            target.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("PlayerSkin")).replace("{skin}", args[2]));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } catch (IOException e) {
                                        if (!GetMessages.getString("prefix").isEmpty()) {
                                            ccs.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("NoPremium")));
                                        } else {
                                            if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                                ccs.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("NoPremium")));
                                            } else {
                                                if (GetMessages.getString("prefix").isEmpty()) {
                                                    ccs.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("NoPremium")));
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    if (!GetMessages.getString("prefix").isEmpty()) {
                                        ccs.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("SetSkinUsage")));
                                    } else {
                                        if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                                            ccs.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("SetSkinUsage")));
                                        } else {
                                            if (GetMessages.getString("prefix").isEmpty()) {
                                                ccs.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("SetSkinUsage")));
                                            }
                                        }
                                    }
                                }
                            } else {
                                ccs.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Console commands: &become reload &7| &become reloadcfg &7| &become reloadmsg | &become setskin <player> <skin>"));
                            }
                        } else {
                            ccs.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Console commands: &become reload &7| &become reloadcfg &7| &become reloadmsg | &become setskin <player> <skin>"));
                        }
                    }
                }
            }
        }
        return false;
    }

    private File SpawnFile = new File(Main.getInst().getDataFolder(), "spawn.yml");

    private FileConfiguration SPAWN = (new YamlConfiguration()).loadConfiguration(SpawnFile);
}
