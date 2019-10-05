package epic.welcome.message.handlers.Updater;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;

public class GetLatestVersion {

    final static String VERSION_URL = "https://api.spigotmc.org/legacy/update.php?resource=71328";

    public String UrlVersion;

    {
        try {
            UrlVersion = IOUtils.toString(new URL(VERSION_URL));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String GetLatest() {
        return String.valueOf(Double.parseDouble(UrlVersion));
    }
}