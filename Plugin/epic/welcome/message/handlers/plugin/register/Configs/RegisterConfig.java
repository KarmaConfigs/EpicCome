package epic.welcome.message.handlers.plugin.register.Configs;

import epic.welcome.message.handlers.Config.PluginConfig;
import epic.welcome.message.handlers.Config.PluginConfigFix;
import epic.welcome.message.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;

public class RegisterConfig implements Listener {

    public static File ConfigFile = new File(Main.getInst().getDataFolder(), "config.yml");
    private static File SpawnFile = new File(Main.getInst().getDataFolder(), "spawn.yml");

    public static FileConfiguration CONFIG = new YamlConfiguration().loadConfiguration(ConfigFile);
    private static FileConfiguration SPAWN = (new YamlConfiguration()).loadConfiguration(SpawnFile);

    public RegisterConfig() {
        try {
            Main.getInst().getDataFolder().mkdirs();
            new PluginConfig(ConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }

        new PluginConfigFix(Main.getInst(), Main.getInst().getClass().getClassLoader());
        PluginConfigFix.changeClassCache(false);

        saveConfig(CONFIG, ConfigFile);

        if (!SpawnFile.exists()) {
            Main.getInst().saveResource("spawn.yml", true);
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
