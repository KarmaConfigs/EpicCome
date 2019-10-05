package epic.welcome.message.handlers.events.Utils;

import epic.welcome.message.Main.Main;
import epic.welcome.message.handlers.plugin.register.Configs.RegisterConfig;
import epic.welcome.message.handlers.plugin.register.Messages.RegisterMessages;
import net.minecraft.server.v1_8_R3.EntityEnderDragon;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;

@SuppressWarnings("all")
public class EmulateJoinEvent implements Listener {

    private static File SpawnFile = new File(Main.getInst().getDataFolder(), "spawn.yml");

    private static FileConfiguration SPAWN = (new YamlConfiguration()).loadConfiguration(SpawnFile);

    public EmulateJoinEvent(Player player) {

        //Utils
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

        if (GetConfigs.getBoolean("JoinSpawn")) {

            if (SPAWN.getString("Spawn.World").isEmpty()) {
            } else {

                if (!SPAWN.getString("Spawn.World").isEmpty()) {

                    World World = Bukkit.getWorld(SPAWN.getString("Spawn.World"));

                    double SX = SPAWN.getDouble("Spawn.X");
                    double SY = SPAWN.getDouble("Spawn.Y");
                    double SZ = SPAWN.getDouble("Spawn.Z");

                    float SYa = (float) SPAWN.getDouble("Spawn.Yaw");
                    float SP = (float) SPAWN.getDouble("Spawn.Pitch");


                    Location Spawn = new Location(World, SX, SY, SZ);

                    Spawn.setYaw(SYa);
                    player.getLocation().setPitch(SP);

                    player.teleport(Spawn);
                }
            }
        } else {
        }

        if (TitleOn) {
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission(UserPerm)) {
                    if (TitlePublic) {
                        online.sendTitle(TitleMessage, SubtitleMsg);
                    } else {
                        if (TitleNotPublic) {
                            player.sendTitle(TitleMessage, SubtitleMsg);
                        }
                    }
                }
                if (player.hasPermission(VipPerm)) {
                    if (VipTitlePublic) {
                        online.sendTitle(VipTitle, VipSubtitle);
                    } else {
                        if (VipTitleNotPublic) {
                            player.sendTitle(VipTitle, VipSubtitle);
                        }
                    }
                }
                if (player.hasPermission(StaffPerm)) {
                    if (StaffTitlePublic) {
                        online.sendTitle(StaffTitle, StaffSubtitle);
                    } else {
                        if (StaffTitleNotPublic) {
                            player.sendTitle(StaffTitle, StaffSubtitle);
                        }
                    }
                }
            }
        } else {
        }

        if (MotdEnabled) {
            player.sendMessage(Line);
            player.sendMessage(Space);
            player.sendMessage(Motd);
            player.sendMessage(Space);
            player.sendMessage(NewsTitle);
            player.sendMessage(Space);
            player.sendMessage(News);
            player.sendMessage(Space);
            player.sendMessage(Line);
        }

        scheduler.scheduleSyncDelayedTask(Main.getInst(), new Runnable()
        {

            public void run()
            {
                if (LightingEnabled) {

                    if (player.hasPermission(LightPerm))
                    {
                        if (!GetConfigs.getBoolean("JoinSpawn")) {
                            playerloc.strikeLightningEffect(PlayerLocation);
                        }
                        else if (GetConfigs.getBoolean("JoinSpawn") &&
                                SPAWN.isSet("Spawn.World")) {
                            World SpawnWorld = Bukkit.getWorld(SPAWN.getString("Spawn.World"));

                            double SpX = SPAWN.getDouble("Spawn.X");
                            double SpY = SPAWN.getDouble("Spawn.Y");
                            double SpZ = SPAWN.getDouble("Spawn.Z");

                            Location SpawnLocation = new Location(SpawnWorld, SpX, SpY, SpZ);

                            playerloc.strikeLightningEffect(SpawnLocation);
                        }
                    }





                    if (IsUserLight) {
                        if (!GetConfigs.getBoolean("JoinSpawn")) {
                            playerloc.strikeLightningEffect(PlayerLocation);
                        }
                        else if (GetConfigs.getBoolean("JoinSpawn") &&
                                SPAWN.isSet("Spawn.World")) {
                            World SpawnWorld = Bukkit.getWorld(SPAWN.getString("Spawn.World"));

                            double SpX = SPAWN.getDouble("Spawn.X");
                            double SpY = SPAWN.getDouble("Spawn.Y");
                            double SpZ = SPAWN.getDouble("Spawn.Z");

                            Location SpawnLocation = new Location(SpawnWorld, SpX, SpY, SpZ);

                            playerloc.strikeLightningEffect(SpawnLocation);


                        }


                    }
                    else if (IsVipLight) {
                        if (player.hasPermission(VipPerm) || player.hasPermission(StaffPerm)) {
                            if (!GetConfigs.getBoolean("JoinSpawn")) {
                                playerloc.strikeLightningEffect(PlayerLocation);
                            }
                            else if (GetConfigs.getBoolean("JoinSpawn") &&
                                    SPAWN.isSet("Spawn.World")) {
                                World SpawnWorld = Bukkit.getWorld(SPAWN.getString("Spawn.World"));

                                double SpX = SPAWN.getDouble("Spawn.X");
                                double SpY = SPAWN.getDouble("Spawn.Y");
                                double SpZ = SPAWN.getDouble("Spawn.Z");

                                Location SpawnLocation = new Location(SpawnWorld, SpX, SpY, SpZ);

                                playerloc.strikeLightningEffect(SpawnLocation);


                            }


                        }

                    }
                    else if (IsStaffLight &&
                            player.hasPermission(StaffPerm)) {
                        if (!GetConfigs.getBoolean("JoinSpawn")) {
                            playerloc.strikeLightningEffect(PlayerLocation);
                        }
                        else if (GetConfigs.getBoolean("JoinSpawn") &&
                                SPAWN.isSet("Spawn.World")) {
                            World SpawnWorld = Bukkit.getWorld(SPAWN.getString("Spawn.World"));

                            double SpX = SPAWN.getDouble("Spawn.X");
                            double SpY = SPAWN.getDouble("Spawn.Y");
                            double SpZ = SPAWN.getDouble("Spawn.Z");

                            Location SpawnLocation = new Location(SpawnWorld, SpX, SpY, SpZ);

                            playerloc.strikeLightningEffect(SpawnLocation);


                        }



                    }



                }
                else if (LightingDisabled && ForceLight) {
                    if (FLUser) {
                        if (!GetConfigs.getBoolean("JoinSpawn")) {
                            playerloc.strikeLightningEffect(PlayerLocation);
                        }
                        else if (GetConfigs.getBoolean("JoinSpawn") &&
                                SPAWN.isSet("Spawn.World")) {
                            World SpawnWorld = Bukkit.getWorld(SPAWN.getString("Spawn.World"));

                            double SpX = SPAWN.getDouble("Spawn.X");
                            double SpY = SPAWN.getDouble("Spawn.Y");
                            double SpZ = SPAWN.getDouble("Spawn.Z");

                            Location SpawnLocation = new Location(SpawnWorld, SpX, SpY, SpZ);

                            playerloc.strikeLightningEffect(SpawnLocation);


                        }


                    }
                    else if (FLVip) {
                        if (player.hasPermission(VipPerm) || player.hasPermission(StaffPerm)) {
                            if (!GetConfigs.getBoolean("JoinSpawn")) {
                                playerloc.strikeLightningEffect(PlayerLocation);
                            }
                            else if (GetConfigs.getBoolean("JoinSpawn") &&
                                    SPAWN.isSet("Spawn.World")) {
                                World SpawnWorld = Bukkit.getWorld(SPAWN.getString("Spawn.World"));

                                double SpX = SPAWN.getDouble("Spawn.X");
                                double SpY = SPAWN.getDouble("Spawn.Y");
                                double SpZ = SPAWN.getDouble("Spawn.Z");

                                Location SpawnLocation = new Location(SpawnWorld, SpX, SpY, SpZ);

                                playerloc.strikeLightningEffect(SpawnLocation);


                            }


                        }

                    }
                    else if (FLStaff &&
                            player.hasPermission(StaffPerm)) {
                        if (!GetConfigs.getBoolean("JoinSpawn")) {
                            playerloc.strikeLightningEffect(PlayerLocation);
                        }
                        else if (GetConfigs.getBoolean("JoinSpawn") &&
                                SPAWN.isSet("Spawn.World")) {
                            World SpawnWorld = Bukkit.getWorld(SPAWN.getString("Spawn.World"));

                            double SpX = SPAWN.getDouble("Spawn.X");
                            double SpY = SPAWN.getDouble("Spawn.Y");
                            double SpZ = SPAWN.getDouble("Spawn.Z");

                            Location SpawnLocation = new Location(SpawnWorld, SpX, SpY, SpZ);

                            playerloc.strikeLightningEffect(SpawnLocation);


                        }



                    }



                }
                else if (NoForceLight) {

                }
            }
        },36L);


        scheduler.scheduleSyncDelayedTask(Main.getInst(), new Runnable() {
            public void run() {

                if (ExplosionEnabled) {
                    if (!GetConfigs.getBoolean("JoinSpawn")) {
                        Location PlayerOver3 = new Location(playerloc, X, Y10, Z, Yaw, Pitch);
                        player.teleport(PlayerOver3);
                    } else if (GetConfigs.getBoolean("JoinSpawn") &&
                            !SPAWN.getString("Spawn.World").isEmpty()) {
                        World World = Bukkit.getWorld(SPAWN.getString("Spawn.World"));

                        double SX = SPAWN.getDouble("Spawn.X");
                        double SY = SPAWN.getDouble("Spawn.Y");
                        double SZ = SPAWN.getDouble("Spawn.Z");

                        Location PlayerOver3 = new Location(World, SX, SY + 10.0D, SZ);
                        player.teleport(PlayerOver3);
                    } else {
                        if (GetConfigs.getBoolean("JoinSpawn") && SPAWN.getString("Spawn.World").isEmpty()) {
                            Location PlayerOver3 = new Location(playerloc, X, Y10, Z, Yaw, Pitch);
                            player.teleport(PlayerOver3);
                        }
                    }


                    if (player.hasPermission(EnderPerm)) {
                        scheduler.scheduleSyncDelayedTask(Main.getInst(), new Runnable() {

                            public void run() {
                                if (!GetConfigs.getBoolean("JoinSpawn")) {

                                    playerloc.playSound(ActualPlayer, Sound.EXPLODE, 3.0F, 0.533F);

                                    player.playEffect(ActualPlayer, Effect.EXPLOSION_HUGE, 20);
                                    player.playEffect(ActualPlayer2, Effect.EXPLOSION_HUGE, 40);
                                    player.playEffect(ActualPlayer3, Effect.EXPLOSION_HUGE, 60);
                                    player.playEffect(ActualPlayer4, Effect.EXPLOSION_HUGE, 80);
                                    player.playEffect(ActualPlayer5, Effect.EXPLOSION_HUGE, 100);
                                    player.playEffect(ActualPlayer6, Effect.EXPLOSION_HUGE, 120);

                                    if (DragonEffects) {
                                        EntityEnderDragon dragon = new EntityEnderDragon(((CraftWorld) player.getWorld()).getHandle());
                                        dragon.setLocation(X, Y - 17.0D, Z, 0.0F, 180.0F);

                                        dragon.setPositionRotation(X, Y - 17.0D, Z, 0.0F, 180.0F);
                                        dragon.setCustomName(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("DragonBarMessage").replace("{player}", PlayerName).replace("{online}", String.valueOf(OnlinePlayers)).replace("{max}", String.valueOf(MaxPlayers))));
                                        dragon.setCustomNameVisible(true);

                                        PacketPlayOutSpawnEntityLiving packetSpawn = new PacketPlayOutSpawnEntityLiving(dragon);
                                        PacketPlayOutEntityStatus packet = new PacketPlayOutEntityStatus(dragon, (byte) 3);

                                        (((CraftPlayer) player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                        (((CraftPlayer) player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                    }

                                } else if (GetConfigs.getBoolean("JoinSpawn")) {

                                    if (SPAWN.isSet("Spawn.World")) {
                                        if (SPAWN.getString("Spawn.World").isEmpty()) {
                                            playerloc.playSound(ActualPlayer, Sound.EXPLODE, 3.0F, 0.533F);

                                            player.playEffect(ActualPlayer, Effect.EXPLOSION_HUGE, 20);
                                            player.playEffect(ActualPlayer2, Effect.EXPLOSION_HUGE, 40);
                                            player.playEffect(ActualPlayer3, Effect.EXPLOSION_HUGE, 60);
                                            player.playEffect(ActualPlayer4, Effect.EXPLOSION_HUGE, 80);
                                            player.playEffect(ActualPlayer5, Effect.EXPLOSION_HUGE, 100);
                                            player.playEffect(ActualPlayer6, Effect.EXPLOSION_HUGE, 120);

                                            if (DragonEffects) {
                                                EntityEnderDragon dragon = new EntityEnderDragon(((CraftWorld) player.getWorld()).getHandle());
                                                dragon.setLocation(X, Y - 17.0D, Z, 0.0F, 180.0F);

                                                dragon.setPositionRotation(X, Y - 17.0D, Z, 0.0F, 180.0F);
                                                dragon.setCustomName(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("DragonBarMessage").replace("{player}", PlayerName).replace("{online}", String.valueOf(OnlinePlayers)).replace("{max}", String.valueOf(MaxPlayers))));
                                                dragon.setCustomNameVisible(true);

                                                PacketPlayOutSpawnEntityLiving packetSpawn = new PacketPlayOutSpawnEntityLiving(dragon);
                                                PacketPlayOutEntityStatus packet = new PacketPlayOutEntityStatus(dragon, (byte) 3);

                                                (((CraftPlayer) player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                                (((CraftPlayer) player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                            }
                                        } else {
                                            if (!SPAWN.getString("Spawn.World").isEmpty()) {
                                                double X = SPAWN.getDouble("Spawn.X");
                                                double Y = SPAWN.getDouble("Spawn.Y");
                                                double Z = SPAWN.getDouble("Spawn.Z");

                                                float Yaw = (float) SPAWN.getDouble("Spawn.Yaw");
                                                float Pitch = (float) SPAWN.getDouble("Spawn.Yaw");

                                                Location ActualPlayer = new Location(playerloc, X, Y, Z, Yaw, Pitch);
                                                Location ActualPlayer2 = new Location(playerloc, X + 1.0D, Y, Z, Yaw, Pitch);
                                                Location ActualPlayer3 = new Location(playerloc, X, Y + 1.0D, Z, Yaw, Pitch);
                                                Location ActualPlayer4 = new Location(playerloc, X, Y, Z + 1.0D, Yaw, Pitch);
                                                Location ActualPlayer5 = new Location(playerloc, X, Y, Z, Yaw + 1.0F, Pitch);
                                                Location ActualPlayer6 = new Location(playerloc, X, Y, Z, Yaw + 1.0F, Pitch + 1.0F);

                                                player.playEffect(ActualPlayer, Effect.EXPLOSION_HUGE, 20);
                                                player.playEffect(ActualPlayer2, Effect.EXPLOSION_HUGE, 40);
                                                player.playEffect(ActualPlayer3, Effect.EXPLOSION_HUGE, 60);
                                                player.playEffect(ActualPlayer4, Effect.EXPLOSION_HUGE, 80);
                                                player.playEffect(ActualPlayer5, Effect.EXPLOSION_HUGE, 100);
                                                player.playEffect(ActualPlayer6, Effect.EXPLOSION_HUGE, 120);

                                                playerloc.playSound(ActualPlayer, Sound.EXPLODE, 3.0F, 0.533F);

                                                if (DragonEffects) {
                                                    EntityEnderDragon dragon = new EntityEnderDragon(((CraftWorld) player.getWorld()).getHandle());
                                                    dragon.setLocation(X, Y - 17.0D, Z, 0.0F, 180.0F);

                                                    dragon.setPositionRotation(X, Y - 17.0D, Z, 0.0F, 180.0F);
                                                    dragon.setCustomName(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("DragonBarMessage").replace("{player}", PlayerName).replace("{online}", String.valueOf(OnlinePlayers)).replace("{max}", String.valueOf(MaxPlayers))));
                                                    dragon.setCustomNameVisible(true);

                                                    PacketPlayOutSpawnEntityLiving packetSpawn = new PacketPlayOutSpawnEntityLiving(dragon);
                                                    PacketPlayOutEntityStatus packet = new PacketPlayOutEntityStatus(dragon, (byte) 3);

                                                    (((CraftPlayer) player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                                    (((CraftPlayer) player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }, 26L);
                    }


                    if (IsUserExplosion) {
                        if (!GetConfigs.getBoolean("JoinSpawn")) {
                            Location PlayerOver3 = new Location(playerloc, X, Y10, Z, Yaw, Pitch);
                            player.teleport(PlayerOver3);
                        } else if (GetConfigs.getBoolean("JoinSpawn") &&
                                !SPAWN.getString("Spawn.World").isEmpty()) {
                            World World = Bukkit.getWorld(SPAWN.getString("Spawn.World"));

                            double SX = SPAWN.getDouble("Spawn.X");
                            double SY = SPAWN.getDouble("Spawn.Y");
                            double SZ = SPAWN.getDouble("Spawn.Z");

                            Location PlayerOver3 = new Location(World, SX, SY + 10.0D, SZ);
                            player.teleport(PlayerOver3);
                        } else {
                            if (GetConfigs.getBoolean("JoinSpawn") && SPAWN.getString("Spawn.World").isEmpty()) {
                                Location PlayerOver3 = new Location(playerloc, X, Y10, Z, Yaw, Pitch);
                                player.teleport(PlayerOver3);
                            }
                        }


                        if (player.hasPermission(EnderPerm)) {
                            scheduler.scheduleSyncDelayedTask(Main.getInst(), new Runnable() {

                                public void run() {
                                    if (!GetConfigs.getBoolean("JoinSpawn")) {

                                        playerloc.playSound(ActualPlayer, Sound.EXPLODE, 3.0F, 0.533F);

                                        player.playEffect(ActualPlayer, Effect.EXPLOSION_HUGE, 20);
                                        player.playEffect(ActualPlayer2, Effect.EXPLOSION_HUGE, 40);
                                        player.playEffect(ActualPlayer3, Effect.EXPLOSION_HUGE, 60);
                                        player.playEffect(ActualPlayer4, Effect.EXPLOSION_HUGE, 80);
                                        player.playEffect(ActualPlayer5, Effect.EXPLOSION_HUGE, 100);
                                        player.playEffect(ActualPlayer6, Effect.EXPLOSION_HUGE, 120);

                                        if (DragonEffects) {
                                            EntityEnderDragon dragon = new EntityEnderDragon(((CraftWorld) player.getWorld()).getHandle());
                                            dragon.setLocation(X, Y - 17.0D, Z, 0.0F, 180.0F);

                                            dragon.setPositionRotation(X, Y - 17.0D, Z, 0.0F, 180.0F);
                                            dragon.setCustomName(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("DragonBarMessage").replace("{player}", PlayerName).replace("{online}", String.valueOf(OnlinePlayers)).replace("{max}", String.valueOf(MaxPlayers))));
                                            dragon.setCustomNameVisible(true);

                                            PacketPlayOutSpawnEntityLiving packetSpawn = new PacketPlayOutSpawnEntityLiving(dragon);
                                            PacketPlayOutEntityStatus packet = new PacketPlayOutEntityStatus(dragon, (byte) 3);

                                            (((CraftPlayer) player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                            (((CraftPlayer) player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                        }

                                    } else if (GetConfigs.getBoolean("JoinSpawn")) {

                                        if (SPAWN.isSet("Spawn.World")) {
                                            if (SPAWN.getString("Spawn.World").isEmpty()) {
                                                playerloc.playSound(ActualPlayer, Sound.EXPLODE, 3.0F, 0.533F);

                                                player.playEffect(ActualPlayer, Effect.EXPLOSION_HUGE, 20);
                                                player.playEffect(ActualPlayer2, Effect.EXPLOSION_HUGE, 40);
                                                player.playEffect(ActualPlayer3, Effect.EXPLOSION_HUGE, 60);
                                                player.playEffect(ActualPlayer4, Effect.EXPLOSION_HUGE, 80);
                                                player.playEffect(ActualPlayer5, Effect.EXPLOSION_HUGE, 100);
                                                player.playEffect(ActualPlayer6, Effect.EXPLOSION_HUGE, 120);

                                                if (DragonEffects) {
                                                    EntityEnderDragon dragon = new EntityEnderDragon(((CraftWorld) player.getWorld()).getHandle());
                                                    dragon.setLocation(X, Y - 17.0D, Z, 0.0F, 180.0F);

                                                    dragon.setPositionRotation(X, Y - 17.0D, Z, 0.0F, 180.0F);
                                                    dragon.setCustomName(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("DragonBarMessage").replace("{player}", PlayerName).replace("{online}", String.valueOf(OnlinePlayers)).replace("{max}", String.valueOf(MaxPlayers))));
                                                    dragon.setCustomNameVisible(true);

                                                    PacketPlayOutSpawnEntityLiving packetSpawn = new PacketPlayOutSpawnEntityLiving(dragon);
                                                    PacketPlayOutEntityStatus packet = new PacketPlayOutEntityStatus(dragon, (byte) 3);

                                                    (((CraftPlayer) player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                                    (((CraftPlayer) player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                                }
                                            } else {
                                                if (!SPAWN.getString("Spawn.World").isEmpty()) {
                                                    double X = SPAWN.getDouble("Spawn.X");
                                                    double Y = SPAWN.getDouble("Spawn.Y");
                                                    double Z = SPAWN.getDouble("Spawn.Z");

                                                    float Yaw = (float) SPAWN.getDouble("Spawn.Yaw");
                                                    float Pitch = (float) SPAWN.getDouble("Spawn.Yaw");

                                                    Location ActualPlayer = new Location(playerloc, X, Y, Z, Yaw, Pitch);
                                                    Location ActualPlayer2 = new Location(playerloc, X + 1.0D, Y, Z, Yaw, Pitch);
                                                    Location ActualPlayer3 = new Location(playerloc, X, Y + 1.0D, Z, Yaw, Pitch);
                                                    Location ActualPlayer4 = new Location(playerloc, X, Y, Z + 1.0D, Yaw, Pitch);
                                                    Location ActualPlayer5 = new Location(playerloc, X, Y, Z, Yaw + 1.0F, Pitch);
                                                    Location ActualPlayer6 = new Location(playerloc, X, Y, Z, Yaw + 1.0F, Pitch + 1.0F);

                                                    player.playEffect(ActualPlayer, Effect.EXPLOSION_HUGE, 20);
                                                    player.playEffect(ActualPlayer2, Effect.EXPLOSION_HUGE, 40);
                                                    player.playEffect(ActualPlayer3, Effect.EXPLOSION_HUGE, 60);
                                                    player.playEffect(ActualPlayer4, Effect.EXPLOSION_HUGE, 80);
                                                    player.playEffect(ActualPlayer5, Effect.EXPLOSION_HUGE, 100);
                                                    player.playEffect(ActualPlayer6, Effect.EXPLOSION_HUGE, 120);

                                                    playerloc.playSound(ActualPlayer, Sound.EXPLODE, 3.0F, 0.533F);

                                                    if (DragonEffects) {
                                                        EntityEnderDragon dragon = new EntityEnderDragon(((CraftWorld) player.getWorld()).getHandle());
                                                        dragon.setLocation(X, Y - 17.0D, Z, 0.0F, 180.0F);

                                                        dragon.setPositionRotation(X, Y - 17.0D, Z, 0.0F, 180.0F);
                                                        dragon.setCustomName(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("DragonBarMessage").replace("{player}", PlayerName).replace("{online}", String.valueOf(OnlinePlayers)).replace("{max}", String.valueOf(MaxPlayers))));
                                                        dragon.setCustomNameVisible(true);

                                                        PacketPlayOutSpawnEntityLiving packetSpawn = new PacketPlayOutSpawnEntityLiving(dragon);
                                                        PacketPlayOutEntityStatus packet = new PacketPlayOutEntityStatus(dragon, (byte) 3);

                                                        (((CraftPlayer) player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                                        (((CraftPlayer) player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }, 26L);
                        }
                    } else if (IsVipExplosion) {
                        if (player.hasPermission(VipPerm) || player.hasPermission(StaffPerm)) {
                            if (!GetConfigs.getBoolean("JoinSpawn")) {
                                Location PlayerOver3 = new Location(playerloc, X, Y10, Z, Yaw, Pitch);
                                player.teleport(PlayerOver3);
                            } else if (GetConfigs.getBoolean("JoinSpawn") &&
                                    !SPAWN.getString("Spawn.World").isEmpty()) {
                                World World = Bukkit.getWorld(SPAWN.getString("Spawn.World"));

                                double SX = SPAWN.getDouble("Spawn.X");
                                double SY = SPAWN.getDouble("Spawn.Y");
                                double SZ = SPAWN.getDouble("Spawn.Z");

                                Location PlayerOver3 = new Location(World, SX, SY + 10.0D, SZ);
                                player.teleport(PlayerOver3);
                            } else {
                                if (GetConfigs.getBoolean("JoinSpawn") && SPAWN.getString("Spawn.World").isEmpty()) {
                                    Location PlayerOver3 = new Location(playerloc, X, Y10, Z, Yaw, Pitch);
                                    player.teleport(PlayerOver3);
                                }
                            }


                            if (player.hasPermission(EnderPerm)) {
                                scheduler.scheduleSyncDelayedTask(Main.getInst(), new Runnable() {

                                    public void run() {
                                        if (!GetConfigs.getBoolean("JoinSpawn")) {

                                            playerloc.playSound(ActualPlayer, Sound.EXPLODE, 3.0F, 0.533F);

                                            player.playEffect(ActualPlayer, Effect.EXPLOSION_HUGE, 20);
                                            player.playEffect(ActualPlayer2, Effect.EXPLOSION_HUGE, 40);
                                            player.playEffect(ActualPlayer3, Effect.EXPLOSION_HUGE, 60);
                                            player.playEffect(ActualPlayer4, Effect.EXPLOSION_HUGE, 80);
                                            player.playEffect(ActualPlayer5, Effect.EXPLOSION_HUGE, 100);
                                            player.playEffect(ActualPlayer6, Effect.EXPLOSION_HUGE, 120);

                                            if (DragonEffects) {
                                                EntityEnderDragon dragon = new EntityEnderDragon(((CraftWorld) player.getWorld()).getHandle());
                                                dragon.setLocation(X, Y - 17.0D, Z, 0.0F, 180.0F);

                                                dragon.setPositionRotation(X, Y - 17.0D, Z, 0.0F, 180.0F);
                                                dragon.setCustomName(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("DragonBarMessage").replace("{player}", PlayerName).replace("{online}", String.valueOf(OnlinePlayers)).replace("{max}", String.valueOf(MaxPlayers))));
                                                dragon.setCustomNameVisible(true);

                                                PacketPlayOutSpawnEntityLiving packetSpawn = new PacketPlayOutSpawnEntityLiving(dragon);
                                                PacketPlayOutEntityStatus packet = new PacketPlayOutEntityStatus(dragon, (byte) 3);

                                                (((CraftPlayer) player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                                (((CraftPlayer) player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                            }

                                        } else if (GetConfigs.getBoolean("JoinSpawn")) {

                                            if (SPAWN.isSet("Spawn.World")) {
                                                if (SPAWN.getString("Spawn.World").isEmpty()) {
                                                    playerloc.playSound(ActualPlayer, Sound.EXPLODE, 3.0F, 0.533F);

                                                    player.playEffect(ActualPlayer, Effect.EXPLOSION_HUGE, 20);
                                                    player.playEffect(ActualPlayer2, Effect.EXPLOSION_HUGE, 40);
                                                    player.playEffect(ActualPlayer3, Effect.EXPLOSION_HUGE, 60);
                                                    player.playEffect(ActualPlayer4, Effect.EXPLOSION_HUGE, 80);
                                                    player.playEffect(ActualPlayer5, Effect.EXPLOSION_HUGE, 100);
                                                    player.playEffect(ActualPlayer6, Effect.EXPLOSION_HUGE, 120);

                                                    if (DragonEffects) {
                                                        EntityEnderDragon dragon = new EntityEnderDragon(((CraftWorld) player.getWorld()).getHandle());
                                                        dragon.setLocation(X, Y - 17.0D, Z, 0.0F, 180.0F);

                                                        dragon.setPositionRotation(X, Y - 17.0D, Z, 0.0F, 180.0F);
                                                        dragon.setCustomName(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("DragonBarMessage").replace("{player}", PlayerName).replace("{online}", String.valueOf(OnlinePlayers)).replace("{max}", String.valueOf(MaxPlayers))));
                                                        dragon.setCustomNameVisible(true);

                                                        PacketPlayOutSpawnEntityLiving packetSpawn = new PacketPlayOutSpawnEntityLiving(dragon);
                                                        PacketPlayOutEntityStatus packet = new PacketPlayOutEntityStatus(dragon, (byte) 3);

                                                        (((CraftPlayer) player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                                        (((CraftPlayer) player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                                    }
                                                } else {
                                                    if (!SPAWN.getString("Spawn.World").isEmpty()) {
                                                        double X = SPAWN.getDouble("Spawn.X");
                                                        double Y = SPAWN.getDouble("Spawn.Y");
                                                        double Z = SPAWN.getDouble("Spawn.Z");

                                                        float Yaw = (float) SPAWN.getDouble("Spawn.Yaw");
                                                        float Pitch = (float) SPAWN.getDouble("Spawn.Yaw");

                                                        Location ActualPlayer = new Location(playerloc, X, Y, Z, Yaw, Pitch);
                                                        Location ActualPlayer2 = new Location(playerloc, X + 1.0D, Y, Z, Yaw, Pitch);
                                                        Location ActualPlayer3 = new Location(playerloc, X, Y + 1.0D, Z, Yaw, Pitch);
                                                        Location ActualPlayer4 = new Location(playerloc, X, Y, Z + 1.0D, Yaw, Pitch);
                                                        Location ActualPlayer5 = new Location(playerloc, X, Y, Z, Yaw + 1.0F, Pitch);
                                                        Location ActualPlayer6 = new Location(playerloc, X, Y, Z, Yaw + 1.0F, Pitch + 1.0F);

                                                        player.playEffect(ActualPlayer, Effect.EXPLOSION_HUGE, 20);
                                                        player.playEffect(ActualPlayer2, Effect.EXPLOSION_HUGE, 40);
                                                        player.playEffect(ActualPlayer3, Effect.EXPLOSION_HUGE, 60);
                                                        player.playEffect(ActualPlayer4, Effect.EXPLOSION_HUGE, 80);
                                                        player.playEffect(ActualPlayer5, Effect.EXPLOSION_HUGE, 100);
                                                        player.playEffect(ActualPlayer6, Effect.EXPLOSION_HUGE, 120);

                                                        playerloc.playSound(ActualPlayer, Sound.EXPLODE, 3.0F, 0.533F);

                                                        if (DragonEffects) {
                                                            EntityEnderDragon dragon = new EntityEnderDragon(((CraftWorld) player.getWorld()).getHandle());
                                                            dragon.setLocation(X, Y - 17.0D, Z, 0.0F, 180.0F);

                                                            dragon.setPositionRotation(X, Y - 17.0D, Z, 0.0F, 180.0F);
                                                            dragon.setCustomName(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("DragonBarMessage").replace("{player}", PlayerName).replace("{online}", String.valueOf(OnlinePlayers)).replace("{max}", String.valueOf(MaxPlayers))));
                                                            dragon.setCustomNameVisible(true);

                                                            PacketPlayOutSpawnEntityLiving packetSpawn = new PacketPlayOutSpawnEntityLiving(dragon);
                                                            PacketPlayOutEntityStatus packet = new PacketPlayOutEntityStatus(dragon, (byte) 3);

                                                            (((CraftPlayer) player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                                            (((CraftPlayer) player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }, 26L);
                            }
                        }

                    } else if (IsStaffExplosion &&
                            player.hasPermission(StaffPerm)) {
                        if (!GetConfigs.getBoolean("JoinSpawn")) {
                            Location PlayerOver3 = new Location(playerloc, X, Y10, Z, Yaw, Pitch);
                            player.teleport(PlayerOver3);
                        } else if (GetConfigs.getBoolean("JoinSpawn") &&
                                !SPAWN.getString("Spawn.World").isEmpty()) {
                            World World = Bukkit.getWorld(SPAWN.getString("Spawn.World"));

                            double SX = SPAWN.getDouble("Spawn.X");
                            double SY = SPAWN.getDouble("Spawn.Y");
                            double SZ = SPAWN.getDouble("Spawn.Z");

                            Location PlayerOver3 = new Location(World, SX, SY + 10.0D, SZ);
                            player.teleport(PlayerOver3);
                        } else {
                            if (GetConfigs.getBoolean("JoinSpawn") && SPAWN.getString("Spawn.World").isEmpty()) {
                                Location PlayerOver3 = new Location(playerloc, X, Y10, Z, Yaw, Pitch);
                                player.teleport(PlayerOver3);
                            }
                        }


                        if (player.hasPermission(EnderPerm)) {
                            scheduler.scheduleSyncDelayedTask(Main.getInst(), new Runnable() {

                                public void run() {
                                    if (!GetConfigs.getBoolean("JoinSpawn")) {

                                        playerloc.playSound(ActualPlayer, Sound.EXPLODE, 3.0F, 0.533F);

                                        player.playEffect(ActualPlayer, Effect.EXPLOSION_HUGE, 20);
                                        player.playEffect(ActualPlayer2, Effect.EXPLOSION_HUGE, 40);
                                        player.playEffect(ActualPlayer3, Effect.EXPLOSION_HUGE, 60);
                                        player.playEffect(ActualPlayer4, Effect.EXPLOSION_HUGE, 80);
                                        player.playEffect(ActualPlayer5, Effect.EXPLOSION_HUGE, 100);
                                        player.playEffect(ActualPlayer6, Effect.EXPLOSION_HUGE, 120);

                                        if (DragonEffects) {
                                            EntityEnderDragon dragon = new EntityEnderDragon(((CraftWorld) player.getWorld()).getHandle());
                                            dragon.setLocation(X, Y - 17.0D, Z, 0.0F, 180.0F);

                                            dragon.setPositionRotation(X, Y - 17.0D, Z, 0.0F, 180.0F);
                                            dragon.setCustomName(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("DragonBarMessage").replace("{player}", PlayerName).replace("{online}", String.valueOf(OnlinePlayers)).replace("{max}", String.valueOf(MaxPlayers))));
                                            dragon.setCustomNameVisible(true);

                                            PacketPlayOutSpawnEntityLiving packetSpawn = new PacketPlayOutSpawnEntityLiving(dragon);
                                            PacketPlayOutEntityStatus packet = new PacketPlayOutEntityStatus(dragon, (byte) 3);

                                            (((CraftPlayer) player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                            (((CraftPlayer) player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                        }

                                    } else if (GetConfigs.getBoolean("JoinSpawn")) {

                                        if (SPAWN.isSet("Spawn.World")) {
                                            if (SPAWN.getString("Spawn.World").isEmpty()) {
                                                playerloc.playSound(ActualPlayer, Sound.EXPLODE, 3.0F, 0.533F);

                                                player.playEffect(ActualPlayer, Effect.EXPLOSION_HUGE, 20);
                                                player.playEffect(ActualPlayer2, Effect.EXPLOSION_HUGE, 40);
                                                player.playEffect(ActualPlayer3, Effect.EXPLOSION_HUGE, 60);
                                                player.playEffect(ActualPlayer4, Effect.EXPLOSION_HUGE, 80);
                                                player.playEffect(ActualPlayer5, Effect.EXPLOSION_HUGE, 100);
                                                player.playEffect(ActualPlayer6, Effect.EXPLOSION_HUGE, 120);

                                                if (DragonEffects) {
                                                    EntityEnderDragon dragon = new EntityEnderDragon(((CraftWorld) player.getWorld()).getHandle());
                                                    dragon.setLocation(X, Y - 17.0D, Z, 0.0F, 180.0F);

                                                    dragon.setPositionRotation(X, Y - 17.0D, Z, 0.0F, 180.0F);
                                                    dragon.setCustomName(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("DragonBarMessage").replace("{player}", PlayerName).replace("{online}", String.valueOf(OnlinePlayers)).replace("{max}", String.valueOf(MaxPlayers))));
                                                    dragon.setCustomNameVisible(true);

                                                    PacketPlayOutSpawnEntityLiving packetSpawn = new PacketPlayOutSpawnEntityLiving(dragon);
                                                    PacketPlayOutEntityStatus packet = new PacketPlayOutEntityStatus(dragon, (byte) 3);

                                                    (((CraftPlayer) player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                                    (((CraftPlayer) player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                                }
                                            } else {
                                                if (!SPAWN.getString("Spawn.World").isEmpty()) {
                                                    double X = SPAWN.getDouble("Spawn.X");
                                                    double Y = SPAWN.getDouble("Spawn.Y");
                                                    double Z = SPAWN.getDouble("Spawn.Z");

                                                    float Yaw = (float) SPAWN.getDouble("Spawn.Yaw");
                                                    float Pitch = (float) SPAWN.getDouble("Spawn.Yaw");

                                                    Location ActualPlayer = new Location(playerloc, X, Y, Z, Yaw, Pitch);
                                                    Location ActualPlayer2 = new Location(playerloc, X + 1.0D, Y, Z, Yaw, Pitch);
                                                    Location ActualPlayer3 = new Location(playerloc, X, Y + 1.0D, Z, Yaw, Pitch);
                                                    Location ActualPlayer4 = new Location(playerloc, X, Y, Z + 1.0D, Yaw, Pitch);
                                                    Location ActualPlayer5 = new Location(playerloc, X, Y, Z, Yaw + 1.0F, Pitch);
                                                    Location ActualPlayer6 = new Location(playerloc, X, Y, Z, Yaw + 1.0F, Pitch + 1.0F);

                                                    player.playEffect(ActualPlayer, Effect.EXPLOSION_HUGE, 20);
                                                    player.playEffect(ActualPlayer2, Effect.EXPLOSION_HUGE, 40);
                                                    player.playEffect(ActualPlayer3, Effect.EXPLOSION_HUGE, 60);
                                                    player.playEffect(ActualPlayer4, Effect.EXPLOSION_HUGE, 80);
                                                    player.playEffect(ActualPlayer5, Effect.EXPLOSION_HUGE, 100);
                                                    player.playEffect(ActualPlayer6, Effect.EXPLOSION_HUGE, 120);

                                                    playerloc.playSound(ActualPlayer, Sound.EXPLODE, 3.0F, 0.533F);

                                                    if (DragonEffects) {
                                                        EntityEnderDragon dragon = new EntityEnderDragon(((CraftWorld) player.getWorld()).getHandle());
                                                        dragon.setLocation(X, Y - 17.0D, Z, 0.0F, 180.0F);

                                                        dragon.setPositionRotation(X, Y - 17.0D, Z, 0.0F, 180.0F);
                                                        dragon.setCustomName(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("DragonBarMessage").replace("{player}", PlayerName).replace("{online}", String.valueOf(OnlinePlayers)).replace("{max}", String.valueOf(MaxPlayers))));
                                                        dragon.setCustomNameVisible(true);

                                                        PacketPlayOutSpawnEntityLiving packetSpawn = new PacketPlayOutSpawnEntityLiving(dragon);
                                                        PacketPlayOutEntityStatus packet = new PacketPlayOutEntityStatus(dragon, (byte) 3);

                                                        (((CraftPlayer) player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                                        (((CraftPlayer) player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }, 26L);
                        }
                    }
                } else if (ExplosionDisabled && ForceExplosion) {
                    if (XPUser) {
                        if (!GetConfigs.getBoolean("JoinSpawn")) {
                            Location PlayerOver3 = new Location(playerloc, X, Y10, Z, Yaw, Pitch);
                            player.teleport(PlayerOver3);
                        }
                        else if (GetConfigs.getBoolean("JoinSpawn") &&
                                !SPAWN.getString("Spawn.World").isEmpty()) {
                            World World = Bukkit.getWorld(SPAWN.getString("Spawn.World"));

                            double SX = SPAWN.getDouble("Spawn.X");
                            double SY = SPAWN.getDouble("Spawn.Y");
                            double SZ = SPAWN.getDouble("Spawn.Z");

                            Location PlayerOver3 = new Location(World, SX, SY + 10.0D, SZ);
                            player.teleport(PlayerOver3);
                        } else {
                            if (GetConfigs.getBoolean("JoinSpawn") && SPAWN.getString("Spawn.World").isEmpty()) {
                                Location PlayerOver3 = new Location(playerloc, X, Y10, Z, Yaw, Pitch);
                                player.teleport(PlayerOver3);
                            }
                        }




                        if (player.hasPermission(EnderPerm))
                        {
                            scheduler.scheduleSyncDelayedTask(Main.getInst(), new Runnable()
                            {

                                public void run()
                                {
                                    if (!GetConfigs.getBoolean("JoinSpawn")) {

                                        playerloc.playSound(ActualPlayer, Sound.EXPLODE, 3.0F, 0.533F);

                                        player.playEffect(ActualPlayer, Effect.EXPLOSION_HUGE, 20);
                                        player.playEffect(ActualPlayer2, Effect.EXPLOSION_HUGE, 40);
                                        player.playEffect(ActualPlayer3, Effect.EXPLOSION_HUGE, 60);
                                        player.playEffect(ActualPlayer4, Effect.EXPLOSION_HUGE, 80);
                                        player.playEffect(ActualPlayer5, Effect.EXPLOSION_HUGE, 100);
                                        player.playEffect(ActualPlayer6, Effect.EXPLOSION_HUGE, 120);

                                        if (DragonEffects)
                                        {
                                            EntityEnderDragon dragon = new EntityEnderDragon(((CraftWorld)player.getWorld()).getHandle());
                                            dragon.setLocation(X, Y - 17.0D, Z, 0.0F, 180.0F);

                                            dragon.setPositionRotation(X, Y - 17.0D, Z, 0.0F, 180.0F);
                                            dragon.setCustomName(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("DragonBarMessage").replace("{player}", PlayerName).replace("{online}", String.valueOf(OnlinePlayers)).replace("{max}", String.valueOf(MaxPlayers))));
                                            dragon.setCustomNameVisible(true);

                                            PacketPlayOutSpawnEntityLiving packetSpawn = new PacketPlayOutSpawnEntityLiving(dragon);
                                            PacketPlayOutEntityStatus packet = new PacketPlayOutEntityStatus(dragon, (byte)3);

                                            (((CraftPlayer)player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                            (((CraftPlayer)player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                        }

                                    } else if (GetConfigs.getBoolean("JoinSpawn")) {

                                        if (SPAWN.isSet("Spawn.World"))
                                        {
                                            if (SPAWN.getString("Spawn.World").isEmpty()) {
                                                playerloc.playSound(ActualPlayer, Sound.EXPLODE, 3.0F, 0.533F);

                                                player.playEffect(ActualPlayer, Effect.EXPLOSION_HUGE, 20);
                                                player.playEffect(ActualPlayer2, Effect.EXPLOSION_HUGE, 40);
                                                player.playEffect(ActualPlayer3, Effect.EXPLOSION_HUGE, 60);
                                                player.playEffect(ActualPlayer4, Effect.EXPLOSION_HUGE, 80);
                                                player.playEffect(ActualPlayer5, Effect.EXPLOSION_HUGE, 100);
                                                player.playEffect(ActualPlayer6, Effect.EXPLOSION_HUGE, 120);

                                                if (DragonEffects)
                                                {
                                                    EntityEnderDragon dragon = new EntityEnderDragon(((CraftWorld)player.getWorld()).getHandle());
                                                    dragon.setLocation(X, Y - 17.0D, Z, 0.0F, 180.0F);

                                                    dragon.setPositionRotation(X, Y - 17.0D, Z, 0.0F, 180.0F);
                                                    dragon.setCustomName(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("DragonBarMessage").replace("{player}", PlayerName).replace("{online}", String.valueOf(OnlinePlayers)).replace("{max}", String.valueOf(MaxPlayers))));
                                                    dragon.setCustomNameVisible(true);

                                                    PacketPlayOutSpawnEntityLiving packetSpawn = new PacketPlayOutSpawnEntityLiving(dragon);
                                                    PacketPlayOutEntityStatus packet = new PacketPlayOutEntityStatus(dragon, (byte)3);

                                                    (((CraftPlayer)player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                                    (((CraftPlayer)player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                                }
                                            } else {
                                                if (!SPAWN.getString("Spawn.World").isEmpty()) {
                                                    double X = SPAWN.getDouble("Spawn.X");
                                                    double Y = SPAWN.getDouble("Spawn.Y");
                                                    double Z = SPAWN.getDouble("Spawn.Z");

                                                    float Yaw = (float)SPAWN.getDouble("Spawn.Yaw");
                                                    float Pitch = (float)SPAWN.getDouble("Spawn.Yaw");

                                                    Location ActualPlayer = new Location(playerloc, X, Y, Z, Yaw, Pitch);
                                                    Location ActualPlayer2 = new Location(playerloc, X + 1.0D, Y, Z, Yaw, Pitch);
                                                    Location ActualPlayer3 = new Location(playerloc, X, Y + 1.0D, Z, Yaw, Pitch);
                                                    Location ActualPlayer4 = new Location(playerloc, X, Y, Z + 1.0D, Yaw, Pitch);
                                                    Location ActualPlayer5 = new Location(playerloc, X, Y, Z, Yaw + 1.0F, Pitch);
                                                    Location ActualPlayer6 = new Location(playerloc, X, Y, Z, Yaw + 1.0F, Pitch + 1.0F);

                                                    player.playEffect(ActualPlayer, Effect.EXPLOSION_HUGE, 20);
                                                    player.playEffect(ActualPlayer2, Effect.EXPLOSION_HUGE, 40);
                                                    player.playEffect(ActualPlayer3, Effect.EXPLOSION_HUGE, 60);
                                                    player.playEffect(ActualPlayer4, Effect.EXPLOSION_HUGE, 80);
                                                    player.playEffect(ActualPlayer5, Effect.EXPLOSION_HUGE, 100);
                                                    player.playEffect(ActualPlayer6, Effect.EXPLOSION_HUGE, 120);

                                                    playerloc.playSound(ActualPlayer, Sound.EXPLODE, 3.0F, 0.533F);

                                                    if (DragonEffects)
                                                    {
                                                        EntityEnderDragon dragon = new EntityEnderDragon(((CraftWorld)player.getWorld()).getHandle());
                                                        dragon.setLocation(X, Y - 17.0D, Z, 0.0F, 180.0F);

                                                        dragon.setPositionRotation(X, Y - 17.0D, Z, 0.0F, 180.0F);
                                                        dragon.setCustomName(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("DragonBarMessage").replace("{player}", PlayerName).replace("{online}", String.valueOf(OnlinePlayers)).replace("{max}", String.valueOf(MaxPlayers))));
                                                        dragon.setCustomNameVisible(true);

                                                        PacketPlayOutSpawnEntityLiving packetSpawn = new PacketPlayOutSpawnEntityLiving(dragon);
                                                        PacketPlayOutEntityStatus packet = new PacketPlayOutEntityStatus(dragon, (byte)3);

                                                        (((CraftPlayer)player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                                        (((CraftPlayer)player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            },26L);
                        }
                    } else if (XPVip) {
                        if (player.hasPermission(VipPerm) || player.hasPermission(StaffPerm)) {
                            if (!GetConfigs.getBoolean("JoinSpawn")) {
                                Location PlayerOver3 = new Location(playerloc, X, Y10, Z, Yaw, Pitch);
                                player.teleport(PlayerOver3);
                            }
                            else if (GetConfigs.getBoolean("JoinSpawn") &&
                                    !SPAWN.getString("Spawn.World").isEmpty()) {
                                World World = Bukkit.getWorld(SPAWN.getString("Spawn.World"));

                                double SX = SPAWN.getDouble("Spawn.X");
                                double SY = SPAWN.getDouble("Spawn.Y");
                                double SZ = SPAWN.getDouble("Spawn.Z");

                                Location PlayerOver3 = new Location(World, SX, SY + 10.0D, SZ);
                                player.teleport(PlayerOver3);
                            } else {
                                if (GetConfigs.getBoolean("JoinSpawn") && SPAWN.getString("Spawn.World").isEmpty()) {
                                    Location PlayerOver3 = new Location(playerloc, X, Y10, Z, Yaw, Pitch);
                                    player.teleport(PlayerOver3);
                                }
                            }




                            if (player.hasPermission(EnderPerm))
                            {
                                scheduler.scheduleSyncDelayedTask(Main.getInst(), new Runnable()
                                {

                                    public void run()
                                    {
                                        if (!GetConfigs.getBoolean("JoinSpawn")) {

                                            playerloc.playSound(ActualPlayer, Sound.EXPLODE, 3.0F, 0.533F);

                                            player.playEffect(ActualPlayer, Effect.EXPLOSION_HUGE, 20);
                                            player.playEffect(ActualPlayer2, Effect.EXPLOSION_HUGE, 40);
                                            player.playEffect(ActualPlayer3, Effect.EXPLOSION_HUGE, 60);
                                            player.playEffect(ActualPlayer4, Effect.EXPLOSION_HUGE, 80);
                                            player.playEffect(ActualPlayer5, Effect.EXPLOSION_HUGE, 100);
                                            player.playEffect(ActualPlayer6, Effect.EXPLOSION_HUGE, 120);

                                            if (DragonEffects)
                                            {
                                                EntityEnderDragon dragon = new EntityEnderDragon(((CraftWorld)player.getWorld()).getHandle());
                                                dragon.setLocation(X, Y - 17.0D, Z, 0.0F, 180.0F);

                                                dragon.setPositionRotation(X, Y - 17.0D, Z, 0.0F, 180.0F);
                                                dragon.setCustomName(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("DragonBarMessage").replace("{player}", PlayerName).replace("{online}", String.valueOf(OnlinePlayers)).replace("{max}", String.valueOf(MaxPlayers))));
                                                dragon.setCustomNameVisible(true);

                                                PacketPlayOutSpawnEntityLiving packetSpawn = new PacketPlayOutSpawnEntityLiving(dragon);
                                                PacketPlayOutEntityStatus packet = new PacketPlayOutEntityStatus(dragon, (byte)3);

                                                (((CraftPlayer)player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                                (((CraftPlayer)player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                            }

                                        } else if (GetConfigs.getBoolean("JoinSpawn")) {

                                            if (SPAWN.isSet("Spawn.World"))
                                            {
                                                if (SPAWN.getString("Spawn.World").isEmpty()) {
                                                    playerloc.playSound(ActualPlayer, Sound.EXPLODE, 3.0F, 0.533F);

                                                    player.playEffect(ActualPlayer, Effect.EXPLOSION_HUGE, 20);
                                                    player.playEffect(ActualPlayer2, Effect.EXPLOSION_HUGE, 40);
                                                    player.playEffect(ActualPlayer3, Effect.EXPLOSION_HUGE, 60);
                                                    player.playEffect(ActualPlayer4, Effect.EXPLOSION_HUGE, 80);
                                                    player.playEffect(ActualPlayer5, Effect.EXPLOSION_HUGE, 100);
                                                    player.playEffect(ActualPlayer6, Effect.EXPLOSION_HUGE, 120);

                                                    if (DragonEffects)
                                                    {
                                                        EntityEnderDragon dragon = new EntityEnderDragon(((CraftWorld)player.getWorld()).getHandle());
                                                        dragon.setLocation(X, Y - 17.0D, Z, 0.0F, 180.0F);

                                                        dragon.setPositionRotation(X, Y - 17.0D, Z, 0.0F, 180.0F);
                                                        dragon.setCustomName(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("DragonBarMessage").replace("{player}", PlayerName).replace("{online}", String.valueOf(OnlinePlayers)).replace("{max}", String.valueOf(MaxPlayers))));
                                                        dragon.setCustomNameVisible(true);

                                                        PacketPlayOutSpawnEntityLiving packetSpawn = new PacketPlayOutSpawnEntityLiving(dragon);
                                                        PacketPlayOutEntityStatus packet = new PacketPlayOutEntityStatus(dragon, (byte)3);

                                                        (((CraftPlayer)player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                                        (((CraftPlayer)player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                                    }
                                                } else {
                                                    if (!SPAWN.getString("Spawn.World").isEmpty()) {
                                                        double X = SPAWN.getDouble("Spawn.X");
                                                        double Y = SPAWN.getDouble("Spawn.Y");
                                                        double Z = SPAWN.getDouble("Spawn.Z");

                                                        float Yaw = (float)SPAWN.getDouble("Spawn.Yaw");
                                                        float Pitch = (float)SPAWN.getDouble("Spawn.Yaw");

                                                        Location ActualPlayer = new Location(playerloc, X, Y, Z, Yaw, Pitch);
                                                        Location ActualPlayer2 = new Location(playerloc, X + 1.0D, Y, Z, Yaw, Pitch);
                                                        Location ActualPlayer3 = new Location(playerloc, X, Y + 1.0D, Z, Yaw, Pitch);
                                                        Location ActualPlayer4 = new Location(playerloc, X, Y, Z + 1.0D, Yaw, Pitch);
                                                        Location ActualPlayer5 = new Location(playerloc, X, Y, Z, Yaw + 1.0F, Pitch);
                                                        Location ActualPlayer6 = new Location(playerloc, X, Y, Z, Yaw + 1.0F, Pitch + 1.0F);

                                                        player.playEffect(ActualPlayer, Effect.EXPLOSION_HUGE, 20);
                                                        player.playEffect(ActualPlayer2, Effect.EXPLOSION_HUGE, 40);
                                                        player.playEffect(ActualPlayer3, Effect.EXPLOSION_HUGE, 60);
                                                        player.playEffect(ActualPlayer4, Effect.EXPLOSION_HUGE, 80);
                                                        player.playEffect(ActualPlayer5, Effect.EXPLOSION_HUGE, 100);
                                                        player.playEffect(ActualPlayer6, Effect.EXPLOSION_HUGE, 120);

                                                        playerloc.playSound(ActualPlayer, Sound.EXPLODE, 3.0F, 0.533F);

                                                        if (DragonEffects)
                                                        {
                                                            EntityEnderDragon dragon = new EntityEnderDragon(((CraftWorld)player.getWorld()).getHandle());
                                                            dragon.setLocation(X, Y - 17.0D, Z, 0.0F, 180.0F);

                                                            dragon.setPositionRotation(X, Y - 17.0D, Z, 0.0F, 180.0F);
                                                            dragon.setCustomName(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("DragonBarMessage").replace("{player}", PlayerName).replace("{online}", String.valueOf(OnlinePlayers)).replace("{max}", String.valueOf(MaxPlayers))));
                                                            dragon.setCustomNameVisible(true);

                                                            PacketPlayOutSpawnEntityLiving packetSpawn = new PacketPlayOutSpawnEntityLiving(dragon);
                                                            PacketPlayOutEntityStatus packet = new PacketPlayOutEntityStatus(dragon, (byte)3);

                                                            (((CraftPlayer)player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                                            (((CraftPlayer)player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                },26L);
                            }
                        }

                    } else if (XPStaff &&
                            player.hasPermission(StaffPerm)) {
                        if (!GetConfigs.getBoolean("JoinSpawn")) {
                            Location PlayerOver3 = new Location(playerloc, X, Y10, Z, Yaw, Pitch);
                            player.teleport(PlayerOver3);
                        }
                        else if (GetConfigs.getBoolean("JoinSpawn") &&
                                !SPAWN.getString("Spawn.World").isEmpty()) {
                            World World = Bukkit.getWorld(SPAWN.getString("Spawn.World"));

                            double SX = SPAWN.getDouble("Spawn.X");
                            double SY = SPAWN.getDouble("Spawn.Y");
                            double SZ = SPAWN.getDouble("Spawn.Z");

                            Location PlayerOver3 = new Location(World, SX, SY + 10.0D, SZ);
                            player.teleport(PlayerOver3);
                        } else {
                            if (GetConfigs.getBoolean("JoinSpawn") && SPAWN.getString("Spawn.World").isEmpty()) {
                                Location PlayerOver3 = new Location(playerloc, X, Y10, Z, Yaw, Pitch);
                                player.teleport(PlayerOver3);
                            }
                        }




                        if (player.hasPermission(EnderPerm))
                        {
                            scheduler.scheduleSyncDelayedTask(Main.getInst(), new Runnable()
                            {

                                public void run()
                                {
                                    if (!GetConfigs.getBoolean("JoinSpawn")) {

                                        playerloc.playSound(ActualPlayer, Sound.EXPLODE, 3.0F, 0.533F);

                                        player.playEffect(ActualPlayer, Effect.EXPLOSION_HUGE, 20);
                                        player.playEffect(ActualPlayer2, Effect.EXPLOSION_HUGE, 40);
                                        player.playEffect(ActualPlayer3, Effect.EXPLOSION_HUGE, 60);
                                        player.playEffect(ActualPlayer4, Effect.EXPLOSION_HUGE, 80);
                                        player.playEffect(ActualPlayer5, Effect.EXPLOSION_HUGE, 100);
                                        player.playEffect(ActualPlayer6, Effect.EXPLOSION_HUGE, 120);

                                        if (DragonEffects)
                                        {
                                            EntityEnderDragon dragon = new EntityEnderDragon(((CraftWorld)player.getWorld()).getHandle());
                                            dragon.setLocation(X, Y - 17.0D, Z, 0.0F, 180.0F);

                                            dragon.setPositionRotation(X, Y - 17.0D, Z, 0.0F, 180.0F);
                                            dragon.setCustomName(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("DragonBarMessage").replace("{player}", PlayerName).replace("{online}", String.valueOf(OnlinePlayers)).replace("{max}", String.valueOf(MaxPlayers))));
                                            dragon.setCustomNameVisible(true);

                                            PacketPlayOutSpawnEntityLiving packetSpawn = new PacketPlayOutSpawnEntityLiving(dragon);
                                            PacketPlayOutEntityStatus packet = new PacketPlayOutEntityStatus(dragon, (byte)3);

                                            (((CraftPlayer)player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                            (((CraftPlayer)player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                        }

                                    } else if (GetConfigs.getBoolean("JoinSpawn")) {

                                        if (SPAWN.isSet("Spawn.World"))
                                        {
                                            if (SPAWN.getString("Spawn.World").isEmpty()) {
                                                playerloc.playSound(ActualPlayer, Sound.EXPLODE, 3.0F, 0.533F);

                                                player.playEffect(ActualPlayer, Effect.EXPLOSION_HUGE, 20);
                                                player.playEffect(ActualPlayer2, Effect.EXPLOSION_HUGE, 40);
                                                player.playEffect(ActualPlayer3, Effect.EXPLOSION_HUGE, 60);
                                                player.playEffect(ActualPlayer4, Effect.EXPLOSION_HUGE, 80);
                                                player.playEffect(ActualPlayer5, Effect.EXPLOSION_HUGE, 100);
                                                player.playEffect(ActualPlayer6, Effect.EXPLOSION_HUGE, 120);

                                                if (DragonEffects)
                                                {
                                                    EntityEnderDragon dragon = new EntityEnderDragon(((CraftWorld)player.getWorld()).getHandle());
                                                    dragon.setLocation(X, Y - 17.0D, Z, 0.0F, 180.0F);

                                                    dragon.setPositionRotation(X, Y - 17.0D, Z, 0.0F, 180.0F);
                                                    dragon.setCustomName(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("DragonBarMessage").replace("{player}", PlayerName).replace("{online}", String.valueOf(OnlinePlayers)).replace("{max}", String.valueOf(MaxPlayers))));
                                                    dragon.setCustomNameVisible(true);

                                                    PacketPlayOutSpawnEntityLiving packetSpawn = new PacketPlayOutSpawnEntityLiving(dragon);
                                                    PacketPlayOutEntityStatus packet = new PacketPlayOutEntityStatus(dragon, (byte)3);

                                                    (((CraftPlayer)player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                                    (((CraftPlayer)player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                                }
                                            } else {
                                                if (!SPAWN.getString("Spawn.World").isEmpty()) {
                                                    double X = SPAWN.getDouble("Spawn.X");
                                                    double Y = SPAWN.getDouble("Spawn.Y");
                                                    double Z = SPAWN.getDouble("Spawn.Z");

                                                    float Yaw = (float)SPAWN.getDouble("Spawn.Yaw");
                                                    float Pitch = (float)SPAWN.getDouble("Spawn.Yaw");

                                                    Location ActualPlayer = new Location(playerloc, X, Y, Z, Yaw, Pitch);
                                                    Location ActualPlayer2 = new Location(playerloc, X + 1.0D, Y, Z, Yaw, Pitch);
                                                    Location ActualPlayer3 = new Location(playerloc, X, Y + 1.0D, Z, Yaw, Pitch);
                                                    Location ActualPlayer4 = new Location(playerloc, X, Y, Z + 1.0D, Yaw, Pitch);
                                                    Location ActualPlayer5 = new Location(playerloc, X, Y, Z, Yaw + 1.0F, Pitch);
                                                    Location ActualPlayer6 = new Location(playerloc, X, Y, Z, Yaw + 1.0F, Pitch + 1.0F);

                                                    player.playEffect(ActualPlayer, Effect.EXPLOSION_HUGE, 20);
                                                    player.playEffect(ActualPlayer2, Effect.EXPLOSION_HUGE, 40);
                                                    player.playEffect(ActualPlayer3, Effect.EXPLOSION_HUGE, 60);
                                                    player.playEffect(ActualPlayer4, Effect.EXPLOSION_HUGE, 80);
                                                    player.playEffect(ActualPlayer5, Effect.EXPLOSION_HUGE, 100);
                                                    player.playEffect(ActualPlayer6, Effect.EXPLOSION_HUGE, 120);

                                                    playerloc.playSound(ActualPlayer, Sound.EXPLODE, 3.0F, 0.533F);

                                                    if (DragonEffects)
                                                    {
                                                        EntityEnderDragon dragon = new EntityEnderDragon(((CraftWorld)player.getWorld()).getHandle());
                                                        dragon.setLocation(X, Y - 17.0D, Z, 0.0F, 180.0F);

                                                        dragon.setPositionRotation(X, Y - 17.0D, Z, 0.0F, 180.0F);
                                                        dragon.setCustomName(ChatColor.translateAlternateColorCodes('&', GetMessages.getString("DragonBarMessage").replace("{player}", PlayerName).replace("{online}", String.valueOf(OnlinePlayers)).replace("{max}", String.valueOf(MaxPlayers))));
                                                        dragon.setCustomNameVisible(true);

                                                        PacketPlayOutSpawnEntityLiving packetSpawn = new PacketPlayOutSpawnEntityLiving(dragon);
                                                        PacketPlayOutEntityStatus packet = new PacketPlayOutEntityStatus(dragon, (byte)3);

                                                        (((CraftPlayer)player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                                        (((CraftPlayer)player).getHandle()).playerConnection.sendPacket(packetSpawn);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            },26L);
                        }
                    }

                } else if (NoForceExplosion) {

                }
            }
        }, (long) (20*0.8));

        scheduler.scheduleSyncDelayedTask(Main.getInst(), new Runnable()
        {
            public void run()
            {
                if (FireWorks)
                {
                    if (player.hasPermission(FireWorkPerm))
                    {
                        for (int x = 0; x < GetConfigs.getInt("FireWorkAmmount"); x++) {

                            final Firework fw = (Firework)player.getWorld().spawn(player.getLocation(), Firework.class);
                            FireworkMeta fwm = fw.getFireworkMeta();

                            FireworkEffect.Builder builder = FireworkEffect.builder();

                            fwm.addEffect(builder.flicker(true).withColor(Color.BLUE).build());
                            if (GetConfigs.getBoolean("FireWorksTrail")) {
                                fwm.addEffect(builder.trail(true).build());
                            }
                            fwm.addEffect(builder.withFade(Color.WHITE).build());

                            fwm.setPower(GetConfigs.getInt("FireWorksPower"));

                            fw.setFireworkMeta(fwm);

                            scheduler.scheduleSyncDelayedTask(Main.getInst(), new Runnable()
                            {

                                public void run()
                                {
                                    fw.detonate();
                                }
                            },  (20 * GetConfigs.getInt("FireWorkDetonateAfter")));
                        }
                    }
                }
            }
        }, 36L);
    }
}
