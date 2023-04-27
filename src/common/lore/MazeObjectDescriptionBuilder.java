/*************************
 * Authors: Martin Pribylina
 *
 * Lore/description builder
 ************************/
package src.common.lore;

import src.gui.MazeObjectDescription;

import java.awt.*;

public class MazeObjectDescriptionBuilder {

    MazeObjectDescription mazeObjectDescription;

    public MazeObjectDescriptionBuilder(){
        mazeObjectDescription = new MazeObjectDescription();
    }

    public MazeObjectDescriptionBuilder pacman(){
        mazeObjectDescription.title = Lore.pacmanName;
        mazeObjectDescription.description = Lore.pacmanDescription;

        return this;
    }

    public MazeObjectDescriptionBuilder ghostType(int ghostType){
        mazeObjectDescription.title = Lore.ghostNames[ghostType];
        mazeObjectDescription.description = Lore.ghostDescriptions[ghostType];

        return this;
    }

    public MazeObjectDescriptionBuilder key(){
        mazeObjectDescription.title = Lore.keyName;
        mazeObjectDescription.description = Lore.keyDescription;

        return this;
    }

    public MazeObjectDescriptionBuilder setDimension(Dimension dimension)
    {
        mazeObjectDescription.size = dimension;
        return this;
    }

    public MazeObjectDescriptionBuilder setPosition(int x, int y)
    {
        mazeObjectDescription.x = x;
        mazeObjectDescription.y = y;
        return this;
    }

    public MazeObjectDescription build(){
        return mazeObjectDescription;
    }
}
