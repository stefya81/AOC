package com.example.AOC.adventDays;

import com.example.AOC.utilities.UtilitiesIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class Day01Test {

    Day01 day01;
    UtilitiesIO utilitiesIO;

    String fileNameProva = "src/main/resources/Day01/Prova.txt";
    String fileNamePart1 = "src/main/resources/Day01/Input.txt";

    Integer outputProva1 = 7;
    Integer outputProva2 = 5;

    @BeforeEach
    void setup() {
        day01 = new Day01();
        utilitiesIO = new UtilitiesIO();
    }

    @Test
    void TestDay01Part1Prova() {
        List<Integer> integers = utilitiesIO.readInput(fileNameProva);
        Integer actualOutput = day01.countNumberIncrement(integers);

        System.out.println("Result: " + actualOutput);
        assert actualOutput.equals(outputProva1);
    }

    @Test
    //My result is 1548
    void TestDay01Part1() {
        List<Integer> integers = utilitiesIO.readInput(fileNamePart1);
        Integer expectedOutput = day01.countNumberIncrement(integers);

        System.out.println("Result: " + expectedOutput);

        assert expectedOutput != 0;
    }

    @Test
    void TestDay01Part2Prova() {
        List<Integer> integers = utilitiesIO.readInput(fileNameProva);
        Integer actualOutput = day01.countNumberIncrementInWindow(integers);

        System.out.println("Result: " + actualOutput);
        assert actualOutput.equals(outputProva2);
    }


    //My result is 1589
    @Test
    void TestDay01Part2() {
        List<Integer> integers = utilitiesIO.readInput(fileNamePart1);
        Integer expectedOutput = day01.countNumberIncrementInWindow(integers);

        System.out.println("Result: " + expectedOutput);

        assert expectedOutput != 0;
    }

}
