package edu.saddleback.microservices.frontend.observable;

/**
 * Listener Interface
 * @param <E>
 */
public interface Listener<E> {
    void update(E message);
}
