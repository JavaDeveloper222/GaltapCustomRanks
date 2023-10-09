package me.galtap.galtapcustomranks;

import me.galtap.galtapcustomranks.api.CustomPlaceholder;
import me.galtap.galtapcustomranks.core.HashDataStore;
import me.galtap.galtapcustomranks.core.RankManager;
import me.galtap.galtapcustomranks.loader.DataLoader;
import me.galtap.galtapcustomranks.loader.impl.DatabaseLoader;
import me.galtap.galtapcustomranks.loader.impl.FileLoader;
import me.galtap.galtapcustomranks.setting.RankSettings;
import org.bukkit.plugin.java.JavaPlugin;

public final class GaltapCustomRanks extends JavaPlugin {
    private static GaltapCustomRanks instance;
    private DatabaseLoader databaseLoader;
    private RankManager rankManager;

    @Override
    public void onEnable() {
        instance = this;
        var rankSettings = new RankSettings(this);
        var dataLoader = createDataLoader(rankSettings);
        rankManager = new HashDataStore(rankSettings,dataLoader);
        new CustomPlaceholder(rankManager).register();

    }

    @Override
    public void onDisable() {
        if(databaseLoader != null){
            databaseLoader.closeConnection();
        }
    }
    private DataLoader createDataLoader(RankSettings rankSettings){
        if(rankSettings.isEnableMySQL()){
            this.databaseLoader = new DatabaseLoader(rankSettings.getHost(),rankSettings.getPort(),rankSettings.getRoot(),
                    rankSettings.getPassword(),rankSettings.getDatabaseName());
            return databaseLoader;
        }
        return new FileLoader(this,rankSettings.getAllRanks());
    }


    public RankManager getRankManager() {
        return rankManager;
    }
    public static GaltapCustomRanks getInstance() {
        return instance;
    }
}
