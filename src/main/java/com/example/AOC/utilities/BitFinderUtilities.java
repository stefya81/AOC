package com.example.AOC.utilities;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class BitFinderUtilities {


    public enum BitValues {
        ZERO("0"),
        ONE("1");

        private final String value;

        public String value() {
            return value;
        }

        BitValues(String value) {
            this.value = value;
        }
    }

    public String getBitStringValueAtPosition(String byteString, Integer pos) {
        return String.valueOf(byteString.charAt(pos));
    }

    public Integer getByteLenght(List<String> binaryList) {
        String binaryString = binaryList.get(0);
        return binaryString.length();
    }

    public List<String> filterListByStringBitAtPos(List<String> binaryStringList, String bitStringValue, Integer pos) {
        return binaryStringList.stream().filter(string -> getBitStringValueAtPosition(string, pos).equals(bitStringValue)).collect(Collectors.toList());
    }

    public Boolean isMostCommonBit(List<String> binaryStringList, Integer pos, String bitValue){
        int filteredBitCount = Math.toIntExact(binaryStringList.stream().map(number -> getBitStringValueAtPosition(number, pos)).filter(number -> number.equals(bitValue)).count());
        return filteredBitCount > binaryStringList.size() / 2.0;
    }

    public Boolean areZeroAndOneBitValueEquals(List<String> binaryStringList, Integer pos)
    {
        int filteredBitCount = Math.toIntExact(binaryStringList.stream().map(number -> getBitStringValueAtPosition(number, pos)).filter(number -> number.equals(BitValues.ONE.value)).count());
        return filteredBitCount == binaryStringList.size() / 2.0;
    }


    public Integer toBinaryInteger(String binaryString) {
        return Integer.valueOf(binaryString, 2);
    }

    public String revertBit(String bitValue) {
        return bitValue.equals(BitValues.ONE.value) ? BitValues.ZERO.value : BitValues.ONE.value;
    }
}
