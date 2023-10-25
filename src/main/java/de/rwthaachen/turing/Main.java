package de.rwthaachen.turing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
	public static void main(String[] args) {
		if(args.length != 2) {
			System.out.println("Usage: java -jar turing.jar <input file> <tape>");
			System.exit(1);
		}
		Character blank = 'B';
		List<String> lines = readFile(args[0]);
		int startState = Integer.parseInt(lines.get(3));
		int finalState = Integer.parseInt(lines.get(4));
		Map<StateInputTuple, StateOutputDirectionTuple> transitionFunction = new HashMap<>();
		for (int i = 5; i < lines.size(); i++) {
			if (lines.get(i).isEmpty() || lines.get(i).startsWith("#")) {
				continue;
			}
			String[] parts = lines.get(i).split(" ");
			transitionFunction.put(new StateInputTuple(new State(Integer.parseInt(parts[0])), parts[1].charAt(0)),
					new StateOutputDirectionTuple(new State(Integer.parseInt(parts[2])), parts[3].charAt(0), directionFromString(parts[4])));
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