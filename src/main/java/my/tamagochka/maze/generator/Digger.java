package my.tamagochka.maze.generator;

import java.util.Random;

public class Digger {

    public enum Direction {

        UP(new Dimension(0, -1)),
        DOWN(new Dimension(0, 1)),
        LEFT(new Dimension(-1, 0)),
        RIGHT(new Dimension(1, 0));

        private Dimension step;
        private static Random randomizer = new Random();

        Direction(Dimension step) {
            this.step = step;
        }

        public Dimension getStep() {
            return step;
        }

        public static Direction getRandomDirection() {
            return Direction.values()[randomizer.nextInt(4)];
        }

    }

    private Dimension currentPosition;
    private int[][] maze;
    private Direction direction;

    public Digger(Dimension position, Direction direction, int[][] maze) {
        this.currentPosition = position;
        this.direction = direction;
        this.maze = maze;
        this.maze[currentPosition.vertical()][currentPosition.horizontal()] = 0;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setRandomDirection() {
        this.direction = Direction.getRandomDirection();
    }

    public Digger clone(Direction direction) {
        return new Digger(currentPosition, direction, maze);
    }

    public void move() { // not dig, only move
        Dimension nextPosition = currentPosition.summ(direction.getStep());
        if(maze[nextPosition.vertical()][nextPosition.horizontal()] == 0)
            currentPosition = nextPosition;
    }

    public void dig() {
        Dimension nextPosition = currentPosition.summ(direction.getStep());

        if(maze[nextPosition.vertical()][nextPosition.horizontal()] == 0) currentPosition = nextPosition;
        if(maze[nextPosition.vertical()][nextPosition.horizontal()] == 1) return;

        Dimension nextPositionLeftSide = nextPosition.summ(direction.getStep().swapDimensions());
        Dimension nextPositionRightSide = nextPosition.diff(direction.getStep().swapDimensions());
        Dimension nextPositionForward = nextPosition.summ(direction.getStep());
        Dimension nextPositionForwardLeft = nextPositionForward.summ(direction.getStep().swapDimensions());
        Dimension nextPositionForwardRight = nextPositionForward.diff(direction.getStep().swapDimensions());

        if(maze[nextPositionForward.vertical()][nextPositionForward.horizontal()] != 0 &&
                maze[nextPositionForwardLeft.vertical()][nextPositionForwardLeft.horizontal()] != 0 &&
                maze[nextPositionForwardRight.vertical()][nextPositionForwardRight.horizontal()] != 0 &&
                maze[nextPositionLeftSide.vertical()][nextPositionLeftSide.horizontal()] != 0 &&
                maze[nextPositionRightSide.vertical()][nextPositionRightSide.horizontal()] != 0)
            currentPosition = nextPosition;

        maze[currentPosition.vertical()][currentPosition.horizontal()] = 0;
    }

    public Dimension getPosition() {
        return currentPosition;
    }

    public void setPosition(Dimension currentPosition) {
        this.currentPosition = currentPosition;
    }
}
