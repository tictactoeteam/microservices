package edu.saddleback.microservices.frontend.interfaces;

/**
 * Listener Interface
 * @param <E>
 */
public interface Listener<E> {
    void update(E message);
}
