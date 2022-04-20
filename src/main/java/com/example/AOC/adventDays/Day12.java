package com.example.AOC.adventDays;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day12 {

    public class Path {
        static public final String START_NODE = "start";
        static public final String END_NODE = "end";

        List<String> nodes;

        public Path() {
            this.nodes = new ArrayList<>();
        }

        public void start() {
            this.nodes.add(START_NODE);
        }

        public void prependPath(Path path) {
            nodes.addAll(0, path.nodes);
        }

        public void addNodeByLabel(String label) {
            this.nodes.add(label);
        }

        String getLastLabel() {
            return nodes.get(nodes.size() - 1);
        }

        public boolean containsKeyNodeByLabel(String label) {
            return nodes.contains(label);
        }


        private boolean hasPathNoMaxLimitedPassages(int maxPassage) {
            List<String> filteredList = nodes.stream().filter(this::isPassageLimitedNode).collect(Collectors.toList());
            long allSmallCaves = filteredList.size();
            long differentCaves = filteredList.stream().distinct().count();
            return allSmallCaves - differentCaves < maxPassage - 1;
        }

        private boolean isPassageLimitedNode(String label) {
            return !label.equals(START_NODE) && label.toLowerCase().equals(label);
        }

        public boolean isNodeTransitOK(String label, int maxPassage) {
            if (!this.nodes.contains(label)) {
                return true;
            }

            if (label.equals(START_NODE)) {
                return false;
            }

            return !isPassageLimitedNode(label) || hasPathNoMaxLimitedPassages(maxPassage);
        }

        @Override
        public String toString() {
            String string = this.nodes.stream().reduce("", (current, next) -> current + "-" + next);
            System.out.println(string);
            return string;
        }
    }


    public class Graph {
        private final String LINK_SEPARATOR = "-";

        Map<String, List<String>> linkedNodes;
        List<Path> paths;


        public Graph() {
            this.paths = new ArrayList<>();
            this.linkedNodes = new HashMap<>();
        }

        public void loadGraph(List<String> input) {
            for (String line : input) {
                loadNodeAndLink(line);
            }
        }


        private void loadNodeAndLink(String inputLine) {
            String[] nodeLinkArray = inputLine.split(LINK_SEPARATOR);
            String startNodeLabel = nodeLinkArray[0];
            String endNodeLabel = nodeLinkArray[1];

            linkedNodes.putIfAbsent(startNodeLabel, new ArrayList<>());
            linkedNodes.get(startNodeLabel).add(endNodeLabel);

            if (!startNodeLabel.equals(Path.START_NODE) && !endNodeLabel.equals(Path.END_NODE)) {
                linkedNodes.putIfAbsent(endNodeLabel, new ArrayList<>());
                linkedNodes.get(endNodeLabel).add(startNodeLabel);
            }
        }

        public Integer evaluateAllPath(int maxPassage) {
            Path path = new Path();
            path.start();
            List<Path> allpaths = evaluatePath(path, maxPassage);

            List<Path> filteredList = allpaths.stream().filter(p -> p.containsKeyNodeByLabel(Path.END_NODE)).collect(Collectors.toList());

            //List<String> convertedString = filteredList.stream().map(Path::toString).collect(Collectors.toList());
            //System.out.println("Print paths: ");
            //System.out.println(convertedString);

            return filteredList.size();
        }

        public List<Path> evaluatePath(Path path, int maxPassage) {

            List<Path> paths = new ArrayList<>();
            List<String> nodeConnections = linkedNodes.get(path.getLastLabel());
            for (String label : nodeConnections) {

                boolean isNodeTransitOK = path.isNodeTransitOK(label, maxPassage);
                if (isNodeTransitOK) {
                    Path currentNodePath = new Path();
                    currentNodePath.prependPath(path);
                    currentNodePath.addNodeByLabel(label);

                    if (label.equals(Path.END_NODE)) {
                        paths.add(currentNodePath);
                    } else {
                        List<Path> subPaths = evaluatePath(currentNodePath, maxPassage);
                        paths.addAll(subPaths);
                    }
                }
            }
            return paths;
        }
    }

    public Integer evaluateAllPath(List<String> input, Integer maxPassage) {
        Graph graph = new Graph();
        graph.loadGraph(input);
        return graph.evaluateAllPath(maxPassage);
    }
}
