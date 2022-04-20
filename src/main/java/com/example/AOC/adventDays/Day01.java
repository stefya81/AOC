package com.example.AOC.adventDays;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Day01 {

    private final Integer windowSize = 3;

    Integer countNumberIncrement(List<Integer> numbers) {
        int incrementCounter = 0;
        for (int i = 1; i < numbers.size(); i++) {
            if (numbers.get(i) > numbers.get(i - 1)) {
                incrementCounter++;
                System.out.println("Increment: " + numbers.get(i) + "> " + numbers.get(i - 1));
            } else {
                System.out.println("Decrement: " + numbers.get(i) + "<= " + numbers.get(i - 1));
            }
        }

        return incrementCounter;
    }


    Integer countNumberIncrementInWindow(List<Integer> numbers) {
        int incrementCounter = 0;

        List<Integer> partialSumIntegers = new ArrayList<>();
        for (int i = 0; i < numbers.size() - windowSize + 1; i++) {
            int endWindowIndex = i + windowSize - 1;
            List<Integer> windowNumbers = numbers.subList(i, i + windowSize);

            Integer currentWindowSum = windowNumbers.stream().reduce(0, Integer::sum);
            partialSumIntegers.add(currentWindowSum);
            System.out.println("Print Window starting from index: " + i + " to index: " + endWindowIndex + ", with sum: " + currentWindowSum);
        }

        incrementCounter = countNumberIncrement(partialSumIntegers);
        return incrementCounter;
    }

}
