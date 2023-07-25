package com.crio.jukebox.dtos;

import java.util.Objects;

public final class CurrentSongDto {
    private final String songName;
    private final String album;
    private final String[] artists;

    public CurrentSongDto(String songName, String album, String[] artists) {
        this.songName = songName;
        this.album = album;
        this.artists = artists;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CurrentSongDto)) return false;
        CurrentSongDto that = (CurrentSongDto) o;
        return Objects.equals(songName, that.songName) && Objects.equals(album, that.album);
    }

    @Override
    public int hashCode() {
        return Objects.hash(songName, album);
    }

    @Override
    public String toString() {
        String artists = String.join(",", this.artists);
        return "Current Song Playing\n" +
                "Song - " + songName + "\n" +
                "Album - " + album + "\n" +
                "Artists - " + artists;
    }

    public String songName() {
        return songName;
    }

    public String album() {
        return album;
    }

    public String[] artists() {
        return artists;
    }

}
