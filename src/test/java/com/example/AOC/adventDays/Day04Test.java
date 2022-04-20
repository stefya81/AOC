package com.example.AOC.adventDays;

import com.example.AOC.utilities.UtilitiesIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class Day04Test {

    Day04 day04;
    UtilitiesIO utilitiesIO;

    @BeforeEach
    void setup() {
        day04 = new Day04();
        utilitiesIO = new UtilitiesIO();
    }


    @ParameterizedTest
    @CsvSource(value = {"src/main/resources/Day04/Prova.txt:4512", "src/main/resources/Day04/Input.txt:71708"}, delimiter = ':')
    void TestDay04_Part1(String fileName, Integer expectedValue) {
        String input = utilitiesIO.readAllFile(fileName);
        day04.loadGame(input);
        Day04.Winner winner = day04.playBingo();

        Integer boardNumber = winner.getValue();
        Integer sumUncheckedNumberInBoard = winner.getTable().sumAllUncheckedCells();

        Integer actualOutput = boardNumber * sumUncheckedNumberInBoard;

        System.out.println("The winner is the table number: " + String.valueOf(winner.getIndex() + 1) + " with extracted number: " + boardNumber);
        assert actualOutput.equals(expectedValue);
    }

    @ParameterizedTest
    @CsvSource(value = {"src/main/resources/Day04/Prova.txt:1924", "src/main/resources/Day04/Input.txt:34726"}, delimiter = ':')
    void TestDay04_Part2(String fileName, Integer expectedValue) {
        String input = utilitiesIO.readAllFile(fileName);
        day04.loadGame(input);
        Day04.Winner winner = day04.playUntilTheLastWin();

        Integer boardNumber = winner.getValue();
        Integer sumUncheckedNumberInBoard = winner.getTable().sumAllUncheckedCells();

        Integer actualOutput = boardNumber * sumUncheckedNumberInBoard;

        System.out.println("The winner is the table number: " + String.valueOf(winner.getIndex() + 1) + " with extracted number: " + boardNumber);
        assert actualOutput.equals(expectedValue);
    }
}
