package me.galtap.galtapcustomranks.core;

import org.bukkit.entity.Player;

import java.util.List;

public interface RankManager {
    Rank getRankFromPlayer(String uuid);
    Rank getRankFromId(String id);
    Rank getRankFromPosition(int position);
    Rank getRankByPrice(int price);
    Rank getRankByBlock(int blockCount);
    List<Rank> getAllRank();
    void updateRank(Player player);


}
