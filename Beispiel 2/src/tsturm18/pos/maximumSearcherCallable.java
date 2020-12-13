/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tsturm18.pos;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 *
 * @author timst
 */
public class maximumSearcherCallable implements Callable<Integer> {

    private final List<Integer> numbers;
    private final int subarraySize;
    private final int start;
    private final int end;

    public maximumSearcherCallable(List<Integer> numbers, int subarraySize, int start, int end) {
        this.numbers = numbers;
        this.subarraySize = subarraySize;
        this.start = start;
        this.end = end;
    }

    @Override
    public Integer call() {
        int maximum = 0;
        for (int i = start; i <= end; i++) {
            if (i + subarraySize <= numbers.size()) {
                List<Integer> numberSet = new ArrayList<>();
                for (int j = i; j < i + subarraySize; j++) {
                    if (!numberSet.contains(numbers.get(j))) {
                        numberSet.add(numbers.get(j));
                    }
                }
                if (numberSet.size() > maximum) {
                    maximum = numberSet.size();
                }
            } else {
                break;
            }
        }
        return maximum;
    }

}
