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

public class MessagesManager {

    public static String PERMISSION_ERROR;
    public static String IS_NOT_PLAYER;
    public static FactionCreate FACTION_CREATE;
    public static Internal INTERNAL;
    private static Messages messages;

    static {
        final Plugin plugin = Bukkit.getPluginManager().getPlugin("MinecraftFactions");
        createLanguagesFiles(plugin);
        loadLanguages(plugin);
        PERMISSION_ERROR = messages.permissionError;
        IS_NOT_PLAYER = messages.isNotPlayer;
        INTERNAL = messages.internal;
        FACTION_CREATE = messages.factionCreate;
    }

    private static void loadLanguages(Plugin plugin) {
        final File file = new File(plugin.getDataFolder() + "/languages/" + ConfigManager.LANGUAGE);
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            messages = gson.fromJson(new FileReader(file), Messages.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createLanguagesFiles(Plugin plugin) {
        final File languagesFolder = new File(plugin.getDataFolder() + "/languages/");
        final boolean b = languagesFolder.mkdirs();
        if (!b) return;
        try {
            Files.copy(plugin.getClass().getResourceAsStream("/languages/en_us.json"),
                       new File(languagesFolder, "en_us.json").toPath());
            Files.copy(plugin.getClass().getResourceAsStream("/languages/en_us.json"),
                       new File(languagesFolder, "pt_br.json").toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Data
    private static class Messages {

        private String permissionError;
        private String isNotPlayer;
        private Internal internal;
        private FactionCreate factionCreate;

    }

    @Data
    public static class Internal {

        public String wait;
        public String failure;

    }

    @Data
    public static class FactionCreate {

        public Error error;
        public String success;

        @Data
        public static class Error {

            public String tagInvalid;
            public String alreadyHasFaction;
            public String nameInvalid;
            public String alreadyExistsTag;
        }
    }

}
