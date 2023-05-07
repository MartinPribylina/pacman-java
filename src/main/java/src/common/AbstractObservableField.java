/*************************
 * Authors: Martin Pribylina
 *
 * Abstract class for managing observers on fields
 ************************/
package src.common;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractObservableField is abstract class for managing observers on fields
 *
 * @author      Martin Pribylina
 */
public abstract class AbstractObservableField implements CommonField {
    private final Set<Observable.Observer> observers = new HashSet<>();

    public AbstractObservableField() {
    }

    public void addObserver(Observable.Observer o) {
        this.observers.add(o);
    }

    public void removeObserver(Observable.Observer o) {
        this.observers.remove(o);
    }

    public void notifyObservers() {
        this.observers.forEach((o) -> o.update(this));
    }
}
