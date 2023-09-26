package me.galtap.galtapcustomranks.core;

import me.clip.placeholderapi.PlaceholderAPI;
import me.galtap.galtapcustomranks.loader.DataLoader;
import me.galtap.galtapcustomranks.setting.RankSettings;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankManager implements GaltapCustomRanksAPI {
    private final List<Rank> ranks = new ArrayList<>();
    private final Map<String,Rank> rankFromPlayer = new HashMap<>();
    private final Map<String,Rank> rankFromId = new HashMap<>();
    private final Map<Integer,Rank> rankFromPosition = new HashMap<>();
    private final DataLoader dataLoader;
    private final String rankFormat;
    public RankManager(RankSettings rankSettings, DataLoader dataLoader){
        this.dataLoader = dataLoader;
        this.rankFormat = rankSettings.getRankFormat();
        this.ranks.addAll(rankSettings.getAllRanks());
        for(Rank rank: rankSettings.getAllRanks()){
            rankFromId.put(rank.getId(),rank);
            rankFromPosition.put(rank.getPosition(),rank);
        }
        for(Map.Entry<String,String> entry: dataLoader.getPlayerData().entrySet()){
            String uuid = entry.getKey();
            String rankId = entry.getValue();
            var rank = rankFromId.get(rankId);
            if(rank == null){
                continue;
            }
            rankFromPlayer.put(uuid,rank);
        }
    }
    @Override
    public List<Rank> getAllRank() {
        return List.copyOf(ranks);
    }

    @Override
    public void updateRank(String uuid, String rankName) {
        dataLoader.updatePlayerData(uuid,rankName);
        var rank = rankFromId.get(rankName);
        rankFromPlayer.put(uuid,rank);

    }
    public void loadPlaceholder(Player player){
        if(ranks.isEmpty()){
            return;
        }
        var rank = getRankFromPlayer(player.getUniqueId().toString());
        if(rank == null){
            updateRank(player.getUniqueId().toString(), ranks.get(0).getId());
        }
        String prefixFormat = PlaceholderAPI.setPlaceholders(player,rankFormat);
        player.setDisplayName(prefixFormat);
        player.setPlayerListName(prefixFormat);
    }

    @Override
    public Rank getRankFromPlayer(String uuid) {
        return rankFromPlayer.get(uuid);
    }

    @Override
    public Rank getRankFromId(String id) {
        return rankFromId.get(id);
    }

    @Override
    public Rank getRankFromPosition(int position) {
        return rankFromPosition.get(position);
    }

}
