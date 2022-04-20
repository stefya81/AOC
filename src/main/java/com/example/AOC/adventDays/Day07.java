package com.example.AOC.adventDays;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day07 {

    public Integer evaluateAlignmentPosition(String input, Boolean isLinearFuelCost) {
        List<Integer> numbers = getPositions(input);
        List<Integer> positions = getAllAlignmentPositions(input);

        List<Integer> fuelCosts = positions.stream().map((alignPoint) -> getAlignmentFuelCost(numbers, alignPoint, isLinearFuelCost)).collect(Collectors.toList());
        Integer indexOfBestAlignment = fuelCosts.indexOf(Collections.min(fuelCosts));

        return positions.get(indexOfBestAlignment);
    }

    public Integer getFuelCost(String input, Integer alignPoint, Boolean isLinearFuelCost) {
        List<Integer> numbers = getPositions(input);
        return getAlignmentFuelCost(numbers, alignPoint, isLinearFuelCost);
    }

    private List<Integer> getPositions(String input) {
        return Arrays.stream(input.split(",")).map(Integer::valueOf).collect(Collectors.toList());
    }

    private List<Integer> getAllAlignmentPositions(String input) {
        List<Integer> intStream = Arrays.stream(input.split(",")).distinct().map(Integer::valueOf).collect(Collectors.toList());
        Integer minRange = intStream.stream().min(Comparator.naturalOrder()).orElse(0);
        Integer maxRange = intStream.stream().max(Comparator.naturalOrder()).orElse(0);
        return IntStream.range(minRange, maxRange+1).boxed().collect(Collectors.toList());
    }

    private Integer getAlignmentFuelCost(List<Integer> numbers, Integer alignPoint, Boolean isLinearFuelCost) {
        return numbers.stream().reduce(0, getFuelCostMapper(alignPoint, isLinearFuelCost));
    }

    private BinaryOperator<Integer> getFuelCostMapper(Integer alignPoint, Boolean isLinearFuelCost) {
        return isLinearFuelCost ? linearFuelEvaluator(alignPoint) : constantFuelEvaluator(alignPoint);
    }

    private BinaryOperator<Integer> constantFuelEvaluator(Integer alignPoint) {
        return (sum, pos) -> sum + Math.abs(pos - alignPoint);
    }

    private BinaryOperator<Integer> linearFuelEvaluator(Integer alignPoint) {
        return (sum, pos) -> sum + getLinearFuelCost(Math.abs(pos - alignPoint));
    }

    private Integer getLinearFuelCost(Integer distance) {
        return IntStream.range(0, distance + 1).reduce(Integer::sum).orElse(0);
    }

}
