package ru.gavrilov;

import ru.gavrilov.command.EditFileCmd;
import ru.gavrilov.command.FindFileCmd;
import ru.gavrilov.command.ReadDrivesCmd;

import java.util.Arrays;

public class App {

    public static void main(String[] args) {
        final MenuCommand menuCommand = new MenuCommand();
        initCommand(menuCommand);

        final CmdResult result = menuCommand.executeCommand(args[0], getCommandArgs(args));

        System.out.println(result);
    }

    private static void initCommand(MenuCommand menuCommand) {
        menuCommand.registerCommand(new ReadDrivesCmd());
        menuCommand.registerCommand(new FindFileCmd());
        menuCommand.registerCommand(new EditFileCmd());
    }

    private static String[] getCommandArgs(String[] args) {
        if (args != null && args.length > 0) {
            return Arrays.stream(args)
                    .skip(1)
                    .toArray(String[]::new);
        }
        return new String[0];
    }
}
