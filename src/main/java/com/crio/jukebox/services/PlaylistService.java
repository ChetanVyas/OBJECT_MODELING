package com.crio.jukebox.services;

import com.crio.jukebox.dtos.PlaylistDto;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.exceptions.PlaylistNotFoundException;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.exceptions.SongNotInPlaylistException;
import com.crio.jukebox.exceptions.UserNotFoundException;
import com.crio.jukebox.repositories.IPlaylistRepository;
import com.crio.jukebox.repositories.ISongRepository;
import com.crio.jukebox.repositories.IUserRepository;

import java.util.Objects;

public class PlaylistService implements IPlaylistService {

    private final IPlaylistRepository playlistRepository;
    private final IUserRepository userRepository;
    private final ISongRepository songRepository;

    public PlaylistService(IPlaylistRepository playlistRepository, IUserRepository userRepository, ISongRepository songRepository) {
        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
        this.songRepository = songRepository;
    }


    /**
     * Creates a new playlist for the specified user with the given name and list of song IDs.
     *
     * @param userId       The ID of the user.
     * @param playlistName The name of the new playlist.
     * @param songIds      The list of song IDs to be added to the playlist.
     * @return The PlaylistDto representing the newly created playlist.
     * @throws UserNotFoundException If the user with the specified ID is not found.
     * @throws SongNotFoundException If any of the song IDs in the list are not found.
     */
    @Override
    public PlaylistDto createPlaylist(String userId, String playlistName, String[] songIds)
            throws UserNotFoundException, SongNotFoundException {

        // Validate if userId, playlistName, and songIdsList are provided
        Objects.requireNonNull(userId, "User ID must be provided");
        Objects.requireNonNull(playlistName, "Playlist name must be provided");
        Objects.requireNonNull(songIds, "List of Song IDs must be provided");

        // Find the user with the given userId or throw UserNotFoundException
        User user = getUserById(userId);

        // Create a new playlist with the given name and user ID
        Playlist newPlaylist = new Playlist(playlistName, userId);

        // Add each song with the provided song IDs to the new playlist
        for (String songId : songIds) {
            // Check if the song exists in the repository or throw SongNotFoundException
            validateSongExists(songId);
            newPlaylist.addSong(songId);
        }

        // Save the new playlist to the playlist repository and update the user's playlist list
        newPlaylist = playlistRepository.save(newPlaylist);
        user.addPlaylist(newPlaylist);
        userRepository.save(user);

        // Return the PlaylistDto representing the newly created playlist
        return new PlaylistDto(newPlaylist.getId(), playlistName, songIds);
    }


    /**
     * Deletes the specified playlist for the user with the given user ID.
     *
     * @param userId     The ID of the user.
     * @param playlistId The ID of the playlist to be deleted.
     * @throws UserNotFoundException     If the user with the specified ID is not found.
     * @throws PlaylistNotFoundException If the playlist with the specified ID is not found.
     */
    @Override
    public void deletePlaylist(String userId, String playlistId)
            throws UserNotFoundException, PlaylistNotFoundException {

        // Validate if userId and playlistId are provided
        Objects.requireNonNull(userId, "User ID must be provided");
        Objects.requireNonNull(playlistId, "Playlist ID must be provided");

        // Find the user with the given userId or throw UserNotFoundException
        User user = getUserById(userId);

        // Find the playlist with the given playlistId or throw PlaylistNotFoundException
        Playlist playlist = getPlaylistById(user, playlistId);

        // Delete the playlist from the user's playlist list
        user.deletePlaylist(playlist);

        // Delete the playlist from the playlist repository
        playlistRepository.delete(playlist);

        // Save the updated user to the user repository
        userRepository.save(user);
    }


