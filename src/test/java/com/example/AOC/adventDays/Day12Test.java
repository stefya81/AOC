package com.example.AOC.adventDays;

import com.example.AOC.utilities.UtilitiesIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

public class Day12Test {

    Day12 day12;
    UtilitiesIO utilitiesIO;

    @BeforeEach
    void setup() {
        day12 = new Day12();
        utilitiesIO = new UtilitiesIO();
    }


    @ParameterizedTest
    @CsvSource(value = {"src/main/resources/Day12/Prova.txt:10", "src/main/resources/Day12/Prova2.txt:19","src/main/resources/Day12/Prova3.txt:226", "src/main/resources/Day12/Input.txt:5756"}, delimiter = ':')
    void TestDay12_Part1(String fileName, Integer expectedValue) {
        List<String> input = utilitiesIO.readFile(fileName);

        Integer actualOutput = day12.evaluateAllPath(input, 1);
        System.out.println("actualOutput: " + actualOutput);

        assert actualOutput.equals(expectedValue);
    }

    @ParameterizedTest
    @CsvSource(value = {"src/main/resources/Day12/Prova.txt:36", "src/main/resources/Day12/Input.txt:144603"}, delimiter = ':')
    void TestDay12_Part2(String fileName, Integer expectedValue) {
        List<String> input = utilitiesIO.readFile(fileName);

        Integer actualOutput = day12.evaluateAllPath(input, 2);
        System.out.println("actualOutput: " + actualOutput);

        assert actualOutput.equals(expectedValue);
    }


}
