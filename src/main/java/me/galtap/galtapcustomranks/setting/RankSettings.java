package me.galtap.galtapcustomranks.setting;

import me.galtap.galtapcustomranks.core.Rank;
import me.galtap.galtapcustomranks.util.DefaultConfig;
import me.galtap.galtapcustomranks.util.ErrorMessages;
import me.galtap.galtapcustomranks.util.TextUtil;
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
        ConfigurationSection defaultRankSection = processSection(section,"Default");
        ConfigurationSection customRankSection = processSection(section,"Custom");
        ConfigurationSection mysqlSection = processSection(section,"MySQL");
        rankFormat = section.getString("Format");
        loadMysqlData(mysqlSection);
        loadDefaultRank(defaultRankSection);
        loadAnotherRanks(customRankSection);

    }

    private void loadDefaultRank(ConfigurationSection section){
        var id = section.getString("id");
        var prefix = section.getString("prefix");
        if(isNull(id,prefix)){
            ErrorMessages.ERROR_RANK.logError("Default");
            return;
        }
        allRanks.add(new Rank(id,prefix,0));
    }
    private void loadAnotherRanks(ConfigurationSection section){
        var position = 1;
        for(String key: section.getKeys(false)){
            var id = section.getString(key+".id");
            String prefix = TextUtil.getColorText(section.getString(key+".prefix"));
            if(isNull(id,prefix)){
                ErrorMessages.ERROR_RANK.logError(key);
                continue;
            }
            var rank = new Rank(id,prefix,position);
            if(allRanks.contains(rank)){
                ErrorMessages.SAME_RANK.logError(id,position);
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
    private static ConfigurationSection processSection(ConfigurationSection section, String name){
        if(section.contains(name)){
            return section.getConfigurationSection(name);
        }
        return section.createSection(name);
    }
    private static boolean isNull(String... args){
        for(String arg: args){
            if(arg == null) {
                return true;
            }
        }
        return false;
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