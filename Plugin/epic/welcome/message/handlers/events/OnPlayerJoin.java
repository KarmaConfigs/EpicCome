package epic.welcome.message.handlers.events;

import epic.welcome.message.Main.Main;
import epic.welcome.message.handlers.Config.ConfigManager;
import epic.welcome.message.handlers.Skins.SetSkin;
import epic.welcome.message.handlers.events.Utils.ActionBar;
import epic.welcome.message.handlers.events.Utils.EmulateJoinEvent;
import epic.welcome.message.handlers.plugin.register.Configs.RegisterConfig;
import epic.welcome.message.handlers.plugin.register.Messages.RegisterMessages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@SuppressWarnings("all")
public class OnPlayerJoin implements Listener {

    public File getDataFolder() {
        File dir = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("%20", " "));
        File d = new File(dir.getParentFile().getPath(), this.plugin.getName() + "/Users");

        if (!d.exists()) {
            d.mkdirs();
        }
        return d;
    }

    public static File CommandFile = new File(Main.getInst().getDataFolder(), "commands.yml");

    public static FileConfiguration CMDS = new YamlConfiguration().loadConfiguration(CommandFile);

    public boolean isUsernamePremium(String username) throws IOException {
        URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + username);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String line;
        StringBuilder result = new StringBuilder();
        while ((line = in.readLine()) != null) {
            result.append(line);
        }
        return !result.toString().equals("");
    }

    private Main plugin;

    public OnPlayerJoin(Main plugin) {
        this.plugin = plugin;
    }

    private static File SpawnFile = new File(Main.getInst().getDataFolder(), "spawn.yml");

    private static FileConfiguration SPAWN = (new YamlConfiguration()).loadConfiguration(SpawnFile);

    private ArrayList<UUID> auth;

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent event) {

        //Utils...
        Player player = event.getPlayer();

        World playerloc = player.getWorld();

        double X = player.getLocation().getX();
        double Y = player.getLocation().getY();
        double Z = player.getLocation().getZ();

        Location PlayerLocation = new Location(playerloc, X, Y, Z);

        float Pitch = player.getLocation().getPitch();
        float Yaw = player.getLocation().getYaw();
        double Y10 = player.getLocation().getY() + 10;

        double RestoreHealth = 20.0;

        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

        Location ActualPlayer = new Location(playerloc, X, Y, Z, Yaw, Pitch);
        Location ActualPlayer2 = new Location(playerloc, X + 1, Y, Z, Yaw, Pitch);
        Location ActualPlayer3 = new Location(playerloc, X, Y + 1, Z, Yaw, Pitch);
        Location ActualPlayer4 = new Location(playerloc, X, Y, Z + 1, Yaw, Pitch);
        Location ActualPlayer5 = new Location(playerloc, X, Y, Z, Yaw + 1, Pitch);
        Location ActualPlayer6 = new Location(playerloc, X, Y, Z, Yaw + 1, Pitch + 1);

        FileConfiguration GetMessages = RegisterMessages.Message;
        FileConfiguration GetConfigs = RegisterConfig.CONFIG;

        //Permissions
        Permission UserPerm = new Permission(GetConfigs.getString("Permissions.User"), PermissionDefault.TRUE);
        Permission LightPerm = new Permission(GetConfigs.getString("Permissions.Lighting"), PermissionDefault.FALSE);
        Permission ExplosionPerm = new Permission(GetConfigs.getString("Permissions.Explosion"), PermissionDefault.FALSE);
        Permission FireWorkPerm = new Permission(GetConfigs.getString("Permissions.Firework"), PermissionDefault.FALSE);
        Permission EnderPerm = new Permission(GetConfigs.getString("Permissions.DragonEffect"), PermissionDefault.FALSE);
        Permission SkinsPerm = new Permission(GetConfigs.getString("Permissions.Skin"), PermissionDefault.FALSE);
        Permission VipPerm = new Permission(GetConfigs.getString("Permissions.Vip"), PermissionDefault.FALSE);
        Permission StaffPerm = new Permission(GetConfigs.getString("Permissions.Staff"), PermissionDefault.FALSE);
        Permission WarnPerm = new Permission(GetConfigs.getString("Permissions.OpWarn"), PermissionDefault.FALSE);
        Permission OpPerm = new Permission(GetConfigs.getString("Permissions.Op"), PermissionDefault.NOT_OP);

        //Booleans
        boolean HumanVerify = GetConfigs.getBoolean("HumanVerify");
        boolean MotdEnabled = GetConfigs.getBoolean("Motd");
        boolean CustomJoinEnabled = GetConfigs.getBoolean("CustomJoin");
        boolean IpProtectionEnabled = GetConfigs.getBoolean("IpProtect");
        boolean KickIPEmpty = GetMessages.getString("IpPunish").isEmpty();
        boolean IsKick = GetConfigs.getString("IpPunishType").equals("kick");
        boolean IsBan = GetConfigs.getString("IpPunishType").equals("ban");
        boolean NotBan = !GetConfigs.getString("IpPunishType").equals("ban");

        boolean FixingSkins = GetConfigs.getBoolean("FixSkins");
        boolean EnableSkinsPerms = GetConfigs.getBoolean("EnableSkinsPerms");
        boolean NotSkinsPerms = !GetConfigs.getBoolean("EnableSkinsPerms");
        boolean SkinEmpty = GetConfigs.getString("DefaultSkin").isEmpty();
        boolean SkinNotEmpty = !GetConfigs.getString("DefaultSkin").isEmpty();

        boolean TitleOn = GetConfigs.getBoolean("Title");
        boolean TitleNotPublic = !GetConfigs.getBoolean("TitlePublic");
        boolean TitlePublic = GetConfigs.getBoolean("TitlePublic");
        boolean VipTitlePublic = GetConfigs.getBoolean("VipTitlePublic");
        boolean VipTitleNotPublic = !GetConfigs.getBoolean("VipTitlePublic");
        boolean StaffTitlePublic = GetConfigs.getBoolean("StaffTitlePublic");
        boolean StaffTitleNotPublic = !GetConfigs.getBoolean("StaffTitlePublic");

        boolean OpProtectOn = GetConfigs.getBoolean("OpProtect");
        boolean OpProtectionOff = !GetConfigs.getBoolean("OpProtect");

        boolean IsUserLight = GetConfigs.getBoolean("UsePlayerPermAsLighting");
        boolean IsVipLight = GetConfigs.getBoolean("UseVipPermAsLighting");
        boolean IsStaffLight = GetConfigs.getBoolean("UseStaffPermAsLighting");

        boolean ForceLight = GetConfigs.getString("ForceLighting") != null && !GetConfigs.getString("ForceLighting").isEmpty();
        boolean NoForceLight = GetConfigs.getString("ForceLighting").isEmpty() || GetConfigs.getString("ForceLighting") == null;
        boolean FLUser = GetConfigs.getString("ForceLighting").equals("User");
        boolean FLVip = GetConfigs.getString("ForceLighting").equals("Vip");
        boolean FLStaff = GetConfigs.getString("ForceLighting").equals("Staff");

        boolean LightingEnabled = GetConfigs.getBoolean("JoinLighting");
        boolean LightingDisabled = !GetConfigs.getBoolean("JoinLighting");

        boolean IsUserExplosion = GetConfigs.getBoolean("UsePlayerPermAsExplosion");
        boolean IsVipExplosion = GetConfigs.getBoolean("UseVipPermAsExplosion");
        boolean IsStaffExplosion = GetConfigs.getBoolean("UseStaffPermAsExplosion");

        boolean ForceExplosion = GetConfigs.getString("ForceExplosion") != null && !GetConfigs.getString("ForceLighting").isEmpty();
        boolean NoForceExplosion = GetConfigs.getString("ForceExplosion").isEmpty() || GetConfigs.getString("ForceLighting") == null;
        boolean XPUser = GetConfigs.getString("ForceExplosion").equals("User");
        boolean XPVip = GetConfigs.getString("ForceExplosion").equals("Vip");
        boolean XPStaff = GetConfigs.getString("ForceExplosion").equals("Staff");

        boolean ExplosionEnabled = GetConfigs.getBoolean("JoinExplosion");
        boolean ExplosionDisabled = !GetConfigs.getBoolean("JoinExplosion");

        boolean DragonEffects = GetConfigs.getBoolean("EnderDragonEffects");

        boolean FireWorks = GetConfigs.getBoolean("FireWorks");

        //Ints

        int OnlinePlayers = Bukkit.getOnlinePlayers().size();

        int MaxPlayers = Bukkit.getMaxPlayers();

        //Strings

        String PlayerName = player.getName();

        String ServerName = Bukkit.getServerName();

        String Line = ChatColor.translateAlternateColorCodes('&', GetMessages.getString("Separator"));

        String Space = "";

        String NewsTitle = ChatColor.translateAlternateColorCodes('&', GetMessages.getString("NewsTitle"));

        String KickNotOp = ChatColor.translateAlternateColorCodes('&', GetMessages.getString("Kick"));
        String OpWarning = ChatColor.translateAlternateColorCodes('&', GetMessages.getString("OpProtectionOffWarning").replace("{player}", PlayerName));

        String TitleMessage = ChatColor.translateAlternateColorCodes('&', GetMessages.getString("TitleMessage").replace("{player}", PlayerName).replace("{server}", ServerName).replace("{online}", String.valueOf(OnlinePlayers)).replace("{max}", String.valueOf(MaxPlayers)));
        String SubtitleMsg = ChatColor.translateAlternateColorCodes('&', GetMessages.getString("Subtitle").replace("{player}", PlayerName).replace("{server}", ServerName).replace("{online}", String.valueOf(OnlinePlayers)).replace("{max}", String.valueOf(MaxPlayers)));
        String VipTitle = ChatColor.translateAlternateColorCodes('&', GetMessages.getString("VipTitle").replace("{vip}", PlayerName));
        String VipSubtitle = ChatColor.translateAlternateColorCodes('&', GetMessages.getString("VipSubtitle").replace("{vip}", PlayerName));
        String StaffTitle = ChatColor.translateAlternateColorCodes('&', GetMessages.getString("StaffTitle").replace("{staff}", PlayerName));
        String StaffSubtitle = ChatColor.translateAlternateColorCodes('&', GetMessages.getString("StaffSubtitle").replace("{staff}", PlayerName));

        String News = ChatColor.translateAlternateColorCodes('&', GetMessages.getString("News"));
        String Motd = ChatColor.translateAlternateColorCodes('&', GetMessages.getString("Motd").replace("{player}", PlayerName).replace("{server}", ServerName).replace("{online}", String.valueOf(OnlinePlayers)).replace("{max}", String.valueOf(MaxPlayers)));

        //Code

        if (!GetConfigs.getBoolean("ManageLogin")) {
            new EmulateJoinEvent(event.getPlayer());
        } else {
            if (GetConfigs.getBoolean("ManageLogin")) {
            }
        }

        if (CustomJoinEnabled) {
            event.setJoinMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("Join")).replace("{player}", PlayerName));
        }

        ConfigManager cm = new ConfigManager(plugin, player);

        if (!cm.exists()) {

            if (HumanVerify) {
                player.kickPlayer(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("VerifyKick").replace("{newline}", "\n").replace("[", "").replace("]", "").replace("{doubleline}", "\n\n")));
            }

            FileConfiguration f = cm.getConfig();

            Date NewJoin = new Date();
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");

            f.set("name", player.getName());
            f.set("Full-UUID", player.getUniqueId().toString());
            f.set("Simple-UUID", player.getUniqueId().toString().replace("-", ""));
            f.set("First-IP", player.getAddress().getAddress().getHostAddress());
            f.set("Common-Ip", player.getAddress().getAddress().getHostAddress());
            f.set("Last-IP", player.getAddress().getAddress().getHostAddress());
            f.set("Join-Date", format.format(NewJoin));
            f.set("Last-Join", format.format(NewJoin));
            f.set("Disconnected", "Player is new!");
            f.set("Banned", false);
            f.set("Vanished", false);
            if (GetConfigs.getBoolean("ManageLogin")) {

                if (!f.isSet("Registered")) {
                    f.set("Registered", false);
                }
                if (!f.isSet("Logged")) {
                    f.set("Logged", false);
                }
                if (!f.isSet("Password")) {
                    f.set("Password", "");
                }
            } else {
            }
            cm.saveConfig();

        } else {

            FileConfiguration f = cm.getConfig();

            if (!player.getAddress().getAddress().getHostAddress().equals(f.getString("Common-Ip"))) {

                f.set("Last-Common-Ip", f.getString("Common-Ip"));

                Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&4[WARNING] &c{player} has a new IP &b(&d{ip}&b) &cmaybe he is usin" +
                        "g VPN").replace("{player}", PlayerName).replace("{ip}", player.getAddress().getAddress().getHostAddress()));

                if (IpProtectionEnabled) {

                    if (IsKick) {
                        if (KickIPEmpty) {
                            player.kickPlayer(ChatColor.translateAlternateColorCodes('&', "&bEpicCome &7&l>> &4&lVPN IS NOT ALLOWED"));
                        } else {
                            player.kickPlayer(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("IpPunish").replace("{newline}", "\n").replace("[", "").replace("]", "").replace("{doubleline}", "\n\n")));
                        }
                    } else {
                        if (IsBan) {
                            player.kickPlayer(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("IpPunish").replace("{newline}", "\n").replace("[", "").replace("]", "").replace("{doubleline}", "\n\n")));

                            Date Banned = new Date();
                            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");

                            f.set("Banned", true);
                            cm.saveConfig();

                            if (f.getBoolean("Banned")) {
                                if (player.getAddress().getAddress().getHostAddress().equals(f.getString("Last-Common-Ip"))) {

                                } else {
                                    if (!player.getAddress().getAddress().getHostAddress().equals(f.getString("Last-Common-Ip"))) {
                                        player.kickPlayer(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("IpPunish").replace("{newline}", "\n").replace("[", "").replace("]", "").replace("{doubleline}", "\n\n")));
                                    }
                                }
                            } else {
                                if (!f.getBoolean("Banned")) {
                                }
                            }
                        }
                    }
                }

                Date LastJoin = new Date();
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");

                f.set("Last-Join", format.format(LastJoin));

                if (f.getBoolean("Banned")) {
                    f.set("Common-Ip", "Banned (Last ip was: " + player.getAddress().getAddress().getHostAddress() + ")");
                } else {
                    f.set("Common-Ip", player.getAddress().getAddress().getHostAddress());
                }
                f.set("Last-IP", player.getAddress().getAddress().getHostAddress());

                Date NewJoin = new Date();

                f.set("name", player.getName());

                if (!f.isSet("Full-UUID") || f.getString("Full-UUID").isEmpty()) {
                    f.set("Full-UUID", player.getUniqueId().toString());
                }
                if (!f.isSet("Simple-UUID") || f.getString("Simple-UUID").isEmpty()) {
                    f.set("Simple-UUID", player.getUniqueId().toString().replace("-", ""));
                }
                if (!f.isSet("First-IP") || f.getString("First-IP").isEmpty()) {
                    f.set("First-IP", player.getAddress().getAddress().getHostAddress());
                }
                if (!f.isSet("Join-Date") || f.getString("Join-Date").isEmpty()) {
                    f.set("Join-Date", format.format(NewJoin));
                }

                if (f.getString("Password").isEmpty()) {
                    f.set("Registered", false);
                } else {
                    if (!f.getString("Password").isEmpty()) {
                        f.set("Registered", true);
                    }
                }

                cm.saveConfig();
            }

            for (Player online : Bukkit.getOnlinePlayers()) {
                if (f.getBoolean("Vanished")) {
                    online.hidePlayer(player);
                } else {
                    if (!f.getBoolean("Vanished")) {
                        online.showPlayer(player);
                    }
                }
            }
        }

        if (player != null) {

            try {
                if (isUsernamePremium(PlayerName)) {
                    if (FixingSkins) {
                        if (EnableSkinsPerms) {
                            if (player.hasPermission(SkinsPerm)) {
                                SetSkin.changeSkin(player, PlayerName);
                            } else {
                            }
                        } else {
                            if (NotSkinsPerms) {
                                SetSkin.changeSkin(player, player.getName());
                            }
                        }
                    } else {
                        if (!isUsernamePremium(PlayerName)) {
                            if (SkinNotEmpty) {
                                if (EnableSkinsPerms) {
                                    if (player.hasPermission(SkinsPerm)) {
                                        SetSkin.changeSkin(player, GetConfigs.getString("DefaultSkin"));
                                    } else {
                                    }
                                } else {
                                    if (NotSkinsPerms) {
                                        SetSkin.changeSkin(player, GetConfigs.getString("DefaultSkin"));
                                    }
                                }
                            } else {
                                if (SkinEmpty) {
                                }
                            }
                        }
                    }
                } else {
                    SetSkin.changeSkin(player, GetConfigs.getString("DefaultSkin"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        if (OpProtectOn) {

            if (player.isOp() && !player.hasPermission(OpPerm)) {
                player.kickPlayer(KickNotOp);
            }
            else if (!player.isOp() || player.hasPermission(OpPerm)) {


            }
        } else if (OpProtectionOff) {
            if (player.isOp() && !player.hasPermission(OpPerm)) {
                Bukkit.broadcast(OpWarning, String.valueOf(WarnPerm));
                Bukkit.getConsoleSender().sendMessage(OpWarning);
            }
            else if (!player.isOp() || player.hasPermission(OpPerm)) {

            }
        }

        FileConfiguration f = cm.getConfig();

        if (GetConfigs.getBoolean("ManageLogin")) {

            File PlayerFile = new File(getDataFolder(), player.getName() + " (" + player.getUniqueId().toString().replace("-", "") + ").yml");

            if (!f.getBoolean("Logged") && f.getBoolean("Registered")) {
                if (!GetMessages.getString("prefix").isEmpty()) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("Login")));
                } else if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("Login")));
                } else if (GetMessages.getString("prefix").isEmpty()) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("Login")));
                }

                new BukkitRunnable() {
                    int counter = GetConfigs.getInt("MaxLogin");
                    public void run() {
                        counter--;

                        FileConfiguration f = cm.getConfig();

                        f = YamlConfiguration.loadConfiguration(PlayerFile);
                        f.getDefaults();

                        if(!f.getBoolean("Logged")) {

                            f = YamlConfiguration.loadConfiguration(PlayerFile);
                            f.getDefaults();

                            String LoginTitle = ChatColor.translateAlternateColorCodes('&', GetMessages.getString("LoginTitle").replace("{time}", String.valueOf(counter)));
                            String LoginSubTitle = ChatColor.translateAlternateColorCodes('&', GetMessages.getString("LoginSubtitle").replace("{time}", String.valueOf(counter)));

                            player.sendTitle(LoginTitle, LoginSubTitle);
                            new ActionBar().sendActionBar(player, ChatColor.translateAlternateColorCodes('&', GetMessages.getString("LoginActionbar").replace("{time}", String.valueOf(counter))));

                        } else {
                            cancel();
                        }
                    }
                }.runTaskTimer(Main.getInst(), GetConfigs.getInt("MaxLogin"), 20);

                scheduler.scheduleSyncDelayedTask(Main.getInst(), new Runnable() {
                    @Override
                    public void run() {

                        FileConfiguration f = cm.getConfig();

                        f = YamlConfiguration.loadConfiguration(PlayerFile);
                        f.getDefaults();

                        if (!f.getBoolean("Logged") && f.getBoolean("Registered")) {
                            player.kickPlayer(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("LoginTimeout")));
                        } else {
                        }
                    }
                }, 20*GetConfigs.getInt("MaxLogin"));
            } else {
                if (!f.getBoolean("Logged") && !f.getBoolean("Registered")) {
                    if (!GetMessages.getString("prefix").isEmpty()) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("prefix") + GetMessages.getString("Register")));
                    } else if (GetMessages.getString("prefix").equalsIgnoreCase("NoPrefix")) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("Register")));
                    } else if (GetMessages.getString("prefix").isEmpty()) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lEpicCome &7>> " + GetMessages.getString("Register")));
                    }

                    new BukkitRunnable() {
                        int counter = GetConfigs.getInt("MaxLogin");
                        public void run() {
                            counter--;

                            FileConfiguration f = cm.getConfig();

                            f = YamlConfiguration.loadConfiguration(PlayerFile);
                            f.getDefaults();

                            if(!f.getBoolean("Registered")) {

                                f = YamlConfiguration.loadConfiguration(PlayerFile);
                                f.getDefaults();

                                String LoginTitle = ChatColor.translateAlternateColorCodes('&', GetMessages.getString("RegisterTitle").replace("{time}", String.valueOf(counter)));
                                String LoginSubTitle = ChatColor.translateAlternateColorCodes('&', GetMessages.getString("RegisterSubtitle").replace("{time}", String.valueOf(counter)));

                                player.sendTitle(LoginTitle, LoginSubTitle);
                                new ActionBar().sendActionBar(player, ChatColor.translateAlternateColorCodes('&', GetMessages.getString("RegisterActionbar").replace("{time}", String.valueOf(counter))));

                            } else {
                                cancel();
                            }
                        }
                    }.runTaskTimer(Main.getInst(), GetConfigs.getInt("MaxLogin"), 20);

                    scheduler.scheduleSyncDelayedTask(Main.getInst(), new Runnable() {
                        @Override
                        public void run() {

                            FileConfiguration f = cm.getConfig();

                            f = YamlConfiguration.loadConfiguration(PlayerFile);
                            f.getDefaults();

                            if (!f.getBoolean("Logged") && !f.getBoolean("Registered")) {
                                player.kickPlayer(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("RegisterTimeout")));
                            } else {
                            }
                        }
                    }, 20 * GetConfigs.getInt("MaxRegister"));
                } else {
                    if (f.getBoolean("Logged") && f.getBoolean("Registered")) {
                    }
                }
            }
        } else {
        }
    }
}