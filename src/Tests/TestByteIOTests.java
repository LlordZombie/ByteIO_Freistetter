package Tests;

import java.io.IOException;
import java.io.UncheckedIOException;

import static ByteIO.TestByteIO.*;
import static Tests.TestFilesTests.bp;

public class TestByteIOTests extends Thread {
    @Override
    public void run() {
        try {
            invertFile(bp("Was_stellt_das_verschluesselte_Bild_dar.gif", false), bp("EntschluesseltesBild.gif", true));
            encryptFile(bp("ausgangsding.txt", false), bp("intEncrypted.txt", true), 123);
            decryptFile(bp("intEncrypted.txt", true), bp("intDecrypted.txt", true), 123);
            encryptFile(bp("ausgangsding.txt", false), bp("stringEncrypted.txt", true), "123");
            decryptFile(bp("stringEncrypted.txt", true), bp("stringDecrypted.txt", true), "123");
            fileSplit(bp("ausgangsding.txt", false), 1024);
            fileUnsplit(bp("ausgangsding.txt", false));
            fileSplit(bp("ausgangsding.txt", false), 2222);
            fileUnsplit(bp("ausgangsding.txt", false));
        } catch (IOException e) {
            throw new UncheckedIOException(new IOException(System.lineSeparator() + "Nicht meine Schuld:" + System.lineSeparator() + e));
        }

    }

}
