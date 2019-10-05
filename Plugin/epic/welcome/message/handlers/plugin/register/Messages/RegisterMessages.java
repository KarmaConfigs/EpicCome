package epic.welcome.message.handlers.plugin.register.Messages;

import epic.welcome.message.Main.Main;
import epic.welcome.message.handlers.Messages.PluginMessagesFix;
import epic.welcome.message.handlers.Messages.PluginMessages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;

public class RegisterMessages implements Listener {

    public static File MessageFile = new File(Main.getInst().getDataFolder(), "messages.yml");
    public static FileConfiguration Message = new YamlConfiguration().loadConfiguration(MessageFile);

    public RegisterMessages() {
        try {
            Main.getInst().getDataFolder().mkdir();
            new PluginMessages(MessageFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }

        new PluginMessagesFix(Main.getInst(), Main.getInst().getClass().getClassLoader());
        PluginMessagesFix.changeClassCache(false);

        saveMessages(Message, MessageFile);
    }

    private static void saveMessages(FileConfiguration conf, File f) {
        try {
            conf.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            new PluginMessages(f);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
