package my.tamagochka.maze.generator;

import java.util.Arrays;

public class DefaultTangler implements Tangler {

    private int[][] maze;
    private int width, height;

    private void fillWithEarth() {
        for(int y = 0; y < height; y++) {
            Arrays.fill(maze[y], 2);
        }
    }

    private void buildBorders() {
        for(int x = 0; x < width; x++) {
            maze[0][x] = 1;
            maze[height - 1][x] = 1;
        }
        for(int y = 0; y < height; y++) {
            maze[y][0] = 1;
            maze[y][width - 1] = 1;
        }
    }

    private void buildColumns() {
        for(int x = 2; x < width; x += 2) {
            for(int y = 2; y < height; y += 2) {
                maze[y][x] = 1;
            }
        }
    }

    private boolean checkTangled() { // check maze ready
        boolean tangled = true;
        for(int x = 0; x < width - 1; x++) {
            for(int y = 0; y < height - 1; y++) {
                if(maze[y][x] != 0 && maze[y + 1][x] != 0 && maze[y][x + 1] != 0 && maze[y + 1][x + 1] != 0) {
                    tangled = false;
                    break;
                }
            }
            if(!tangled) break;
        }
        return tangled;
    }

    private void vulcanize() {
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                if(maze[y][x] != 0) maze[y][x] = 1;
            }
        }
    }

    @Override
    public int[][] tangle(Dimension size, Dimension startPosition, Dimension finishPosition) {

        width = size.horizontal();
        height = size.vertical();
        Dimension start = startPosition == null ? new Dimension(1, 1) : startPosition;
        Dimension finish = finishPosition == null ? size.diff(new Dimension(2, 2)) : finishPosition;
        maze = new int[height][width];

        fillWithEarth();
        buildBorders();
        buildColumns();

        int countEarth = (width - 2) * (height - 2) - ((width - 3) / 2) * ((height - 3) / 2);
        int countDiggers = countEarth / 500 + 1;
        Digger[] diggers = new Digger[countDiggers];

        for(int i = 0; i < countDiggers; i++) {
            diggers[i] = new Digger(new Dimension(start), Digger.Direction.getRandomDirection(), maze);
        }

        while(!checkTangled()) {
            for(int i = 0; i < countDiggers; i++) {
                if(diggers[i].getPosition().horizontal() == finish.horizontal() &&
                        diggers[i].getPosition().vertical() == finish.vertical()) // not dig new tunnel from finish
                    diggers[i].move();
                else
                    diggers[i].dig();
                if(!(diggers[i].getPosition().horizontalEven() || diggers[i].getPosition().verticalEven())) {
                    diggers[i].setRandomDirection();
                }
            }
        }

        vulcanize();

        if(startPosition != null)
            maze[startPosition.vertical()][startPosition.horizontal()] = 5; // start point
        if(finishPosition != null)
            maze[finishPosition.vertical()][finishPosition.horizontal()] = 7; // finish point
        return maze;
    }

}
