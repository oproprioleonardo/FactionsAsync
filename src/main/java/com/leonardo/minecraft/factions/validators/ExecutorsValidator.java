package com.leonardo.minecraft.factions.validators;

import com.leonardo.minecraft.factions.entities.MinecraftUser;
import com.leonardo.minecraft.factions.utils.MessagesManager;
import com.leonardo.minecraft.factions.validators.exceptions.ExecutorException;
import org.bukkit.command.CommandSender;

import java.util.regex.Pattern;

public class ExecutorsValidator {

    public static class FactionCreate {

        public static void hasFaction(MinecraftUser user, CommandSender sender) throws ExecutorException {
            if (user.hasFaction()) {
                final ExecutorException exception = new ExecutorException();
                exception.setDeceived(sender);
                exception.setMessage(MessagesManager.FACTION_CREATE.error.alreadyHasFaction);
            }
        }

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
