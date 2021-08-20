package com.leonardo.minecraft.factions.listeners;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.leonardo.minecraft.factions.entities.impl.MinecraftUserImpl;
import com.leonardo.minecraft.factions.events.FactionLoadEvent;
import com.leonardo.minecraft.factions.events.MUserLoadEvent;
import com.leonardo.minecraft.factions.managers.FactionManager;
import com.leonardo.minecraft.factions.managers.MUserManager;
import com.leonardo.minecraft.factions.services.FactionService;
import com.leonardo.minecraft.factions.services.MUserService;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Locale;

@Singleton
@Getter
public class WhenPlayerJoin implements Listener {

    @Inject
    private FactionService factionService;
    @Inject
    private FactionManager factionManager;
    @Inject
    private MUserService mUserService;
    @Inject
    private MUserManager mUserManager;

    @EventHandler
    public void onEvent(PlayerJoinEvent e) {
        final String username = e.getPlayer().getName().toLowerCase(Locale.ROOT);
        mUserManager.require(username).onFailure().call(() -> {
            final MinecraftUserImpl user = new MinecraftUserImpl();
            user.setUsername(username);
            return mUserService.create(user);
        }).onItem().call(user -> {
            final MUserLoadEvent userLoadEvent = new MUserLoadEvent(e.getPlayer(), user);
            Bukkit.getPluginManager().callEvent(userLoadEvent);
            return factionService.readById(user.getFactionId())
                                 .onItem()
                                 .invoke(faction -> {
                                     factionManager.load(faction);
                                     Bukkit.getPluginManager().callEvent(new FactionLoadEvent(faction));
                                 });
        }).await().indefinitely();
    }

}
