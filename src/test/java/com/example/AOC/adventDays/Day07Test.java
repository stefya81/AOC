package com.example.AOC.adventDays;

import com.example.AOC.utilities.UtilitiesIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class Day07Test {

    Day07 day07;
    UtilitiesIO utilitiesIO;

    @BeforeEach
    void setup() {
        day07 = new Day07();
        utilitiesIO = new UtilitiesIO();
    }


    @ParameterizedTest
    @CsvSource(value = {"src/main/resources/Day07/Prova.txt:37", "src/main/resources/Day07/Input.txt:355764"}, delimiter = ':')
    void TestDay05_Part1(String fileName, Integer expectedValue) {
        String input = utilitiesIO.readAllFile(fileName);

        Integer alignmentPosition = day07.evaluateAlignmentPosition(input, false);
        Integer actualOutput = day07.getFuelCost(input,alignmentPosition,false);
        System.out.println("actualOutput: " + actualOutput);


        assert actualOutput.equals(expectedValue);
    }

    @ParameterizedTest
    @CsvSource(value = {"src/main/resources/Day07/Prova.txt:168", "src/main/resources/Day07/Input.txt:99634572"}, delimiter = ':')
    void TestDay05_Part2(String fileName, Integer expectedValue) {
        String input = utilitiesIO.readAllFile(fileName);

        Integer alignmentPosition = day07.evaluateAlignmentPosition(input, true);
        Integer actualOutput = day07.getFuelCost(input,alignmentPosition,true);
        System.out.println("actualOutput: " + actualOutput);


        assert actualOutput.equals(expectedValue);
    }

}
