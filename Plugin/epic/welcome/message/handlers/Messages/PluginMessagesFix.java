package epic.welcome.message.handlers.Messages;

import org.bukkit.plugin.Plugin;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URLConnection;

public class PluginMessagesFix extends ClassLoader {
    public static boolean changeClassCache(boolean status) {
        try {
            final Field cacheField = URLConnection.class.getDeclaredField("defaultUseCaches");
            cacheField.setAccessible(true);
            cacheField.setBoolean(null, status);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private final Plugin plugin;
    public PluginMessagesFix(Plugin plugin, ClassLoader parent) {
        super(parent);

        this.plugin = plugin;
    }

    @Override
    public InputStream getResourceAsStream(String name) {
        InputStream resourceStream = plugin.getResource(name);
        if (resourceStream == null) {
            resourceStream = super.getResourceAsStream(name);
        }

        return resourceStream;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (name.startsWith("org.joda.time.")) {
            return getParent().getParent().loadClass(name);
        }

        return super.loadClass(name);
    }
}