package my.tamagochka.maze.generator;

public class MazeWrongSizeException extends Exception {

    private int wrongSize;

    public int getWrongSize() {
        return wrongSize;
    }

    public MazeWrongSizeException(String message, int wrongSize) {
        super(message);
        this.wrongSize = wrongSize;
    }
}
