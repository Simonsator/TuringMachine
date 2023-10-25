package de.rwthaachen.turing;

/**
 * Repräsentiert ein Tupel aus Zustand, Zeichen und Richtung.
 * @param state
 * @param output
 * @param direction
 */
public record StateOutputDirectionTuple(State state, Character output, Direction direction) {
}
