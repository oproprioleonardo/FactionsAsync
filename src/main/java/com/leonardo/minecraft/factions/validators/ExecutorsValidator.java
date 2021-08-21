package com.leonardo.minecraft.factions.validators;

import com.leonardo.minecraft.factions.entities.MinecraftUser;
import com.leonardo.minecraft.factions.utils.MessagesManager;
import com.leonardo.minecraft.factions.validators.exceptions.ExecutorException;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

public class ExecutorsValidator {


    public static void hasFaction(MinecraftUser user, CommandSender sender) throws ExecutorException {
        if (user.hasFaction()) {
            final ExecutorException exception = new ExecutorException();
            exception.setDeceived(sender);
            exception.setMessage(MessagesManager.FACTION_CREATE.error.alreadyHasFaction);
            throw exception;
        }
    }

    public static void hasNotFaction(MinecraftUser user, CommandSender sender) throws ExecutorException {
        if (!user.hasFaction()) {
            final ExecutorException exception = new ExecutorException();
            exception.setDeceived(sender);
            exception.setMessage(MessagesManager.HAS_NOT_FACTION);
            throw exception;
        }
    }

    public static void targetHasFaction(MinecraftUser user, CommandSender sender) throws ExecutorException {
        if (user.hasFaction()) {
            final ExecutorException exception = new ExecutorException();
            exception.setDeceived(sender);
            exception.setMessage(MessagesManager.TARGET_HAS_FACTION);
            throw exception;
        }
    }

    public static void targetHasNotFaction(MinecraftUser user, CommandSender sender) throws ExecutorException {
        if (!user.hasFaction()) {
            final ExecutorException exception = new ExecutorException();
            exception.setDeceived(sender);
            exception.setMessage(MessagesManager.TARGET_HAS_NOT_FACTION);
            throw exception;
        }
    }

    public static void validatePlayer(String username, CommandSender sender) throws ExecutorException {
        if (Bukkit.getPlayer(username) == null ) {
            final ExecutorException exception = new ExecutorException();
            exception.setDeceived(sender);
            exception.setMessage(MessagesManager.INTERNAL.userNotFound);
            throw exception;
        }
    }

    public static void validatePlayerInstance(CommandSender sender) throws ExecutorException {
        if (!(sender instanceof Player)) {
            final ExecutorException exception = new ExecutorException();
            exception.setDeceived(sender);
            exception.setMessage(MessagesManager.IS_NOT_PLAYER);
            throw exception;
        }
    }

    public static class FactionInvite {


    }

    public static class FactionCreate {


        public static void validateTag(String tag, CommandSender sender) throws ExecutorException {
            final boolean lenght = tag.length() == 3;
            final boolean characters = Pattern.compile("[a-zA-Z]{3}").matcher(tag).matches();
            if (!lenght || !characters) {
                final ExecutorException exception = new ExecutorException();
                exception.setDeceived(sender);
                exception.setMessage(MessagesManager.FACTION_CREATE.error.tagInvalid);
                throw exception;
            }
        }

        public static void validateName(String name, CommandSender sender) throws ExecutorException {
            final boolean lenght = name.length() >= 5 && name.length() <= 16;
            final boolean characters = Pattern.compile("[a-zA-Z]*").matcher(name).matches();
            if (!lenght || !characters) {
                final ExecutorException exception = new ExecutorException();
                exception.setDeceived(sender);
                exception.setMessage(MessagesManager.FACTION_CREATE.error.nameInvalid);
                throw exception;
            }
        }

    }

}
