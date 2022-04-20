package com.example.AOC.utilities;

public class Point {
    private final int x;
    private final int y;

    static final private String COORDINATE_SEPARATOR = ",";

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(String str, String coordSeparator) {
        String[] coordinateArray = str.split(coordSeparator);
        this.x = Integer.parseInt(coordinateArray[0]);
        this.y = Integer.parseInt(coordinateArray[1]);
    }

    public Point(String str) {
        this(str, COORDINATE_SEPARATOR);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() != Point.class) {
            return false;
        }
        Point point = (Point) obj;
        return point.getX() == this.x && point.getY() == this.y;
    }

    @Override
    public int hashCode() {
        return this.x*10000 + this.y;
    }
}
