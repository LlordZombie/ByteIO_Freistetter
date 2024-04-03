package Tests;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static ByteIO.TestFiles.createFilesAndDirectories;

public class TestFilesTests extends Thread {
    static Path bp(String fn, boolean out) {
        return Paths.get(String.format("%s/%s", out ? "destfiles" : "ressources", fn));
    }

    @Override
    public void run() {
        try {
            createFilesAndDirectories(bp("directories.txt", false));
            createFilesAndDirectories(bp("directories2.txt", false));
        } catch (IOException e) {
            throw new UncheckedIOException(new IOException(System.lineSeparator() + "Nicht meine Schuld:" + System.lineSeparator() + e));
        }

    }
}
