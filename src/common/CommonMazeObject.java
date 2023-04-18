package src.common;

import java.io.Serializable;

public interface CommonMazeObject extends Serializable {
    boolean canMove(CommonField.Direction var1);

    boolean move(CommonField.Direction var1);

    default boolean isPacman() {
        return false;
    }

    CommonField getField();

    int getLives();

    CommonField.Direction lastMove();

    void setLastMove(CommonField.Direction direction);

    int animStep();
}

