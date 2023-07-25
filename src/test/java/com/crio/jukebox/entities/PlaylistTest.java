package com.crio.jukebox.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaylistTest {

    private Playlist playlist;

    @BeforeEach
    void setup() {
        User user = new User("1", "Tester");
        List<String> songIdList = List.of("123", "213", "312");
        playlist = new Playlist("1", "TestPlaylist", user.getId(), songIdList);
    }

    @Test
    void checkIfSongExist_ShouldReturnTrue_WhenSongExist() {
        // Arrange
        // Act
        boolean isPresent = playlist.checkIfSongExist("213");

        // Assert
        assertTrue(isPresent);
    }

    @Test
    void checkIfSongExist_ShouldReturnFalse_WhenSongDoesNotExist() {
        // Arrange
        // Act
        boolean isPresent = playlist.checkIfSongExist("223");

        // Assert
        assertFalse(isPresent);
    }
}