package de.rwthaachen.turing;

import java.util.Map;

public class TuringMachine {
	// Die Zustandsüberführungsfunktion
	private final Map<StateInputTuple, StateOutputDirectionTuple> transitionFunction;
	// Der Endzustand
	private final State FINAL_STATE;
	// Der Startzustand
	private final State START_STATE;
	// Die Momentanekopfposition
	private Head head;

	public TuringMachine(Head head, State startState, State finalState, Map<StateInputTuple, StateOutputDirectionTuple> transitionFunction) {
		this.head = head;
		this.START_STATE = startState;
		this.FINAL_STATE = finalState;
		this.transitionFunction = transitionFunction;
	}

	public void run() {
		State currentState = START_STATE;
		while (!currentState.equals(FINAL_STATE)) {
			// Laufe solange, bis der Endzustand erreicht ist
			// Gebe Band aus
			System.out.println(head.outputBand(currentState));
			// Lese Zeichen an aktuelle Bandposition
			Character input = head.getCharacter();
			StateInputTuple stateInputTuple = new StateInputTuple(currentState, input);
			// Schaue nach in welchen Zustand gewechselt werden soll, welches Zeichen geschrieben werden soll und in welche Richtung sich der Kopf bewegen soll.
			StateOutputDirectionTuple stateOutputDirectionTuple = transitionFunction.get(stateInputTuple);
			if (stateOutputDirectionTuple == null) {
				// Es gibt keine Zustandsüberführung für den aktuellen Zustand und das aktuelle Zeichen
				throw new RuntimeException("No transition for state " + currentState + " and input " + input);
			}
			// Schreibe Zeichen an aktuelle Bandposition
			head.setCharacter(stateOutputDirectionTuple.output());
			// Wechsle in neuen Zustand
			currentState = stateOutputDirectionTuple.state();
			// Bewege Kopf in die entsprechende Richtung
			head = head.move(stateOutputDirectionTuple.direction());
		}
		// Gebe Band aus
		System.out.println(head.outputBand(currentState));
	}
}
