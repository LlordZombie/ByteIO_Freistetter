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
                buffer[i] = (byte) ((buffer[i] ^ randNums[i]) & 255);
            }
            out.write(buffer, 0, bytesRead);
        }
        out.close();
        in.close();
    }

    public static void decryptFile(Path srcFile, Path destFile, int key) throws IOException {
        encryptFile(srcFile, destFile, key);
    }

    public static void encryptFile(Path srcFile, Path destFile, String key) throws IOException {
        decryptFile(srcFile, destFile, key.hashCode());
    }

    public static void decryptFile(Path srcFile, Path destFile, String key) throws IOException {
        decryptFile(srcFile, destFile, key.hashCode());
    }

    public static void fileSplit(Path file, long maxBytes) throws IOException {
        InputStream in = Files.newInputStream(file);
        int splitSize = (int) (Files.size(file) / maxBytes) + 1;
        if (splitSize > 99999999) {
            throw new IllegalArgumentException("too many files");
        }
        for (int i = 0; i < splitSize; i++) {
            OutputStream out = Files.newOutputStream(Path.of(String.format("%s.%08d", file, i)), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            int toRead = Math.min(in.available(), (int) maxBytes);
            int size = Math.min(Math.min(2048, (int) maxBytes), in.available());
            byte[] buffer = new byte[size];
            int available = toRead;
            while (available > 0) {
                available -= in.read(buffer, 0, size);
                out.write(buffer, 0, size);
            }
            out.close();
        }
        in.close();
    }

    public static void fileUnsplit(Path file) throws IOException {
        if (!Files.exists(Path.of(file.toString() + ".00000000"))) {
            throw new IllegalArgumentException("no parts found");
        }
        OutputStream out = Files.newOutputStream(file, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        Path p;
        for (int i = 0; Files.exists((p = Path.of(String.format("%s.%08d", file, i)))); i++) {
            InputStream in = Files.newInputStream(p);
            byte[] buffer = new byte[2048];
            int bytesRead;
            while ((bytesRead = in.read(buffer, 0, buffer.length)) > 0) {
                out.write(buffer, 0, bytesRead);
            }
            in.close();
            Files.delete(p);
        }
        out.close();
    }
}