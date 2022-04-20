package com.example.AOC.adventDays;

import com.example.AOC.utilities.BitFinderUtilities;
import com.example.AOC.utilities.UtilitiesIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class Day03Test {

    Day03 day03;
    UtilitiesIO utilitiesIO;

    String fileNameProva = "src/main/resources/Day03/Prova.txt";
    String fileNameInput = "src/main/resources/Day03/Input.txt";
    Integer outputProva1 = 198;
    Integer outputProva2 = 230;

    @BeforeEach
    void setup() {
        day03 = new Day03(new BitFinderUtilities());
        utilitiesIO = new UtilitiesIO();
    }

    @Test
    void TestDay03Part1Prova() {
        List<String> input = utilitiesIO.readFile(fileNameProva);

        Integer gammaRate = day03.evaluateGammaRate(input);
        Integer epsilonRate = day03.evaluateEpsilonRate(input);

        Integer actualOutput = gammaRate * epsilonRate;

        System.out.println("Result: " + actualOutput);
        assert actualOutput.equals(outputProva1);
    }

    @Test
        //My result 4118544
    void TestDay03Part1() {
        List<String> input = utilitiesIO.readFile(fileNameInput);

        Integer gammaRate = day03.evaluateGammaRate(input);
        Integer epsilonRate = day03.evaluateEpsilonRate(input);

        Integer actualOutput = gammaRate * epsilonRate;

        System.out.println("Result: " + actualOutput);
        assert actualOutput != 0;
    }

    @Test
    void TestDay03Part2Prova() {
        List<String> input = utilitiesIO.readFile(fileNameProva);

        Integer oxigenRate = day03.evaluateOxigenGenerator(input);
        Integer co2Scrubber = day03.evaluateCO2Scrubber(input);

        Integer actualOutput = oxigenRate * co2Scrubber;

        System.out.println("Result: " + actualOutput);
        assert actualOutput.equals(outputProva2);
    }

    @Test
    void TestDay03Part2() {
        List<String> input = utilitiesIO.readFile(fileNameInput);

        Integer oxigenRate = day03.evaluateOxigenGenerator(input);
        Integer co2Scrubber = day03.evaluateCO2Scrubber(input);

        Integer actualOutput = oxigenRate * co2Scrubber;

        System.out.println("Result: " + actualOutput);
        assert actualOutput != 0;
    }


}
