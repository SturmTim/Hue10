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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class WebPasswordFinder {

    private final String password;

    public WebPasswordFinder(String file) {
        String lines = "";
        try {
            lines = Files.lines(new File("passwords/" + file).toPath())
                    .reduce((string1, string2) -> string1 + "\n" + string2)
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
        List<String> list = readCreatures();

        if (list != null) {
            List<WebPasswordCallable> callables = new ArrayList<>();
            int start = 0;
            int space = list.size() / 20;
            for (int counter = 0; counter < 19; counter++) {
                callables.add(new WebPasswordCallable(this, list, start, start + space));
                start += space + 1;
            }
            callables.add(new WebPasswordCallable(this, list, start, list.size() - 1));

            ExecutorService executor = Executors.newFixedThreadPool(20);
            String result = "";
            try {
                result = executor.invokeAny(callables);
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("Exception in find()");
            }

            executor.shutdown();
            WebPasswordCallable.isRunning = false;

            return result;
        }
        return null;
    }

    private List<String> readCreatures() {
        try {
            Document document = Jsoup.connect("https://de.wikipedia.org/wiki/Liste_von_Fabelwesen").get();
            return document.select("ul li a").stream()
                    .map(creature -> creature.attr("title"))
                    .filter(creature -> !creature.equals(""))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("IOException in readCreatures");
        }
        return null;
    }

}
