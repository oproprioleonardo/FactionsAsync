package com.leonardo.minecraft.factions.cmds;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import com.google.inject.Inject;
import com.leonardo.minecraft.factions.entities.impl.FactionImpl;
import com.leonardo.minecraft.factions.events.FactionCreateEvent;
import com.leonardo.minecraft.factions.managers.MUserManager;
import com.leonardo.minecraft.factions.services.FactionService;
import com.leonardo.minecraft.factions.services.MUserService;
import com.leonardo.minecraft.factions.utils.MessagesManager;
import com.leonardo.minecraft.factions.validators.ExecutorsValidator;
import com.leonardo.minecraft.factions.validators.exceptions.ExecutorException;
import io.smallrye.mutiny.Uni;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Locale;

@CommandAlias(value = "faction|f|fac")
public class FactionCmd extends BaseCommand {

    @Inject
    private FactionService factionService;
    @Inject
    private MUserService userService;
    @Inject
    private MUserManager userManager;

    @Subcommand(value = "create")
    @Syntax(value = "/faction create (tag) (name)")
    @Description(value = "This command creates a faction")
    public void create(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessagesManager.IS_NOT_PLAYER);
            return;
        }
        userManager.require(sender.getName().toLowerCase(Locale.ROOT)).chain(user -> {
            if (args.length == 3) {
                final String tag = args[1];
                final String name = args[2];
                try {
                    ExecutorsValidator.FactionCreate.hasFaction(user, sender);
                    ExecutorsValidator.FactionCreate.validateTag(tag, sender);
                    ExecutorsValidator.FactionCreate.validateName(name, sender);
                } catch (ExecutorException e) {
                    e.informSender();
                    return Uni.createFrom().nothing();
                }
                sender.sendMessage(MessagesManager.INTERNAL.wait);
                return this.factionService.existsTag(tag).chain(exists -> {
                    if (exists) {
                        sender.sendMessage(MessagesManager.FACTION_CREATE.error.alreadyExistsTag);
                    } else {
                        final FactionImpl faction = new FactionImpl();
                        faction.setTag(tag);
                        faction.setName(name);
                        faction.setLeaderUsername(sender.getName().toLowerCase(Locale.ROOT));
                        final FactionCreateEvent event = new FactionCreateEvent(((Player) sender), faction);
                        Bukkit.getPluginManager().callEvent(event);
                        if (!event.isCancelled()) return this.factionService.create(faction);
                    }
                    return Uni.createFrom().nothing();
                })
                                          .onItem()
                                          .call(faction -> {
                                              sender.sendMessage(MessagesManager.FACTION_CREATE.success);
                                              user.setFactionId(faction.getId());
                                              return userService.update(user);
                                          })
                                          .onFailure()
                                          .invoke(() -> sender.sendMessage(MessagesManager.INTERNAL.failure));
            }
            return Uni.createFrom().nothing();
        }).await().indefinitely();


    }

}
