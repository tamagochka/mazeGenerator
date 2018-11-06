package my.tamagochka.mazeGenerator;

import my.tamagochka.matrix.Graph;
import my.tamagochka.matrix.Matrix;

public class Main {

    private static int LABYRINTH_WIDTH = 10;
    private static int LABYRINTH_HEIGHT = 10;

    public static void main(String[] args) {

/*
        Labyrinth labyrinth = Labyrinth.simpleGenerator(LABYRINTH_WIDTH, LABYRINTH_HEIGHT,
                new Position(0, 0), new Position(LABYRINTH_WIDTH - 1, LABYRINTH_HEIGHT - 1));
*/



/*
        Labyrinth labyrinth = Labyrinth.clusterGenerator(LABYRINTH_WIDTH, LABYRINTH_HEIGHT,
                new Position(0, 0), new Position(LABYRINTH_WIDTH - 1, LABYRINTH_HEIGHT - 1));

        if(labyrinth != null) {
            Painter painter = new Painter(labyrinth.getFullWidth(), labyrinth.getFullHeight());
            painter.drawLabyrinth(labyrinth);
        }
*/

/*
        int[][] A = {
                {0, 1, 1, 0, 1, 0},
                {1, 0, 0, 1, 1, 1},
                {1, 0, 0, 1, 0, 0},
                {0, 1, 1, 0, 0, 1},
                {1, 1, 0, 0, 0, 1},
                {0, 1, 0, 1, 1, 0}
        };
*/
        int[][] A = {
                {0, 1, 0, 0, 1},
                {1, 0, 1, 0, 0},
                {0, 1, 0, 1, 0},
                {0, 0, 1, 0, 1},
                {1, 0, 0, 1, 0},
        };


/*
        int[][] d = Matrix.minPathsBetweenAllVertices(A);

        for(int i = 0; i < d.length; i++) {
            for(int j = 0; j < d.length; j++) {
                System.out.print(d[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
*/


        int[][] d = Graph.minPathsToVertexThroughVertices(A, 4);

        for(int i = 0; i < d.length; i++) {
            for(int j = 0; j < d.length; j++) {
                System.out.print(d[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("maximal path length: " + Graph.maxPathLengthBetweenVertices(A, 0, 4));


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
