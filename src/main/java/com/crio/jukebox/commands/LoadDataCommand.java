package com.crio.jukebox.commands;

import com.crio.jukebox.repositories.data.SongDataLoader;

import java.util.List;

public class LoadDataCommand implements ICommand {

    private final SongDataLoader songDataLoader;

    public LoadDataCommand(SongDataLoader songDataLoader) {
        this.songDataLoader = songDataLoader;
    }

    @Override
    public void execute(List<String> tokens) {
        String file = tokens.get(1);
        songDataLoader.loadData(file);
        System.out.println("Songs Loaded successfully");
    }
}
