package me.galtap.galtapcustomranks.event;

import me.galtap.galtapcustomranks.core.RankManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class EventListener implements Listener {
    private final RankManager rankManager;

    public EventListener(RankManager rankManager){

        this.rankManager = rankManager;
    }

    @EventHandler
    public void joinServer(PlayerJoinEvent event){
        var player = event.getPlayer();
        rankManager.loadPlaceholder(player);
    }
}
