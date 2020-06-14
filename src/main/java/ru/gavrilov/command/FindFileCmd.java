package ru.gavrilov.command;

import ru.gavrilov.CmdResult;

import java.io.IOException;
import java.nio.file.*;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author gavrilov-sv
 * created on 12.06.2020
 */
public class FindFileCmd implements Command {

    @Override
    public String getCode() {
        return "-findFile";
    }

    @Override
    public CmdResult execute(String[] args) {
        args = validateArgs(args);

        final String absolutePath = args[0];
        final Path directory = Paths.get(absolutePath);
        if (!directory.isAbsolute()) {
            throw new IllegalArgumentException("The path must be absolute!");
        }

        final String fileName = args[1];
        final String pattern = ".*" + fileName + ".*";
        final PathMatcher pathMatcher =
                FileSystems.getDefault().getPathMatcher("regex:" + pattern);

        List<Path> foundFiles = findBy(directory, pathMatcher);

        final List<String> fileNames = foundFiles.stream().map(Path::toString).collect(Collectors.toList());
        return new CmdResult(fileNames);
    }

    private List<Path> findBy(Path directory, PathMatcher matcher) {
        if (Files.isDirectory(directory)) {
            try (Stream<Path> files = Files.walk(directory)) {
                return files
                        .filter(path -> Files.isRegularFile(path.toAbsolutePath()))
                        .filter(path -> matcher.matches(path.getFileName()))
                        .collect(Collectors.toList());
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        return Collections.emptyList();
    }

    private String[] validateArgs(String[] args) {
        if (args == null || args.length < 2 || args[0].isEmpty() || args[1].isEmpty()) {
            throw new IllegalArgumentException("Incorrect arguments!");
        }
        return args;
    }
}
