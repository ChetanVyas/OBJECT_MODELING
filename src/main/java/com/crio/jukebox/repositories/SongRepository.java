package com.crio.jukebox.repositories;

import com.crio.jukebox.entities.Song;

import java.util.*;
import java.util.stream.Collectors;

/**
 * SongRepository is an implementation of the ISongRepository interface that uses a Map to store Song entities.
 * This class provides CRUD operations for Song entities and supports searching by various attributes.
 */
public class SongRepository implements ISongRepository {

    /**
     * A map to store Song entities with their IDs as keys.
     */
    private final Map<String, Song> songMap;
    private Integer autoIncrement = 0;

    /**
     * Default constructor to initialize the SongRepository with an empty HashMap.
     */
    public SongRepository() {
        songMap = new HashMap<>();
    }

    /**
     * Constructor to initialize the SongRepository with an existing map of Song entities.
     * @param songMap A map containing Song entities with their IDs as keys.
     */
    public SongRepository(Map<String, Song> songMap) {
        this.songMap = songMap;
        autoIncrement = songMap.size();
    }

    /**
     * Generates an auto-incremented ID for new Song entities.
     * @return The generated ID as a string.
     */
    private String generateId() {
        return String.valueOf(++autoIncrement);
    }

    /**
     * Saves a Song entity to the repository. If the entity already has an ID, it updates the existing entity.
     * Otherwise, it creates a new Song entity with a generated ID.
     * @param entity The Song entity to be saved or updated.
     * @return The saved or updated Song entity.
     */
    @Override
    public Song save(Song entity) {
        if (entity.getId() != null) {
            // Update the existing entity in the map.
            return songMap.put(entity.getId(), entity);
        } else {
            // Create a new Song entity with a generated ID and save it to the map.
            Song song = new Song(generateId(), entity);
            songMap.put(song.getId(), song);
            return song;
        }
    }

    /**
     * Returns a list of all Song entities in the repository.
     * @return A list containing all Song entities.
     */
    @Override
    public List<Song> findAll() {
        return new ArrayList<>(songMap.values());
    }

    /**
     * Retrieves a Song entity by its ID.
     * @param id The ID of the Song entity to retrieve.
     * @return An Optional containing the retrieved Song entity, or an empty Optional if not found.
     */
    @Override
    public Optional<Song> findById(String id) {
        return Optional.ofNullable(songMap.get(id));
    }

    /**
     * Checks if a Song entity with the given ID exists in the repository.
     * @param id The ID of the Song entity to check.
     * @return True if the Song entity exists, false otherwise.
     */
    @Override
    public boolean existsById(String id) {
        return songMap.containsKey(id);
    }

    /**
     * Deletes a Song entity from the repository.
     * @param entity The Song entity to be deleted.
     */
    @Override
    public void delete(Song entity) {
        if (entity.getId() != null) {
            // Remove the Song entity with the specified ID from the map.
            songMap.remove(entity.getId());
        }
    }

    /**
     * Deletes a Song entity from the repository by its ID.
     * @param id The ID of the Song entity to be deleted.
     */
    @Override
    public void deleteById(String id) {
        songMap.remove(id);
    }

    /**
     * Returns the number of Song entities in the repository.
     * @return The number of Song entities.
     */
    @Override
    public long count() {
        return songMap.size();
    }

    /**
     * Searches for songs with a given song name.
     *
     * @param songName The name of the song to search for.
     * @return A list of Song entities that match the provided song name.
     */
    @Override
    public List<Song> findByName(String songName) {
        // Streams to filter songs by matching their names with the provided song name.
        return songMap.values().stream()
                .filter(song -> song.getName().equals(songName))
                .collect(Collectors.toList());
    }

    /**
     * Searches for Song entities by artist.
     * @param artist The name of the artist to search for.
     * @return A list containing all Song entities with the specified artist name.
     */
    @Override
    public List<Song> findByArtist(String artist) {
        return songMap.values().stream()
                .filter(song -> song.getArtist().equals(artist))
                .collect(Collectors.toList());
    }

    /**
     * Searches for Song entities by album name.
     * @param album The name of the album to search for.
     * @return A list containing all Song entities from the specified album.
     */
    @Override
    public List<Song> findByAlbum(String album) {
        return songMap.values().stream()
                .filter(song -> song.getAlbumName().equals(album))
                .collect(Collectors.toList());
    }

    /**
     * Searches for Song entities by genre.
     * @param genre The genre to search for.
     * @return A list containing all Song entities with the specified genre.
     */
    @Override
    public List<Song> findByGenre(String genre) {
        return songMap.values().stream()
                .filter(song -> song.getGenre().equals(genre))
                .collect(Collectors.toList());
    }
}