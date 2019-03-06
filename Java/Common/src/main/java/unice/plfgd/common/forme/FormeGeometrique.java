package unice.plfgd.common.forme;

import java.util.List;

public interface FormeGeometrique {
	Point getG();

	void setG(double x, double y);

	double getAire();

	double getPerim();

	List<Point> make();

	String toString();
}

