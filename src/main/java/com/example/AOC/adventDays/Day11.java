package com.example.AOC.adventDays;

import com.example.AOC.utilities.BaseCell;
import com.example.AOC.utilities.MatrixTable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day11 {
    private final String TABLE_ROW_SEPARATOR = "\r\n";
    private final String TABLE_NUMBER_SEPARATOR = "";

    public class PositionCountdownCell extends BaseCell {
        Integer CELL_MAX_VALUE = 9;
        Boolean hasFlashed = false;

        public PositionCountdownCell(Integer value, Integer row, Integer column) {
            super(value, row, column);
        }

        public Boolean isFlashed() {
            return hasFlashed;
        }

        public boolean isMaxValue() {
            return this.value.equals(CELL_MAX_VALUE);
        }

        void resetFlash() {
            hasFlashed = false;
        }

        public boolean incrementCellAndSendFlash() {
            if (!isMaxValue() && !hasFlashed) {
                value += 1;
                return false;
            }

            Boolean initialHasFlashed = hasFlashed;
            value = 0;
            hasFlashed = true;
            return !initialHasFlashed;
        }
    }

    public class FlashMatrix extends MatrixTable<PositionCountdownCell> {

        @Override
        protected PositionCountdownCell getCellInstance(Integer value, Integer row, Integer column) {
            return new PositionCountdownCell(value, row, column);
        }

        private void appendCell(Integer row, Integer column, List<PositionCountdownCell> surroundingCells) {
            if (row >= 0 && row < rowNumber && column >= 0 && column < columnNumber) {
                PositionCountdownCell cell = getCell(row, column);
                surroundingCells.add(cell);
            }
        }

        private List<PositionCountdownCell> getSurroundingCells(PositionCountdownCell cell) {
            List<PositionCountdownCell> surroundingCells = new ArrayList<>();
            Integer row = cell.getRow();
            Integer column = cell.getColumn();
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    appendCell(row + i, column + j, surroundingCells);
                }
            }

            surroundingCells.remove(cell);
            return surroundingCells;
        }

        boolean areAllFlashedCells() {
            return data.stream().allMatch(PositionCountdownCell::isFlashed);
        }

        Integer step() {
            data.forEach(PositionCountdownCell::resetFlash);
            return incrementCellAndCollectedFlashes(data);
        }

        private Integer incrementCellAndCollectedFlashes(List<PositionCountdownCell> cells) {
            List<PositionCountdownCell> flashedCells = new ArrayList<>();
            for (PositionCountdownCell cell : cells) {
                if (cell.incrementCellAndSendFlash()) {
                    flashedCells.add(cell);
                }
            }
            Integer flashNumber = flashedCells.size();
            for (PositionCountdownCell cell : flashedCells) {
                List<PositionCountdownCell> surroundingCells = getSurroundingCells(cell);
                flashNumber += incrementCellAndCollectedFlashes(surroundingCells);
            }

            return flashNumber;
        }

    }

    public Integer evaluateFlashesOnStepNumber(String input, Integer stepNumber) {
        FlashMatrix matrix = new FlashMatrix();
        matrix.loadDataFromString(input, TABLE_ROW_SEPARATOR, TABLE_NUMBER_SEPARATOR);
        List<Integer> stepsIterations = IntStream.range(0, stepNumber).boxed().collect(Collectors.toList());
        Integer totNumber = stepsIterations.stream().reduce(0, (flashNumber, currentStep) ->
        {
            flashNumber += matrix.step();
            matrix.printMatrix();
            return flashNumber;
        });
        return totNumber;
    }

    public Integer stopAndReturnStepOfSyncronizedFlash(String input) {
        FlashMatrix matrix = new FlashMatrix();
        matrix.loadDataFromString(input, TABLE_ROW_SEPARATOR, TABLE_NUMBER_SEPARATOR);
        boolean synchFlash = false;
        Integer stepNumber = 0;
        while (!synchFlash) {
            matrix.step();
            stepNumber++;
            synchFlash = matrix.areAllFlashedCells();
        }
        matrix.printMatrix();
        return stepNumber;
    }
}
