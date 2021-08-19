package com.leonardo.minecraft.factions.events;

import com.leonardo.minecraft.factions.entities.Faction;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

//new instance object is created when this is loaded
@Getter
@Setter
public class FactionLoadEvent extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private Faction faction;

    public FactionLoadEvent(Faction faction) {
        super(true);
        this.faction = faction;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

}
