package de.rwthaachen.turing;

/**
 * Repräsentiert eine Band Position, mit der Möglichkeit sich in eine Richtung zu bewegen.
 */
public class Head {
	/**
	 * Die linke Nachbar Positionen.
	 */
	private Head left;
	/**
	 * Die rechte Nachbar Positionen.
	 */
	private Head right;
	/**
	 * Das Zeichen das an dieser Band Position steht.
	 */
	private Character character;
	/**
	 * Das Blank Zeichen.
	 */
	private final Character BLANK;

	/**
	 *
	 * @param left Band Position Links der neuen Band Position
	 * @param right Band Position Rechts der neuen Band Position
	 * @param character Das Zeichen das an dieser Band Position steht.
	 * @param blank Das Blank Zeichen
	 */
	public Head(Head left, Head right, Character character, Character blank) {
		this.left = left;
		this.right = right;
		this.character = character;
		this.BLANK = blank;
	}

	/**
	 *
	 * @return Gibt die linke Nachbar Position zurück. Falls diese nicht existiert, wird eine neue erstellt mit dem Inhalt von {@link #BLANK}.
	 */
	private Head getLeft() {
		if (left == null)
			left = new Head(null, this, BLANK, BLANK);
		return left;
	}
	/**
	 *
	 * @return Gibt die rechte Nachbar Position zurück. Falls diese nicht existiert, wird eine neue erstellt mit dem Inhalt von {@link #BLANK}.
	 */
	private Head getRight() {
		if (right == null)
			right = new Head(this, null, BLANK, BLANK);
		return right;
	}

	/**
	 *
	 * @return Gibt das Zeichen an dieser Band Position zurück.
	 */
	public Character getCharacter() {
		return character;
	}

	/**
	 *
	 * @param character Setzt das Zeichen an dieser Band Position.
	 */
	public void setCharacter(Character character) {
		this.character = character;
	}

	/**
	 *
	 * @param direction Richtung
	 * @return Bewegt den Kopf in die angegebene Richtung.
	 */
	public Head move(Direction direction) {
		return switch (direction) {
			case L -> getLeft();
			case R -> getRight();
			case N -> this;
		};
	}

	/**
	 *
	 * @param state Der momentane Zustand
	 * @return Gibt den Inhalt des Bandes als String zurück
	 */
	public String outputBand(State state) {
		StringBuilder sb = new StringBuilder();
		Head head = this;
		sb.append("...").append(BLANK);
		while (head.left != null) {
			// Gehe nach ganz links
			head = head.left;
		}
		while (head != null) {
			// Gehe von links nach rechts
			if (head == this) {
				// Gebe den momentanen Zustand an, wenn der Kopf auf dieser Position ist
				sb.append("[").append(state.number()).append("]");
			}
			sb.append(head.character);
			head = head.right;
		}
		sb.append(BLANK).append("...");
		return sb.toString();
	}

	/**
	 *
	 * @param pInput Der Inhalt des Bandes
	 * @param pBlank Das Blank Zeichen
	 * @return Erstellt ein Band aus einem String
	 */
	public static Head fromString(String pInput, Character pBlank) {
		if (pInput.isEmpty()) {
			// Das Band ist leer
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