    /**
     * Adds the specified songs to the playlist for the user with the given user ID and playlist ID.
     *
     * @param userId     The ID of the user.
     * @param playlistId The ID of the playlist to which the songs will be added.
     * @param songIds    The array of song IDs to be added to the playlist.
     * @return The PlaylistDto representing the updated playlist.
     * @throws UserNotFoundException     If the user with the specified ID is not found.
     * @throws PlaylistNotFoundException If the playlist with the specified ID is not found.
     * @throws SongNotFoundException     If any of the song IDs in the list are not found.
     */
    @Override
    public PlaylistDto addSongsToPlaylist(String userId, String playlistId, String[] songIds)
            throws UserNotFoundException, PlaylistNotFoundException, SongNotFoundException {
        // Validate if userId, playlistId, and songIds are provided
        Objects.requireNonNull(userId, "User ID must be provided");
        Objects.requireNonNull(playlistId, "Playlist ID must be provided");
        Objects.requireNonNull(songIds, "List of Song IDs must be provided");

        // Find the user with the given userId or throw UserNotFoundException
        User user = getUserById(userId);

        // Find the playlist with the given playlistId or throw PlaylistNotFoundException
        Playlist playlist = getPlaylistById(user, playlistId);

        // Add each song with the provided song IDs to the playlist
        for (String songId : songIds) {
            // Check if the song exists in the repository or throw SongNotFoundException
            validateSongExists(songId);
            if (!playlist.checkIfSongExist(songId)) playlist.addSong(songId);
        }

        // Save the updated playlist to the playlist repository and update the user's playlist list
        playlistRepository.save(playlist);
        userRepository.save(user);

        // Convert the updated songIds list to an array and return the updated PlaylistDto
        songIds = playlist.getSongIdList().toArray(new String[0]);
        return new PlaylistDto(playlist.getId(), playlist.getName(), songIds);
    }


    /**
     * Deletes the specified songs from the playlist for the user with the given user ID and playlist ID.
     *
     * @param userId     The ID of the user.
     * @param playlistId The ID of the playlist from which songs will be deleted.
     * @param songIds    The array of song IDs to be deleted from the playlist.
     * @return The PlaylistDto representing the updated playlist.
     * @throws UserNotFoundException    If the user with the specified ID is not found.
     * @throws PlaylistNotFoundException If the playlist with the specified ID is not found.
     * @throws SongNotFoundException    If any of the song IDs in the list are not found in the playlist.
     */
    @Override
    public PlaylistDto deleteSongsFromPlaylist(String userId, String playlistId, String[] songIds)
            throws UserNotFoundException, PlaylistNotFoundException, SongNotInPlaylistException {
        // Validate if userId, playlistId, and songIds are provided
        Objects.requireNonNull(userId, "User ID must be provided");
        Objects.requireNonNull(playlistId, "Playlist ID must be provided");
        Objects.requireNonNull(songIds, "List of Song IDs must be provided");

        // Find the user with the given userId or throw UserNotFoundException
        User user = getUserById(userId);

        // Find the playlist with the given playlistId or throw PlaylistNotFoundException
        Playlist playlist = getPlaylistById(user, playlistId);

        // Delete each song with the provided song IDs from the playlist
        for (String songId : songIds) {
            // Check if the song exists in the playlist or throw SongNotFoundException
            if (!playlist.checkIfSongExist(songId)) {
                throw new SongNotInPlaylistException("Requested Song with ID: " + songId +
                        " is not present in the playlist ID: " + playlistId);
            }
            playlist.deleteSong(songId);
        }

        // Save the updated playlist to the playlist repository and update the user's playlist list
        playlistRepository.save(playlist);
        userRepository.save(user);

        // Convert the updated songIds list to an array and return the updated PlaylistDto
        songIds = playlist.getSongIdList().toArray(new String[0]);
        return new PlaylistDto(playlist.getId(), playlist.getName(), songIds);
    }



    private User getUserById(String userId) throws UserNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User for given ID:" + userId + " not found!"));
    }

    private Playlist getPlaylistById(User user, String playlistId) throws PlaylistNotFoundException {
        return user.getPlaylist(playlistId)
                .orElseThrow(() -> new PlaylistNotFoundException("Playlist not found for ID: " + playlistId));
    }

    private void validateSongExists(String songId) throws SongNotFoundException {
        if (!songRepository.existsById(songId)) {
            throw new SongNotFoundException("Song not found for ID: " + songId);
        }
    }
}