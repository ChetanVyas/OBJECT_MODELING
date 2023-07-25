package com.crio.jukebox.commands;

import com.crio.jukebox.dtos.UserDto;
import com.crio.jukebox.services.IUserService;

import java.util.List;

public class CreateUserCommand implements ICommand{

    private final IUserService userService;

    public CreateUserCommand(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(List<String> tokens) {
        UserDto user = userService.createUser(tokens.get(1));
        System.out.println(user);
    }
}
