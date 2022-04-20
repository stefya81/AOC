package com.example.AOC.adventDays;

import com.example.AOC.utilities.UtilitiesIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

public class Day06Test {

    Day06 day06;
    UtilitiesIO utilitiesIO;

    @BeforeEach
    void setup() {
        day06 = new Day06();
        utilitiesIO = new UtilitiesIO();
    }


    @ParameterizedTest
    @CsvSource(value = {"src/main/resources/Day06/Prova.txt:26", "src/main/resources/Day06/Input.txt:355386",
                        "src/main/resources/Day06/Prova2.txt:26984457539", "src/main/resources/Day06/Input2.txt:1613415325809"}, delimiter = ':')
    void TestDay05_Part1_2(String fileName, Long expectedValue) {
        List<String> lines = utilitiesIO.readFile(fileName);

        Long actualOutput = day06.startLaternFishSwamReproduction(lines);
        System.out.println("actualOutput: " + actualOutput);


        assert actualOutput.equals(expectedValue);
    }

}
