package checkers;

public class GameBoardPiece {
    private boolean king;
    private String color;
    private int position;
    private int row;
    private int column;

    public GameBoardPiece() { }

    public int getColumn() {
        return column;
    }

    public String getColor() {
        return color;
    }

    public int getRow() {
        return row;
    }

    public boolean isKing() {
        return king;
    }

    @Override
    public String toString() {
        return "GameBoardPiece{" +
                "color='" + color + '\'' +
                ", position=" + position +
                ", row=" + row +
                ", column=" + column +
                ", king=" + king +
                '}';
    }
}