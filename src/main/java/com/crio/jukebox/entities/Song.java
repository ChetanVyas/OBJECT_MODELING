package com.crio.jukebox.entities;

import java.util.Objects;

public class Song extends BaseEntity {

    private final String name;
    private final String genre;
    private final String albumName;
    private final String artist;
    private final String[] featuredArtists;

    public Song(String id, String name, String genre, String albumName, String artist, String[] featuredArtists) {
        super(id);
        this.name = name;
        this.genre = genre;
        this.albumName = albumName;
        this.artist = artist;
        this.featuredArtists = featuredArtists;
    }

    public Song(String id, Song other) {
        this(id, other.name, other.genre, other.albumName, other.artist, other.featuredArtists);
    }

    public Song(String name, String genre, String albumName, String artist, String[] featuredArtists) {
        this(null, name, genre, albumName, artist, featuredArtists);
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String getAlbumName() {
        return albumName;
    }

    public String getArtist() {
        return artist;
    }

    public String[] getFeaturedArtists() {
        return featuredArtists;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;
        Song song = (Song) o;
        return Objects.equals(id, song.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
