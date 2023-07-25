package com.crio.jukebox.entities;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Playlist extends BaseEntity {

    private String name;
    private final String creatorId;
    private final List<String> songIdList;

    public Playlist(String id, String name, String creatorId, List<String> songIdList) {
        super(id);
        this.name = name;
        this.creatorId = creatorId;
        this.songIdList = songIdList;
    }

    public Playlist(String name, String creatorId) {
        this(null, name, creatorId, new LinkedList<>());
    }

    public Playlist(String id, String name, String creatorId) {
        this(id, name, creatorId, new LinkedList<>());
    }

    public Playlist(String id, Playlist other) {
        this(id, other.name, other.creatorId, other.songIdList);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public List<String> getSongIdList() {
        return songIdList;
    }

    public boolean checkIfSongExist(String songId) {
        return this.songIdList.contains(songId);
    }

    public boolean isEmpty() {
        return songIdList.isEmpty();
    }

    public void addSong(String songId) {
        this.songIdList.add(songId);
    }

    public void deleteSong(String songId) {
        this.songIdList.remove(songId);
    }

    public int getSongCount() {
        return songIdList.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Playlist)) return false;
        Playlist that = (Playlist) o;
        return this.id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "name='" + name + '\'' +
                ", creator=" + creatorId +
                ", songIdList=" + songIdList +
                ", id='" + id + '\'' +
                '}';
    }
}
