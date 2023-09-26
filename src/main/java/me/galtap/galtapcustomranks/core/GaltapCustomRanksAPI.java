package me.galtap.galtapcustomranks.core;

import java.util.List;

public interface GaltapCustomRanksAPI {
    Rank getRankFromPlayer(String uuid);
    Rank getRankFromId(String id);
    Rank getRankFromPosition(int position);
    List<Rank> getAllRank();
    void updateRank(String uuid, String rankName);


}
