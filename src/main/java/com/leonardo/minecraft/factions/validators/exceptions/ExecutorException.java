package com.leonardo.minecraft.factions.validators.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;

import java.util.Map;

@Getter
@Setter
public class ExecutorException extends Exception {

    private String message;
    private Map<String, String> replacements;
    private CommandSender deceived;

    public ExecutorException() {
    }

    public ExecutorException(String message, Map<String, String> replacements, CommandSender deceived) {
        this.message = message;
        this.replacements = replacements;
        this.deceived = deceived;
    }

    private String getCorrectMessage() {
        final String[] msg = {this.message};
        replacements.forEach((s, s2) -> msg[0] = msg[0].replace("%" + s, s2));
        return msg[0].replace("&", "§");
    }

    public void informSender() {
        this.deceived.sendMessage(this.getCorrectMessage());
    }

    public void informSender(CommandSender sender) {
        sender.sendMessage(this.getCorrectMessage());
    }
}
