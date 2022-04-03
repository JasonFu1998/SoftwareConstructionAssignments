package com.io;

import com.mapping.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Input {
    public List<Position> read(String input) throws Exception {
        String[] userInput = input.toLowerCase().split("(?<=])[x]");
        if (userInput.length > 2) {
            throw new Exception("Too many inputs!");
        }
        if (userInput.length < 2) {
            throw new Exception("Too less inputs!");
        }
        Pattern regex = Pattern.compile("\\[([a-h][1-8])\\]");
        List<Position> validInputs = new ArrayList<>();
        for (String s : userInput) {
            Matcher regexMatcher = regex.matcher(s);
            while (regexMatcher.find()) {
                validInputs.add(new Position(Integer.parseInt(regexMatcher.group(1).substring(1, 2)) - 1, regexMatcher.group(1).substring(0, 1).charAt(0)));
            }
        }
        if (validInputs.size() != 2) {
            throw new Exception("Too less valid inputs!");
        }
        return validInputs;
    }
}