/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsturm18.pos;

/**
 *
 * @author timst
 */
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PasswordFinder {

    private final String password;
    private String file;

    public PasswordFinder(String file) {
        this.file = file;
        String lines = "";
        try {
            lines = Files.lines(new File("passwords/" + file).toPath())
                    .reduce((string1, string2) -> string1 + string2)
                    .orElse("");
        } catch (IOException e) {
            System.out.println("IOException in Constructor");
        }
        password = lines;
    }

    public String getPassword() {
        return password;
    }

    public String find() {

        List<PasswordCallable> callables = new ArrayList<>();
        String endString;
        if (file.equals("password0")) {
            for (String startString = "aaaa"; startString != null; startString = endString) {
                endString = getNextString(startString);
                callables.add(new PasswordCallable(this, startString, endString));
            }
        } else if (file.equals("password1")) {
            for (String startString = "AAAAAA"; startString != null; startString = endString) {
                endString = getNextString(startString);
                callables.add(new PasswordCallable(this, startString, endString));
            }
        } else {
            for (String startString = "00000"; startString != null; startString = endString) {
                endString = getNextString(startString);
                callables.add(new PasswordCallable(this, startString, endString));
            }
        }

        ExecutorService executor = Executors.newFixedThreadPool(callables.size());
        String result = "";
        try {
            result = executor.invokeAny(callables);
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Exception in find()");
        }

        executor.shutdown();
        PasswordCallable.isRunning = false;

        return result;
    }

    private String getNextString(String string) {
        char[] chars = string.toCharArray();
        int charValue = chars[0];
        charValue++;
        if (file.equals("password1")) {

            if (charValue == 91) {
                return null;
            }
        } else {
            switch (charValue) {
                case 58:
                    charValue = 65;
                    break;
                case 91:
                    charValue = 97;
                    break;
                case 123:
                    return null;
                default:
                    break;
            }
        }

        chars[0] = (char) charValue;

        return String.valueOf(chars);
    }

    public char[] next(char[] chars, int index) {
        int charValue = chars[index];
        charValue++;

        if (file.equals("password1")) {

            if (charValue == 91) {
                if (index == 0) {
                    return null;
                } else {
                    chars[index] = 65;
                    return next(chars, index - 1);
                }
            } else {
                chars[index] = (char) charValue;
            }
        } else {
            switch (charValue) {
                case 58:
                    chars[index] = 65;
                    break;
                case 91:
                    chars[index] = 97;
                    break;
                case 123:
                    if (index == 0) {
                        return null;
                    } else {
                        if (!file.equals("password0")) {
                            chars[index] = 48;
                        } else {
                            chars[index] = 97;
                        }

                        return next(chars, index - 1);
                    }
                default:
                    chars[index] = (char) charValue;
                    break;
            }
        }

        return chars;
    }
}
