package com.crio.jukebox.services;

import com.crio.jukebox.dtos.CurrentSongDto;
import com.crio.jukebox.dtos.UserDto;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.SongPlaybackControl;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.exceptions.*;
import com.crio.jukebox.repositories.ISongRepository;
import com.crio.jukebox.repositories.IUserRepository;

public class UserService implements IUserService {

    private final IUserRepository userRepository;

    private final ISongRepository songRepository;

    public UserService(IUserRepository userRepository, ISongRepository songRepository) {
        this.userRepository = userRepository;
        this.songRepository = songRepository;
    }


    @Override
    public UserDto createUser(String name) {
        User newUser = userRepository.save(new User(null, name));
        return new UserDto(newUser.getId(), newUser.getName());
    }


    /**
     * Plays the playlist for the specified user.
     *
     * @param userId The ID of the user.
     * @param playlistId The ID of the playlist to be played.
     * @return The CurrentSongDto representing the currently playing song in the playlist.
     * @throws PlaylistNotFoundException If the specified playlist is not found.
     * @throws UserNotFoundException If the user with the specified ID is not found.
     * @throws EmptyPlaylistException If the playlist is empty.
     * @throws SongNotFoundException If the first song in the playlist is not found.
     */
    @Override
    public CurrentSongDto playPlaylist(String userId, String playlistId)
            throws PlaylistNotFoundException, UserNotFoundException, EmptyPlaylistException, SongNotFoundException {

        // Validate if userId and playlistId are provided
        if (userId == null || playlistId == null) {
            throw new IllegalArgumentException("User ID and playlist ID must be provided");
        }

        // Find the user with the given userId or throw UserNotFoundException
        User user = getUserById(userId);

        // Find the playlist with the given playlistId for the user or throw PlaylistNotFoundException
        Playlist playlist = user.getPlaylist(playlistId)
                .orElseThrow(() -> new PlaylistNotFoundException("Playlist for given ID: " + playlistId + " not found!"));

        // Check if the playlist is empty or throw EmptyPlaylistException
        if (playlist.isEmpty()) {
            throw new EmptyPlaylistException("Playlist for given ID: " + playlistId + " is Empty!");
        }

        // Get the ID of the first song in the playlist
        String firstSongId = playlist.getSongIdList().get(0);
        // Find the song with the given firstSongId or throw SongNotFoundException
        Song song = getSongById(firstSongId);

        // Set the active playlist and song for the user
        user.setActivePlaylist(playlist);
        user.setActiveSong(song);
        userRepository.save(user);

        // Return the CurrentSongDto with the details of the currently playing song
        return new CurrentSongDto(song.getName(), song.getAlbumName(), song.getFeaturedArtists());
    }


    /**
     * Plays the specified song for the user from active playlist with the given user ID.
     *
     * @param userId The ID of the user.
     * @param songId The ID of the song to be played.
     * @return The CurrentSongDto representing the currently playing song.
     * @throws UserNotFoundException If the user with the specified ID is not found.
     * @throws PlaylistNotFoundException If the active playlist for the user is not found.
     * @throws SongNotFoundException If the specified song is not found in the active playlist.
     */
    @Override
    public CurrentSongDto playSongInPlaylist(String userId, String songId)
            throws UserNotFoundException, PlaylistNotFoundException, SongNotFoundException, SongNotInPlaylistException {

        // Validate if userId and songId are provided
        if (userId == null || songId == null) {
            throw new IllegalArgumentException("User ID and Song ID must be provided");
        }

        // Find the user with the given userId or throw UserNotFoundException
        User user = getUserById(userId);

        // Find the active playlist for the user or throw PlaylistNotFoundException
        Playlist playlist = user.getActivePlaylist()
                .orElseThrow(() -> new PlaylistNotFoundException("No active playlist found for given user: " + userId));

        // Check if the specified song exists in the active playlist or throw SongNotFoundException
        if (!playlist.checkIfSongExist(songId)) {
            throw new SongNotInPlaylistException("Song for given ID:" + songId + " not found in Active Playlist ID: " + playlist.getId());
        }

        // Find the song with the given songId or throw SongNotFoundException
        Song song = getSongById(songId);

        // Set the active song for the user and save the updated user to the repository
        user.setActiveSong(song);
        userRepository.save(user);

        // Return the CurrentSongDto with the details of the currently playing song
        return new CurrentSongDto(song.getName(), song.getAlbumName(), song.getFeaturedArtists());
    }


    /**
     * Plays the next or previous song in the active playlist for the specified user.
     *
     * @param userId   The ID of the user.
     * @param playback The SongPlaybackControl representing the control options for song playback (NEXT or PREVIOUS).
     * @return The CurrentSongDto representing the currently playing song after playback.
     * @throws UserNotFoundException  If the user with the specified ID is not found.
     * @throws PlaylistNotFoundException  If the active playlist for the user is not found.
     * @throws InvalidOperationException  If there is no active playing song in the playlist for the user.
     * @throws SongNotFoundException  If the next or previous song is not found in the active playlist.
     */
    @Override
    public CurrentSongDto playSongInPlaylist(String userId, SongPlaybackControl playback)
            throws UserNotFoundException, PlaylistNotFoundException, InvalidOperationException, SongNotFoundException {

        // Validate if userId is provided
        if (userId == null) {
            throw new IllegalArgumentException("User ID must be provided");
        }

        // Find the user with the given userId or throw UserNotFoundException
        User user = getUserById(userId);

        // Find the active playlist for the user or throw PlaylistNotFoundException
        Playlist playlist = user.getActivePlaylist()
                .orElseThrow(() -> new PlaylistNotFoundException("No active playlist found for given user: " + userId));

        // Get the currently playing song or throw InvalidOperationException
        Song currentSong = user.getActiveSong()
                .orElseThrow(() -> new InvalidOperationException("No active playing song found for given user: " + userId));

        // Find the next or previous song in the playlist or throw SongNotFoundException
        Song songToPlay = nextSongToPlay(playlist, currentSong, playback);

        // Set the active song for the user and save the updated user to the repository
        user.setActiveSong(songToPlay);
        userRepository.save(user);

        // Return the CurrentSongDto with the details of the currently playing song after playback
        return new CurrentSongDto(songToPlay.getName(), songToPlay.getAlbumName(), songToPlay.getFeaturedArtists());
    }


    private Song nextSongToPlay(Playlist currPlaylist, Song currSong, SongPlaybackControl playback)
            throws SongNotFoundException {

        // Find the index of the currently playing song in the playlist
        int currentSongIndex = currPlaylist.getSongIdList().indexOf(currSong.getId());

        // Calculate the index of the next or previous song based on the playback control
        int nextIndex;
        if (playback == SongPlaybackControl.NEXT) {
            nextIndex = (currentSongIndex + 1) % currPlaylist.getSongCount();
        } else {
            nextIndex = (currentSongIndex - 1 + currPlaylist.getSongCount()) % currPlaylist.getSongCount();
        }

        // Get the ID of the next or previous song in the playlist
        String nextSongId = currPlaylist.getSongIdList().get(nextIndex);

        return getSongById(nextSongId);

    }

    private User getUserById(String userId) throws UserNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User for given ID:" + userId + " not found!"));
    }

    private Song getSongById(String songId) throws SongNotFoundException {
        return songRepository.findById(songId)
                .orElseThrow(() -> new SongNotFoundException("Song for given ID: " + songId + " not found!"));
    }
}