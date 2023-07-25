package com.crio.jukebox.config;

import com.crio.jukebox.commands.*;
import com.crio.jukebox.repositories.*;
import com.crio.jukebox.repositories.data.SongDataLoader;
import com.crio.jukebox.repositories.data.strategies.SongFromCsvStrategy;
import com.crio.jukebox.services.IPlaylistService;
import com.crio.jukebox.services.IUserService;
import com.crio.jukebox.services.PlaylistService;
import com.crio.jukebox.services.UserService;

public class ApplicationConfig {

    private final IUserRepository userRepository = new UserRepository();
    private final IPlaylistRepository playlistRepository = new PlaylistRepository();
    private final ISongRepository songRepository = new SongRepository();

    private final SongDataLoader songDataLoader = new SongDataLoader(songRepository, new SongFromCsvStrategy());

    private final IUserService userService = new UserService(userRepository, songRepository);
    private final IPlaylistService playlistService = new PlaylistService(playlistRepository, userRepository, songRepository);

    private final ICommand createUserCommand= new CreateUserCommand(userService);
    private final ICommand createPlaylistCommand = new CreatePlaylistCommand(playlistService);
    private final ICommand deletePlaylistCommand = new DeletePlaylistCommand(playlistService);
    private final ICommand modifyPlaylistCommand = new ModifyPlaylistCommand(playlistService);
    private final ICommand playPlaylistCommand = new PlayPlaylistCommand(userService);
    private  final ICommand playSongCommand = new PlaySongCommand(userService);
    private final ICommand loadData = new LoadDataCommand(songDataLoader);

    private final CommandInvoker commandInvoker = new CommandInvoker();

    public CommandInvoker getCommandInvoker() {
        commandInvoker.register("CREATE-USER", createUserCommand);
        commandInvoker.register("CREATE-PLAYLIST", createPlaylistCommand);
        commandInvoker.register("DELETE-PLAYLIST", deletePlaylistCommand);
        commandInvoker.register("MODIFY-PLAYLIST", modifyPlaylistCommand);
        commandInvoker.register("PLAY-PLAYLIST", playPlaylistCommand);
        commandInvoker.register("PLAY-SONG", playSongCommand);
        commandInvoker.register("LOAD-DATA", loadData);
        return commandInvoker;
    }
}
