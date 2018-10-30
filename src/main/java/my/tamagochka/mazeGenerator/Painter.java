package my.tamagochka.mazeGenerator;

import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;

public class Painter {

    private int width, height;
    private Screen screen;
    private TextGraphics tg;

    public Painter(int width, int height) {
        this.width = width;
        this.height = height;
        DefaultTerminalFactory factory = new DefaultTerminalFactory();
        factory.setInitialTerminalSize(new TerminalSize(this.width, this.height));
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

    public void drawLabyrinth(Labyrinth labyrinth) {
        for(int y = 0; y < labyrinth.getFullHeight(); y++) {
            for(int x = 0; x < labyrinth.getFullWidth(); x++) {
                Position position = new Position(x, y);
                if(labyrinth.getPoint(position) == 5) {
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
                    if(y + 1 < labyrinth.getFullHeight() && labyrinth.getPoint(position.addY(1)) == 1) dir |= 0b00000100;
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
        try {
            screen.refresh();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
