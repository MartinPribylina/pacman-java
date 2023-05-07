/*************************
 * Authors: Martin Pribylina
 *
 * CommonField interface
 ************************/
package src.common;

import java.util.List;

/**
 * CommonField is interface for field
 *
 * @author      Martin Pribylina
 */
public interface CommonField extends Observable {
    CommonField nextField(Direction var1);

    boolean isEmpty();

    CommonMazeObject get();

    List<CommonMazeObject> getAll();

    boolean canMove();

    boolean contains(CommonMazeObject var1);

    enum Direction {
        LEFT(0, -1){
            @Override
            public Direction Reverse(){
                return RIGHT;
            }
        },
        UP(-1, 0){
            @Override
            public Direction Reverse(){
                return DOWN;
            }
        },
        RIGHT(0, 1){
            @Override
            public Direction Reverse(){
                return LEFT;
            }
        },
        DOWN(1, 0){
            @Override
            public Direction Reverse(){
                return UP;
            }
        };

        public Direction Reverse(){
            return null;
        }

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
