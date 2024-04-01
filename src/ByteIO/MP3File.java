package ByteIO;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Stream;

public class MP3File {
    private final ArrayList<String> id3s = new ArrayList<>();

    public MP3File(Path... in) {
        for (Path p : in) {
            try {
                id3s.addAll(new MP3File(p).id3s);
            } catch (IllegalArgumentException ignored) {
            }
        }
        if (id3s.isEmpty()) {
            throw new IllegalArgumentException("no mp3s found");
        }
    }

    public MP3File(Path in) {
        try {
            if (Files.isDirectory(in)) {
                try (Stream<Path> paths = Files.walk(in)) {
                    paths.filter(Files::isRegularFile).forEach(this::processFile);
                }
            } else if (Files.isRegularFile(in)) {
                processFile(in);
            } else {
                throw new IllegalArgumentException("Path [" + in + "] could not be handled");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        if (id3s.isEmpty()) {
            throw new IllegalArgumentException("no mp3s found");
        }
    }

    private static HashMap<Byte, String> genId3Genre() {
        HashMap<Byte, String> id3GenreTags = new HashMap<>();
        id3GenreTags.put((byte) 0, "Blues");
        id3GenreTags.put((byte) 1, "Classic Rock");
        id3GenreTags.put((byte) 2, "Country");
        id3GenreTags.put((byte) 3, "Dance");
        id3GenreTags.put((byte) 4, "Disco");
        id3GenreTags.put((byte) 5, "Funk");
        id3GenreTags.put((byte) 6, "Grunge");
        id3GenreTags.put((byte) 7, "Hip-Hop");
        id3GenreTags.put((byte) 8, "Jazz");
        id3GenreTags.put((byte) 9, "Metal");
        id3GenreTags.put((byte) 10, "New Age");
        id3GenreTags.put((byte) 11, "Oldies");
        id3GenreTags.put((byte) 12, "Other");
        id3GenreTags.put((byte) 13, "Pop");
        id3GenreTags.put((byte) 14, "R&B");
        id3GenreTags.put((byte) 15, "Rap");
        id3GenreTags.put((byte) 16, "Reggae");
        id3GenreTags.put((byte) 17, "Rock");
        id3GenreTags.put((byte) 18, "Techno");
        id3GenreTags.put((byte) 19, "Industrial");
        id3GenreTags.put((byte) 20, "Alternative");
        id3GenreTags.put((byte) 21, "Ska");
        id3GenreTags.put((byte) 22, "Death Metal");
        id3GenreTags.put((byte) 23, "Pranks");
        id3GenreTags.put((byte) 24, "Soundtrack");
        id3GenreTags.put((byte) 25, "Euro-Techno");
        id3GenreTags.put((byte) 26, "Ambient");
        id3GenreTags.put((byte) 27, "Trip-Hop");
        id3GenreTags.put((byte) 28, "Vocal");
        id3GenreTags.put((byte) 29, "Jazz+Funk");
        id3GenreTags.put((byte) 30, "Fusion");
        id3GenreTags.put((byte) 31, "Trance");
        id3GenreTags.put((byte) 32, "Classical");
        id3GenreTags.put((byte) 33, "Instrumental");
        id3GenreTags.put((byte) 34, "Acid");
        id3GenreTags.put((byte) 35, "House");
        id3GenreTags.put((byte) 36, "Game");
        id3GenreTags.put((byte) 37, "Sound Clip");
        id3GenreTags.put((byte) 38, "Gospel");
        id3GenreTags.put((byte) 39, "Noise");
        id3GenreTags.put((byte) 40, "AlternRock");
        id3GenreTags.put((byte) 41, "Bass");
        id3GenreTags.put((byte) 42, "Soul");
        id3GenreTags.put((byte) 43, "Punk");
        id3GenreTags.put((byte) 44, "Space");
        id3GenreTags.put((byte) 45, "Meditative");
        id3GenreTags.put((byte) 46, "Instrumental Pop");
        id3GenreTags.put((byte) 47, "Instrumental Rock");
        id3GenreTags.put((byte) 48, "Ethnic");
        id3GenreTags.put((byte) 49, "Gothic");
        id3GenreTags.put((byte) 50, "Darkwave");
        id3GenreTags.put((byte) 51, "Techno-Industrial");
        id3GenreTags.put((byte) 52, "Electronic");
        id3GenreTags.put((byte) 53, "Pop-Folk");
        id3GenreTags.put((byte) 54, "Eurodance");
        id3GenreTags.put((byte) 55, "Dream");
        id3GenreTags.put((byte) 56, "Southern Rock");
        id3GenreTags.put((byte) 57, "Comedy");
        id3GenreTags.put((byte) 58, "Cult");
        id3GenreTags.put((byte) 59, "Gangsta");
        id3GenreTags.put((byte) 60, "Top 40");
        id3GenreTags.put((byte) 61, "Christian Rap");
        id3GenreTags.put((byte) 62, "Pop/Funk");
        id3GenreTags.put((byte) 63, "Jungle");
        id3GenreTags.put((byte) 64, "Native US");
        id3GenreTags.put((byte) 65, "Cabaret");
        id3GenreTags.put((byte) 66, "New Wave");
        id3GenreTags.put((byte) 67, "Psychadelic");
        id3GenreTags.put((byte) 68, "Rave");
        id3GenreTags.put((byte) 69, "Showtunes");
        id3GenreTags.put((byte) 70, "Trailer");
        id3GenreTags.put((byte) 71, "Lo-Fi");
        id3GenreTags.put((byte) 72, "Tribal");
        id3GenreTags.put((byte) 73, "Acid Punk");
        id3GenreTags.put((byte) 74, "Acid Jazz");
        id3GenreTags.put((byte) 75, "Polka");
        id3GenreTags.put((byte) 76, "Retro");
        id3GenreTags.put((byte) 77, "Musical");
        id3GenreTags.put((byte) 78, "Rock & Roll");
        id3GenreTags.put((byte) 79, "Hard Rock");
        id3GenreTags.put((byte) 80, "Folk");
        id3GenreTags.put((byte) 81, "Folk-Rock");
        id3GenreTags.put((byte) 82, "National Folk");
        id3GenreTags.put((byte) 83, "Swing");
        id3GenreTags.put((byte) 84, "Fast Fusion");
        id3GenreTags.put((byte) 85, "Bebop");
        id3GenreTags.put((byte) 86, "Latin");
        id3GenreTags.put((byte) 87, "Revival");
        id3GenreTags.put((byte) 88, "Celtic");
        id3GenreTags.put((byte) 89, "Bluegrass");
        id3GenreTags.put((byte) 90, "Avantgarde");
        id3GenreTags.put((byte) 91, "Gothic Rock");
        id3GenreTags.put((byte) 92, "Progressive Rock");
        id3GenreTags.put((byte) 93, "Psychedelic Rock");
        id3GenreTags.put((byte) 94, "Symphonic Rock");
        id3GenreTags.put((byte) 95, "Slow Rock");
        id3GenreTags.put((byte) 96, "Big Band");
        id3GenreTags.put((byte) 97, "Chorus");
        id3GenreTags.put((byte) 98, "Easy Listening");
        id3GenreTags.put((byte) 99, "Acoustic");
        id3GenreTags.put((byte) 100, "Humour");
        id3GenreTags.put((byte) 101, "Speech");
        id3GenreTags.put((byte) 102, "Chanson");
        id3GenreTags.put((byte) 103, "Opera");
        id3GenreTags.put((byte) 104, "Chamber Music");
        id3GenreTags.put((byte) 105, "Sonata");
        id3GenreTags.put((byte) 106, "Symphony");
        id3GenreTags.put((byte) 107, "Booty Bass");
        id3GenreTags.put((byte) 108, "Primus");
        id3GenreTags.put((byte) 109, "Porn Groove");
        id3GenreTags.put((byte) 110, "Satire");
        id3GenreTags.put((byte) 111, "Slow Jam");
        id3GenreTags.put((byte) 112, "Club");
        id3GenreTags.put((byte) 113, "Tango");
        id3GenreTags.put((byte) 114, "Samba");
        id3GenreTags.put((byte) 115, "Folklore");
        id3GenreTags.put((byte) 116, "Ballad");
        id3GenreTags.put((byte) 117, "Power Ballad");
        id3GenreTags.put((byte) 118, "Rhythmic Soul");
        id3GenreTags.put((byte) 119, "Freestyle");
        id3GenreTags.put((byte) 120, "Duet");
        id3GenreTags.put((byte) 121, "Punk Rock");
        id3GenreTags.put((byte) 122, "Drum Solo");
        id3GenreTags.put((byte) 123, "A capella");
        id3GenreTags.put((byte) 124, "Euro-House");
        id3GenreTags.put((byte) 125, "Dance Hall");
        id3GenreTags.put((byte) 126, "Goa");
        id3GenreTags.put((byte) 127, "Drum & Bass");
        id3GenreTags.put((byte) 128, "Club-House");
        id3GenreTags.put((byte) 129, "Hardcore");
        id3GenreTags.put((byte) 130, "Terror");
        id3GenreTags.put((byte) 131, "Indie");
        id3GenreTags.put((byte) 132, "BritPop");
        id3GenreTags.put((byte) 133, "Negerpunk");
        id3GenreTags.put((byte) 134, "Polsk Punk");
        id3GenreTags.put((byte) 135, "Beat");
        id3GenreTags.put((byte) 136, "Christian Gangsta Rap");
        id3GenreTags.put((byte) 137, "Heavy Metal");
        id3GenreTags.put((byte) 138, "Black Metal");
        id3GenreTags.put((byte) 139, "Crossover");
        id3GenreTags.put((byte) 140, "Contemporary Christian");
        id3GenreTags.put((byte) 141, "Christian Rock");
        id3GenreTags.put((byte) 142, "Merengue");
        id3GenreTags.put((byte) 143, "Salsa");
        id3GenreTags.put((byte) 144, "Thrash Metal");
        id3GenreTags.put((byte) 145, "Anime");
        id3GenreTags.put((byte) 146, "Jpop");
        id3GenreTags.put((byte) 147, "Synthpop");
        id3GenreTags.put((byte) 148, "Abstract");
        id3GenreTags.put((byte) 149, "Art Rock");
        id3GenreTags.put((byte) 150, "Baroque");
        id3GenreTags.put((byte) 151, "Bhangra");
        id3GenreTags.put((byte) 152, "Big Beat");
        id3GenreTags.put((byte) 153, "Breakbeat");
        id3GenreTags.put((byte) 154, "Chillout");
        id3GenreTags.put((byte) 155, "Downtempo");
        id3GenreTags.put((byte) 156, "Dub");
        id3GenreTags.put((byte) 157, "EBM");
        id3GenreTags.put((byte) 158, "Eclectic");
        id3GenreTags.put((byte) 159, "Electro");
        id3GenreTags.put((byte) 160, "Electroclash");
        id3GenreTags.put((byte) 161, "Emo");
        id3GenreTags.put((byte) 162, "Experimental");
        id3GenreTags.put((byte) 163, "Garage");
        id3GenreTags.put((byte) 164, "Global");
        id3GenreTags.put((byte) 165, "IDM");
        id3GenreTags.put((byte) 166, "Illbient");
        id3GenreTags.put((byte) 167, "Industro-Goth");
        id3GenreTags.put((byte) 168, "Jam Band");
        id3GenreTags.put((byte) 169, "Krautrock");
        id3GenreTags.put((byte) 170, "Leftfield");
        id3GenreTags.put((byte) 171, "Lounge");
        id3GenreTags.put((byte) 172, "Math Rock");
        id3GenreTags.put((byte) 173, "New Romantic");
        id3GenreTags.put((byte) 174, "Nu-Breakz");
        id3GenreTags.put((byte) 175, "Post-Punk");
        id3GenreTags.put((byte) 176, "Post-Rock");
        id3GenreTags.put((byte) 177, "Psytrance");
        id3GenreTags.put((byte) 178, "Shoegaze");
        id3GenreTags.put((byte) 179, "Space Rock");
        id3GenreTags.put((byte) 180, "Trop Rock");
        id3GenreTags.put((byte) 181, "World Music");
        id3GenreTags.put((byte) 182, "Neoclassical");
        id3GenreTags.put((byte) 183, "Audiobook");
        id3GenreTags.put((byte) 184, "Audio Theatre");
        id3GenreTags.put((byte) 185, "Neue Deutsche Welle");
        id3GenreTags.put((byte) 186, "Podcast");
        id3GenreTags.put((byte) 187, "Indie Rock");
        id3GenreTags.put((byte) 188, "G-Funk");
        id3GenreTags.put((byte) 189, "Dubstep");
        id3GenreTags.put((byte) 190, "Garage Rock");
        id3GenreTags.put((byte) 191, "Psybient");
        return id3GenreTags;
    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        id3s.forEach(song -> {
            String[] values = song.split(";");
            String[] labels = {"Title", "Artist", "Album", "Year", "Comment", "Genre"};
            for (int i = 0; i < values.length; i++) {
                b.append(labels[i]).append(": [").append(values[i]).append("]").append("\n");
            }
            b.append("\n");
        });
        return b.toString();
    }

    private void processFile(Path file) {//TODO: id3v1.1 einbauen
        if (Arrays.asList(file.toString().split("[.]")).getLast().equals("mp3")) {
            try (InputStream in = Files.newInputStream(file)) {
                in.skipNBytes(in.available() - 125);
                byte[] current = new byte[30];
                HashMap<Byte, String> genres = genId3Genre();
                in.readNBytes(current, 0, 30);
                String title = new String(current).strip().replace(String.valueOf((char) 0), "");
                in.readNBytes(current, 0, 30);
                String artist = new String(current).strip().replace(String.valueOf((char) 0), "");
                in.readNBytes(current, 0, 30);
                String album = new String(current).strip().replace(String.valueOf((char) 0), "");
                current = new byte[4];
                in.readNBytes(current, 0, 4);
                String year = new String(current).strip().replace(String.valueOf((char) 0), "");
                current = new byte[30];
                in.readNBytes(current, 0, 30);
                String comment = new String(current).strip().replace(String.valueOf((char) 0), "");
                String genre = genres.get(in.readNBytes(1)[0]);
                id3s.add(title + ";" + artist + ";" + album + ";" + year + ";" + comment + ";" + genre);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
