package epic.welcome.message.handlers.plugin.register.metrics;

import epic.welcome.message.Main.Main;
import epic.welcome.message.handlers.Metrics.Metrics;
import org.bukkit.event.Listener;

public class RegisterMetrics implements Listener {

    public RegisterMetrics() {
        Metrics metrics = new Metrics(Main.getInst());

        metrics.getPluginData();
    }
}
