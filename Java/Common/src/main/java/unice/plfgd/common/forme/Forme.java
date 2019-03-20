package unice.plfgd.common.forme;

public enum Forme {
	POINT("Point"),
	SEGMENT("Segment"),
	SQUARE("Carre"),
	TRIANGLE("Triangle"),
    ELLIPSE("Ellipse"),
	CIRCLE("Cercle"),
    RECTANGLE("Rectangle"),
    QUADRI("Quadrilatere"),
	UNKNOWN("Inconnu");


    private String nom = "";

	Forme(String nom){
	    this.nom = nom;
    }

    public String getName(){
        return nom;
    }
}
