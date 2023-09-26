package me.galtap.galtapcustomranks.util;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class CustomFile {
    private final FileConfiguration section;
    private final File file;
    private final JavaPlugin plugin;

    public CustomFile(String name, JavaPlugin plugin) {
        this.plugin = plugin;
        File dataFolder = plugin.getDataFolder();

        if (!dataFolder.exists()) {
            boolean created = dataFolder.mkdirs();
            if (!created) {
                throw new RuntimeException("["+plugin.getName()+"]Не удалось создать папку");
            }
        }

        file = new File(dataFolder, name);

        if (!file.exists()) {
            try {
                boolean created = file.createNewFile();
                if (!created) {
                    throw new RuntimeException("["+plugin.getName()+"]Не удалось создать файл " + name);
                }
            } catch (IOException e) {
                throw new RuntimeException("["+plugin.getName()+"]Не удалось создать файл " + name, e);
            }
        }

        section = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getSection() {
        return section;
    }

    public void save() {
        try {
            section.save(file);
        } catch (IOException e) {
            throw new RuntimeException("["+plugin.getName()+"]Не удалось сохранить файл", e);
        }
    }

    public void reload() {
        try {
            section.load(file);
        } catch (IOException e) {
            throw new RuntimeException("["+plugin.getName()+"]Не удалось перезагрузить файл - ошибка ввода/вывода", e);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException("["+plugin.getName()+"]Не удалось перезагрузить файл - неверный формат конфигурации", e);
        }
    }
}


