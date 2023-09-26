package me.galtap.galtapcustomranks.util;

import org.bukkit.Bukkit;

public enum ErrorMessages {
    ERROR_RANK("Обнаружена ошибка в ranks.yml некорректно введены данные о ранге. секция ранга: %s"),
    SAME_RANK("Ранг с таким же Id или позицией как %s %s уже существуют");
    private final String message;


    ErrorMessages(String message){
        this.message =  "[GaltapCustomRanks] "+message;
    }
    public void logError(Object... args){
        Bukkit.getLogger().severe(String.format(message, args));
    }
}
