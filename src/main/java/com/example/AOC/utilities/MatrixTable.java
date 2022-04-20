package com.example.AOC.utilities;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class MatrixTable<T extends BaseCell> {

    protected Integer rowNumber;
    protected Integer columnNumber;
    protected ArrayList<T> data;

    public MatrixTable() {
        this.rowNumber = 0;
        this.columnNumber = 0;
        this.data = new ArrayList<>();
    }

    public MatrixTable(Integer rowNumber, Integer columnNumber) {
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
        this.data = new ArrayList<>(rowNumber * columnNumber);
    }

    protected abstract T getCellInstance(Integer value, Integer row, Integer column);

    public void loadData(List<Integer> dataStream) {
        for (int i = 0; i < rowNumber; i++)
            for (int j = 0; j < columnNumber; j++) {
                {
                    int arrayPos = i * columnNumber + j;
                    Integer currentValue = dataStream.get(arrayPos);
                    T currentCell = getCellInstance(currentValue, i, j);
                    data.add(currentCell);
                }
            }
    }

    public void loadDataFromString(String input, String TABLE_ROW_SEPARATOR, String TABLE_NUMBER_SEPARATOR){
        MatrixTableUtilities utilities = new MatrixTableUtilities(TABLE_ROW_SEPARATOR, TABLE_NUMBER_SEPARATOR);
        this.rowNumber = utilities.getTableRowSize(input);
        this.columnNumber = utilities.getTableColumnSize(input);

        List<Integer> tableIntegers = utilities.convertStringLineToIntegerList(input);
        this.loadData(tableIntegers);
    }

    public T getCell(Integer row, Integer column) {
        return this.data.stream().filter(cell -> cell.getColumn().equals(column) && cell.getRow().equals(row)).findFirst().orElse(null);
    }

    public List<T> getRow(Integer row) {
        return this.data.stream().filter(cell -> cell.getRow().equals(row)).collect(Collectors.toList());
    }

    public void printMatrix() {
        System.out.println("\n");
        for (int i = 0; i < this.rowNumber; i++) {
            List<T> currentRow = getRow(i);
            List<String> currentRowStrings = currentRow.stream().map(cell -> String.valueOf(cell.getValue())).collect(Collectors.toList());
            String currentString = String.join("", currentRowStrings);
            System.out.println(currentString);
        }
    }
}
