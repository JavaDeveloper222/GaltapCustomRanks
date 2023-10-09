package me.galtap.galtapcustomranks.core;

import me.clip.placeholderapi.PlaceholderAPI;
import me.galtap.galtapcustomranks.loader.DataLoader;
import me.galtap.galtapcustomranks.setting.RankSettings;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Comparator;


public class HashDataStore implements RankManager {
    private final List<Rank> ranks = new ArrayList<>();
    private final List<Rank> ranksSortedByPrice = new ArrayList<>(getRanksSortedByPrice());
    private final List<Rank> rankSortedByBlocks = new ArrayList<>(getRanksSortedByBlocks());
    private final Map<String,Rank> rankFromPlayer = new HashMap<>();
    private final Map<String,Rank> rankFromId = new HashMap<>();
    private final Map<Integer,Rank> rankFromPosition = new HashMap<>();

    private final DataLoader dataLoader;
    private final String rankFormat;
    public HashDataStore(RankSettings rankSettings, DataLoader dataLoader){
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
    public void updateRank(Player player) {
        if(ranks.isEmpty()){
            return;
        }
        var rank = getRankFromPlayer(player.getUniqueId().toString());
        if(rank == null){
            fileUpdate(player.getUniqueId().toString(),ranks.get(0).getId());
        }
        String prefixFormat = PlaceholderAPI.setPlaceholders(player,rankFormat);
        player.setDisplayName(prefixFormat);
        player.setPlayerListName(prefixFormat);


    }
    private void fileUpdate(String uuid, String rankName){
        dataLoader.updatePlayerData(uuid,rankName);
        var rank = rankFromId.get(rankName);
        rankFromPlayer.put(uuid,rank);
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

    @Override
    public Rank getRankByPrice(int price) {
        for(Rank rank: ranksSortedByPrice){
            if(price >= rank.getPrice()){
                return rank;
            }
        }
        return null;
    }

    @Override
    public Rank getRankByBlock(int blockCount) {
        for(Rank rank: rankSortedByBlocks){
            if(blockCount >= rank.getBlockCount()){
                return rank;
            }
        }
        return null;
    }
    private List<Rank> getRanksSortedByPrice() {
        List<Rank> list = new ArrayList<>(ranks);
        list.sort(Comparator.comparingInt(Rank::getPrice).reversed());
        return list;
    }

    private List<Rank> getRanksSortedByBlocks() {
        List<Rank> list = new ArrayList<>(ranks);
        list.sort(Comparator.comparingInt(Rank::getBlockCount).reversed());
        return list;
    }

}
