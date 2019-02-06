package unice.plfgd.common;

public class Move {
	private int move;
	private boolean bigger;

	public int getMove() {
		return move;
	}

	public void setMove(int move) {
		this.move = move;
	}

	public boolean isBigger() {
		return bigger;
	}

	public void setBigger(boolean bigger) {
		this.bigger = bigger;
	}

	/**
	 * constructeur par défaut pour le mapping JSON - Objet java
	 */
	public Move() {
	}

	/**
	 * constructeur pour créer un move joué avec la réponse (si ce n'est pas la bonne)
	 *
	 * @param val la valeur qui a été proposée par le joueur
	 * @param sup indique si la valeur est plus grande que le nombre recherché
	 */
	public Move(int val, boolean sup) {
		setMove(val);
		setBigger(sup);
	}

	@Override
	public String toString() {
		return "" + getMove() + "/" + (isBigger() ? ">" : "<");
	}
}
