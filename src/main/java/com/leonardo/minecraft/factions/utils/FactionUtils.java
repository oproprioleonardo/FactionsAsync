package com.leonardo.minecraft.factions.utils;

import com.leonardo.minecraft.factions.UserInvite;
import com.leonardo.minecraft.factions.cache.UserInvites;
import com.leonardo.minecraft.factions.entities.MinecraftUser;
import de.janmm14.jsonmessagemaker.api.JsonMessageConverter;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;

@UtilityClass
public class FactionUtils {

    public void invitePlayer(UserInvites cache, MinecraftUser sender, MinecraftUser target) {
        final UserInvite invite = new UserInvite();
        invite.setUsername(target.getUsername());
        invite.setSender(sender.getUsername());
        invite.setFactionId(sender.getFactionId());
        cache.register(invite);
        sender.getPlayer().sendMessage(MessagesManager.FACTION_INVITE.sended);
        final BaseComponent[] components = JsonMessageConverter.DEFAULT
                .convert(MessagesManager.FACTION_INVITE.received + "[jmm|suggest=/f invites]");
        sender.getPlayer().spigot().sendMessage(components);
    }

}
