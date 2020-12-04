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
import java.util.List;
import java.util.concurrent.Callable;

public class WebPasswordCallable implements Callable<String> {

    private final WebPasswordFinder webFinder;
    private final List<String> creatureList;
    private final int startString;
    private final int endString;

    public static boolean isRunning;

    public WebPasswordCallable(WebPasswordFinder webFinder, List<String> creatureList, int startString, int endString) {
        this.webFinder = webFinder;
        this.creatureList = creatureList;
        this.startString = startString;
        this.endString = endString;

        isRunning = true;
    }

    @Override
    public String call() {
        for (int index = startString; index <= endString && isRunning; index++) {
            if (StringUtil.applySha256(creatureList.get(index)).equals(webFinder.getPassword())) {
                return creatureList.get(index);
            }
        }
        while (isRunning) {
        }
        return null;
    }
}
