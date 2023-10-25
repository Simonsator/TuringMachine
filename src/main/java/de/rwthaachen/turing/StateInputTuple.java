package de.rwthaachen.turing;

/**
 * Repräsentiert ein Tupel aus Zustand und Zeichen.
 * @param state
 * @param input
 */
public record StateInputTuple(State state, Character input) {
}
