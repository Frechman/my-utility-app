package ru.gavrilov;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author gavrilov-sv
 * created on 12.06.2020
 */
public class CmdResult {

    private final List<String> result;

    public CmdResult() {
        result = Collections.emptyList();
    }

    public CmdResult(List<String> commandResult) {
        result = new ArrayList<>(commandResult);
    }

    public List<String> getResult() {
        return result;
    }

    @Override
    public String toString() {
        return result.stream().collect(Collectors.joining(System.lineSeparator()));
    }
}
