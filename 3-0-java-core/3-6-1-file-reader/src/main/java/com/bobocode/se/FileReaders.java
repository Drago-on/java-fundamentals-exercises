package com.bobocode.se;

import com.bobocode.util.ExerciseNotCompletedException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * {@link FileReaders} provides an API that allow to read whole file into a {@link String} by file name.
 */
public class FileReaders {

    /**
     * Returns a {@link String} that contains whole text from the file specified by name.
     *
     * @param fileName a name of a text file
     * @return string that holds whole file content
     */
    public static String readWholeFile(String fileName) {
        URL resource = FileReaders.class.getClassLoader().getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("File not found: " + fileName);
        }
        try {
            Path path = Paths.get(resource.toURI());
            String content = Files.readString(path);
            return content.replace("\r\n", "\n");
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException("Failed to read file: " + fileName, e);
        }
    }
}
