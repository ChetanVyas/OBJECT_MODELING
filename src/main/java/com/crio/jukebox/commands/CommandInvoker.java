package com.crio.jukebox.commands;

import com.crio.jukebox.exceptions.NoSuchCommandException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The CommandInvoker class is responsible for registering and executing commands
 * based on their names.
 */
public class CommandInvoker {

    // A map to store command names and their corresponding ICommand implementations.
    private final Map<String, ICommand> commandMap = new HashMap<>();


    /**
     * Registers a command with the specified command name.
     *
     * @param commandName The name of the command to be registered.
     * @param command     The ICommand implementation for the command.
     */
    public void register(String commandName, ICommand command) {
        commandMap.put(commandName, command);
    }


    private ICommand get(String commandName) {
        return commandMap.get(commandName);
    }


    /**
     * Executes the command with the given command name and tokens.
     *
     * @param commandName The name of the command to be executed.
     * @param tokens      The list of tokens to be passed to the command's execute method.
     * @throws NoSuchCommandException If no ICommand is registered with the provided command name.
     */
    public void executeCommand(String commandName, List<String> tokens) throws NoSuchCommandException {
        // Validate if commandName is provided
        Objects.requireNonNull(commandName, "Command name must be provided.");

        // Get the ICommand implementation for the commandName
        ICommand command = get(commandName);
        if (command == null) {
            // Throw NoSuchCommandException if the command is not registered
            throw new NoSuchCommandException("No such Command: " + commandName);
        }

        // Execute the command with the given tokens
        command.execute(tokens);
    }
}