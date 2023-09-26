package me.galtap.galtapcustomranks.util;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractMySQL {
    private final String pluginName;
    protected Connection connection;

    protected AbstractMySQL(String host, int port, String userName, String password, String databaseName, JavaPlugin plugin){
        this.connection = getConnect(host,port,databaseName,userName,password);
        this.pluginName = "["+plugin.getName()+"]";

    }

    private Connection getConnect(String host, int port, String databaseName, String userName, String password) {
        try {

            if (connection != null && !connection.isClosed()) return connection;
            Bukkit.getLogger().warning(pluginName + " Подключение к базе данных");
            String url = "jdbc:mysql://" + host + ":" + port + "/" + databaseName;
            connection = DriverManager.getConnection(url, userName, password);

        } catch (SQLException e) {
            throw new RuntimeException(pluginName + "не удалось подключиться к базе данных", e);
        }

        Bukkit.getLogger().info(ChatColor.GREEN + pluginName + "успешное подключение к базе данных");
        return connection;
    }
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(pluginName + " Ошибка при отключении соединения из базы данных", e);
        }
    }
}
