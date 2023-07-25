package com.crio.jukebox.commands;

import com.crio.jukebox.dtos.PlaylistDto;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.exceptions.UserNotFoundException;
import com.crio.jukebox.services.IPlaylistService;

import java.util.List;

public class CreatePlaylistCommand implements ICommand {

    private final IPlaylistService playlistService;

    public CreatePlaylistCommand(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Override
    public void execute(List<String> tokens) {

        String userId = tokens.get(1);
        String playlistName = tokens.get(2);
        String[] songIds = tokens.subList(3, tokens.size()).toArray(new String[0]);

        try {
            PlaylistDto playlist = playlistService.createPlaylist(userId, playlistName, songIds);
            System.out.println("Playlist ID - " + playlist.playlistId());
        }
        catch (SongNotFoundException e) {
            System.out.println("Some Requested Songs Not Available. Please try again.");
        }
        catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
