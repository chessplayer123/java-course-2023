package edu.hw3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordFrequencyCounter {
    public WordFrequencyCounter() {
    }

    public <T> Map<T, Integer> freqDict(List<T> list) {
        Map<T, Integer> dict = new HashMap<T, Integer>();
        list.forEach(element -> dict.merge(element, 1, Integer::sum));
        return dict;
    }
}
