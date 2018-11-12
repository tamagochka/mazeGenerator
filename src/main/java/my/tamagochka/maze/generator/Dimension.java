package my.tamagochka.maze.generator;

public class Dimension {

    private int h, v;

    public Dimension() {
        h = 0;
        v = 0;
    }

    public Dimension(Dimension dimension) {
        this.h = dimension.h;
        this.v = dimension.v;
    }

    public Dimension(int horizontal, int vertical) {
        this.h = horizontal;
        this.v = vertical;
    }

    public int horizontal() {
        return h;
    }

    public int vertical() {
        return v;
    }

    public Dimension addHorizontal(int dHorizontal) {
        return new Dimension(this.h + dHorizontal, this.v);
    }

    public Dimension addVertical(int dVertical) {
        return new Dimension(this.h, this.v + dVertical);
    }

    public Dimension subHorizontal(int dHorizontal) {
        return new Dimension(this.h - dHorizontal, this.v);
    }

    public Dimension subVertical(int dVertical) {
        return new Dimension(this.h, this.v - dVertical);
    }

    public Dimension diff(Dimension dimension) {
        return new Dimension(this.h - dimension.h, this.v - dimension.v);
    }

    public Dimension summ(Dimension dimension) {
        return new Dimension(this.h + dimension.h, this.v + dimension.v);
    }

    public boolean bothEven() {
        return this.h % 2 == 0 && this.v % 2 == 0;
    }

    public boolean horizontalEven() {
        return this.h % 2 == 0;
    }

    public boolean verticalEven() {
        return this.v % 2 == 0;
    }

    public Dimension normalize() {
        return new Dimension(this.h == 0 ? 0 : this.h / Math.abs(this.h), this.v == 0 ? 0 : this.v / Math.abs(this.v));
    }

    public Dimension magnitude() {
        return new Dimension(Math.abs(this.h), Math.abs(this.v));
    }

    public Dimension swapDimensions() {
        return new Dimension(this.v, this.h);
    }

    @Override
    public boolean equals(Object dimension) {
        return this.h == ((Dimension)dimension).h && this.v == ((Dimension)dimension).v;
    }

    @Override
    public String toString() {
        return "[" + h + "; " + v + "]";
    }

}
