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
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        PasswordFinder pf = new PasswordFinder("password0");
        System.out.println("Passwort 0: " + pf.find());
//        PasswordFinder pf = new PasswordFinder("password1");
//        System.out.println("Passwort 1: " + pf.find());
//        PasswordFinder pf = new PasswordFinder("password2");
//        System.out.println("Passwort 2: " + pf.find());
        WebPasswordFinder wpf = new WebPasswordFinder("password3");
        System.out.println("Passwort 3: " + wpf.find());
    }

}
