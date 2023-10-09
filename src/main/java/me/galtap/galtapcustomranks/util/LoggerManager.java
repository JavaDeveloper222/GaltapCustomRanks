package me.galtap.galtapcustomranks.util;

import me.galtap.galtapcustomranks.GaltapCustomRanks;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public enum LoggerManager {
    CONNECT_PROCESS("Подключение к базе данных..."),
    CONNECTION_FAILED("Не удалось подключиться к базе данных"),
    CONNECTED( "успешное подключение к базе данных"),
    CLOSE_CONNECTION_ERROR("Ошибка при отключении соединения из базы данных"),
    ERROR_RANK("Обнаружена ошибка в ranks.yml некорректно введены данные о ранге. секция ранга: %s"),
    SAME_RANK("Ранг с таким же Id или позицией как %s %s уже существуют"),
    DERICTORY_CREATE_ERROR("Не удалось создать папку  для файла %s"),
    FILE_CREATE_ERROR("Не удалось создать файл %s"),
    FILE_SAVE_ERROR("Не удалось сохранить файл %s"),
    FILE_RELOAD_ERROR("Не удалось перезагрузить файл %s"),
    MYSQL_TABLE_CREATE_ERROR("Не удалось создать таблицу в базе данных"),
    MYSQL_GET_PLAYER_DATA_ERROR("Не удалось получить данные игроков из базы данных"),
    MYSQL_DATA_UPDATE_ERROR("Не удалось обновить данные игрока в базе данных");

    private final static Logger LOGGER = JavaPlugin.getPlugin(GaltapCustomRanks.class).getLogger();
    private final String message;
    LoggerManager(String message){
        this.message = message;
    }

    public void logWarning(){
        String text = String.format(message);
        LOGGER.warning(text);
    }
    public void logColorMessage(ChatColor color){
        String text = color+String.format(message);
        LOGGER.info(text);
    }
    public void logFatalError(Throwable e, Object... args){
        String text = String.format(message,args);
        LOGGER.log(Level.WARNING,text,e);
    }
    public void logJustError(Object... args){
        String text = String.format(message,args);
        LOGGER.severe(text);
    }
}
