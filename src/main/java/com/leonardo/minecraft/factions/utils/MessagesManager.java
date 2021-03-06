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
    public static String TARGET_HAS_FACTION;
    public static String TARGET_HAS_NOT_FACTION;
    public static String HAS_NOT_FACTION;
    public static FactionCreate FACTION_CREATE;
    public static FactionInvite FACTION_INVITE;
    public static Internal INTERNAL;

    static {
        final Plugin plugin = Bukkit.getPluginManager().getPlugin("MinecraftFactions");
        createLanguagesFiles(plugin);
        loadLanguages(plugin);

    }

    private static void loadLanguages(Plugin plugin) {
        final File file = new File(plugin.getDataFolder() + "/languages/" + ConfigManager.LANGUAGE);
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            final Messages messages = gson.fromJson(new FileReader(file), Messages.class);
            PERMISSION_ERROR = messages.permissionError;
            IS_NOT_PLAYER = messages.isNotPlayer;
            INTERNAL = messages.internal;
            TARGET_HAS_FACTION = messages.targetHasFaction;
            TARGET_HAS_NOT_FACTION = messages.targetHasNotFaction;
            HAS_NOT_FACTION = messages.hasNotFaction;
            FACTION_CREATE = messages.factionCreate;
            FACTION_INVITE = messages.factionInvite;
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
        private String targetHasFaction;
        private String targetHasNotFaction;
        private String hasNotFaction;
        private Internal internal;
        private FactionCreate factionCreate;
        private FactionInvite factionInvite;

    }

    @Data
    public static class Internal {

        public String wait;
        public String failure;
        public String userNotFound;
        public String factionNotFound;

    }

    @Data
    public static class FactionInvite {

        public String sended;
        public String received;
        public String accepted;
        public String refused;

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
