package com.example.AOC.adventDays;


import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class Day02 {

    public enum CommandType {
        FORWARD,
        DOWN,
        UP
    }

    public class Command {

        private CommandType commandType;
        private Integer value;

        public Command(CommandType commandType, Integer value) {
            this.commandType = commandType;
            this.value = value;
        }

        public CommandType getCommandType() {
            return commandType;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }

    public class Position {
        private Integer orizontalOffset;
        private Integer depth;
        private Integer aim;


        public Position() {
            orizontalOffset = 0;
            depth = 0;
            aim = 0;
        }

        public Integer getOrizontalOffset() {
            return orizontalOffset;
        }

        public Integer getDepth() {
            return depth;
        }

        public Integer getAim() {
            return aim;
        }

        public void updatePosition(Command command) {

            Integer commandValue = command.getValue();
            switch (command.getCommandType()) {
                case FORWARD:
                    orizontalOffset += commandValue;
                    break;
                case DOWN:
                    depth += commandValue;
                    break;
                case UP:
                    depth -= commandValue;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + command);
            }
        }

        public void updatePositionByAim(Command command) {
            Integer commandValue = command.getValue();
            switch (command.getCommandType()) {
                case FORWARD:
                    orizontalOffset += commandValue;
                    depth += aim * commandValue;
                    break;
                case DOWN:
                    aim += commandValue;
                    break;
                case UP:
                    aim -= commandValue;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + command);
            }
        }


    }

    public List<Command> convertToCommandList(List<String> stringList) {
        return stringList.stream().map(this::convertStringToCommand).collect(Collectors.toList());
    }

    private Command convertStringToCommand(String line) {
        List<String> list = Arrays.stream(line.split(" ")).collect(Collectors.toList());
        CommandType currentCommand = CommandType.valueOf(list.get(0).toUpperCase(Locale.ROOT));
        Integer current = Integer.valueOf(list.get(1));
        return new Command(currentCommand, current);
    }

    public Position parseCommandListAndEvaluatePosition(List<Command> commands) {
        Position position = new Position();
        commands.forEach(position::updatePosition);
        return position;
    }

    public Position parseCommandListAndEvaluatePositionByAim(List<Command> commands) {
        Position position = new Position();
        commands.forEach(position::updatePositionByAim);
        return position;
    }


}


