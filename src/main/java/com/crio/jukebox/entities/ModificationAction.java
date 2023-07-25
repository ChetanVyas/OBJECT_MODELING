package com.crio.jukebox.entities;

public enum ModificationAction {
    ADD_SONG,
    DELETE_SONG;

    public static ModificationAction fromString(String token) {
        return switch (token.toUpperCase()) {
            case "ADD-SONG" -> ADD_SONG;
            case "DELETE-SONG" -> DELETE_SONG;
            default -> throw new IllegalArgumentException(token + " Invalid operation (enum not found)");
        };
    }
}
