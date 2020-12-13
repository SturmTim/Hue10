/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsturm18.pos;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author timst
 */
public class numberSplitter {

    private final int subarraySize;
    private final List<Integer> numbers;

    public numberSplitter(int subarraySize, List<Integer> numbers) {
        this.subarraySize = subarraySize;
        this.numbers = numbers;
    }

    public void split(int numberOfThreads) {
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);

        int start = 0;
        int space = (numbers.size() / numberOfThreads) - 1;
        List<maximumSearcherCallable> callables = new ArrayList<>();
        for (int i = 1; i <= numberOfThreads; i++) {
            if (i == numberOfThreads) {
                callables.add(new maximumSearcherCallable(numbers, subarraySize, start, numbers.size() - 1));
            } else {
                callables.add(new maximumSearcherCallable(numbers, subarraySize, start, start + space));
                start += space + 1;
            }
        }

        int maximum = 0;

        try {
            maximum = executor.invokeAll(callables).stream()
                    .mapToInt(integerFuture -> {
                        try {
                            return integerFuture.get();
                        } catch (InterruptedException | ExecutionException e) {
                            System.out.println("Exception in mapToInt");
                        }
                        return 0;
                    })
                    .max()
                    .orElse(0);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
        }

        System.out.println(maximum);

        executor.shutdown();
    }
}
