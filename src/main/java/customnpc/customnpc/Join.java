package customnpc.customnpc;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(NPC.getNPC()==null){
            return;
        }
        if(NPC.getNPC().isEmpty()){
            return;
        }
        NPC.addJoinPacket(e.getPlayer());
    }
}
