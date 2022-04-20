package com.example.AOC.adventDays;

import com.example.AOC.utilities.Point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day05 {

    public class Vector {
        public Point initialPoint;
        public Point finalPoint;

        private final String VECTOR_SEPARATOR = " -> ";
        private final String POINT_SEPARATOR = ",";

        public Vector(String data) {
            String[] pointStringData = data.split(VECTOR_SEPARATOR);
            int initialXPoint = Integer.parseInt(pointStringData[0].split(POINT_SEPARATOR)[0]);
            int initialYPoint = Integer.parseInt(pointStringData[0].split(POINT_SEPARATOR)[1]);
            int finalXPoint = Integer.parseInt(pointStringData[1].split(POINT_SEPARATOR)[0]);
            int finalYPoint = Integer.parseInt(pointStringData[1].split(POINT_SEPARATOR)[1]);
            this.initialPoint = new Point(initialXPoint, initialYPoint);
            this.finalPoint = new Point(finalXPoint, finalYPoint);
        }

        boolean isVerticalLine() {
            return initialPoint.getX() == finalPoint.getX();
        }

        boolean isHorizontalLine() {
            return initialPoint.getY() == finalPoint.getY();
        }

        boolean isDiagonalLine() {
            return Math.abs(finalPoint.getY() - initialPoint.getY()) == Math.abs(finalPoint.getX() - initialPoint.getX());
        }

        public String toString() {
            return initialPoint.getX() + POINT_SEPARATOR + initialPoint.getY() + VECTOR_SEPARATOR + finalPoint.getX() + POINT_SEPARATOR + finalPoint.getY();
        }

    }

    public class Diagram {
        private int xSize;
        private int ySize;

        private List<Vector> vectors;
        private ArrayList<Integer> points;


        public Diagram() {
            this.xSize = 0;
            this.ySize = 0;
            this.vectors = new ArrayList<>();
        }

        public void draw(List<String> inputs, boolean keepDiagonal) {
            inputs.forEach(line -> {
                        Vector currentVector = new Vector(line);
                        if (!currentVector.isDiagonalLine() || keepDiagonal) {
                            updateXSize(currentVector);
                            updateYSize(currentVector);
                            vectors.add(currentVector);
                        }

                    }
            );

            drawDiagram();
        }

        public Integer evaluatePointWithOverlapOf(Integer overlapNumber) {
            return (int) this.points.stream().filter(value -> value >= overlapNumber).count();
        }

        private void updateXSize(Vector currentVector) {
            int currentVectorMaxX = Math.max(currentVector.finalPoint.getX(), currentVector.initialPoint.getX());
            this.xSize = Math.max(this.xSize, currentVectorMaxX + 1);
        }

        private void updateYSize(Vector currentVector) {
            int currentVectorMaxY = Math.max(currentVector.finalPoint.getY(), currentVector.initialPoint.getY());
            this.ySize = Math.max(this.ySize, currentVectorMaxY + 1);
        }

        private void drawDiagram() {
            this.points = new ArrayList<>(Collections.nCopies(this.xSize * this.ySize, 0));
            vectors.forEach(this::drawVector);
            //printDiagram();
        }

        private void drawVector(Vector vector) {
            if (vector.isHorizontalLine() || vector.isVerticalLine()) {
                drawStraightLine(vector);
            }
            if (vector.isDiagonalLine()) {
                drawDiagonalLine(vector);
            }

        }

        private void drawDiagonalLine(Vector vector) {
            boolean isFinalPointXTheMax = Math.max(vector.finalPoint.getX(), vector.initialPoint.getX()) == vector.finalPoint.getX();

            Point startPoint = isFinalPointXTheMax ? vector.initialPoint : vector.finalPoint;
            Point endPoint = isFinalPointXTheMax ? vector.finalPoint : vector.initialPoint;

            List<Integer> xPoints = getVectorXPoints(startPoint, endPoint);
            List<Integer> yPoints = getVectorYPoints(startPoint, endPoint);

            Iterator<Integer> itrX = xPoints.iterator();
            Iterator<Integer> itrY = yPoints.iterator();
            while(itrX.hasNext() && itrY.hasNext()) {
                int x = itrX.next();
                int y = itrY.next();
                drawPoint(x, y);
            }


        }

        private List<Integer> getVectorXPoints(Point startPoint, Point endPoint){
            IntStream intStream = IntStream.range(startPoint.getX(), endPoint.getX()+1);
            return intStream.boxed().collect(Collectors.toList());
        }

        private List<Integer> getVectorYPoints(Point startPoint, Point endPoint){
            int startRange = Math.min(startPoint.getY(), endPoint.getY());
            int endRange = Math.max(startPoint.getY(), endPoint.getY());
            List<Integer> intList  = IntStream.range(startRange, endRange+1).boxed().collect(Collectors.toList());

            if(startPoint.getY() > endPoint.getY()) {
                Collections.reverse(intList);
            }

            return intList;

        }


        private void drawPoint(int i, int j) {
            int currentIndex = (this.ySize * i) + j;
            Integer currentValue = points.get(currentIndex) + 1;
            points.set(currentIndex, currentValue);
        }


        private void drawStraightLine(Vector vector) {
            int currentVectorMinX = Math.min(vector.finalPoint.getX(), vector.initialPoint.getX());
            int currentVectorMaxX = Math.max(vector.finalPoint.getX(), vector.initialPoint.getX());

            int currentVectorMinY = Math.min(vector.finalPoint.getY(), vector.initialPoint.getY());
            int currentVectorMaxY = Math.max(vector.finalPoint.getY(), vector.initialPoint.getY());

            for (int i = currentVectorMinX; i <= currentVectorMaxX; i++) {
                for (int j = currentVectorMinY; j <= currentVectorMaxY; j++) {
                    drawPoint(i, j);
                }
            }

        }


        private void printDiagram() {
            System.out.println("\n");
            for (int i = 0; i < this.xSize; i++) {
                List<String> currentRow = getRow(i).stream().map(String::valueOf).collect(Collectors.toList());
                String currentString = String.join("", currentRow);
                System.out.println(currentString);
            }
        }


        List<Integer> getRow(int i) {
            return IntStream.range(0, points.size())
                    .filter(j -> (j - i) % this.ySize == 0)
                    .mapToObj(j -> this.points.get(j))
                    .collect(Collectors.toList());
        }

    }


    public Integer drawAndEvaluateOverlapPoints(List<String> inputs, boolean keepDiagonal) {
        Diagram diagram = new Diagram();
        diagram.draw(inputs, keepDiagonal);
        int pointNum = diagram.evaluatePointWithOverlapOf(2);
        return pointNum;
    }
}
