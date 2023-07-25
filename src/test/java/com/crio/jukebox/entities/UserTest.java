package com.crio.jukebox.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setup() {
        user = new User("1", "Tester");
        List<Playlist> playlistList = user.getListOfPlaylist();
        playlistList.add(new Playlist("1", "Playlist1", user.getId()));
        playlistList.add(new Playlist("2", "Playlist2", user.getId()));
        playlistList.add(new Playlist("3", "Playlist3", user.getId()));
    }

    @Test
    @DisplayName("checkIfPlaylistExist should Return true If Playlist is Found")
    void checkIfPlaylistExist_ShouldReturnTrue_WhenPlaylistExist() {

        // Arrange
        Playlist givenPlaylist = new Playlist("2", "Playlist2", user.getId());

        //Act
        boolean isPresent = user.checkIfPlaylistExist(givenPlaylist);

        //Assert
        assertTrue(isPresent);
    }

    @Test
    @DisplayName("checkIfPlaylistExist should Return false If Playlist is Not Found")
    void checkIfPlaylistExist_ShouldReturnFalse_WhenPlaylistDoesNotExist() {

        // Arrange
        User userX = new User("7", "TesterX");
        Playlist givenPlaylist = new Playlist("4", "Playlist7", userX.getId());

        //Act
        boolean isPresent = user.checkIfPlaylistExist(givenPlaylist);

        //Assert
        assertFalse(isPresent);
    }
}