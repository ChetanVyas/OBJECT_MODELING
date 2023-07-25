package com.crio.jukebox.services;

import com.crio.jukebox.dtos.CurrentSongDto;
import com.crio.jukebox.dtos.UserDto;
import com.crio.jukebox.entities.SongPlaybackControl;
import com.crio.jukebox.exceptions.*;

public interface IUserService {

    UserDto createUser(String name);

    CurrentSongDto playPlaylist(String userId, String playlistId)
            throws EmptyPlaylistException, UserNotFoundException, PlaylistNotFoundException, SongNotFoundException;

    CurrentSongDto playSongInPlaylist(String userId, String songID)
            throws SongNotFoundException, UserNotFoundException, PlaylistNotFoundException, SongNotInPlaylistException;

    CurrentSongDto playSongInPlaylist(String userId, SongPlaybackControl playback)
            throws UserNotFoundException, PlaylistNotFoundException, InvalidOperationException, SongNotFoundException;
}
