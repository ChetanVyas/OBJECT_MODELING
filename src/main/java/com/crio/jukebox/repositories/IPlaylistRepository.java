package com.crio.jukebox.repositories;

import com.crio.jukebox.entities.Playlist;

import java.util.List;

public interface IPlaylistRepository extends CRUDRepository<Playlist, String> {
    public List<Playlist> findByUserId(String userId);
}
