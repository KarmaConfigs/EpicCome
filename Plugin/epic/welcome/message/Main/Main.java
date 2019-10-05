package epic.welcome.message.Main;

import epic.welcome.message.handlers.plugin.Updater.AutoChecks;
import epic.welcome.message.handlers.plugin.Updater.MakeUpdater;
import epic.welcome.message.handlers.plugin.disable.MessageOnDisable;
import epic.welcome.message.handlers.plugin.enable.MessageOnEnable;
import epic.welcome.message.handlers.plugin.register.CheckValues.CheckingConfig;
import epic.welcome.message.handlers.plugin.register.Configs.RegisterConfig;
import epic.welcome.message.handlers.plugin.register.Messages.RegisterMessages;
import epic.welcome.message.handlers.plugin.register.commands.RegisterCommands;
import epic.welcome.message.handlers.plugin.register.events.RegisterEvents;
import epic.welcome.message.handlers.plugin.register.metrics.RegisterMetrics;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;

public class Main extends JavaPlugin {

    public static Main inst;
    public static File file;
    public static boolean enable;

    public Main() {
        Main.inst = this;
        Main.file = this.getFile();
    }

    public static Main getInst() {
        return Main.inst;
    }

    @Override
    public void onEnable() {

        new MessageOnEnable();
        new RegisterEvents();
        new RegisterConfig();
        new RegisterMetrics();
        new RegisterMessages();
        new MakeUpdater();
        new BukkitRunnable() {
            @Override
            public void run() {
                new AutoChecks();
                cancel();
            }
        }.runTaskTimer(Main.getInst(), 5, 20);
        new RegisterCommands();
        new CheckingConfig();
    }

    @Override
    public void onDisable() {
        new MessageOnDisable();
    }
}
