package customnpc.customnpc;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.PlayerInteractManager;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.network.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R2.CraftServer;
import org.bukkit.craftbukkit.v1_19_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class NPC {
    private static List<EntityPlayer> NPC = new ArrayList<EntityPlayer>();
    public static void createNPC(Player pl, String skin){
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) Objects.requireNonNull(pl.getWorld())).getHandle();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), ChatColor.DARK_AQUA + "" + ChatColor.BOLD + skin);
        EntityPlayer npc = new EntityPlayer(server, world, gameProfile);
        npc.b(pl.getLocation().getX(), pl.getLocation().getY(), pl.getLocation().getZ(), pl.getLocation().getYaw(), pl.getLocation().getPitch());
        String[] name = getSkin(pl, skin);
        gameProfile.getProperties().put("textures", new Property("textures", name[0], name[1]));
        addNPCpacket(npc);
        NPC.add(npc);
    }

    private static String[] getSkin(Player pl, String name){
        try {
            String texture = "ewogICJ0aW1lc3RhbXAiIDogMTU5NjU2MjU2OTI0MSwKICAicHJvZmlsZUlkIiA6ICIyZGM3N2FlNzk0NjM0ODAyOTQyODBjODQyMjc0YjU2NyIsCiAgInByb2ZpbGVOYW1lIiA6ICJzYWR5MDYxMCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS81ZDU4YWM1NzhkOGQ0NmIwOWM2MWU2NTA4Y2Q5MWE0Mjc2MDY4MjQzNDQyMzRlOGU4ZWIwODJjYWMyOGI1YjQwIiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=";
            String signature = "lFemLWH7miWJM1QOMKgvoDVzsp90LFKJ8OFhATwtBmACw1rfXQjwq5iZG0D2dIwRoP3hEKKKbfxPUAr2WS+JLxq/6m/3bywCAqV+YfNLDa+2UVyku7dyaxMSaci/Q2p/qyA+VvAwrxOSASGbpFqMqg+Iz4JNlcdnfs4Sq1+hhRec7cvWhelYsaDGPElokQO3cJttc1/K3rzDDuNMhqKTcH7n4ELiBXYs8H3bEtkHL7cx/cbZpPOrTS7iiDF+UNLZ3h8X3J7UyMHiwMCa4BaKCkuq0vO0lZmpI2niMYks+KnN6H5xLEo+NEizFEiSwHZKP3ruqlVdbGRZCx9FjBPfwqoyfICpPAqBdFUlNvs12pjXJVg7Yi5fRCmx/RhH9dnKFJ8rUVyO9xdKZTXA4VQETRiR7XKlAUd5oKBMhrJih0aYh5c95QDiXcn/8w+hgx6geVB1sg/PxBMxFtob/zASuUxFRU1pfpHIPYcMXRCbHBCZslxmpm13QzwrNUbfdetLTTls3UuwnWs4Sj/6o3ewCFPB+BtHpgajV4qEF5MbfEGhTjf/lCraRN/hsBv9aAM2Y6hjJB8VlsO6nmmLUUceJGm1v0DtyCNWf+zkSBkd5QkVJ2/9uuZZgqBmrKyARx7h8x6ZpA+aCnIIJMreWJi1wgbkeLa1d1Mg9pcFnDNoyIg=";
            return new String[]{texture, signature};
        }catch (Exception e){
            String texture = "ewogICJ0aW1lc3RhbXAiIDogMTU5NjU2MjU2OTI0MSwKICAicHJvZmlsZUlkIiA6ICIyZGM3N2FlNzk0NjM0ODAyOTQyODBjODQyMjc0YjU2NyIsCiAgInByb2ZpbGVOYW1lIiA6ICJzYWR5MDYxMCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS81ZDU4YWM1NzhkOGQ0NmIwOWM2MWU2NTA4Y2Q5MWE0Mjc2MDY4MjQzNDQyMzRlOGU4ZWIwODJjYWMyOGI1YjQwIiwKICAgICAgIm1ldGFkYXRhIiA6IHsKICAgICAgICAibW9kZWwiIDogInNsaW0iCiAgICAgIH0KICAgIH0KICB9Cn0=";
            String signature = "lFemLWH7miWJM1QOMKgvoDVzsp90LFKJ8OFhATwtBmACw1rfXQjwq5iZG0D2dIwRoP3hEKKKbfxPUAr2WS+JLxq/6m/3bywCAqV+YfNLDa+2UVyku7dyaxMSaci/Q2p/qyA+VvAwrxOSASGbpFqMqg+Iz4JNlcdnfs4Sq1+hhRec7cvWhelYsaDGPElokQO3cJttc1/K3rzDDuNMhqKTcH7n4ELiBXYs8H3bEtkHL7cx/cbZpPOrTS7iiDF+UNLZ3h8X3J7UyMHiwMCa4BaKCkuq0vO0lZmpI2niMYks+KnN6H5xLEo+NEizFEiSwHZKP3ruqlVdbGRZCx9FjBPfwqoyfICpPAqBdFUlNvs12pjXJVg7Yi5fRCmx/RhH9dnKFJ8rUVyO9xdKZTXA4VQETRiR7XKlAUd5oKBMhrJih0aYh5c95QDiXcn/8w+hgx6geVB1sg/PxBMxFtob/zASuUxFRU1pfpHIPYcMXRCbHBCZslxmpm13QzwrNUbfdetLTTls3UuwnWs4Sj/6o3ewCFPB+BtHpgajV4qEF5MbfEGhTjf/lCraRN/hsBv9aAM2Y6hjJB8VlsO6nmmLUUceJGm1v0DtyCNWf+zkSBkd5QkVJ2/9uuZZgqBmrKyARx7h8x6ZpA+aCnIIJMreWJi1wgbkeLa1d1Mg9pcFnDNoyIg=";
            return new String[]{texture, signature};
        }
    }

    public static void addNPCpacket(EntityPlayer npc){
        for(Player pl : Bukkit.getOnlinePlayers()){
            PlayerConnection connection = ((CraftPlayer) pl).getHandle().b;
            connection.a(new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.a.a, npc));
            connection.a(new PacketPlayOutNamedEntitySpawn(npc));
            connection.a(new PacketPlayOutEntityHeadRotation(npc, (byte) (pl.getLocation().getYaw()*256/360) ));
        }
    }
    public static void addJoinPacket(Player pl){
        for(EntityPlayer npc : NPC){
            PlayerConnection connection = ((CraftPlayer) pl).getHandle().b;
            connection.a(new ClientboundPlayerInfoUpdatePacket(ClientboundPlayerInfoUpdatePacket.a.a, npc));
            connection.a(new PacketPlayOutNamedEntitySpawn(npc));
            connection.a(new PacketPlayOutEntityHeadRotation(npc, (byte) (pl.getLocation().getYaw()*256/360)));
        }
    }

    public static List<EntityPlayer> getNPC() {
        return NPC;
    }

}
