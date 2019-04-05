package unice.plfgd.common.forme.generation;

import unice.plfgd.common.forme.forme.Point;

import java.util.List;

public interface FormeGeometrique {
	Point getG();

	void setG(double x, double y);

	double getAire();

	double getPerim();

	List<Point> make();

	String toString();
}

