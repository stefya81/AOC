package com.example.AOC.adventDays;

import java.util.*;
import java.util.stream.Collectors;

public class Day10 {

    public enum BracketType {
        ROUND_OPEN("(", ")", true),
        ROUND_CLOSED(")", "(", false),
        SQUARED_OPEN("[", "]", true),
        SQUARED_CLOSED("]", "[", false),
        CURLY_OPEN("{", "}", true),
        CURLY_CLOSED("}", "{", false),
        CHEVRON_OPEN("<", ">", true),
        CHEVRON_CLOSED(">", "<", false);

        private final String symbol;
        private final boolean open;
        private final String opposite;

        BracketType(String symbol, String opposite, boolean open) {
            this.symbol = symbol;
            this.opposite = opposite;
            this.open = open;
        }

        public boolean isOpen() {
            return this.open;
        }

        public static BracketType isValueOf(String s) {
            return Arrays.stream(BracketType.values())
                    .filter(v -> v.symbol.equals(s))
                    .findFirst().orElse(null);
        }

        public boolean isTheOppositeBracketType(BracketType type) {
            return type.symbol.equals(this.opposite);
        }
    }

    public class ResultParse {
        Integer errorScore;
        List<BracketType> brackets;

        public ResultParse(Integer errorScore, Stack<BracketType> brackets) {
            this.errorScore = errorScore;
            this.brackets = new ArrayList<>(brackets);
            Collections.reverse(this.brackets);
        }

        public Integer getErrorScore() {
            return errorScore;
        }

        public List<BracketType> getBrackets() {
            return brackets;
        }
    }


    public class BracketLineParser {
        private final Map<BracketType, List<Integer>> fromBracketToScoreMap;

        private final Integer CHAR_COST = 5;

        public BracketLineParser() {
            fromBracketToScoreMap = Map.ofEntries(
                    Map.entry(BracketType.ROUND_OPEN, Arrays.asList(3, 1)),
                    Map.entry(BracketType.ROUND_CLOSED, Arrays.asList(3, 1)),
                    Map.entry(BracketType.SQUARED_OPEN, Arrays.asList(57, 2)),
                    Map.entry(BracketType.SQUARED_CLOSED, Arrays.asList(57, 2)),
                    Map.entry(BracketType.CURLY_OPEN, Arrays.asList(1197, 3)),
                    Map.entry(BracketType.CURLY_CLOSED, Arrays.asList(1197, 3)),
                    Map.entry(BracketType.CHEVRON_OPEN, Arrays.asList(25137, 4)),
                    Map.entry(BracketType.CHEVRON_CLOSED, Arrays.asList(25137, 4))
            );
        }

        ResultParse parse(List<String> bracketToParse) {
            Integer parseError = 0;
            Stack<BracketType> brackets = new Stack<>();
            for (String bracket : bracketToParse) {
                BracketType type = BracketType.isValueOf(bracket);
                if (type.isOpen()) {
                    brackets.push(type);
                } else {
                    BracketType lastBracket = brackets.peek();
                    if (!lastBracket.isTheOppositeBracketType(type)) {
                        parseError = fromBracketToScoreMap.get(type).get(0);
                        return new ResultParse(parseError, new Stack<>());
                    }
                    brackets.pop();
                }
            }
            return new ResultParse(parseError, brackets);
        }

        Long evaluateMissingBracketScore(List<BracketType> brackets) {
            Long bracketScore = brackets.stream().map(bracket -> Long.valueOf(fromBracketToScoreMap.get(bracket).get(1))).reduce(0L, ((subtotal, typeScore) -> CHAR_COST * subtotal + typeScore));
            return bracketScore;
        }
    }

    public Integer parseAndEvaluateErrors(List<String> lines) {
        Integer errorAmount = 0;
        BracketLineParser bracketLineParser = new BracketLineParser();
        for (String line : lines) {
            List<String> bracketList = Arrays.stream(line.split("")).collect(Collectors.toList());
            errorAmount += bracketLineParser.parse(bracketList).getErrorScore();
        }

        return errorAmount;
    }

    public Long parseAndEvaluateMissingBracketScore(List<String> lines) {
        BracketLineParser bracketLineParser = new BracketLineParser();
        List<Long> scores = new ArrayList<>();
        for (String line : lines) {
            List<String> bracketList = Arrays.stream(line.split("")).collect(Collectors.toList());
            List<BracketType> missingBrackets = bracketLineParser.parse(bracketList).getBrackets();
            Long currentScore = bracketLineParser.evaluateMissingBracketScore(missingBrackets);
            scores.add(currentScore);
        }

        return evaluateMiddleValue(scores);
    }

    private Long evaluateMiddleValue(List<Long> scores) {
        scores = scores.stream().filter(value -> !value.equals(0L)).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        Long score = scores.get(scores.size() / 2);
        return score;
    }
}
