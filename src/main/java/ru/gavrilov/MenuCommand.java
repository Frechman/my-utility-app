package ru.gavrilov;

import ru.gavrilov.command.Command;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gavrilov-sv
 * created on 12.06.2020
 */
public class MenuCommand {

    private final Map<String, Command> commandMap = new HashMap<>();

    public MenuCommand() {
    }

    public CmdResult executeCommand(String commandCode, String[] args) {
        final Command command = commandMap.get(commandCode);
        if (command == null) {
            return new CmdResult(Collections.singletonList(commandCode + " not supported yet!"));
        }

        return command.execute(args);
    }

    public void registerCommand(Command command) {
        commandMap.putIfAbsent(command.getCode(), command);
    }
}
