package com.example.AOC.adventDays;

import com.example.AOC.utilities.UtilitiesIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

public class Day08Test {

    Day08 day08;
    UtilitiesIO utilitiesIO;

    @BeforeEach
    void setup() {
        day08 = new Day08();
        utilitiesIO = new UtilitiesIO();
    }


    @ParameterizedTest
    @CsvSource(value = {"src/main/resources/Day08/Prova1.txt:0", "src/main/resources/Day08/Prova.txt:26", "src/main/resources/Day08/Input.txt:521"}, delimiter = ':')
    void TestDay05_Part1(String fileName, Integer expectedValue) {
        String input = utilitiesIO.readAllFile(fileName);
        List<Day08.DIGIT> digits = day08.loadAndDecodeData(input);

        Integer actualOutput = day08.findDigits(digits, Day08.DIGIT.ONE, Day08.DIGIT.FOUR, Day08.DIGIT.SEVEN, Day08.DIGIT.EIGHT);
        System.out.println("actualOutput: " + actualOutput);

        assert actualOutput.equals(expectedValue);
    }

    @ParameterizedTest
    @CsvSource(value = {"src/main/resources/Day08/Prova1.txt:5353", "src/main/resources/Day08/Prova.txt:61229", "src/main/resources/Day08/Input.txt:1016804"}, delimiter = ':')
    void TestDay05_Part2(String fileName, Integer expectedValue) {
        String input = utilitiesIO.readAllFile(fileName);
        List<Integer> digits = day08.convertEachLineToSingleNumber(input);

        Integer actualOutput = digits.stream().mapToInt(Integer::intValue).sum();
        System.out.println("actualOutput: " + actualOutput);

        assert actualOutput.equals(expectedValue);
    }

}
