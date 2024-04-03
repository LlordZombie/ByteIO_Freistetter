package Tests;

import ByteIO.MP3File;

import java.nio.file.Path;

import static Tests.TestFilesTests.bp;

public class MP3FileTests extends Thread {
    @Override
    public void run() {
        MP3File mp3 = new MP3File(bp("goethe_osterspaziergang.mp3", false));
        System.out.println(mp3);
        MP3File mp3File = new MP3File(Path.of("ressources"));
        System.out.println(mp3File);
        MP3File two = new MP3File(bp("goethe_osterspaziergang.mp3", false), bp("file_example.mp3", false), bp("file_example_id3v11.mp3", false));
        System.out.println(two);
    }
}
