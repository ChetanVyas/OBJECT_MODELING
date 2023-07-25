package com.crio.jukebox.repositories.data;

import com.crio.jukebox.entities.Song;
import com.crio.jukebox.repositories.ISongRepository;
import com.crio.jukebox.repositories.data.strategies.IParsingStrategy;
import com.crio.jukebox.repositories.data.strategies.SongFromCsvStrategy;

import java.util.List;

public class SongDataLoader {

    private final ISongRepository songRepository;

    private IParsingStrategy<Song> strategy;

    public SongDataLoader(ISongRepository songRepository, IParsingStrategy<Song> strategy) {
        this.songRepository = songRepository;
        this.strategy = strategy;
    }

    public void changeStrategy(IParsingStrategy<Song> strategy) {
        this.strategy = strategy;
    }

    public void loadData(String filePath) {
        List<Song> execute = strategy.execute(filePath);
        execute.forEach(songRepository::save);
    }
}
