package com.crio.jukebox.repositories;

import com.crio.jukebox.entities.Playlist;

import java.util.*;
import java.util.stream.Collectors;

/**
 * PlaylistRepository is an implementation of the IPlaylistRepository interface that uses a Map to store Playlist entities.
 * This class provides CRUD operations for Playlist entities and supports searching by user ID.
 */
public class PlaylistRepository implements IPlaylistRepository {

    /**
     * A map to store Playlist entities with their IDs as keys.
     */
    private final Map<String, Playlist> playlistMap;
    private Integer autoIncrement = 0;

    /**
     * Default constructor to initialize the PlaylistRepository with an empty HashMap.
     */
    public PlaylistRepository() {
        playlistMap = new HashMap<>();
    }

    /**
     * Constructor to initialize the PlaylistRepository with an existing map of Playlist entities.
     * @param playlistMap A map containing Playlist entities with their IDs as keys.
     */
    public PlaylistRepository(Map<String, Playlist> playlistMap) {
        this.playlistMap = playlistMap;
        autoIncrement = playlistMap.size();
    }

    /**
     * Generates an auto-incremented ID for new Playlist entities.
     * @return The generated ID as a string.
     */
    private String generateId() {
        return String.valueOf(++autoIncrement);
    }

    /**
     * Saves a Playlist entity to the repository. If the entity already has an ID, it updates the existing entity.
     * Otherwise, it creates a new Playlist entity with a generated ID.
     * @param entity The Playlist entity to be saved or updated.
     * @return The saved or updated Playlist entity.
     */
    @Override
    public Playlist save(Playlist entity) {
        if (entity.getId() != null) {
            // Update the existing entity in the map.
            return playlistMap.put(entity.getId(), entity);
        } else {
            // Create a new Playlist entity with a generated ID and save it to the map.
            Playlist playlist = new Playlist(generateId(), entity);
            playlistMap.put(playlist.getId(), playlist);
            return playlist;
        }
    }

    /**
     * Returns a list of all Playlist entities in the repository.
     * @return A list containing all Playlist entities.
     */
    @Override
    public List<Playlist> findAll() {
        return new ArrayList<>(playlistMap.values());
    }

    /**
     * Retrieves a Playlist entity by its ID.
     * @param id The ID of the Playlist entity to retrieve.
     * @return An Optional containing the retrieved Playlist entity, or an empty Optional if not found.
     */
    @Override
    public Optional<Playlist> findById(String id) {
        return Optional.ofNullable(playlistMap.get(id));
    }

    /**
     * Checks if a Playlist entity with the given ID exists in the repository.
     * @param id The ID of the Playlist entity to check.
     * @return True if the Playlist entity exists, false otherwise.
     */
    @Override
    public boolean existsById(String id) {
        return playlistMap.containsKey(id);
    }

    /**
     * Deletes a Playlist entity from the repository.
     * @param entity The Playlist entity to be deleted.
     */
    @Override
    public void delete(Playlist entity) {
        if (entity.getId() != null) {
            // Remove the Playlist entity with the specified ID from the map.
            playlistMap.remove(entity.getId());
        }
    }

    /**
     * Deletes a Playlist entity from the repository by its ID.
     * @param id The ID of the Playlist entity to be deleted.
     */
    @Override
    public void deleteById(String id) {
        playlistMap.remove(id);
    }

    /**
     * Returns the number of Playlist entities in the repository.
     * @return The number of Playlist entities.
     */
    @Override
    public long count() {
        return playlistMap.size();
    }

    /**
     * Searches for Playlist entities by user ID.
     * @param userId The ID of the user whose playlists are to be retrieved.
     * @return A list containing all Playlist entities created by the user.
     */
    @Override
    public List<Playlist> findByUserId(String userId) {
        return playlistMap.values().stream()
                .filter(playlist -> playlist.getCreatorId().equals(userId))
                .collect(Collectors.toList());
    }
}