package my.tamagochka.maze.generator;

public class MazeFactory {

    private Tangler tangler;

    public MazeFactory(Tangler tangler) {
        this.tangler = tangler;
    }

    public Maze generate(Dimension size, Dimension startPosition, Dimension finishPosition) throws MazeWrongSizeException {
        if(size.vertical() % 2 == 0)
            throw new MazeWrongSizeException("The maze size should not be even. Height == " + size.vertical(), size.vertical());
        if(size.horizontal() % 2 == 0)
            throw new MazeWrongSizeException("The maze size should not be even: Width == " + size.horizontal(), size.horizontal());
        return new Maze(size, startPosition, finishPosition, tangler.tangle(size, startPosition, finishPosition));
    }

}
