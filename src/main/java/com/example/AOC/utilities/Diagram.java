package com.example.AOC.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Diagram<T> {

    private final int width;
    private final int height;
    private final ArrayList<T> points;

    public Diagram() {
        this.width = 0;
        this.height = 0;
        this.points = new ArrayList<>();
    }

    public Diagram( int width, int height, T emptySymbol) {
        this.width = width;
        this.height = height;
        this.points = new ArrayList<T>(Collections.nCopies(this.width * this.height, emptySymbol));
    }

    public void markPoint(int x, int y, T markPoint) {
        int index = (this.width * y) + x;
        points.set(index, markPoint);
    }

    public void printDiagram() {
        System.out.println("\n");
        for (int row = 0; row < this.height; row++) {
            List<String> currentRow = getRow(row).stream().map(String::valueOf).collect(Collectors.toList());
            String currentString = String.join("", currentRow);
            System.out.println(currentString);
        }
    }

    public void printDiagram(List<Point> points, T markPoint) {
        points.forEach(p -> markPoint(p.getX(), p.getY(), markPoint));
        printDiagram();
    }

    List<T> getRow(int row) {
        return IntStream.range(0, points.size())
                .filter(index -> (index / this.width) == row)
                .mapToObj(this.points::get)
                .collect(Collectors.toList());
    }




}
