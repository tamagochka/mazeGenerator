package my.tamagochka;

import my.tamagochka.maze.generator.DefaultTangler;
import my.tamagochka.maze.generator.Dimension;
import my.tamagochka.maze.generator.Maze;
import my.tamagochka.maze.generator.MazeFactory;
import my.tamagochka.maze.textDrawer.TextDrawer;

public class Main {

    private static int LABYRINTH_WIDTH = 171;
    private static int LABYRINTH_HEIGHT = 49;

    public static void main(String[] args) throws Exception {

        MazeFactory mazeFactory = new MazeFactory(new DefaultTangler());
        Maze maze = mazeFactory.generate(new Dimension(LABYRINTH_WIDTH, LABYRINTH_HEIGHT), null, null);
        TextDrawer drawer = new TextDrawer(maze, false);
        drawer.draw();


/*
        DefaultTerminalFactory factory = new DefaultTerminalFactory();
        factory.setInitialTerminalSize(new TerminalSize(150, 20));


        Terminal terminal = factory.createTerminal();



        Screen screen = new TerminalScreen(terminal);

        TextGraphics tg = screen.newTextGraphics();
        screen.startScreen();


        tg.putString(10, 10, "Fuck!");
        tg.drawRectangle(new TerminalPosition(3,3), new TerminalSize(10, 4), '*');
        tg.drawTriangle(new TerminalPosition(12, 3),
                new TerminalPosition(16, 12),
                new TerminalPosition(20, 3), Symbols.SOLID_SQUARE);

        screen.refresh();

        boolean keepRuning = true;
        while(keepRuning) {
            KeyStroke keyPressed = terminal.pollInput(); // non-blocking input
            if(keyPressed != null) {

                switch(keyPressed.getKeyType()) {
                    case Escape:
                        keepRuning = false;
                        break;
                    case ArrowRight:
                        screen.setCursorPosition(null); // hide cursor
                        tg.setForegroundColor(TextColor.ANSI.GREEN);
                        tg.setBackgroundColor(TextColor.ANSI.BLUE);
                        tg.putString(0,0, "size terminal: " + screen.getTerminalSize().getColumns() +
                                "x" + screen.getTerminalSize().getRows(), SGR.UNDERLINE);
                        screen.refresh();
                        break;
                    default:
                        System.out.println(keyPressed);
                        break;
                }
            }
        }



        screen.readInput(); //blocking input
        screen.stopScreen();
        screen.close();
*/

    }
}
