package com.crio.jukebox.repositories;

import com.crio.jukebox.entities.User;

import java.util.*;

/**
 * UserRepository is an implementation of the IUserRepository interface that uses a Map to store User entities.
 * This class provides CRUD operations for User entities and supports searching by name and ID.
 */
public class UserRepository implements IUserRepository {

    /**
     * A map to store User entities with their IDs as keys.
     */
    private final Map<String, User> userMap;

    /**
     * A counter to generate auto-incremented IDs for new User entities.
     */
    private Integer autoIncrement = 0;

    /**
     * Default constructor to initialize the UserRepository with an empty HashMap.
     */
    public UserRepository() {
        userMap = new HashMap<>();
    }

    /**
     * Constructor to initialize the UserRepository with an existing map of User entities.
     * @param userMap A map containing User entities with their IDs as keys.
     */
    public UserRepository(Map<String, User> userMap) {
        this.userMap = userMap;
        autoIncrement = userMap.size();
    }

    /**
     * Generates an auto-incremented ID for new User entities.
     * @return The generated ID as a string.
     */
    private String generateId() {
        return String.valueOf(++autoIncrement);
    }

    /**
     * Saves a User entity to the repository. If the entity already has an ID, it updates the existing entity.
     * Otherwise, it creates a new User entity with a generated ID.
     * @param entity The User entity to be saved or updated.
     * @return The saved or updated User entity.
     */
    @Override
    public User save(User entity) {
        if (entity.getId() != null) {
            // Update the existing entity in the map.
            return userMap.put(entity.getId(), entity);
        } else {
            // Create a new User entity with a generated ID and save it to the map.
            User newUser = new User(generateId(), entity);
            userMap.put(newUser.getId(), newUser);
            return newUser;
        }
    }

    /**
     * Returns a list of all User entities in the repository.
     * @return A list containing all User entities.
     */
    @Override
    public List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }

    /**
     * Retrieves a User entity by its ID.
     * @param id The ID of the User entity to retrieve.
     * @return An Optional containing the retrieved User entity, or an empty Optional if not found.
     */
    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(userMap.get(id));
    }

    /**
     * Checks if a User entity with the given ID exists in the repository.
     * @param id The ID of the User entity to check.
     * @return True if the User entity exists, false otherwise.
     */
    @Override
    public boolean existsById(String id) {
        return userMap.containsKey(id);
    }

    /**
     * Deletes a User entity from the repository.
     * @param entity The User entity to be deleted.
     */
    @Override
    public void delete(User entity) {
        if (entity.getId() != null) {
            // Remove the User entity with the specified ID from the map.
            userMap.remove(entity.getId());
        }
    }

    /**
     * Deletes a User entity from the repository by its ID.
     * @param id The ID of the User entity to be deleted.
     */
    @Override
    public void deleteById(String id) {
        userMap.remove(id);
    }

    /**
     * Returns the number of User entities in the repository.
     * @return The number of User entities.
     */
    @Override
    public long count() {
        return userMap.size();
    }

    /**
     * Searches for a User entity by name.
     * @param name The name to search for.
     * @return An Optional containing the first User entity with the matching name, or an empty Optional if not found.
     */
    @Override
    public Optional<User> findByName(String name) {
        return userMap.values().stream()
                .filter(user -> user.getName().equals(name))
                .findFirst();
    }
}
