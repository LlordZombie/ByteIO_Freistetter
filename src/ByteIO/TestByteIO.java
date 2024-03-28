package ByteIO;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Random;

public class TestByteIO {
    public static void invertFile(Path srcFile, Path destFile) throws IOException {
        InputStream in = Files.newInputStream(srcFile);
        OutputStream out = Files.newOutputStream(destFile, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        byte[] buffer = new byte[2048];
        int bytesRead;
        while ((bytesRead = in.read(buffer, 0, buffer.length)) > 0) {
            for (int i = 0; i < 2048; i++) {
                buffer[i] = (byte) ((~buffer[i]) & 255);
            }
            out.write(buffer, 0, bytesRead);
        }
        out.close();
        in.close();
    }

    public static void encryptFile(Path srcFile, Path destFile, int key) throws IOException {
        InputStream in = Files.newInputStream(srcFile);
        OutputStream out = Files.newOutputStream(destFile, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        byte[] buffer = new byte[2048];
        byte[] randNums = new byte[2048];
        Random r = new Random(key);
        int bytesRead;
        while ((bytesRead = in.read(buffer, 0, buffer.length)) > 0) {
            r.nextBytes(randNums);
            for (int i = 0; i < 2048; i++) {
                buffer[i] = (byte) ((buffer[i]^randNums[i]) & 255);
            }
            out.write(buffer, 0, bytesRead);
        }
        out.close();
        in.close();
    }

    public static void decryptFile(Path srcFile, Path destFile, int key) throws IOException {
        encryptFile(srcFile,destFile,key);
    }
}