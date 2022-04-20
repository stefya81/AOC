package com.example.AOC.adventDays;

import com.example.AOC.utilities.UtilitiesIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

public class Day05Test {

    Day05 day05;
    UtilitiesIO utilitiesIO;

    @BeforeEach
    void setup() {
        day05 = new Day05();
        utilitiesIO = new UtilitiesIO();
    }


    @ParameterizedTest
    @CsvSource(value = {"src/main/resources/Day05/Prova.txt:5", "src/main/resources/Day05/Input.txt:5835"}, delimiter = ':')
    void TestDay05_Part1(String fileName, Integer expectedValue) {
        List<String> lines = utilitiesIO.readFile(fileName);


        Integer actualOutput = day05.drawAndEvaluateOverlapPoints(lines, false);
        System.out.println("actualOutput: " + actualOutput);


        assert actualOutput.equals(expectedValue);
    }

    @ParameterizedTest
    @CsvSource(value = {"src/main/resources/Day05/Prova.txt:12", "src/main/resources/Day05/Input.txt:17013"}, delimiter = ':')
    void TestDay05_Part2(String fileName, Integer expectedValue) {
        List<String> lines = utilitiesIO.readFile(fileName);


        Integer actualOutput = day05.drawAndEvaluateOverlapPoints(lines, true);
        System.out.println("actualOutput: " + actualOutput);


        assert actualOutput.equals(expectedValue);
    }
}
