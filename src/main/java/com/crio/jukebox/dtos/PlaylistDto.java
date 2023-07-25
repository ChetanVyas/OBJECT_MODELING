package com.crio.jukebox.dtos;

import java.util.Objects;

public record PlaylistDto(String playlistId, String playlistName, String[] songIdList) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaylistDto that)) return false;
        return Objects.equals(playlistId, that.playlistId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playlistId);
    }

    @Override
    public String toString() {

        String songIds = String.join(" ", songIdList);
        return "Playlist ID - " + playlistId + "\n" +
                "Playlist Name - " + playlistName + "\n" +
                "Song IDs - " + songIds;
    }
}
