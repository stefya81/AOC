package com.example.AOC.utilities;

public class BaseCell {
    protected Integer value;
    protected Integer row;
    protected Integer column;

    public BaseCell(Integer value, Integer row, Integer column) {
        this.value = value;
        this.row = row;
        this.column = column;
    }

    public Integer getValue() {
        return value;
    }

    public Integer getRow() {
        return row;
    }

    public Integer getColumn() {
        return column;
    }


}
