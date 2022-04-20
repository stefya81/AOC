package com.example.AOC.adventDays;

import com.example.AOC.utilities.BaseCell;
import com.example.AOC.utilities.MatrixTable;
import com.example.AOC.utilities.MatrixTableUtilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day09 {
    private final String TABLE_ROW_SEPARATOR = "\r\n";
    private final String TABLE_NUMBER_SEPARATOR = "";

    private PositionMatrixTable table;

    public class PositionCell extends BaseCell {
        Integer CELL_MAX_VALUE = 9;

        public PositionCell(Integer value, Integer row, Integer column) {
            super(value, row, column);
        }

        public boolean isMaxValue(){
            return this.value.equals(CELL_MAX_VALUE);
        }
    }

    public class PositionMatrixTable extends MatrixTable<PositionCell> {

        public PositionMatrixTable(Integer rowNumber, Integer columnNumber) {
            super(rowNumber, columnNumber);
        }

        @Override
        protected PositionCell getCellInstance(Integer value, Integer row, Integer column) {
            return new PositionCell(value, row, column);
        }

        private void appendLeft(Integer row, Integer column, List<PositionCell> surroundingCells) {
            if (column > 0) {
                PositionCell cell = getCell(row, column - 1);
                appendSurroundingCell(surroundingCells, cell);
            }
        }

        private void appendSurroundingCell(List<PositionCell> surroundingCells, PositionCell cell) {
            if(!cell.isMaxValue()) {
                surroundingCells.add(cell);
            }
        }

        private void appendUp(Integer row, Integer column, List<PositionCell> surroundingCells) {
            if (row > 0) {
                PositionCell cell = getCell(row - 1, column);
                appendSurroundingCell(surroundingCells, cell);
            }
        }

        private void appendRight(Integer row, Integer column, List<PositionCell> surroundingCells) {
            if (column < columnNumber - 1) {
                PositionCell cell = getCell(row, column + 1);
                appendSurroundingCell(surroundingCells, cell);
            }
        }

        private void appendDown(Integer row, Integer column, List<PositionCell> surroundingCells) {
            if (row < rowNumber - 1) {
                PositionCell cell = getCell(row + 1, column);
                appendSurroundingCell(surroundingCells, cell);
            }
        }

        private List<PositionCell> getSurroundingCells(PositionCell cell) {
            List<PositionCell> surroundingCells = new ArrayList<>();
            appendUp(cell.getRow(), cell.getColumn(), surroundingCells);
            appendLeft(cell.getRow(), cell.getColumn(), surroundingCells);
            appendRight(cell.getRow(), cell.getColumn(), surroundingCells);
            appendDown(cell.getRow(), cell.getColumn(), surroundingCells);
            return surroundingCells;
        }

        private Integer getMinSurroundingCells(List<PositionCell> cells) {
            return cells.stream().map(BaseCell::getValue).min(Integer::compare).orElse(0);
        }

        private boolean isTheLocalMinimum(PositionCell cell) {
            List<PositionCell> cells = getSurroundingCells(cell);
            Integer surroundingMinValue = getMinSurroundingCells(cells);
            return cell.getValue() < surroundingMinValue;
        }

        private Integer getBasinSizeAroundCell(PositionCell cell) {
            List<PositionCell> cells = new ArrayList<>();
            cells.add(cell);

            searchSurroundingCellsAndAppend(cell, cells);
            return cells.size();
        }

        private void searchSurroundingCellsAndAppend(PositionCell cell, List<PositionCell> inputs){
            List<PositionCell> cells = getSurroundingCells(cell);
            List<PositionCell> cellToSearch = cells.stream().filter(c -> !inputs.contains(c)).collect(Collectors.toList());

            if(cellToSearch.isEmpty()){
                return;
            }

            inputs.addAll(cellToSearch);

            cellToSearch.forEach(currentCell -> {
                searchSurroundingCellsAndAppend(currentCell, inputs);
            });

        }

        public List<Integer> parseAndFindLocalRiskValues(Integer riskLevel) {
            return findLocalMinimumCells().stream().map(cell -> cell.getValue() + riskLevel).collect(Collectors.toList());
        }

        public List<PositionCell> findLocalMinimumCells(){
            return this.data.stream().filter(this::isTheLocalMinimum).collect(Collectors.toList());
        }
    }

    private void loadTable(String input) {
        MatrixTableUtilities utilities = new MatrixTableUtilities(TABLE_ROW_SEPARATOR, TABLE_NUMBER_SEPARATOR);
        Integer rowSize = utilities.getTableRowSize(input);
        Integer columnSize = utilities.getTableColumnSize(input);

        this.table = new PositionMatrixTable(rowSize, columnSize);

        List<Integer> tableIntegers = utilities.convertStringLineToIntegerList(input);
        this.table.loadData(tableIntegers);
    }

    public Integer evaluateRiskLevel(String input, Integer riskLevel) {
        this.loadTable(input);
        List<Integer> riskValues = this.table.parseAndFindLocalRiskValues(riskLevel);
        return riskValues.stream().mapToInt(Integer::intValue).sum();
    }

    public Integer threeLargestBasinsMultiplication(String input){
        this.loadTable(input);
        List<PositionCell> minCells = this.table.findLocalMinimumCells();
        List<Integer> basinSizes = minCells.stream()
                .map(cell -> this.table.getBasinSizeAroundCell(cell))
                .sorted(Collections.reverseOrder())
                .collect(Collectors.toList());
        Integer basinSizeMultiplication = basinSizes.subList(0,3).stream().reduce(1, (a, b) -> a * b);
        return basinSizeMultiplication;
    }
}
