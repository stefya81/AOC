package com.example.AOC.utilities;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UtilitiesIO {

    public List<String> readFile(String fileName) {
        File inputFile = new File(fileName);
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(inputFile.toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Exception: file " + fileName + "not found");
        }

        System.out.println("Read file: " + fileName);

        return lines;

    }

    public List<Integer> readInput(String fileName) {
        List<String> lines = readFile(fileName);
        List<Integer> outputList = lines.stream().map(Integer::valueOf).collect(Collectors.toList());

        return outputList;
    }

    public String readAllFile(String fileName) {
        File inputFile = new File(fileName);
        String fileContent = "";
        try {
            fileContent = Files.readString(inputFile.toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("Exception: file " + fileName + "not found");
        }

        System.out.println("Read file: " + fileName);

        return fileContent;

    }
}
