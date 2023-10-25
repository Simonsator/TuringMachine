package de.rwthaachen.turing;

public class Head {
	private Head left;
	private Head right;
	private Character character;
	private final Character BLANK;

	public Head(Head left, Head right, Character character, Character blank) {
		this.left = left;
		this.right = right;
		this.character = character;
		this.BLANK = blank;
	}

	private Head getLeft() {
		if (left == null)
			left = new Head(null, this, BLANK, BLANK);
		return left;
	}

	private Head getRight() {
		if (right == null)
			right = new Head(this, null, BLANK, BLANK);
		return right;
	}

	public Character getCharacter() {
		return character;
	}

	public void setCharacter(Character character) {
		this.character = character;
	}

	public Head move(Direction direction) {
		return switch (direction) {
			case L -> getLeft();
			case R -> getRight();
			case N -> this;
		};
	}

	public String outputBand(State state) {
		StringBuilder sb = new StringBuilder();
		Head head = this;
		sb.append("...").append(BLANK);
		while (head.left != null) {
			head = head.left;
		}
		while (head != null) {
			if (head == this) {
				sb.append("[").append(state.number()).append("]");
			} else {
				sb.append(head.character);
			}
			head = head.right;
		}
		sb.append(BLANK).append("...");
		return sb.toString();
	}

	public static Head fromString(String pInput, Character pBlank) {
		if (pInput.isEmpty()) {
			return new Head(null, null, pBlank, pBlank);
		}
		Head head = new Head(null, null, pInput.charAt(0), pBlank);
		Head current = head;
		for (int i = 1; i < pInput.length(); i++) {
			Head next = new Head(current, null, pInput.charAt(i), pBlank);
			current.right = next;
			current = next;
		}
		return head;
	}
}
