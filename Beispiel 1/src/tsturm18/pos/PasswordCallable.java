/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsturm18.pos;

import java.util.concurrent.Callable;

/**
 *
 * @author timst
 */
public class PasswordCallable implements Callable<String> {

    private final PasswordFinder passwordFinder;
    private final String startString;
    private final String endString;

    public static boolean isRunning;

    public PasswordCallable(PasswordFinder passwordFinder, String startString, String endString) {
        this.passwordFinder = passwordFinder;
        this.startString = startString;
        this.endString = endString;

        isRunning = true;
    }

    @Override
    public String call() throws Exception {
        String password = startString;
        while (!password.equals(endString) && isRunning) {
            if (StringUtil.applySha256(password).equals(passwordFinder.getPassword())) {
                return password;
            }
            password = String.valueOf(passwordFinder.next(password.toCharArray(), password.length() - 1));
        }
        while (isRunning) {
        }
        return null;
    }

}
