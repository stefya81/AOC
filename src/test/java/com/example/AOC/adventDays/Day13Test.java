package com.example.AOC.adventDays;

import com.example.AOC.utilities.UtilitiesIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class Day13Test {

    Day13 day13;
    UtilitiesIO utilitiesIO;

    @BeforeEach
    void setup() {
        day13 = new Day13();
        utilitiesIO = new UtilitiesIO();
    }


    @ParameterizedTest
    @CsvSource(value = {"src/main/resources/Day13/Prova.txt:17", "src/main/resources/Day13/Input.txt:618"}, delimiter = ':')
    void TestDay13_Part1(String fileName, Integer expectedValue) {
        String input = utilitiesIO.readAllFile(fileName);

        Integer actualOutput = day13.countDots(input, 1);
        System.out.println("actualOutput: " + actualOutput);

        assert actualOutput.equals(expectedValue);
    }

    @ParameterizedTest
    @CsvSource(value = {"src/main/resources/Day13/Prova.txt:16", "src/main/resources/Day13/Input.txt:98"}, delimiter = ':')
    void TestDay13_Part2(String fileName, Integer expectedValue) {
        String input = utilitiesIO.readAllFile(fileName);

        Integer actualOutput = day13.countDotsOfAllFolding(input);
        System.out.println("actualOutput: " + actualOutput);

        assert actualOutput.equals(expectedValue);
    }


}
