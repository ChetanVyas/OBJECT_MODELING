package com.crio.jukebox;

import com.crio.jukebox.commands.CommandInvoker;
import com.crio.jukebox.config.ApplicationConfig;
import com.crio.jukebox.exceptions.NoSuchCommandException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public class App {
    // To run the application  ./gradlew run --args="INPUT_FILE=jukebox-input.txt"
	public static void main(String[] args) {
		List<String> commandLineArgs = new LinkedList<>(Arrays.asList(args));
        String expectedSequence = "INPUT_FILE";
        String actualSequence = commandLineArgs.stream()
                .map(a -> a.split("=")[0])
                .collect(Collectors.joining("$"));
        if(expectedSequence.equals(actualSequence)){
            run(commandLineArgs);
        }
	}

    public static void run(List<String> commandLineArgs) {
    // Complete the final logic to run the complete program.

        ApplicationConfig applicationConfig = new ApplicationConfig();
        CommandInvoker commandInvoker = applicationConfig.getCommandInvoker();
        String inputFile = commandLineArgs.get(0).split("=")[1];

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile))) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                List<String> tokens = Arrays.asList(line.split(" "));
                commandInvoker.executeCommand(tokens.get(0),tokens);
            }
        }
        catch (NoSuchCommandException | IOException e) {
            e.printStackTrace();
        }
    }
}
