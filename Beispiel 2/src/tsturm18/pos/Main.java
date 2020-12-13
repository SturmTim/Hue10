/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsturm18.pos;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author timst
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String file = "numbers/numbers4";
        try {
            int subarraySize = Integer.parseInt(Files.lines(new File(file).toPath())
                    .findFirst()
                    .orElse("0"));
            List<Integer> numbers = new ArrayList<>();
            Files.lines(new File(file).toPath())
                    .skip(1)
                    .forEach(line -> Arrays.stream(line.split(" "))
                    .forEach(numberString -> numbers.add(Integer.parseInt(numberString))));

            numberSplitter divider = new numberSplitter(subarraySize, numbers);

            divider.split(5);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
