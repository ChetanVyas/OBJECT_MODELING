package com.crio.jukebox.repositories.data.strategies;

import com.crio.jukebox.entities.Song;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SongFromCsvStrategy is an implementation of the IParsingStrategy interface that parses song data from a CSV file.
 * It converts the CSV data into Song entities and returns a list of songs.
 */
public class SongFromCsvStrategy implements IParsingStrategy<Song> {

    /**
     * A list to store the newly parsed Song entities.
     */
    private final List<Song> newSongList;

    /**
     * Constructs a new SongFromCsvStrategy object with an empty list of songs.
     */
    public SongFromCsvStrategy() {
        newSongList = new ArrayList<>();
    }

    /**
     * Parses song data from the specified CSV file and converts it into a list of Song entities.
     * @param filePath The path of the CSV file containing the song data.
     * @return A list of Song entities parsed from the CSV file.
     * @throws IllegalArgumentException If the file path is null.
     */
    @Override
    public List<Song> execute(String filePath) {

        if (filePath == null) {
            throw new IllegalArgumentException("File path and delimiter must be provided");
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {

            String delimiter = ",";

            return bufferedReader.lines()
                    .map(line -> line.split(delimiter))
                    .map(this::csvToSong)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            System.out.println("Songs are not loaded to Songs Repository");
            System.out.println("Error loading data from file: " + filePath);
        }

        return newSongList;
    }


    /**
     * Converts an array of CSV tokens into a Song entity.
     * @param tokens The CSV tokens representing song attributes.
     * @return The Song entity created from the CSV tokens.
     */
    private Song csvToSong(String[] tokens) {
        String name = tokens[0];
        String genre = tokens[1];
        String albumName = tokens[2];
        String artist = tokens[3];
        String[] featuredArtists = tokens[4].split("#");
        return new Song(name, genre, albumName, artist, featuredArtists);
    }
}
