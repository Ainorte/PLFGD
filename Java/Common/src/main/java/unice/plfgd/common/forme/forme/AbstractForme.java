package unice.plfgd.common.forme.forme;

import unice.plfgd.common.forme.generation.FormeGeometrique;

abstract public class AbstractForme implements FormeGeometrique {

	public Point G; //barycentre

	public AbstractForme(Point G) {
		this.G = G;
	}

	public Point getG() {
		return this.G;
	}

	public void setG(double x, double y) {
		this.G = new Point(x, y);
	}

	public String toString() {
		return "Le barycentre est à : " + this.getG().getX() + ", " + this.getG().getY();
	}

}


