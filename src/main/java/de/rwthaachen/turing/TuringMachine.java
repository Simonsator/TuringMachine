package de.rwthaachen.turing;

import java.util.Map;

public class TuringMachine {
	private final Map<StateInputTuple, StateOutputDirectionTuple> transitionFunction;
	private final State FINAL_STATE;
	private final State START_STATE;
	private Head head;

	public TuringMachine(Head head, State startState, State finalState, Map<StateInputTuple, StateOutputDirectionTuple> transitionFunction) {
		this.head = head;
		this.START_STATE = startState;
		this.FINAL_STATE = finalState;
		this.transitionFunction = transitionFunction;
	}

	public void run() {
		State currentState = START_STATE;
		while (!currentState.equals( FINAL_STATE)) {
			System.out.println(head.outputBand(currentState));
			Character input = head.getCharacter();
			StateInputTuple stateInputTuple = new StateInputTuple(currentState, input);
			StateOutputDirectionTuple stateOutputDirectionTuple = transitionFunction.get(stateInputTuple);
			if (stateOutputDirectionTuple == null)
				throw new RuntimeException("No transition for state " + currentState + " and input " + input);
			head.setCharacter(stateOutputDirectionTuple.output());
			currentState = stateOutputDirectionTuple.state();
			head = head.move(stateOutputDirectionTuple.direction());
		}
		System.out.println(head.outputBand(currentState));
	}
}
