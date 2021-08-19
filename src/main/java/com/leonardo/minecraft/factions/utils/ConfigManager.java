package com.leonardo.minecraft.factions.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;

public class ConfigManager {

    public static String LANGUAGE;
    private static Config config;

    static {
        final Plugin plugin = Bukkit.getPluginManager().getPlugin("MinecraftFactions");
        createConfigFile(plugin);
        loadConfig(plugin);
        LANGUAGE = config.language;
    }

    private static void loadConfig(Plugin plugin) {
        final File file = new File(plugin.getDataFolder() + "/config.json");
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            config = gson.fromJson(new FileReader(file), Config.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createConfigFile(Plugin plugin) {
        plugin.getDataFolder().mkdirs();
        final File file = new File(plugin.getDataFolder() + "/config.json");
        if (file.exists()) return;
        try {
            Files.copy(plugin.getClass().getResourceAsStream("config.json"), file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Data
    private static class Config {

        private String language;

    }

}
