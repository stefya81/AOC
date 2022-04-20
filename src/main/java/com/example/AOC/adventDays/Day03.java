package com.example.AOC.adventDays;

import com.example.AOC.utilities.BitFinderUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Day03 {

    @Autowired
    private BitFinderUtilities utilities;

    public Day03(BitFinderUtilities utilities) {
        this.utilities = utilities;
    }

    private String getMostOrLessCommonBitValueAtPos(List<String> binaryStringList, Integer pos, Boolean mostOrLessFrequencyEnabled) {
        String defaultBitForEqualValue = mostOrLessFrequencyEnabled ? BitFinderUtilities.BitValues.ONE.value() : BitFinderUtilities.BitValues.ZERO.value();

        boolean isEqualCommonBit = utilities.areZeroAndOneBitValueEquals(binaryStringList, pos);
        if (isEqualCommonBit) {
            return defaultBitForEqualValue;
        }

        boolean isMostCommonBit = utilities.isMostCommonBit(binaryStringList, pos, defaultBitForEqualValue);
        return isMostCommonBit == mostOrLessFrequencyEnabled ? defaultBitForEqualValue : utilities.revertBit(defaultBitForEqualValue);
    }

    private String evaluateByteInColumnsByMostOrLessCommonBit(List<String> binaryStringList, Boolean mostOrLessFrequencyEnabled) {
        int bitNumber = utilities.getByteLenght(binaryStringList);

        String gammaRayString = "";
        for (int i = 0; i < bitNumber; i++) {
            String bitStringValue = getMostOrLessCommonBitValueAtPos(binaryStringList, i, mostOrLessFrequencyEnabled);
            gammaRayString = gammaRayString.concat(bitStringValue);
        }

        return gammaRayString;
    }

    private String filterListByMostOrLessCommonBitAtEachIteration(List<String> binaryStringList, Boolean mostOrLessFrequencyEnabled){
        String filteredString = "";
        int bitNumber = utilities.getByteLenght(binaryStringList);

        List<String> currentFilteredList = binaryStringList;
        for (int i = 0; i < bitNumber; i++) {
            String bitStringValue = getMostOrLessCommonBitValueAtPos(currentFilteredList, i, mostOrLessFrequencyEnabled);
            currentFilteredList = utilities.filterListByStringBitAtPos(currentFilteredList, bitStringValue, i);
            if (currentFilteredList.size() == 1) {
                filteredString = currentFilteredList.get(0);
                break;
            }
        }

        return filteredString;
    }

    public Integer evaluateGammaRate(List<String> binaryStringList) {
        String mostCommonBitStringWithDefaultOneBit = evaluateByteInColumnsByMostOrLessCommonBit(binaryStringList, true);
        return utilities.toBinaryInteger(mostCommonBitStringWithDefaultOneBit);
    }

    public Integer evaluateEpsilonRate(List<String> binaryStringList) {
        String lessCommonBitStringWithDefaultZeroBit = evaluateByteInColumnsByMostOrLessCommonBit(binaryStringList, false);
        return utilities.toBinaryInteger(lessCommonBitStringWithDefaultZeroBit);
    }

    public Integer evaluateOxigenGenerator(List<String> binaryStringList) {
        String filteredString = filterListByMostOrLessCommonBitAtEachIteration(binaryStringList, true);
        return utilities.toBinaryInteger(filteredString);
    }

    public Integer evaluateCO2Scrubber(List<String> binaryStringList) {
        String filteredString = filterListByMostOrLessCommonBitAtEachIteration(binaryStringList, false);
        return utilities.toBinaryInteger(filteredString);
    }


}
