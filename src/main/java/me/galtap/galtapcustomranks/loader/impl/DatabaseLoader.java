package me.galtap.galtapcustomranks.loader.impl;

import me.galtap.galtapcustomranks.loader.DataLoader;
import me.galtap.galtapcustomranks.util.LoggerManager;
import me.galtap.galtapcustomranks.util.MySQLConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class DatabaseLoader implements DataLoader {
    private final Connection connection;
    private final MySQLConnector mySQLConnector;

    public DatabaseLoader(String host, int port, String userName, String password, String databaseName) {
        mySQLConnector = new MySQLConnector(host,port,userName,password,databaseName);
        connection = mySQLConnector.getConnection();
        createTable();
    }

    private void createTable(){
        var sql = "CREATE TABLE IF NOT EXISTS playerRankData (uuid VARCHAR(37), rank VARCHAR(20));";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            LoggerManager.MYSQL_TABLE_CREATE_ERROR.logFatalError(e);
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
            LoggerManager.MYSQL_GET_PLAYER_DATA_ERROR.logFatalError(e);
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
                LoggerManager.MYSQL_DATA_UPDATE_ERROR.logFatalError(e);
            }
        });
    }
    public void closeConnection(){
        mySQLConnector.closeConnection();
    }

}

