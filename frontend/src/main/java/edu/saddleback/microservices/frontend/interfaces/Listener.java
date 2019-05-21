package edu.saddleback.microservices.frontend.interfaces;

/**
 * Listener Interface for Observable booleans.
 *
 * @param <E>
 */
public interface Listener<E> {
    void update(E message);
}
