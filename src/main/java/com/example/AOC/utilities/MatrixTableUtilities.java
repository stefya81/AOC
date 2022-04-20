package com.example.AOC.utilities;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class MatrixTableUtilities {

    private String rowSeparator;
    private String numberSeparator;

    public MatrixTableUtilities(String rowSeparator, String numberSeparator) {
        this.rowSeparator = rowSeparator;
        this.numberSeparator = numberSeparator;
    }

    public Integer getTableRowSize(String tableString) {
        return tableString.split(rowSeparator).length;
    }

    public Integer getTableColumnSize(String tableString) {
        String[] tableLineArray = tableString.split(rowSeparator);
        return convertRowToStringArray(tableLineArray[0]).length;
    }

    public List<Integer> convertStringLineToIntegerList(String tableString) {
        tableString = tableString.replaceAll(rowSeparator, numberSeparator);
        return Arrays.stream(convertRowToStringArray(tableString)).map(Integer::valueOf).collect(Collectors.toList());
    }

    private String[] convertRowToStringArray(String rowString) {
        rowString = rowString.replaceAll("  ", numberSeparator);
        rowString = rowString.trim();
        return rowString.split(numberSeparator);
    }

}
