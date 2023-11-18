package edu.hw6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// In case of a hash collision, key:value pair will be written to the same file on different lines
@SuppressWarnings("MultipleStringLiterals")
public class DiskMap implements Map<String, String> {
    private final Path path;

    private static final String LINE_FORMAT = "%s:%s";
    private static final Logger LOGGER = LogManager.getLogger();

    public DiskMap(Path pathToDiskMap) throws IOException {
        path = pathToDiskMap;
        if (!path.toFile().exists()) {
            Files.createDirectories(path);
            LOGGER.info("New directory {} was created", path);
        } else if (!path.toFile().isDirectory()) {
            throw new IOException("path must lead to directory");
        }
    }

    private String extractKey(String string) {
        String[] parts = string.split(":");
        if (parts.length != 2) {
            return null;
        }
        return parts[0];
    }

    private String extractValue(String string) {
        String[] parts = string.split(":");
        if (parts.length != 2) {
            return null;
        }
        return parts[1];
    }

    private Entry<String, String> extractEntry(String string) {
        String[] parts = string.split(":");
        if (parts.length != 2) {
            return null;
        }
        return new AbstractMap.SimpleEntry<>(parts[0], parts[1]);
    }

    @Override
    public int size() {
        return keySet().size();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public boolean containsKey(Object o) {
        String hash = String.valueOf(o.hashCode());
        Path filePath = path.resolve(hash);

        if (!filePath.toFile().isFile()) {
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath.toString()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (Objects.equals(extractKey(line), o)) {
                    return true;
                }
            }
        } catch (IOException exception) {
            LOGGER.error("Found error attempting to check '{}' presence as key: {}", o, exception);
        }
        return false;
    }

    @Override
    public boolean containsValue(Object o) {
        if (o.getClass() != String.class) {
            return false;
        }
        return values().contains((String) o);
    }

    @Override
    public String get(Object o) {
        if (o.getClass() != String.class) {
            return null;
        }

        String hash = String.valueOf(o.hashCode());
        Path pathToFile = path.resolve(hash);

        if (!pathToFile.toFile().isFile()) {
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(pathToFile.toString()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Entry<String, String> entry = extractEntry(line);
                if (entry != null && entry.getKey().equals(o)) {
                    return entry.getValue();
                }
            }
        } catch (IOException exception) {
            LOGGER.error("Found error attempting to get '{}': {}", o, exception);
        }

        return null;
    }

    @Nullable
    @Override
    public String put(String key, String value) {
        String hash = String.valueOf(key.hashCode());
        Path filePath = path.resolve(hash);
        String prevValue = null;

        List<String> lines;
        try {
            filePath.toFile().createNewFile();
            lines = Files.readAllLines(filePath);
        } catch (IOException exception) {
            LOGGER.error("Found error attempting to create file {}: {}", filePath, exception);
            return null;
        }

        try (PrintStream stream = new PrintStream(filePath.toFile())) {
            for (String line : lines) {
                Entry<String, String> entry = extractEntry(line);
                if (entry != null && entry.getKey().equals(key)) {
                    prevValue = entry.getValue();
                } else {
                    stream.println(line);
                }
            }
            stream.printf(LINE_FORMAT, key, value);
        } catch (IOException streamException) {
            LOGGER.error("Found error attempting to put ({} : {}): {}", key, value, streamException);
        }

        return prevValue;
    }

    @Override
    public String remove(Object o) {
        String hash = String.valueOf(o.hashCode());
        Path filePath = path.resolve(hash);

        List<String> lines;
        try {
            lines = Files.readAllLines(filePath);
        } catch (IOException cause) {
            LOGGER.error("Found error attempting to read file: {}", cause.toString());
            return null;
        }

        String prevValue = null;
        boolean isFileEmpty = true;
        try (PrintStream stream = new PrintStream(filePath.toFile())) {
            for (String line : lines) {
                Entry<String, String> entry = extractEntry(line);
                if (entry != null && Objects.equals(entry.getKey(), o)) {
                    prevValue = entry.getValue();
                } else {
                    isFileEmpty = false;
                    stream.println(line);
                }
            }
        } catch (IOException cause) {
            LOGGER.error("Found error attempting to remove '{}': {}", o, cause);
            return null;
        }

        if (isFileEmpty) {
            filePath.toFile().delete();
            LOGGER.info("Map no longer contains keys with hash {} -> file was removed", hash);
        }
        return prevValue;
    }

    @Override
    public void putAll(@NotNull Map<? extends String, ? extends String> map) {
        for (var entry: map.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        for (File file: Objects.requireNonNull(path.toFile().listFiles())) {
            file.delete();
        }
    }

    @NotNull
    @Override
    public Set<String> keySet() {
        Set<String> keys = new HashSet<>();
        try {
            for (File file: Objects.requireNonNull(path.toFile().listFiles())) {
                Files.readAllLines(file.toPath())
                    .stream()
                    .map(this::extractKey)
                    .forEach(keys::add);
            }
        } catch (IOException exception) {
            LOGGER.error("Found error attempting to get keys set: {}", exception.toString());
        }
        return keys;
    }

    @NotNull
    @Override
    public Collection<String> values() {
        List<String> values = new ArrayList<>();
        try {
            for (File file: Objects.requireNonNull(path.toFile().listFiles())) {
                Files.readAllLines(file.toPath())
                    .stream()
                    .map(this::extractValue)
                    .forEach(values::add);
            }
        } catch (IOException cause) {
            LOGGER.error("Found error attempting to get values set: {}", cause.toString());
        }
        return values;
    }

    @NotNull
    @Override
    public Set<Entry<String, String>> entrySet() {
        Set<Entry<String, String>> entries = new HashSet<>();
        try {
            for (File file: Objects.requireNonNull(path.toFile().listFiles())) {
                Files.readAllLines(file.toPath())
                    .stream()
                    .map(this::extractEntry)
                    .forEach(entries::add);
            }
        } catch (IOException cause) {
            LOGGER.error("Found error attempting to get entries collection: {}", cause.toString());
        }
        return entries;
    }
}
