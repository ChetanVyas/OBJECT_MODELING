package com.crio.jukebox.repositories;

import com.crio.jukebox.entities.Song;

import java.util.Optional;

import java.util.List;

public interface ISongRepository extends CRUDRepository<Song, String> {
    public List<Song> findByName(String songName);

    public List<Song> findByArtist(String artist);

    public List<Song> findByAlbum(String album);

    public List<Song> findByGenre(String genre);
}
