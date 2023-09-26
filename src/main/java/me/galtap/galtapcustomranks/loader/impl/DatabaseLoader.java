package me.galtap.galtapcustomranks.loader.impl;

import me.galtap.galtapcustomranks.loader.DataLoader;
import me.galtap.galtapcustomranks.util.AbstractMySQL;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class DatabaseLoader extends AbstractMySQL implements DataLoader {

    public DatabaseLoader(String host, int port, String userName, String password, String databaseName, JavaPlugin plugin) {
        super(host, port, userName, password, databaseName, plugin);
        createTable();
    }

    private void createTable(){
        var sql = "CREATE TABLE IF NOT EXISTS playerRankData (uuid VARCHAR(37), rank VARCHAR(20));";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[GaltapCustomRanks] не удалось создать таблицу в базе данных", e);
        }
    }

    @Override
    public Map<String, String> getPlayerData() {
        Map<String, String> playerData = new HashMap<>();
        var sql = "SELECT * FROM playerRankData;";
        try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                var uuid = resultSet.getString("uuid");
                var rank = resultSet.getString("rank");
                playerData.put(uuid, rank);
            }
        } catch (SQLException e) {
            throw new RuntimeException("[GaltapCustomRanks] не удалось получить данные игроков из базы данных", e);
        }
        return playerData;
    }

    @Override
    public void updatePlayerData(String uuid, String rankName) {
        var sql = "INSERT INTO playerRankData (uuid, rank) VALUES (?, ?) ON DUPLICATE KEY UPDATE rank = VALUES(rank);";
        CompletableFuture.runAsync(() -> {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, uuid);
                statement.setString(2, rankName);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("[GaltapCustomRanks] не удалось обновить данные игрока в базе данных", e);
            }
        });
    }

}

