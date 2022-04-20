package com.example.AOC.adventDays;

import com.example.AOC.utilities.Diagram;
import com.example.AOC.utilities.Point;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Day13 {

    enum FoldingRuleType {
        H_FOLDING("fold along y="),
        V_FOLDING("fold along x="),
        NONE("");

        private final String rule;

        FoldingRuleType(String rules) {
            this.rule = rules;
        }

        public String getRule() {
            return rule;
        }

        public static FoldingRuleType extractFoldingRule(String str) {
            FoldingRuleType enumType = Arrays.stream(FoldingRuleType.values()).filter(enumValue -> str.contains(enumValue.getRule())).findFirst().orElse(NONE);
            return enumType;
        }
    }

    private final String POINT_FOLDING_RULES_SEPARATOR = "\r\n\r\n";
    private TransparentPaper paper;

    public class TransparentPaper {
        List<Point> points;

        private final String POINT_SEPARATOR = "\r\n";
        private final String RULES_SEPARATOR = "\r\n";


        public void loadPoints(String pointLines) {
            this.points = Arrays.stream(pointLines.split(POINT_SEPARATOR)).map(Point::new).collect(Collectors.toList());
        }

        int maxX() {
            return this.points.stream().map(Point::getX).max(Comparator.naturalOrder()).orElse(0);
        }

        int maxY() {
            return this.points.stream().map(Point::getY).max(Comparator.naturalOrder()).orElse(0);
        }

        Integer getFoldingOffset(String ruleStr, FoldingRuleType ruleType) {
            Integer offset = Integer.parseInt(ruleStr.replace(ruleType.getRule(), ""));
            return offset;
        }


        public List<Point> applyFoldingRules(String foldingRules, int ruleNumbers, boolean ignoreRuleNumber) {

            String[] rules = foldingRules.split(RULES_SEPARATOR);
            int ruleCounter = ignoreRuleNumber? rules.length : ruleNumbers;
            for (int counter = 0; counter < ruleCounter; counter++) {
                String ruleStr = rules[counter];
                FoldingRuleType ruleType = FoldingRuleType.extractFoldingRule(ruleStr);
                Integer offset = getFoldingOffset(ruleStr, ruleType);
                applyFolding(ruleType, offset);
            }
            return this.points;
        }

        private void applyFolding(FoldingRuleType rule, Integer offset) {

            List<Point> newPoints = null;
            switch (rule) {
                case V_FOLDING:
                    newPoints = filterAndMapVFOLDING(this.points, offset);
                    break;
                case H_FOLDING:
                    newPoints = filterAndMapHFOLDING(this.points, offset);
                    break;
                default:
            }

            this.points = newPoints;

        }

        /* folding horizontal linea y=H
        [x, y]  -> [ x, 2*H-y] */

        /* folding vertical linea X=V
        [x, y]  -> [ -(x - 2*V), y] */

        private List<Point> filterAndMapVFOLDING(List<Point> points, Integer offset) {
            return points.stream().filter(point -> point.getX() != offset)
                    .map(point -> point.getX() > offset ? new Point(2 * offset - point.getX(), point.getY()): point)
                    .distinct().collect(Collectors.toList());
        }

        private List<Point> filterAndMapHFOLDING(List<Point> points, Integer offset) {
            return points.stream().filter(point -> point.getY() != offset)
                    .map(point -> point.getY() > offset ? new Point(point.getX(), 2 * offset - point.getY()) : point)
                    .distinct().collect(Collectors.toList());
        }

        public void printPoints() {
            Diagram<String> diagram = new Diagram<>(this.maxX() + 1, this.maxY() + 1, ".");
            diagram.printDiagram(this.points, "#");
        }

    }


    public Integer countDots(String input, int foldingNumber) {
        List<String> pointAndRuleList = Arrays.stream(input.split(POINT_FOLDING_RULES_SEPARATOR)).collect(Collectors.toList());

        String points = pointAndRuleList.get(0);
        TransparentPaper transparentPaper = new TransparentPaper();
        transparentPaper.loadPoints(points);

//        System.out.println("DIAGRAM BEFORE FOLDING");
//        transparentPaper.printDiagram();

        String foldingRules = pointAndRuleList.get(1);
        List<Point> dots = transparentPaper.applyFoldingRules(foldingRules, foldingNumber, false);

        System.out.println("DIAGRAM AFTER FOLDING number: " + foldingNumber);
        transparentPaper.printPoints();
        Integer dotNumber = dots.size();

        return dotNumber;
    }

    public Integer countDotsOfAllFolding(String input) {
        List<String> pointAndRuleList = Arrays.stream(input.split(POINT_FOLDING_RULES_SEPARATOR)).collect(Collectors.toList());

        String points = pointAndRuleList.get(0);
        TransparentPaper transparentPaper = new TransparentPaper();
        transparentPaper.loadPoints(points);

        String foldingRules = pointAndRuleList.get(1);
        List<Point> dots = transparentPaper.applyFoldingRules(foldingRules, 0, true);

        System.out.println("DIAGRAM AFTER FOLDINGS");
        transparentPaper.printPoints();
        Integer dotNumber = dots.size();

        return dotNumber;
    }

}
