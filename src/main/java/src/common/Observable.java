/*************************
 * Authors: Martin Pribylina
 *
 * Observable interface
 ************************/
package src.common;

/**
 * Observable is interface for observable objects mainly fields
 *
 * @author      Martin Pribylina
 */
public interface Observable {
    void addObserver(Observer var1);

    void removeObserver(Observer var1);

    void notifyObservers();

    interface Observer {
        void update(Observable var1);
    }
}

