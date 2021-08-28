package com.leonardo.minecraft.factions.cmds;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.leonardo.minecraft.factions.cache.UserInvites;
import com.leonardo.minecraft.factions.entities.MinecraftUser;
import com.leonardo.minecraft.factions.entities.impl.FactionImpl;
import com.leonardo.minecraft.factions.events.FactionCreateEvent;
import com.leonardo.minecraft.factions.managers.MUserManager;
import com.leonardo.minecraft.factions.services.FactionService;
import com.leonardo.minecraft.factions.services.MUserService;
import com.leonardo.minecraft.factions.utils.FactionUtils;
import com.leonardo.minecraft.factions.utils.MessagesManager;
import com.leonardo.minecraft.factions.validators.ExecutorsValidator;
import com.leonardo.minecraft.factions.validators.exceptions.ExecutorException;
import io.smallrye.mutiny.Uni;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Locale;

@CommandAlias(value = "faction|f|fac")
@Singleton
public class FactionCmd extends BaseCommand {

    @Inject
    private FactionService factionService;
    @Inject
    private MUserService userService;
    @Inject
    private MUserManager userManager;
    @Inject
    private UserInvites userInvites;

    @Subcommand(value = "invite|convidar")
    @Syntax(value = "/faction invite (username)")
    @Description(value = "This command invites the user to faction")
    public void invite(CommandSender sender, String[] args) {

        //todo concat "accept" and "refuse" tasks
        if (args.length != 2) return;
        final String target = args[1];
        try {
            ExecutorsValidator.validatePlayerInstance(sender);
            ExecutorsValidator.validatePlayer(target, sender);
        } catch (ExecutorException e) {
            e.informSender();
            return;
        }
        final Uni<MinecraftUser> requireSender = userManager.require(sender.getName().toLowerCase(Locale.ROOT));
        final Uni<MinecraftUser> requireTarget = userManager.require(target.toLowerCase(Locale.ROOT));
        Uni.combine().all().unis(requireSender, requireTarget).asTuple().onFailure().invoke(() -> {
            //todo internal error
        }).invoke(objects -> {
            final MinecraftUser mSender = objects.getItem1();
            final MinecraftUser mTarget = objects.getItem2();
            try {
                //todo check user role and your permissions
                ExecutorsValidator.hasNotFaction(mSender, sender);
                ExecutorsValidator.targetHasFaction(mTarget, sender);
            } catch (ExecutorException e) {
                e.informSender();
                return;
            }
            FactionUtils.invitePlayer(userInvites, mSender, mTarget);
        }).await().indefinitely();
    }

    @Subcommand(value = "create|criar")
    @Syntax(value = "/faction create (tag) (name)")
    @Description(value = "This command creates a faction")
    public void create(CommandSender sender, String[] args) {
        if (args.length != 3) return;
        final String tag = args[1];
        final String name = args[2];
        try {
            ExecutorsValidator.validatePlayerInstance(sender);
            ExecutorsValidator.FactionCreate.validateTag(tag, sender);
            ExecutorsValidator.FactionCreate.validateName(name, sender);
        } catch (ExecutorException e) {
            e.informSender();
            return;
        }
        userManager.require(sender.getName().toLowerCase(Locale.ROOT)).chain(user -> {
            try {
                ExecutorsValidator.hasFaction(user, sender);
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
        }).await().indefinitely();
    }

}
