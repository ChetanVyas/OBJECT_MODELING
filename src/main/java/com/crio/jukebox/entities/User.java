package com.crio.jukebox.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class User extends BaseEntity {

    private String name;
    private final List<Playlist> playlistList;
    private Playlist activePlaylist;
    private Song activeSong;

    public User(String id, String name, List<Playlist> listOfPlaylist, Playlist activePlaylist, Song activeSong) {
        super(id);
        this.name = name;
        this.playlistList = listOfPlaylist;
        this.activePlaylist = activePlaylist;
        this.activeSong = activeSong;
    }

    public User(String id, String name) {
        this(id, name, new ArrayList<>(), null, null);
    }

    public User(String id, User other) {
        this(id, other.name, other.playlistList, other.activePlaylist, other.activeSong);
    }

    public String getName() {
        return name;
    }

    public List<Playlist> getListOfPlaylist() {
        return playlistList;
    }

    public Optional<Playlist> getPlaylist(String playlistId) {
        return playlistList.stream()
                .filter(playlist -> playlist.getId().equals(playlistId))
                .findFirst();
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<Playlist> getActivePlaylist() {
        return Optional.ofNullable(activePlaylist);
    }

    public void setActivePlaylist(Playlist activePlaylist) {
        this.activePlaylist = activePlaylist;
    }

    public Optional<Song> getActiveSong() {
        return Optional.ofNullable(activeSong);
    }

    public void setActiveSong(Song activeSong) {
        this.activeSong = activeSong;
    }

    public void addPlaylist(Playlist playlist) {
        this.playlistList.add(playlist);
    }

    public  void deletePlaylist(Playlist playlist) {
        this.playlistList.remove(playlist);
    }

    public boolean checkIfPlaylistExist(Playlist playlist) {
        return playlistList.contains(playlist);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", listOfPlaylist=" + playlistList +
                ", id='" + id + '\'' +
                '}';
    }
}