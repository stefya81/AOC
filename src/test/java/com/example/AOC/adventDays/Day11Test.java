package com.example.AOC.adventDays;

import com.example.AOC.utilities.UtilitiesIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class Day11Test {

    Day11 day11;
    UtilitiesIO utilitiesIO;

    @BeforeEach
    void setup() {
        day11 = new Day11();
        utilitiesIO = new UtilitiesIO();
    }


    @ParameterizedTest
    @CsvSource(value = {"src/main/resources/Day11/Prova.txt:1656", "src/main/resources/Day11/Input.txt:1686"}, delimiter = ':')
    void TestDay11_Part1(String fileName, Integer expectedValue) {
        String input = utilitiesIO.readAllFile(fileName);

        Integer actualOutput = day11.evaluateFlashesOnStepNumber(input, 100);
        System.out.println("actualOutput: " + actualOutput);

        assert actualOutput.equals(expectedValue);
    }

    @ParameterizedTest
    @CsvSource(value = {"src/main/resources/Day11/Prova.txt:195", "src/main/resources/Day11/Input.txt:360"}, delimiter = ':')
    void TestDay11_Part2(String fileName, Integer expectedValue) {
        String input = utilitiesIO.readAllFile(fileName);

        Integer actualOutput = day11.stopAndReturnStepOfSyncronizedFlash(input);
        System.out.println("actualOutput: " + actualOutput);

        assert actualOutput.equals(expectedValue);
    }


}
