package com.example.AOC.adventDays;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day08 {

    public enum SEGMENT {
        A,
        B,
        C,
        D,
        E,
        F,
        G
    }

    public enum DIGIT {
        ZERO(SEGMENT.A, SEGMENT.B, SEGMENT.C, SEGMENT.E, SEGMENT.F, SEGMENT.G),
        ONE(SEGMENT.C, SEGMENT.F),
        TWO(SEGMENT.A, SEGMENT.C, SEGMENT.D, SEGMENT.E, SEGMENT.G),
        THREE(SEGMENT.A, SEGMENT.C, SEGMENT.D, SEGMENT.F, SEGMENT.G),
        FOUR(SEGMENT.B, SEGMENT.C, SEGMENT.D, SEGMENT.F),
        FIVE(SEGMENT.A, SEGMENT.B, SEGMENT.D, SEGMENT.F, SEGMENT.G),
        SIX(SEGMENT.A, SEGMENT.B, SEGMENT.D, SEGMENT.E, SEGMENT.F, SEGMENT.G),
        SEVEN(SEGMENT.A, SEGMENT.C, SEGMENT.F),
        EIGHT(SEGMENT.A, SEGMENT.B, SEGMENT.C, SEGMENT.D, SEGMENT.E, SEGMENT.F, SEGMENT.G),
        NINE(SEGMENT.A, SEGMENT.B, SEGMENT.C, SEGMENT.D, SEGMENT.F, SEGMENT.G),
        ;

        private final List<SEGMENT> segments;

        DIGIT(SEGMENT... segments) {
            this.segments = new ArrayList<>(Arrays.asList(segments));
        }

        public Integer lenght() {
            return this.segments.size();
        }

        public String toString() {
            return this.name();
        }

        public List<SEGMENT> segments() {
            return segments;
        }
    }

    public class DigitMapper {
        Map<SEGMENT, String> segmentValueMap;

        public DigitMapper() {
            segmentValueMap = new HashMap<>();
        }

        public void loadSegmentValueByString(DIGIT digit, String digitInString) {
            for (char c : digitInString.toCharArray()) {
                String currentChar = String.valueOf(c);
                if (!segmentValueMap.containsValue(currentChar)) {
                    SEGMENT segment = getFirstNotAssignedSegment(digit);
                    segmentValueMap.put(segment, String.valueOf(c));
                }
            }
        }

        private boolean exchangeValueInSegments(SEGMENT seg1, SEGMENT seg2) {
            String valueSeg1 = segmentValueMap.get(seg1);
            String valueSeg2 = segmentValueMap.get(seg2);
            if (valueSeg1.isEmpty() || valueSeg2.isEmpty()) {
                return false;
            }
            segmentValueMap.put(seg1, valueSeg2);
            segmentValueMap.put(seg2, valueSeg1);

            return true;
        }

        SEGMENT getFirstNotAssignedSegment(DIGIT digit) {
            return digit.segments().stream().filter(segment -> !segmentValueMap.containsKey(segment)).findFirst().orElse(null);
        }

        private boolean containsAllSegmentOfDigit(DIGIT digit, String digitInString) {
            return digit.segments().stream().allMatch(segment -> digitInString.contains(segmentValueMap.get(segment)));
        }

        private int numberOfMatchedSegmentOfDigit(DIGIT digit, String digitInString) {
            return (int) digit.segments().stream().filter(segment -> digitInString.contains(segmentValueMap.get(segment))).count();
        }

        private boolean findDigitONE(List<String> digits) {
            return !findAndLoadDigit(digits, DIGIT.ONE, isDigitByLenghtPredicate(DIGIT.ONE)).isEmpty();
        }

        private boolean findDigitSEVEN(List<String> digits) {
            return !findAndLoadDigit(digits, DIGIT.SEVEN, isDigitByLenghtPredicate(DIGIT.SEVEN)).isEmpty();
        }

        private boolean findDigitFOUR(List<String> digits) {
            return !findAndLoadDigit(digits, DIGIT.FOUR, isDigitByLenghtPredicate(DIGIT.FOUR)).isEmpty();
        }

        private boolean findDigitTHREE(List<String> digits) {
            String foundString = findAndLoadDigit(digits, DIGIT.THREE, hasTheNumberOfDigitMatchingPredicate(DIGIT.THREE, DIGIT.SEVEN, DIGIT.THREE.lenght() - 2));
            if (foundString.isEmpty()) {
                return false;
            }

            return checkDigitMatchAndFixSegments(foundString, DIGIT.THREE, SEGMENT.B, SEGMENT.D);
        }

        private boolean findDigitEIGHT(List<String> digits) {
            return !findAndLoadDigit(digits, DIGIT.EIGHT, isDigitByLenghtPredicate(DIGIT.EIGHT)).isEmpty();
        }

        private boolean findDigitSIX(List<String> digits) {
            String foundString = findAndLoadDigit(digits, DIGIT.SIX, hasTheNumberOfDigitMatchingPredicate(DIGIT.SIX, DIGIT.ONE, DIGIT.ONE.lenght() - 1));
            if (foundString.isEmpty()) {
                return false;
            }

            return checkDigitMatchAndFixSegments(foundString, DIGIT.SIX, SEGMENT.C, SEGMENT.F);
        }

        private boolean checkDigitMatchAndFixSegments(String foundString, DIGIT refDigit, SEGMENT seg1, SEGMENT seg2) {
            boolean isDigitMatchingString = isDigitMatchingString(refDigit, foundString);
            if (!isDigitMatchingString) {
                return exchangeValueInSegments(seg1, seg2);
            }

            return true;
        }

        private Predicate<String> isDigitByLenghtPredicate(DIGIT digit) {
            return string -> digit.lenght().equals(string.length());
        }

        private Predicate<String> hasTheNumberOfDigitMatchingPredicate(DIGIT digit, DIGIT refDigit, int segmentNumber) {
            return string -> digit.lenght().equals(string.length()) && numberOfMatchedSegmentOfDigit(refDigit, string) == segmentNumber;
        }

        private String findAndLoadDigit(List<String> digits, DIGIT digit, Predicate<String> digitCondition) {
            String digitInString = digits.stream().filter(digitCondition).findFirst().orElse("");

            if (!digitInString.isEmpty()) {
                loadSegmentValueByString(digit, digitInString);
            }

            return digitInString;
        }

        public boolean findAllDigitAndLoad(List<String> digits) {
            boolean areAllFound;

            areAllFound = findDigitONE(digits);
            areAllFound &= findDigitSEVEN(digits);
            areAllFound &= findDigitFOUR(digits);
            areAllFound &= findDigitTHREE(digits);
            areAllFound &= findDigitEIGHT(digits);
            areAllFound &= findDigitSIX(digits);

            return areAllFound;
        }

        private boolean isDigitMatchingString(DIGIT digit, String digitInString) {
            return digit.lenght() == digitInString.length() && containsAllSegmentOfDigit(digit, digitInString);
        }

        private DIGIT decodeString(String digitInString) {
            List<DIGIT> digitList = Arrays.asList(DIGIT.values());
            DIGIT foundDigit = digitList.stream().filter(digit -> isDigitMatchingString(digit, digitInString)).findFirst().orElse(null);
            return foundDigit;
        }

        List<DIGIT> decodeOutput(List<String> outputs) {
            return outputs.stream().map(this::decodeString).collect(Collectors.toList());
        }

    }

    public List<DIGIT> loadAndDecodeData(String input) {
        List<String> data = getLines(input);

        List<DIGIT> decodedDigits = new ArrayList<>();
        data.forEach(line -> {
            List<DIGIT> digits = loadAndDecodeLine(line);
            if (digits != null) {
                decodedDigits.addAll(digits);
            }
        });
        return decodedDigits;
    }

    public Integer findDigits(List<DIGIT> decodedList, DIGIT... digits) {
        List<DIGIT> toFindDigits = Arrays.asList(digits);
        return (int) decodedList.stream().filter(toFindDigits::contains).count();
    }

    public List<Integer> convertEachLineToSingleNumber(String input) {
        List<String> data = getLines(input);

        List<Integer> decodedDigitsToInt = new ArrayList<>();
        data.forEach(line -> {
            List<DIGIT> digits = loadAndDecodeLine(line);
            if (digits != null) {
                Integer currentInt = convertToInt(digits);
                decodedDigitsToInt.add(currentInt);
            }
        });

        return decodedDigitsToInt;
    }

    private List<String> getLines(String input) {
        return Arrays.stream(input.split("\r\n")).collect(Collectors.toList());
    }

    private List<String> inputDigit(String input) {
        String decodingData = input.split(" \\| ")[0];
        return Arrays.stream(decodingData.split(" ")).collect(Collectors.toList());
    }

    private List<String> outputDigit(String input) {
        String outputData = input.split(" \\| ")[1];
        return Arrays.stream(outputData.split(" ")).collect(Collectors.toList());
    }

    private List<DIGIT> loadAndDecodeLine(String input) {
        DigitMapper digitMapper = new DigitMapper();
        if (!digitMapper.findAllDigitAndLoad(inputDigit(input))) {
            return null;
        }

        List<String> outputData = outputDigit(input);
        List<DIGIT> decodedDigits = digitMapper.decodeOutput(outputData);
        System.out.println("Decoded output: " + outputData + " INTO: " + convertToInt(decodedDigits));
        return decodedDigits;
    }

    private Integer convertToInt(List<DIGIT> digits) {
        return Integer.parseInt(digits.stream().map(digit -> String.valueOf(digit.ordinal())).collect(Collectors.joining("")));
    }


}
