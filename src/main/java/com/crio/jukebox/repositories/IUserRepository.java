package com.crio.jukebox.repositories;

import com.crio.jukebox.entities.User;

import java.util.Optional;

public interface IUserRepository extends CRUDRepository<User, String> {
    public Optional<User> findByName(String name);
}
