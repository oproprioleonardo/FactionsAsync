package com.leonardo.minecraft.factions;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.BukkitCommandManager;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.leonardo.minecraft.factions.cmds.FactionCmd;
import com.leonardo.minecraft.factions.di.MinecraftFactionsModule;
import com.leonardo.minecraft.factions.listeners.WhenPlayerJoin;
import com.leonardo.minecraft.factions.services.FactionService;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class MinecraftFactions extends JavaPlugin {

    private Injector injector;
    @Inject
    private FactionService service;
    @Inject
    private BukkitCommandManager manager;

    @SneakyThrows
    @Override
    public void onEnable() {
        this.injector = Guice.createInjector(MinecraftFactionsModule.of(this));
        this.injector.injectMembers(this);
        this.registerCommands();
    }

    @Override
    public void onDisable() {

    }

    private void registerListeners() {
        this.registerListener(WhenPlayerJoin.class);
    }

    private <O extends Listener> void registerListener(Class<O> listener) {
        Bukkit.getPluginManager().registerEvents(this.getInstance(listener), this);
    }

    private void registerCommands() {
        this.registerCommand(FactionCmd.class);
    }

    private <O extends BaseCommand> void registerCommand(Class<O> baseCommandClass) {
        this.manager.registerCommand(this.getInstance(baseCommandClass));
    }

    public <O> O getInstance(Class<O> clazz) {
        return this.injector.getInstance(clazz);
    }
}
