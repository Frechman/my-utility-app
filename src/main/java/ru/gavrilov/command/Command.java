package ru.gavrilov.command;

import ru.gavrilov.CmdResult;

/**
 * @author gavrilov-sv
 * created on 12.06.2020
 */
public interface Command {

    String getCode();

    CmdResult execute(String[] args);
}
