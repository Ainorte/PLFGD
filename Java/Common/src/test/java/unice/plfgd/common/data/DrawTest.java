package unice.plfgd.common.data;

import unice.plfgd.common.data.packet.Draw;
import unice.plfgd.common.forme.Point;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class DrawTest {

	Draw draw;
	Point p;
	Point cr;

	@org.junit.Before
	public void setUp() throws Exception {
		draw = new Draw(100,100);
		p = new Point(1,2);
	}

	@org.junit.Test
	public void addLine() {
		draw.addLine();
		assertEquals(new ArrayList<ArrayList<Point>>(){{add(new ArrayList<>());}},draw.getPoints());
		draw.addLine();
		assertEquals(new ArrayList<ArrayList<Point>>(){{add(new ArrayList<>());add(new ArrayList<>());}},draw.getPoints());
	}

	@org.junit.Test
	public void addPoint() {
		draw.addLine();
		draw.addPoint(p);
		assertEquals(new ArrayList<ArrayList<Point>>(){{add((new ArrayList<Point>(){{add(p);}}));}},draw.getPoints());

	}

	@org.junit.Test
	public void convertRefactor() {
		draw.addLine();
		draw.addPoint(p);
		Draw d2 = draw.convertRefactor(50,80);
		cr = new Point(0.5,16); // 50/100 < 80/100, coef = 0.5, 1 * 0.5 = 0.5, (80-(100*0.5))/2 = 15, 1 + 15 = 16
		assertEquals(new ArrayList<ArrayList<Point>>(){{add((new ArrayList<Point>(){{add(cr);}}));}},d2.getPoints());
	}

}
