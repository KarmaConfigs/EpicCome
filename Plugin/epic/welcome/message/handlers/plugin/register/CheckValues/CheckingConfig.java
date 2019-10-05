package epic.welcome.message.handlers.plugin.register.CheckValues;

import epic.welcome.message.Main.Main;
import epic.welcome.message.handlers.Config.PluginConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;

public class CheckingConfig implements Listener {

    public static File ConfigFile = new File(Main.getInst().getDataFolder(), "config.yml");
    public static FileConfiguration CONFIG = new YamlConfiguration().loadConfiguration(ConfigFile);

    public CheckingConfig() {

        if (ConfigFile.exists()) {

            boolean ForceLight = CONFIG.getString("ForceLighting") != null && !CONFIG.getString("ForceLighting").isEmpty();
            boolean FLUser = CONFIG.getString("ForceLighting").equals("User");
            boolean FLVip = CONFIG.getString("ForceLighting").equals("Vip");
            boolean FLStaff = CONFIG.getString("ForceLighting").equals("Staff");
            boolean FLEqualsFalse = CONFIG.getString("ForceLighting").equals("false");
            boolean FLEqualsTrue = CONFIG.getString("ForceLighting").equals("true");
            boolean ForceExplosion = CONFIG.getString("ForceExplosion") != null && !CONFIG.getString("ForceLighting").isEmpty();
            boolean XPUser = CONFIG.getString("ForceExplosion").equals("User");
            boolean XPVip = CONFIG.getString("ForceExplosion").equals("Vip");
            boolean XPStaff = CONFIG.getString("ForceExplosion").equals("Staff");
            boolean XPEqualsFalse = CONFIG.getString("ForceExplosion").equals("false");
            boolean XPEqualsTrue = CONFIG.getString("ForceExplosion").equals("true");
            boolean IsKick = CONFIG.getString("IpPunishType").equals("kick");
            boolean IsBan = CONFIG.getString("IpPunishType").equals("ban");
            boolean Empty = CONFIG.getString("IpPunishType").isEmpty();
            boolean EqualsFalse = CONFIG.getString("IpPunishType").equals("false");
            boolean EqualsTrue = CONFIG.getString("IpPunishType").equals("true");
            boolean SkinEqualsFalse = CONFIG.getString("DefaultSkin").equals("true");
            boolean SkinEqualsTrue = CONFIG.getString("DefaultSkin").equals("true");
            boolean MaxLoginEmpty = CONFIG.getString("MaxLogin").isEmpty();
            boolean MaxRegIsEmpty = CONFIG.getString("MaxRegister").isEmpty();
            boolean MaxLoginUnder = CONFIG.getInt("MaxLogin") <= 10;
            boolean MaxRegUnder = CONFIG.getInt("MaxRegister") <= 15;

            if (!FLUser && !FLVip && !FLStaff && ForceLight) {
                CONFIG.set("ForceLighting", "Staff");
                saveConfig(CONFIG, ConfigFile);
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&bEpicCome &7>> &cThere was an error on your config.yml" +
                        " (ForceLighting wasn't User, Vip or Staff) so it've been restored to Staff"));
            }
            if (FLEqualsTrue || FLEqualsFalse) {
                CONFIG.set("ForceLighting", "Staff");
                saveConfig(CONFIG, ConfigFile);
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&bEpicCome &7>> &cThere was an error on your config.yml" +
                        " (ForceLighting has a boolean value) so it've been restored to Staff"));
            }
            if (!XPUser && !XPVip && !XPStaff && ForceExplosion) {
                CONFIG.set("ForceExplosion", "Staff");
                saveConfig(CONFIG, ConfigFile);
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&bEpicCome &7>> &cThere was an error on your config.yml" +
                        " (ForceExplosion wasn't User, Vip or Staff) so it've been restored to Staff"));
            }
            if (XPEqualsTrue || XPEqualsFalse) {
                CONFIG.set("ForceExplosion", "Staff");
                saveConfig(CONFIG, ConfigFile);
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&bEpicCome &7>> &cThere was an error on your config.yml" +
                        " (ForceExplosion has a boolean value) so it've been restored to Staff"));
            }
            if (!IsKick && !IsBan && Empty) {
                CONFIG.set("IpPunishType", "kick");
                saveConfig(CONFIG, ConfigFile);
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&bEpicCome &7>> &cThere was an error on your config.yml" +
                        " (IpPunishType wasn't kick, ban or was empty) so it've been restored to kick"));
            }
            if (EqualsTrue || EqualsFalse) {
                CONFIG.set("IpPunishType", "kick");
                saveConfig(CONFIG, ConfigFile);
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&bEpicCome &7>> &cThere was an error on your config.yml" +
                        " (IpPunishType has a boolean value) so it've been restored to kick"));
            }
            if (SkinEqualsFalse || SkinEqualsTrue) {
                CONFIG.set("DefaultSkin", "Steve");
                saveConfig(CONFIG, ConfigFile);
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&bEpicCome &7>> &cThere was an error on your config.yml" +
                        " (DefaultSkin has a boolean value) so it've been restored to Steve"));
            }
            if (MaxLoginUnder) {
                CONFIG.set("MaxLogin", 30);
                saveConfig(CONFIG, ConfigFile);
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&b&lEpicCome &7>> &cThere was an error on your config.yml (MaxLogin had a very low value) so it've been restored to default"));
            }
            if (MaxRegUnder) {
                CONFIG.set("MaxRegister", 60);
                saveConfig(CONFIG, ConfigFile);
                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',"&b&lEpicCome &7>> &cThere was an error on your config.yml (MaxRegister had a very low value) so it've been restored to default"));
            }
        } else {
        }
    }

    private static void saveConfig(FileConfiguration conf, File f) {
        try {
            conf.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            new PluginConfig(f);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
