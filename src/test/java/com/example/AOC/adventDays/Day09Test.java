package com.example.AOC.adventDays;

import com.example.AOC.utilities.UtilitiesIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class Day09Test {

    Day09 day09;
    UtilitiesIO utilitiesIO;

    @BeforeEach
    void setup() {
        day09 = new Day09();
        utilitiesIO = new UtilitiesIO();
    }


    @ParameterizedTest
    @CsvSource(value = {"src/main/resources/Day09/Prova.txt:15", "src/main/resources/Day09/Input.txt:566"}, delimiter = ':')
    void TestDay05_Part1(String fileName, Integer expectedValue) {
        String input = utilitiesIO.readAllFile(fileName);

        Integer actualOutput = day09.evaluateRiskLevel(input, 1);
        System.out.println("actualOutput: " + actualOutput);

        assert actualOutput.equals(expectedValue);
    }


    @ParameterizedTest
    @CsvSource(value = {"src/main/resources/Day09/Prova.txt:1134", "src/main/resources/Day09/Input.txt:891684"}, delimiter = ':')
    void TestDay05_Part2(String fileName, Integer expectedValue) {
        String input = utilitiesIO.readAllFile(fileName);

        Integer actualOutput = day09.threeLargestBasinsMultiplication(input);
        System.out.println("actualOutput: " + actualOutput);

        assert actualOutput.equals(expectedValue);
    }

}
