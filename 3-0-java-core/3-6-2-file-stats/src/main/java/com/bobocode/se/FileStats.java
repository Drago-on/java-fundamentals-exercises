package com.bobocode.se;

import com.bobocode.util.ExerciseNotCompletedException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link FileStats} provides an API that allow to get character statistic based on text file. All whitespace characters
 * are ignored.
 */
public class FileStats {
    private final Map<Character, Integer> charCounts;

    private FileStats(Map<Character, Integer> charCounts) {
        this.charCounts = charCounts;
    }
    /**
     * Creates a new immutable {@link FileStats} objects using data from text file received as a parameter.
     *
     * @param fileName input text file name
     * @return new FileStats object created from text file
     */
    public static FileStats from(String fileName) {
        try {
            URL resource = FileStats.class.getClassLoader().getResource(fileName);
            if (resource == null) {
                throw new FileStatsException("File not found: " + fileName);
            }
            Path path = Paths.get(resource.toURI());
            String content = Files.readString(path).replaceAll("\\s", "");

            Map<Character, Integer> charCounts = new HashMap<>();
            for (char c : content.toCharArray()) {
                charCounts.put(c, charCounts.getOrDefault(c, 0) + 1);
            }

            return new FileStats(charCounts);
        } catch (IOException | URISyntaxException e) {
            throw new FileStatsException("Failed to read file: " + fileName, e);
        }
    }

    /**
     * Returns a number of occurrences of the particular character.
     *
     * @param character a specific character
     * @return a number that shows how many times this character appeared in a text file
     */
    public int getCharCount(char character) {
        return charCounts.getOrDefault(character, 0);
    }

    /**
     * Returns a character that appeared most often in the text.
     *
     * @return the most frequently appeared character
     */
    public char getMostPopularCharacter() {
        return charCounts.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElseThrow(() -> new FileStatsException("No characters found"))
                .getKey();
    }

    /**
     * Returns {@code true} if this character has appeared in the text, and {@code false} otherwise
     *
     * @param character a specific character to check
     * @return {@code true} if this character has appeared in the text, and {@code false} otherwise
     */
    public boolean containsCharacter(char character) {
        return charCounts.containsKey(character);
    }
}
