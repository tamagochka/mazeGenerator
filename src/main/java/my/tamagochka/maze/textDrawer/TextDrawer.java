package my.tamagochka.maze.textDrawer;

import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import my.tamagochka.maze.generator.Dimension;
import my.tamagochka.maze.generator.Drawable;

import java.io.IOException;

public class TextDrawer {

    private Drawable maze;
    private Screen screen;
    private TextGraphics tg;
    private boolean consoleOutput;

    public TextDrawer(Drawable maze, boolean consoleOutput) {
        this.maze = maze;
        this.consoleOutput = consoleOutput;
        DefaultTerminalFactory factory = new DefaultTerminalFactory();
        factory.setInitialTerminalSize(new TerminalSize(maze.getSize().horizontal(), maze.getSize().vertical()));
        try {
            Terminal terminal = factory.createTerminal();
            screen = new TerminalScreen(terminal);
            tg = screen.newTextGraphics();
            screen.startScreen();
        } catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void draw() {
        for(int y = 0; y < maze.getSize().vertical(); y++) {
            for(int x = 0; x < maze.getSize().horizontal(); x++) {
                Dimension position = new Dimension(x, y);
                switch(maze.getPoint(position)) {
                    case 5:
                        tg.setCharacter(x, y, 's');
                        if(consoleOutput) System.out.print(maze.getPoint(position));
                        break;
                    case 7:
                        tg.setCharacter(x, y, 'e');
                        if(consoleOutput) System.out.print(maze.getPoint(position));
                        break;
                    case 2: // for debug earth block
                        tg.setCharacter(x, y, Symbols.BLOCK_SPARSE);
                        if(consoleOutput) System.out.print(maze.getPoint(position));
                        break;
                    case 1:
                        if(consoleOutput) System.out.print(maze.getPoint(position));
                        byte dir = 0;
                        if(x - 1 >= 0 && maze.getPoint(position.subHorizontal(1)) == 1) dir |= 0b00000010;
                        if(x + 1 < maze.getSize().horizontal() && maze.getPoint(position.addHorizontal(1)) == 1)
                            dir |= 0b00000001;
                        if(y - 1 >= 0 && maze.getPoint(position.subVertical(1)) == 1) dir |= 0b00001000;
                        if(y + 1 < maze.getSize().vertical() && maze.getPoint(position.addVertical(1)) == 1)
                            dir |= 0b00000100;
                        switch(dir) {
                            case 0b1000:
                                tg.setCharacter(x, y, Symbols.SINGLE_LINE_VERTICAL);
                                break;
                            case 0b0100:
                                tg.setCharacter(x, y, Symbols.SINGLE_LINE_VERTICAL);
                                break;
                            case 0b0010:
                                tg.setCharacter(x, y, Symbols.SINGLE_LINE_HORIZONTAL);
                                break;
                            case 0b0001:
                                tg.setCharacter(x, y, Symbols.SINGLE_LINE_HORIZONTAL);
                                break;
                            case 0b1100:
                                tg.setCharacter(x, y, Symbols.SINGLE_LINE_VERTICAL);
                                break;
                            case 0b0011:
                                tg.setCharacter(x, y, Symbols.SINGLE_LINE_HORIZONTAL);
                                break;
                            case 0b0101:
                                tg.setCharacter(x, y, Symbols.SINGLE_LINE_TOP_LEFT_CORNER);
                                break;
                            case 0b0110:
                                tg.setCharacter(x, y, Symbols.SINGLE_LINE_TOP_RIGHT_CORNER);
                                break;
                            case 0b1010:
                                tg.setCharacter(x, y, Symbols.SINGLE_LINE_BOTTOM_RIGHT_CORNER);
                                break;
                            case 0b1001:
                                tg.setCharacter(x, y, Symbols.SINGLE_LINE_BOTTOM_LEFT_CORNER);
                                break;
                            case 0b1101:
                                tg.setCharacter(x, y, Symbols.SINGLE_LINE_T_RIGHT);
                                break;
                            case 0b1110:
                                tg.setCharacter(x, y, Symbols.SINGLE_LINE_T_LEFT);
                                break;
                            case 0b1011:
                                tg.setCharacter(x, y, Symbols.SINGLE_LINE_T_UP);
                                break;
                            case 0b0111:
                                tg.setCharacter(x, y, Symbols.SINGLE_LINE_T_DOWN);
                                break;
                            case 0b1111:
                                tg.setCharacter(x, y, Symbols.SINGLE_LINE_CROSS);
                                break;
                            default:
                                tg.setCharacter(x, y, Symbols.OUTLINED_SQUARE);
                                break;
                        }
                        break;
                    default:
                        if(consoleOutput) System.out.print(" ");
                        break;
                }
            }
            if(consoleOutput) System.out.println();
        }

/*
                if(maze.getPoint(position) == 5) {
//                    System.out.print("s");
                    tg.setCharacter(x, y, 's');
                }
                if(labyrinth.getPoint(position) == 7) {
//                    System.out.print("e");
                    tg.setCharacter(x, y, 'e');
                }
                if(labyrinth.getPoint(position) == 0) {
//                    System.out.print(" ");
                }
                if(labyrinth.getPoint(position) == 2) {
//                    System.out.print("2");
                }

                if(labyrinth.getPoint(position) == 1) {
//                    System.out.print(labyrinth.getPoint(position));
                    byte dir = 0;
                    if(x - 1 >= 0 && labyrinth.getPoint(position.subX(1)) == 1) dir |= 0b00000010;
                    if(x + 1 < labyrinth.getFullWidth() && labyrinth.getPoint(position.addX(1)) == 1) dir |= 0b00000001;
                    if(y - 1 >= 0 && labyrinth.getPoint(position.subY(1)) == 1) dir |= 0b00001000;
                    if(y + 1 < labyrinth.getFullHeight() && labyrinth.getPoint(position.addY(1)) == 1)
                        dir |= 0b00000100;
                    switch(dir) {
                        case 0b1000:
                            tg.setCharacter(x, y, Symbols.SINGLE_LINE_VERTICAL);
                            break;
                        case 0b0100:
                            tg.setCharacter(x, y, Symbols.SINGLE_LINE_VERTICAL);
                            break;
                        case 0b0010:
                            tg.setCharacter(x, y, Symbols.SINGLE_LINE_HORIZONTAL);
                            break;
                        case 0b0001:
                            tg.setCharacter(x, y, Symbols.SINGLE_LINE_HORIZONTAL);
                            break;
                        case 0b1100:
                            tg.setCharacter(x, y, Symbols.SINGLE_LINE_VERTICAL);
                            break;
                        case 0b0011:
                            tg.setCharacter(x, y, Symbols.SINGLE_LINE_HORIZONTAL);
                            break;
                        case 0b0101:
                            tg.setCharacter(x, y, Symbols.SINGLE_LINE_TOP_LEFT_CORNER);
                            break;
                        case 0b0110:
                            tg.setCharacter(x, y, Symbols.SINGLE_LINE_TOP_RIGHT_CORNER);
                            break;
                        case 0b1010:
                            tg.setCharacter(x, y, Symbols.SINGLE_LINE_BOTTOM_RIGHT_CORNER);
                            break;
                        case 0b1001:
                            tg.setCharacter(x, y, Symbols.SINGLE_LINE_BOTTOM_LEFT_CORNER);
                            break;
                        case 0b1101:
                            tg.setCharacter(x, y, Symbols.SINGLE_LINE_T_RIGHT);
                            break;
                        case 0b1110:
                            tg.setCharacter(x, y, Symbols.SINGLE_LINE_T_LEFT);
                            break;
                        case 0b1011:
                            tg.setCharacter(x, y, Symbols.SINGLE_LINE_T_UP);
                            break;
                        case 0b0111:
                            tg.setCharacter(x, y, Symbols.SINGLE_LINE_T_DOWN);
                            break;
                        case 0b1111:
                            tg.setCharacter(x, y, Symbols.SINGLE_LINE_CROSS);
                            break;
                        default:
                            tg.setCharacter(x, y, Symbols.OUTLINED_SQUARE);
                            break;
                    }
                }
            }
//            System.out.println();
        }
*/
        try {
            screen.refresh();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
