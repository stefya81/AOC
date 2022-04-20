package com.example.AOC.adventDays;


import com.example.AOC.utilities.BaseCell;
import com.example.AOC.utilities.MatrixTable;
import com.example.AOC.utilities.MatrixTableUtilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day04 {

    private BingoGame game;

    public class BingoTableCell extends BaseCell {
        private Boolean marked;

        public BingoTableCell(Integer value, Integer row, Integer column) {
            super(value, row, column);
            this.marked = false;
        }

        public void markCell() {
            this.marked = true;
        }

        public Boolean isMarked() {
            return marked;
        }
    }

    public class BingoTable extends MatrixTable<BingoTableCell> {
        private Boolean hasWin;


        public BingoTable(Integer rowNumber, Integer columnNumber) {
            super(rowNumber,columnNumber);
            this.hasWin = false;
        }

        @Override
        protected BingoTableCell getCellInstance(Integer value, Integer row, Integer column) {
            return new BingoTableCell(value, row, column);
        }

        public Boolean hasWin() {
            return hasWin;
        }

        public void setWin() {
            hasWin = true;
        }

        public void resetWin() {
            hasWin = false;
        }

        public Boolean markCellByValueAndCheckBingoIfFound(Integer value) {
            BingoTableCell foundBingoTableCell = searchValueAndReturnTableCell(value);
            if (foundBingoTableCell == null) {
                return false;
            }

            foundBingoTableCell.markCell();
            return checkBingoOnRowAndOnColumn(foundBingoTableCell.getRow(), foundBingoTableCell.getColumn());

        }

        private BingoTableCell searchValueAndReturnTableCell(Integer value) {
            return data.stream().filter(cell -> cell.getValue().equals(value)).findFirst().orElse(null);
        }

        private Boolean checkBingoOnRowAndOnColumn(Integer rowPos, Integer colPos) {
            return checkBingoOnRow(rowPos) || checkBingoOnColumn(colPos);
        }

        private Boolean checkBingoOnRow(Integer row) {
            return data.stream().filter(cell -> cell.getRow().equals(row)).allMatch(BingoTableCell::isMarked);
        }

        private Boolean checkBingoOnColumn(Integer column) {
            return data.stream().filter(cell -> cell.getColumn().equals(column)).allMatch(BingoTableCell::isMarked);
        }

        public Integer sumAllUncheckedCells() {
            return data.stream().filter(cell -> !cell.isMarked()).map(BingoTableCell::getValue).reduce(0, Integer::sum);
        }
    }

    public class Winner {

        private BingoTable table;
        private Integer index;
        private Integer value;


        public Winner(BingoTable table, Integer index, Integer value) {
            this.table = table;
            this.index = index;
            this.value = value;
        }

        public BingoTable getTable() {
            return table;
        }

        public Integer getIndex() {
            return index;
        }

        public Integer getValue() {
            return value;
        }
    }


    public class BingoGame {

        private final String EXTRACT_NUMBER_SEPARATOR = ",";
        private final String TABLE_NUMBER_SEPARATOR = " ";
        private final String TABLE_SEPARATOR = "\r\n\r\n";
        private final String TABLE_ROW_SEPARATOR = "\r\n";

        private Integer rowNumber;
        private Integer columnNumber;

        private List<Integer> randomExtractedNumber;
        private List<BingoTable> tables;

        private Winner winner;

        public BingoGame() {
            this.rowNumber = 0;
            this.columnNumber = 0;
            this.tables = new ArrayList<>();
            this.randomExtractedNumber = new ArrayList<>();
            this.winner = null;
        }

        private void loadRandomExtractedNumbers(String input) {
            List<Integer> listOfNumber = Arrays.stream(input.split(TABLE_SEPARATOR)[0].split(EXTRACT_NUMBER_SEPARATOR)).map(Integer::valueOf).collect(Collectors.toList());
            this.randomExtractedNumber.addAll(listOfNumber);
        }

        private void loadTables(String input) {
            List<String> listOfTable = Arrays.stream(input.split(TABLE_SEPARATOR)).collect(Collectors.toList());
            listOfTable.remove(0);
            MatrixTableUtilities tableUtilities = new MatrixTableUtilities(TABLE_ROW_SEPARATOR, TABLE_NUMBER_SEPARATOR);
            this.rowNumber = tableUtilities.getTableRowSize(listOfTable.get(0));
            this.columnNumber = tableUtilities.getTableColumnSize(listOfTable.get(0));
            listOfTable.forEach( tableData -> {
                List<Integer> integers = tableUtilities.convertStringLineToIntegerList(tableData);
                loadTable(integers);
            });
        }

        private void loadTable(List<Integer> tableInput) {
            BingoTable table = new BingoTable(rowNumber, columnNumber);
            table.loadData(tableInput);
            tables.add(table);
        }

        private void resetWinner() {
            winner = null;
            tables.forEach(BingoTable::resetWin);
        }

        public Winner playAndReturnWinner() {
            resetWinner();

            boolean isThereAWinner = false;
            int index = 0;
            while (!isThereAWinner && index < randomExtractedNumber.size()) {
                isThereAWinner = markNumberInEachTableAndCheckWinner(randomExtractedNumber.get(index));
                index++;
            }

            return winner;
        }

        public Winner playUntilTheLastWin() {
            int index = 0;
            while (!hasAllWinned() && index < randomExtractedNumber.size()) {
                markNumberInEachTableAndSetWinner(randomExtractedNumber.get(index));
                index++;
            }

            return winner;

        }

        private boolean hasAllWinned() {
            return tables.stream().allMatch(BingoTable::hasWin);
        }

        private void markNumberInEachTableAndSetWinner(Integer value) {
            for (int i = 0; i < tables.size(); i++) {
                BingoTable currentTable = tables.get(i);
                if (currentTable.markCellByValueAndCheckBingoIfFound(value)) {
                    currentTable.setWin();
                    if (hasAllWinned()) {
                        winner = new Winner(currentTable, i, value);
                        return;
                    }
                }
            }
        }

        private Boolean markNumberInEachTableAndCheckWinner(Integer value) {
            for (int i = 0; i < tables.size(); i++) {
                BingoTable currentTable = tables.get(i);
                if (currentTable.markCellByValueAndCheckBingoIfFound(value)) {
                    currentTable.setWin();
                    winner = new Winner(currentTable, i, value);
                    return true;
                }
            }
            return false;
        }
    }

    public void loadGame(String input) {
        game = new BingoGame();
        game.loadRandomExtractedNumbers(input);
        game.loadTables(input);
    }

    public Winner playBingo() {
        return game.playAndReturnWinner();
    }

    public Winner playUntilTheLastWin() {
        return game.playUntilTheLastWin();
    }
}
