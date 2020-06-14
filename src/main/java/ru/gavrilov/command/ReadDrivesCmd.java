package ru.gavrilov.command;

import ru.gavrilov.CmdResult;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gavrilov-sv
 * created on 12.06.2020
 */
public class ReadDrivesCmd implements Command {

    private static final String DRIVE_DESCRIPTION = "Drive: ";
    private static final String TYPE_DESCRIPTION = "Type: ";
    private static final String FREE_SPACE_DESCRIPTION = "Free space: ";
    private static final String TOTAL_SPACE_DESCRIPTION = "Total space: ";

    @Override
    public String getCode() {
        return "-readDrives";
    }

    @Override
    public CmdResult execute(String[] args) {
        final List<String> result = new ArrayList<>();
        final FileSystemView fsv = FileSystemView.getFileSystemView();
        for (File file : File.listRoots()) {
            result.add(DRIVE_DESCRIPTION + file.getPath());
            result.add(TYPE_DESCRIPTION + fsv.getSystemTypeDescription(file));
            result.add(FREE_SPACE_DESCRIPTION + file.getFreeSpace());
            result.add(TOTAL_SPACE_DESCRIPTION + file.getTotalSpace());
        }
        return new CmdResult(result);
    }
}
