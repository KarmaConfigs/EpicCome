package epic.welcome.message.handlers.Config;

import epic.welcome.message.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;


@SuppressWarnings("all")
public class ConfigManager {
    private Main plugin;
    private Player player;
    private File file;
    private FileConfiguration fc;

    public ConfigManager(Main plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    public Player getOwner() {
        if (this.player == null)
            try {
                throw new Exception();
            } catch (Exception e) {
                this.plugin.getLogger().warning("Player is null");
                e.printStackTrace();
            }
        return this.player;
    }

    public boolean exists() {
        if (this.fc == null || this.file == null) {
            File temp = new File(getDataFolder(), getOwner().getName() + " (" + getOwner().getUniqueId().toString().replace("-", "") + ").yml");
            if (!temp.exists()) {
                return false;
            }
            this.file = temp;
        }

        return true;
    }

    public File getDataFolder() {
        File dir = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("%20", " "));
        File d = new File(dir.getParentFile().getPath(), this.plugin.getName() + "/Users");

        if (!d.exists()) {
            d.mkdirs();
        }
        return d;
    }

    public File getFile() {
        if (this.file == null) {
            this.file = new File(getDataFolder(), getOwner().getName() + " (" + getOwner().getUniqueId().toString().replace("-", "") + ").yml");
            if (!this.file.exists()) {
                try {
                    this.file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return this.file;
    }

    public File getAllFiles() {
        if (this.file == null) {
            for (Player online : Bukkit.getOnlinePlayers()
            ) {
                this.file = new File(getDataFolder(), online.getName() + " (" + online.getUniqueId().toString().replace("-", "") + ").yml");
                if (!this.file.exists()) {
                    try {
                        this.file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return this.file;
    }

    public FileConfiguration getConfig() {
        if (this.fc == null) {
            this.fc = YamlConfiguration.loadConfiguration(getFile());
        }
        return this.fc;
    }

    public FileConfiguration getAllConfigs() {
        if (this.fc == null) {
            this.fc = YamlConfiguration.loadConfiguration(getAllFiles());
        }
        return this.fc;
    }

    public void saveConfig() {
        try {
            getConfig().save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
