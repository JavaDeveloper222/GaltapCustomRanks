package me.galtap.galtapcustomranks;

import me.galtap.galtapcustomranks.api.CustomPlaceholder;
import me.galtap.galtapcustomranks.core.RankManager;
import me.galtap.galtapcustomranks.event.EventListener;
import me.galtap.galtapcustomranks.loader.DataLoader;
import me.galtap.galtapcustomranks.loader.impl.DatabaseLoader;
import me.galtap.galtapcustomranks.loader.impl.FileLoader;
import me.galtap.galtapcustomranks.setting.RankSettings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class GaltapCustomRanks extends JavaPlugin {
    private DatabaseLoader databaseLoader;
    private RankManager rankManager;

    @Override
    public void onEnable() {
        var rankSettings = new RankSettings(this);
        var dataLoader = createDataLoader(rankSettings);
        this.rankManager = new RankManager(rankSettings,dataLoader);
        new CustomPlaceholder(rankManager).register();
        Bukkit.getPluginManager().registerEvents(new EventListener(rankManager),this);
        for(Player player: Bukkit.getOnlinePlayers()){
            rankManager.loadPlaceholder(player);
        }

    }

    @Override
    public void onDisable() {
        if(databaseLoader != null){
            databaseLoader.closeConnection();
        }
    }
    private DataLoader createDataLoader(RankSettings rankSettings){
        if(rankSettings.isEnableMySQL()){
            this.databaseLoader = new DatabaseLoader(rankSettings.getHost(),rankSettings.getPort(),rankSettings.getRoot(),rankSettings.getPassword(),rankSettings.getDatabaseName(),this);
            return databaseLoader;
        }
        return new FileLoader(this,rankSettings.getAllRanks());
    }

    public RankManager getRankManager() {
        return rankManager;
    }
}
