package checkers;
public class Step {
    private Position first;
    private Position second;

    public Step(Position first, Position second) {
        this.first = first;
        this.second = second;
    }

    public int returnChangedPos() {
        return convertPos(first);
    }

    public int returnChangedPosTo() {
        return convertPos(second);
    }

    public static int convertPos(Position pos) {
        return pos.getY() * 4 + (pos.getX()) / 2 + 1;
    }

    @Override
    public String toString() {
        return "Step: " + returnChangedPos() + ", " + returnChangedPosTo();
    }

    public static class Position {
        private int x;
        private int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }
    }
}
