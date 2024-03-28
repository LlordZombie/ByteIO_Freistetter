package Tests;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static ByteIO.TestByteIO.invertFile;

public class AllTests {
    public static void main(String[] args) {
        new TestByteIOTests().start();

    }
}
