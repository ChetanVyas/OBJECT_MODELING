package com.crio.jukebox.dtos;

import java.util.Objects;

public final class PlaylistDto {
    private final String playlistId;
    private final String playlistName;
    private final String[] songIdList;

    public PlaylistDto(String playlistId, String playlistName, String[] songIdList) {
        this.playlistId = playlistId;
        this.playlistName = playlistName;
        this.songIdList = songIdList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaylistDto)) return false;
        PlaylistDto that = (PlaylistDto) o;
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

    public String playlistId() {
        return playlistId;
    }

    public String playlistName() {
        return playlistName;
    }

    public String[] songIdList() {
        return songIdList;
    }

}
