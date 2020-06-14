package ru.gavrilov.command;

import ru.gavrilov.CmdResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author gavrilov-sv
 * created on 12.06.2020
 */
public class EditFileCmd implements Command {

    private static final String SUFFIX_FOR_COPY = "_copy";
    private static final String ANY_STRING = "NEW_STRING\n";

    @Override
    public String getCode() {
        return "-editFile";
    }

    @Override
    public CmdResult execute(String[] args) {
        final FindFileCmd findFileCmd = new FindFileCmd();
        final CmdResult findFileCmdResult = findFileCmd.execute(args);
        final List<String> listPaths = findFileCmdResult.getResult();

        listPaths.stream()
                .filter(s -> s.endsWith(".txt"))
                .map(Paths::get)
                .forEach(this::createCopy);

        return findFileCmdResult;
    }

    private void createCopy(Path sourcePath) {
        final Path destinationPath = createCopyPath(sourcePath, SUFFIX_FOR_COPY);

        try (var sourceReader = Files.newBufferedReader(sourcePath, UTF_8);
             var destWriter = Files.newBufferedWriter(destinationPath, UTF_8)) {

            destWriter.write(ANY_STRING);

            String line = null;
            while ((line = sourceReader.readLine()) != null) {
                destWriter.write(line);
            }
        } catch (IOException e) {
            //todo logs etc.
            System.out.println("Error: " + e.getMessage());
        }
    }

    private Path createCopyPath(Path original, String suffix) {
        String path = original.getParent().toString();

        String fullName = original.getFileName().toString();
        int dotIndex = fullName.lastIndexOf('.');
        String fileName = (dotIndex == -1) ? fullName : fullName.substring(0, dotIndex);
        String fileExtension = (dotIndex == -1) ? "" : fullName.substring(dotIndex);

        String fullPath = path + File.separator + fileName + suffix + fileExtension;
        return Paths.get(fullPath);
    }
}
