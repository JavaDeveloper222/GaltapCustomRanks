package me.galtap.galtapcustomranks.setting;

import me.galtap.galtapcustomranks.core.Rank;
import me.galtap.galtapcustomranks.util.DefaultConfig;
import me.galtap.galtapcustomranks.util.LoggerManager;
import me.galtap.galtapcustomranks.util.SimpleUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class RankSettings {
    private final String rankFormat;
    private boolean enableMySQL;
    private String host;
    private int port;
    private String root;
    private String password;
    private String databaseName;
    private final List<Rank> allRanks = new ArrayList<>();
    public RankSettings(JavaPlugin plugin){
        var rankConfig = new DefaultConfig(plugin,"ranks.yml");
        rankConfig.saveConfig();
        ConfigurationSection section = rankConfig.getConfig();
        ConfigurationSection defaultRankSection = SimpleUtil.processSection(section,"Default");
        ConfigurationSection customRankSection = SimpleUtil.processSection(section,"Custom");
        ConfigurationSection mysqlSection = SimpleUtil.processSection(section,"MySQL");
        rankFormat = section.getString("Format");
        loadMysqlData(mysqlSection);
        loadDefaultRank(defaultRankSection);
        loadAnotherRanks(customRankSection);

    }

    private void loadDefaultRank(ConfigurationSection section){
        var id = section.getString("id");
        var prefix = section.getString("prefix");
        if(id == null || prefix == null){
            LoggerManager.ERROR_RANK.logJustError("Default");
            return;
        }
        allRanks.add(new Rank(id,prefix,0,0,0));
    }
    private void loadAnotherRanks(ConfigurationSection section){
        var position = 1;
        for(String key: section.getKeys(false)){
            var id = section.getString(key+".id");
            String prefix = SimpleUtil.getColorText(section.getString(key+".prefix"));
            int price = section.getInt(key+".price",-1);
            int blockCount = section.getInt(key+".blocks",-1);
            if(id == null || prefix == null || price == -1 || blockCount == -1){
                LoggerManager.ERROR_RANK.logJustError(key);
                continue;
            }
            var rank = new Rank(id,prefix,position,price,blockCount);
            if(allRanks.contains(rank)){
                LoggerManager.SAME_RANK.logJustError(id,position);
                continue;
            }
            allRanks.add(rank);
            position++;
        }
    }
    private void loadMysqlData(ConfigurationSection section){
        enableMySQL = section.getBoolean("enable");
        host = section.getString("host");
        port = section.getInt("port");
        root = section.getString("root");
        password = section.getString("password");
        databaseName = section.getString("databaseName");
    }

    public List<Rank> getAllRanks() {
        return List.copyOf(allRanks);
    }

    public String getRankFormat() {
        return rankFormat;
    }

    public boolean isEnableMySQL() {
        return enableMySQL;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getRoot() {
        return root;
    }

    public String getPassword() {
        return password;
    }

    public String getDatabaseName() {
        return databaseName;
    }
}
