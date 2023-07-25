package com.crio.jukebox.commands;

import com.crio.jukebox.dtos.CurrentSongDto;
import com.crio.jukebox.entities.SongPlaybackControl;
import com.crio.jukebox.exceptions.*;
import com.crio.jukebox.services.IUserService;

import java.util.List;

public class PlaySongCommand implements ICommand {

    private final IUserService userService;

    public PlaySongCommand(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(List<String> tokens) {

        String userId = tokens.get(1);
        SongPlaybackControl playbackControl = null;
        String songId = null;
        try {
            playbackControl = SongPlaybackControl.valueOf(tokens.get(2));
        } catch (IllegalArgumentException e) {
            songId = tokens.get(2);
        }


        CurrentSongDto currentSong;
        try {

            if (playbackControl != null)
                currentSong = userService.playSongInPlaylist(userId, playbackControl);
            else
                currentSong = userService.playSongInPlaylist(userId, songId);

            System.out.println(currentSong);
        }
        catch (SongNotInPlaylistException e) {
            System.out.println("Given song id is not a part of the active playlist");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
