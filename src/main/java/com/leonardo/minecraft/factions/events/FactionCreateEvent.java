package com.leonardo.minecraft.factions.events;

import com.leonardo.minecraft.factions.entities.Faction;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@Setter
public class FactionCreateEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private Player player;
    private Faction faction;
    private boolean cancelled = false;

    public FactionCreateEvent(Player player, Faction faction) {
        super(true);
        this.player = player;
        this.faction = faction;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

}
