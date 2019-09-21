package epic.welcome.message.handlers.Skins;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.util.UUIDTypeAdapter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;

public class UUIDFetcher {

    private static final Gson gson = new GsonBuilder().registerTypeAdapter(UUID.class, new UUIDTypeAdapter()).create();
    private static final String UUID_URL = "https://api.mojang.com/users/profiles/minecraft/%s?at=%d";
    private static final String NAME_URL = "https://api.mojang.com/user/profiles/%s/names";
    private static HashMap<UUID, String> names = new HashMap<>();
    private static HashMap<String, UUID> uuids = new HashMap<>();

    public static UUID getUUID(Player p) {
        return getUUID(p.getName());
    }

    public static UUID getUUID(OfflinePlayer p) {
        return getUUID(p.getName());
    }

    @SuppressWarnings("deprecation")
    public static UUID getUUID(String name) {
        if (name == null)
            return UUID.randomUUID();
        name = name.toLowerCase();

        if (uuids.containsKey(name))
            return uuids.get(name);

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(
                    String.format(UUID_URL, name, System.currentTimeMillis() / 1000)).openConnection();
            connection.setReadTimeout(5000);

            PlayerUUID player = gson.fromJson(new BufferedReader(new InputStreamReader(connection.getInputStream())),
                    PlayerUUID.class);

            uuids.put(name, player.getId());

            return player.getId();
        } catch (Exception e) {
            Bukkit.getConsoleSender()
                    .sendMessage("Your server has no connection to the mojang servers or is runnig slowly.");
            uuids.put(name, Bukkit.getOfflinePlayer(name).getUniqueId());
            return Bukkit.getOfflinePlayer(name).getUniqueId();
        }
    }

    public static String getName(UUID uuid) {

        if (names.containsKey(uuid))
            return names.get(uuid);

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(
                    String.format(NAME_URL, UUIDTypeAdapter.fromUUID(uuid))).openConnection();
            connection.setReadTimeout(5000);

            PlayerUUID[] allUserNames = gson.fromJson(
                    new BufferedReader(new InputStreamReader(connection.getInputStream())), PlayerUUID[].class);
            PlayerUUID currentName = allUserNames[allUserNames.length - 1];

            names.put(uuid, currentName.getName());

            return currentName.getName();
        } catch (Exception e) {
            Bukkit.getConsoleSender()
                    .sendMessage("§cYour server has no connection to the mojang servers or is runnig slow.");
            names.put(uuid, Bukkit.getOfflinePlayer(uuid).getName());
            return Bukkit.getOfflinePlayer(uuid).getName();
        }
    }
}

class PlayerUUID {

    private String name;
    private UUID id;

    public String getName() {
        return name;
    }

    public UUID getId() {
        return id;
    }

}