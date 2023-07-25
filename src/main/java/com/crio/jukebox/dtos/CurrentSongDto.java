package com.crio.jukebox.dtos;

import java.util.Objects;

public record CurrentSongDto(String songName, String album, String[] artists) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CurrentSongDto that)) return false;
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
}
