package com.crio.jukebox.commands;

import com.crio.jukebox.dtos.CurrentSongDto;
import com.crio.jukebox.exceptions.EmptyPlaylistException;
import com.crio.jukebox.services.IUserService;

import java.util.List;

public class PlayPlaylistCommand implements ICommand {

    private final IUserService userService;

    public PlayPlaylistCommand(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(List<String> tokens) {

        String userId = tokens.get(1);
        String playlistId = tokens.get(2);

        try {
            CurrentSongDto currentSong = userService.playPlaylist(userId, playlistId);
            System.out.println(currentSong);
        }
        catch (EmptyPlaylistException e) {
            System.out.println("Playlist is empty.");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
