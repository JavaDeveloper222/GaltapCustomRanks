

package me.galtap.galtapcustomranks.api;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.galtap.galtapcustomranks.core.RankManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
public class CustomPlaceholder extends PlaceholderExpansion {

    private final RankManager rankManager;


    public CustomPlaceholder(RankManager rankManager){

        this.rankManager = rankManager;
    }
    @Override
    public @NotNull String getIdentifier() {
        return "rank";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Galtap";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }
    @Override
    public String onPlaceholderRequest(Player player, @NotNull String params){
        if(player == null){
            return null;
        }
        if(params.equals("prefix")){
            var rank = rankManager.getRankFromPlayer(player.getUniqueId().toString());
            if(rank == null){
                return null;
            }
            return rank.getPrefix();
        }
        return null;


    }

}
