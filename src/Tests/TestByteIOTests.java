package Tests;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static ByteIO.TestByteIO.*;
public class TestByteIOTests extends Thread{
    @Override
    public void run() {
        try {
            invertFile(bp("Was_stellt_das_verschluesselte_Bild_dar.gif",false),bp("EntschluesseltesBild.gif",true));

        } catch (IOException e) {
            throw new UncheckedIOException(new IOException(System.lineSeparator()+"Nicht meine Schuld:"+System.lineSeparator()+e));
        }

    }
    private static Path bp(String fn, boolean out){
        return Paths.get(String.format("%s/%s",out?"destfiles":"ressources",fn));
    }
}
