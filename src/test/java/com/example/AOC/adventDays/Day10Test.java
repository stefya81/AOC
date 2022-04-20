package com.example.AOC.adventDays;

import com.example.AOC.utilities.UtilitiesIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

public class Day10Test {

    Day10 day10;
    UtilitiesIO utilitiesIO;

    @BeforeEach
    void setup() {
        day10 = new Day10();
        utilitiesIO = new UtilitiesIO();
    }


    @ParameterizedTest
    @CsvSource(value = {"src/main/resources/Day10/Prova.txt:26397", "src/main/resources/Day10/Input.txt:358737"}, delimiter = ':')
    void TestDay10_Part1(String fileName, Integer expectedValue) {
        List<String> input = utilitiesIO.readFile(fileName);

        Integer actualOutput = day10.parseAndEvaluateErrors(input);
        System.out.println("actualOutput: " + actualOutput);

        assert actualOutput.equals(expectedValue);
    }

    @ParameterizedTest
    @CsvSource(value = {"src/main/resources/Day10/Prova.txt:288957", "src/main/resources/Day10/Input.txt:4329504793"}, delimiter = ':')
    //@CsvSource(value = {"src/main/resources/Day10/Prova.txt:288957"}, delimiter = ':')
    void TestDay10_Part2(String fileName, Long expectedValue) {
        List<String> input = utilitiesIO.readFile(fileName);

        Long actualOutput = day10.parseAndEvaluateMissingBracketScore(input);
        System.out.println("actualOutput: " + actualOutput);

        assert actualOutput.equals(expectedValue);
    }


}
