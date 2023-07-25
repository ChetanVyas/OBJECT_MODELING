package com.crio.jukebox.exceptions;

public class SongNotInPlaylistException extends Exception{

    public SongNotInPlaylistException(String message) {
        super(message);
    }
}
