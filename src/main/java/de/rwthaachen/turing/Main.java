package de.rwthaachen.turing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: java -jar turing.jar <input file> <tape>");
			System.exit(1);
		}
		Character blank = 'B';
		List<String> lines = readFile(args[0]);
		int startState = Integer.parseInt(lines.get(3));
		int finalState = Integer.parseInt(lines.get(4));
		Map<StateInputTuple, StateOutputDirectionTuple> transitionFunction = new HashMap<>();
		int statesTotalCount = Integer.parseInt(lines.get(0));
		List<State> states = new ArrayList<>(statesTotalCount);
		for (int i = 5; i < lines.size(); i++) {
			if (lines.get(i).isEmpty() || lines.get(i).startsWith("#")) {
				continue;
			}
			String[] parts = lines.get(i).split(" ");
			transitionFunction.put(new StateInputTuple(new State(Integer.parseInt(parts[0])), parts[1].charAt(0)),
					new StateOutputDirectionTuple(new State(Integer.parseInt(parts[2])), parts[3].charAt(0), directionFromString(parts[4])));
			if (!states.contains(new State(Integer.parseInt(parts[0])))) {
				states.add(new State(Integer.parseInt(parts[0])));
			}
		}
		String alphabetString = lines.get(2);
		Character[] alphabet = new Character[alphabetString.length()];
		for (int i = 0; i < alphabetString.length(); i++) {
			alphabet[i] = alphabetString.charAt(i);
		}
		states.sort(Comparator.comparingInt(State::number));
		StringBuilder outputFirstLine = new StringBuilder("        \\delta ");
		for (Character character : alphabet) {
			outputFirstLine.append("& ").append(character).append(" ");
		}
		System.out.println(outputFirstLine.append("\\\\ \\hline").toString().replace("#", "\\#"));
		for (State state : states) {
			StringBuilder output = new StringBuilder("        q\\textsubscript{" + state.number() + "} ");
			for (Character character : alphabet) {
				StateOutputDirectionTuple tuple = transitionFunction.get(new StateInputTuple(state, character));
				output.append("& ").append(tuple == null ? "-" : ("(q\\textsubscript{" + tuple.state().number() + "}, " + tuple.output() + ", " + tuple.direction() + ")")).append(" ");
			}
			System.out.println(output.append("\\\\ \\hline").toString().replace("#", "\\#"));
		}
		TuringMachine turingMachine = new TuringMachine(Head.fromString(args[1], blank), new State(startState), new State(finalState), transitionFunction);
		turingMachine.run();
	}

	private static List<String> readFile(String path) {
		try {
			return Files.readAllLines(Paths.get(path));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static Direction directionFromString(String direction) {
		return switch (direction) {
			case "L" -> Direction.L;
			case "R" -> Direction.R;
			case "N" -> Direction.N;
			default -> throw new RuntimeException("Unknown direction " + direction);
		};
	}
}