package com.crio.jukebox.dtos;

public record UserDto(String id,String name) {

    @Override
    public String toString() {
        return id + " " + name;
    }
}
