# Jukebox

Jukebox is a command line-based music player that allows user to create, manage playlists and play songs from playlists.

## Functional Requirements

* A user can create a playlist from a pool of available songs.
* A user can delete a playlist.
* A user can add / delete songs from the playlist.
* A user can start playing songs by choosing a playlist. On choosing a playlist, the first song in the playlist will start playing.
* A user can switch songs by using Next, Back command or by choosing another song from the playlist that has been chosen.
  1. On reaching the end, Next will switch to the first song in the current playlist.
  2. On reaching the start, Back will switch to the last song in the playlist.
* Only one song can be played at a time.
* A user can choose to play the song from another playlist if and only if that playlist is selected. Basically two operations have to be done to successfully play the song of their choice.
  1. Select the playlist ( which will play the first song when selected )
  2. Choose the song of your choice.
* An album is a collection of songs owned by the original artist / artist group .
Artist Group Example:- One Direction.
One Direction Group is the album owner and is considered as an artist.
* Each song can feature multiple artists but it will be owned by the artist whose album this song belongs to.
* Each song can only be a part of one album.

## Commands
The following commands are supported by the application:

* `LOAD-DATA {input_file}`: Load songs from a CSV file into the Song repository.
* `CREATE-USER {name}`: Create a new user in the system.
* `CREATE-PLAYLIST {user_id} {playlist_name} {song_ids}`: Create a new playlist with the given songs.
* `DELETE-PLAYLIST {user_id} {playlist_id}`: Delete a playlist with the specified ID.
* `MODIFY-PLAYLIST ADD-SONG {user_id} {playlist_id} {song_ids}`: Add songs to an existing playlist.
* `MODIFY-PLAYLIST DELETE-SONG {user_id} {playlist_id} {song_ids}`: Delete songs from an existing playlist.
* `PLAY-PLAYLIST {user_id} {playlist_id}`: Start playing the selected playlist.
* `PLAY-SONG {user_id} BACK`: Switch to the previous song in the active playlist.
* `PLAY-SONG {user_id} NEXT`: Switch to the next song in the active playlist.
* `PLAY-SONG {user_id} {song_id}`: Switch to the preferred song in the active playlist.

## Implementation
The application follows **SOLID Principles** and a modular layered **Clean Architecture** with the following key components:
Models - Plain Java objects for entities like Song, Playlist, User etc.

* **Repositories** - Data access objects that handle data persistence. Repository abstracts the Data Store and enables your business logic to define read and write operations at a logical level.

* **Services** - Business logic layer, encapsulate and implement all the approved use cases for the application.

* **Command Handlers** - Logic to parse input and execute relevant services

* **Command Runner** - Main entry point to read input and invoke relevant handlers