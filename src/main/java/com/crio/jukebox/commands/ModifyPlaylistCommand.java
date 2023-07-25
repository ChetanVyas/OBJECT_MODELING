package com.crio.jukebox.commands;

import com.crio.jukebox.dtos.PlaylistDto;
import com.crio.jukebox.entities.ModificationAction;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.exceptions.SongNotInPlaylistException;
import com.crio.jukebox.services.IPlaylistService;

import java.util.List;

public class ModifyPlaylistCommand implements ICommand {

    private final IPlaylistService playlistService;

    public ModifyPlaylistCommand(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Override
    public void execute(List<String> tokens) {

        ModificationAction action = ModificationAction.fromString(tokens.get(1));
        String userId = tokens.get(2);
        String playlistId = tokens.get(3);
        String[] songIds = tokens.subList(4, tokens.size()).toArray(new String[0]);

        PlaylistDto playlist;
        try {

            if(action == ModificationAction.ADD_SONG)
                playlist = playlistService.addSongsToPlaylist(userId, playlistId, songIds);
            else
                playlist = playlistService.deleteSongsFromPlaylist(userId, playlistId, songIds);

            System.out.println(playlist);
        }
        catch (SongNotFoundException e) {
            System.out.println("Some Requested Songs Not Available. Please try again.");
        }
        catch (SongNotInPlaylistException e) {
            System.out.println("Some Requested Songs for Deletion are not present in the playlist. Please try again.");
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
