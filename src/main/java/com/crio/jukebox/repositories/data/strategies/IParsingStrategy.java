package com.crio.jukebox.repositories.data.strategies;

import java.util.List;

public interface IParsingStrategy <T> {
    List<T> execute(String filePath);
}
