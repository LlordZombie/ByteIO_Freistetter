package ByteIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class TestFiles {
    public static void createFilesAndDirectories(Path file) throws IOException {
        BufferedReader in = Files.newBufferedReader(file, StandardCharsets.UTF_8);
        ArrayList<String> layers = new ArrayList<>();
        in.lines().forEach(line -> {
            line = line.stripTrailing();
            if (line.charAt(0) != '#') {
                int layer = (line.length() - line.stripLeading().length()) / 2;
                if (layer == layers.size()) {
                    layers.add(line.strip());
                } else if (layer > layers.size()) {
                    throw new IllegalArgumentException("Layers are dumb");
                } else {
                    layers.set(layer, line.strip());
                    if (layers.size() >= layer + 1) {
                        layers.subList(layer + 1, layers.size()).clear();
                    }
                }
                Path p = buildPath(layers);
                try {
                    if (!Files.exists(p)) {
                        if (layers.getLast().charAt(layers.getLast().length() - 1) == ':') {
                            Files.createDirectories(p);
                        } else {
                            Files.createFile(p);
                        }
                    }
                } catch (IOException e) {
                    throw new UncheckedIOException(new IOException("scheiß files oida" + e));
                }
            }
        });
        in.close();
    }

    private static Path buildPath(ArrayList<String> layers) {
        StringBuilder p = new StringBuilder();
        for (int i = 0; i < layers.size(); i++) {
            String layer = layers.get(i);
            if (layer.charAt(layer.length() - 1) != ':' && i != layers.size() - 1) {
                throw new IllegalArgumentException("A file cannot be a child of a file");
            } else {
                p.append(layer.charAt(layer.length() - 1) == ':' ? layer.substring(0, layer.length() - 1) : layer).append("/");
            }
        }
        return Path.of(p.substring(0, p.length()));
    }
}
