package my.tamagochka.mazeGenerator;

public class Position {

    private int x, y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Position(Position position) {
        this.x = position.x;
        this.y = position.y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position addX(int dx) {
        return new Position(x + dx, y);
    }

    public Position addY(int dy) {
        return new Position(x, y + dy);
    }

    public Position subX(int dx) {
        return new Position(x - dx, y);
    }

    public Position subY(int dy) {
        return new Position(x, y - dy);
    }

    @Override
    public boolean equals(Object position) {
        return this.x == ((Position)position).x && this.y == ((Position)position).y;
    }

}
