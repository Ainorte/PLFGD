package unice.plfgd.common;

public class Identification {
	private String nom;
	private int level;

	public Identification() {
	}

	public Identification(String nom, int level) {
		this.nom = nom;
		this.level = level;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
