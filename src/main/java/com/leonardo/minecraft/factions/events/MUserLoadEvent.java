package com.leonardo.minecraft.factions.events;

import com.leonardo.minecraft.factions.entities.MinecraftUser;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

//new instance object is created when this is loaded
@Getter
@Setter
public class MUserLoadEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private Player player;
    private MinecraftUser user;

    public MUserLoadEvent(Player player, MinecraftUser user) {
        super(true);
        this.player = player;
        this.user = user;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

}
