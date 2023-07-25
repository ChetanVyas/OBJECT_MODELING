package com.crio.jukebox.entities;

public abstract class BaseEntity {

    protected final String id;

    protected BaseEntity(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
