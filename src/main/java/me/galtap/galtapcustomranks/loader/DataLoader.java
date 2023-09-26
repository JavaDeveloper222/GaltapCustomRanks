package me.galtap.galtapcustomranks.loader;

import java.util.Map;

public interface DataLoader {
    Map<String,String> getPlayerData();
    void updatePlayerData(String uuid, String rankName);


}
