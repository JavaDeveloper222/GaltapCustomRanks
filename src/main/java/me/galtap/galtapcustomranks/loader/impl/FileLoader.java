package me.galtap.galtapcustomranks.loader.impl;

import me.galtap.galtapcustomranks.loader.DataLoader;
import me.galtap.galtapcustomranks.core.Rank;
import me.galtap.galtapcustomranks.util.CustomFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileLoader implements DataLoader {
    private final CustomFile dataFile;
    private final List<Rank> allRanks;
    private final Map<String,String> dataContainer = new HashMap<>();

    public FileLoader(JavaPlugin plugin, List<Rank> allRanks){
        this.allRanks = List.copyOf(allRanks);
        dataFile = new CustomFile("data.yml",plugin);
        dataFile.save();
        loadData();
    }
    @Override
    public Map<String, String> getPlayerData() {
        return Map.copyOf(dataContainer);
    }

    @Override
    public void updatePlayerData(String uuid, String rankName) {
        if(rankName == null){
            dataFile.getSection().set(uuid,allRanks.get(0).getId());
            saveEndReload();
            return;
        }
        dataFile.getSection().set(uuid, rankName);
        saveEndReload();
    }
    private boolean rankNotExists(String rankName, String uuid){
        for(Rank rank: allRanks){
            if(rank.getId().equals(rankName)){
                return false;
            }
        }
        dataFile.getSection().set(uuid,allRanks.get(0).getId());
        dataFile.save();
        return true;
    }
    private void saveEndReload(){
        dataFile.save();
        dataFile.reload();
    }
    private void loadData(){
        for(String uuid: dataFile.getSection().getKeys(false)){
            var rankName =dataFile.getSection().getString(uuid);
            if(rankNotExists(rankName,uuid)){
                continue;
            }
            dataContainer.put(uuid,rankName);
        }
        dataFile.reload();
    }
}
