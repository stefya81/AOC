package com.example.AOC.adventDays;

import com.example.AOC.utilities.UtilitiesIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class Day02Test {

    Day02 day02;
    UtilitiesIO utilitiesIO;

    String fileNameProva = "src/main/resources/Day02/Prova.txt";
    String fileNameInput = "src/main/resources/Day02/Input.txt";

    Integer outputProva1 = 150;
    Integer outputProva2 = 900;

    @BeforeEach
    void setup() {
        day02 = new Day02();
        utilitiesIO = new UtilitiesIO();
    }

    @Test
    void TestDay02Part1Prova() {
        List<String> lines = utilitiesIO.readFile(fileNameProva);
        List<Day02.Command> commands = day02.convertToCommandList(lines);

        Day02.Position udpatedPosition = day02.parseCommandListAndEvaluatePosition(commands);

        Integer actualOutput = udpatedPosition.getDepth() * udpatedPosition.getOrizontalOffset();

        System.out.println("Result: " + actualOutput);
        assert actualOutput.equals(outputProva1);
    }

    @Test
    void TestDay02Part1() {
        List<String> lines = utilitiesIO.readFile(fileNameInput);
        List<Day02.Command> commands = day02.convertToCommandList(lines);

        Day02.Position udpatedPosition = day02.parseCommandListAndEvaluatePosition(commands);

        Integer actualOutput = udpatedPosition.getDepth() * udpatedPosition.getOrizontalOffset();

        System.out.println("Result: " + actualOutput);
        assert actualOutput != 0;
    }

    @Test
    void TestDay02Part2Prova() {
        List<String> lines = utilitiesIO.readFile(fileNameProva);
        List<Day02.Command> commands = day02.convertToCommandList(lines);

        Day02.Position udpatedPosition = day02.parseCommandListAndEvaluatePositionByAim(commands);

        Integer actualOutput = udpatedPosition.getDepth() * udpatedPosition.getOrizontalOffset();

        System.out.println("Result: " + actualOutput);
        assert actualOutput.equals(outputProva2);
    }

    @Test
    //My Result is 2120734350
    void TestDay02Part2() {
        List<String> lines = utilitiesIO.readFile(fileNameInput);
        List<Day02.Command> commands = day02.convertToCommandList(lines);

        Day02.Position udpatedPosition = day02.parseCommandListAndEvaluatePositionByAim(commands);

        Integer actualOutput = udpatedPosition.getDepth() * udpatedPosition.getOrizontalOffset();

        System.out.println("Result: " + actualOutput);
        assert actualOutput != 0;
    }
}
