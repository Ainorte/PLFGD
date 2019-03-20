package unice.plfgd.common.forme;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PointTest {

	Point p1;
	Point p2;
	Point sym;

	@org.junit.Before
	public void setUp() throws Exception {
		p1 = new Point(2,2);
		p2 = new Point(0,0);
	}

	@Test
	public void rotation() {
		double x = p1.getX();
		double y = p1.getY();
		p1.rotation(p2,180);
		// 180 degre donc x et y ont change de signe
		// on a une tolerande de 0.00001
		assertTrue(Math.abs(p1.getX() + x) < 0.00001 &&  Math.abs(p1.getY() + y) < 0.00001);
	}

	@Test
	public void getDirection() {
		// comme precedement on utilise Math.abs et la tolerance
		assertTrue(Math.abs(p1.getDirection(p2) - Math.atan((p2.getY() - p1.getY()) / (p2.getX() - p1.getX()))) < 0.00001);
	}

	@Test
	public void pointSym() {
		sym = p2.pointSym(p1);
		double xS = 2 * p2.getX() - p1.getX();
		double yS = 2 * p2.getY() - p1.getY();
		assertEquals(sym, new Point(xS, yS));
	}
}
