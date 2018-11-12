package my.tamagochka.maze.generator;

public class Maze implements Drawable {

    private int[][] maze;

    private Dimension size;
    private Dimension start;
    private Dimension finish;

    protected Maze(Dimension size, Dimension startPosition, Dimension finishPosition, int[][] maze) {
        this.size = size;
        this.start = startPosition;
        this.finish = finishPosition;
        this.maze = maze;
    }

    @Override
    public Dimension getSize() {
        return this.size;
    }

    public Dimension getStartPosition() {
        return this.start;
    }

    public Dimension getFinishPosition() {
        return this.finish;
    }

    @Override
    public int getPoint(Dimension position) {
        return maze[position.vertical()][position.horizontal()];
    }

}
