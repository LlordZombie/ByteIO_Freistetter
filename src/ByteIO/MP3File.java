package ByteIO;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
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

    private static String[] genId3Genre() {
        return new String[]{"Blues", "Classic Rock", "Country", "Dance", "Disco", "Funk", "Grunge", "Hip-Hop", "Jazz", "Metal", "New Age", "Oldies", "Other", "Pop", "R&B", "Rap", "Reggae", "Rock", "Techno", "Industrial", "Alternative", "Ska", "Death Metal", "Pranks", "Soundtrack", "Euro-Techno", "Ambient", "Trip-Hop", "Vocal", "Jazz+Funk", "Fusion", "Trance", "Classical", "Instrumental", "Acid", "House", "Game", "Sound Clip", "Gospel", "Noise", "AlternRock", "Bass", "Soul", "Punk", "Space", "Meditative", "Instrumental Pop", "Instrumental Rock", "Ethnic", "Gothic", "Darkwave", "Techno-Industrial", "Electronic", "Pop-Folk", "Eurodance", "Dream", "Southern Rock", "Comedy", "Cult", "Gangsta", "Top 40", "Christian Rap", "Pop/Funk", "Jungle", "Native US", "Cabaret", "New Wave", "Psychadelic", "Rave", "Showtunes", "Trailer", "Lo-Fi", "Tribal", "Acid Punk", "Acid Jazz", "Polka", "Retro", "Musical", "Rock & Roll", "Hard Rock", "Folk", "Folk-Rock", "National Folk", "Swing", "Fast Fusion", "Bebop", "Latin", "Revival", "Celtic", "Bluegrass", "Avantgarde", "Gothic Rock", "Progressive Rock", "Psychedelic Rock", "Symphonic Rock", "Slow Rock", "Big Band", "Chorus", "Easy Listening", "Acoustic", "Humour", "Speech", "Chanson", "Opera", "Chamber Music", "Sonata", "Symphony", "Booty Bass", "Primus", "Porn Groove", "Satire", "Slow Jam", "Club", "Tango", "Samba", "Folklore", "Ballad", "Power Ballad", "Rhythmic Soul", "Freestyle", "Duet", "Punk Rock", "Drum Solo", "A capella", "Euro-House", "Dance Hall", "Goa", "Drum & Bass", "Club-House", "Hardcore", "Terror", "Indie", "BritPop", "Negerpunk", "Polsk Punk", "Beat", "Christian Gangsta Rap", "Heavy Metal", "Black Metal", "Crossover", "Contemporary Christian", "Christian Rock", "Merengue", "Salsa", "Thrash Metal", "Anime", "Jpop", "Synthpop", "Abstract", "Art Rock", "Baroque", "Bhangra", "Big Beat", "Breakbeat", "Chillout", "Downtempo", "Dub", "EBM", "Eclectic", "Electro", "Electroclash", "Emo", "Experimental", "Garage", "Global", "IDM", "Illbient", "Industro-Goth", "Jam Band", "Krautrock", "Leftfield", "Lounge", "Math Rock", "New Romantic", "Nu-Breakz", "Post-Punk", "Post-Rock", "Psytrance", "Shoegaze", "Space Rock", "Trop Rock", "World Music", "Neoclassical", "Audiobook", "Audio Theatre", "Neue Deutsche Welle", "Podcast", "Indie Rock", "G-Funk", "Dubstep", "Garage Rock", "Psybient"};

    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        id3s.forEach(song -> {
            String[] values = song.split(";");
            String[] labels = values.length == 6 ? new String[]{"Title", "Artist", "Album", "Year", "Comment", "Genre"} : new String[]{"Title", "Artist", "Album", "Year", "Comment", "Titlenum", "Genre"};
            for (int i = 0; i < values.length; i++) {
                b.append(labels[i]).append(": [").append(values[i]).append("]").append("\n");
            }
            b.append("\n");
        });
        return b.toString();
    }

    private void processFile(Path file) {
        if (Arrays.asList(file.toString().split("[.]")).getLast().equals("mp3")) {
            try (InputStream in = Files.newInputStream(file)) {
                in.skipNBytes(in.available() - 125);
                byte[] current = new byte[30];
                String[] genres = genId3Genre();
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
                in.readNBytes(current, 0, 28);
                byte[] num = new byte[2];
                in.readNBytes(num, 0, 2);
                String fnum = "";
                if (num[0] != 0) {
                    current[28] = num[0];
                    current[29] = num[1];
                } else {
                    fnum = String.valueOf(num[1]);
                }
                String comment = new String(current).strip().replace(String.valueOf((char) 0), "");
                String genre = genres[(in.readNBytes(1)[0])];
                String csv = title + ";" + artist + ";" + album + ";" + year + ";" + comment;
                if (!fnum.isEmpty()) {
                    csv += ";" + fnum;
                }
                csv += ";" + genre;
                id3s.add(csv);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
