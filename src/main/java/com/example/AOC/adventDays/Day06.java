package com.example.AOC.adventDays;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day06 {

    public class LanternFishGroup {

        private final Integer resetCounter;
        private Integer counter;
        private Long groupFishCount;

        public LanternFishGroup(Integer initialCounter, Integer resetCounter, Long initialFishNumber) {
            this.resetCounter = resetCounter;
            this.counter = initialCounter;
            this.groupFishCount = initialFishNumber;
        }

        public Integer getCounter() {
            return counter;
        }

        Boolean growAndReturnIsTimeForReproduce() {
            if (this.counter == 0) {
                this.counter = this.resetCounter;
                return true;
            }

            this.counter--;
            return false;
        }

        public void increaseGroupCapacity(Long fishNumber) {
            this.groupFishCount += fishNumber;
        }

        public Long getGroupFishCount() {
            return groupFishCount;
        }
    }


    public class LaternFishSwam {
        private final String INPUT_SEPARATOR = ",";
        private final Integer RESET_COUNTER = 6;
        private final Integer START_COUNTER = 8;

        private ArrayList<LanternFishGroup> fishes;

        public LaternFishSwam(String input) {
            this.fishes = new ArrayList<>();
            List<Integer> fishInitialCounters = getFishInitialCounter(input);
            fishInitialCounters.forEach(counter -> makeSwamGrow(counter, 1L));
        }

        private List<Integer> getFishInitialCounter(String input) {
            return Arrays.stream(input.split(INPUT_SEPARATOR)).map(Integer::valueOf).collect(Collectors.toList());
        }

        private void makeSwamGrow(Integer counter, Long fishNumber) {
            Optional<LanternFishGroup> alreadyPresentLanternFish = fishes.stream().filter(fish -> fish.getCounter().equals(counter)).findFirst();
            if (alreadyPresentLanternFish.isPresent()) {
                alreadyPresentLanternFish.get().increaseGroupCapacity(fishNumber);
            } else {
                fishes.add(new LanternFishGroup(counter, RESET_COUNTER, fishNumber));
            }

        }

        void startGrow(Integer countDown) {
            List<Integer> days = IntStream.range(0, countDown).boxed().collect(Collectors.toList());

            for (Integer day : days) {
                List<LanternFishGroup> groupsNeedToGrow = decrementAllCountersAndReturnGroupsNeedToGrow();
                for (LanternFishGroup group : groupsNeedToGrow) {
                    makeSwamGrow(START_COUNTER, group.getGroupFishCount());
                }

                System.out.println("day: " + day + " fishNumber: " + fishNumber());
            }
        }

        List<LanternFishGroup> decrementAllCountersAndReturnGroupsNeedToGrow(){
            List<LanternFishGroup> groupsNeedToGrow = new ArrayList<>();
            for (LanternFishGroup lanternFishGroup: fishes){
                boolean isTimeToReproduce = lanternFishGroup.growAndReturnIsTimeForReproduce();
                if (isTimeToReproduce) {
                    groupsNeedToGrow.add(lanternFishGroup);
                }
            }
            return groupsNeedToGrow;
        }

        Long fishNumber() {
        return fishes.stream().map(LanternFishGroup::getGroupFishCount).reduce(0L, Long::sum);
        }

    }

    Long startLaternFishSwamReproduction(List<String> data) {
        Integer countDown = Integer.valueOf(data.get(0));
        String inputs = data.get(1);

        LaternFishSwam swam = new LaternFishSwam(inputs);
        swam.startGrow(countDown);
        return swam.fishNumber();
    }
}
