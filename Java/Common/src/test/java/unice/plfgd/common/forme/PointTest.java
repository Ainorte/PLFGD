package unice.plfgd.common.forme;

import org.junit.Test;
import unice.plfgd.common.forme.forme.Point;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PointTest {

	unice.plfgd.common.forme.forme.Point p1;
	unice.plfgd.common.forme.forme.Point p2;
	unice.plfgd.common.forme.forme.Point sym;

	@org.junit.Before
	public void setUp() throws Exception {
		p1 = new unice.plfgd.common.forme.forme.Point(2,2);
		p2 = new unice.plfgd.common.forme.forme.Point(0,0);
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
