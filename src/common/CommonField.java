package src.common;

public interface CommonField extends Observable {
    CommonField nextField(Direction var1);

    boolean isEmpty();

    CommonMazeObject get();

    boolean canMove();

    boolean contains(CommonMazeObject var1);

    enum Direction {
        LEFT(0, -1),
        UP(-1, 0),
        RIGHT(0, 1),
        DOWN(1, 0);

        private final int r;
        private final int c;

        Direction(int dr, int dc) {
            this.r = dr;
            this.c = dc;
        }

        public int deltaRow() {
            return this.r;
        }

        public int deltaCol() {
            return this.c;
        }
    }
}
