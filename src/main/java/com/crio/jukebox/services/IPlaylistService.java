package com.crio.jukebox.services;

import com.crio.jukebox.dtos.PlaylistDto;
import com.crio.jukebox.exceptions.PlaylistNotFoundException;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.exceptions.SongNotInPlaylistException;
import com.crio.jukebox.exceptions.UserNotFoundException;

public interface IPlaylistService {

    PlaylistDto createPlaylist(String userId, String playListName, String[] songIds)
            throws UserNotFoundException, SongNotFoundException;

    void deletePlaylist(String userId, String playlistId)
            throws UserNotFoundException, PlaylistNotFoundException;

    PlaylistDto addSongsToPlaylist(String userId, String playlistId, String[] songIds)
            throws UserNotFoundException, PlaylistNotFoundException, SongNotFoundException;

    PlaylistDto deleteSongsFromPlaylist(String userId, String playlistId, String[] songIds)
            throws UserNotFoundException, PlaylistNotFoundException, SongNotInPlaylistException;

}
