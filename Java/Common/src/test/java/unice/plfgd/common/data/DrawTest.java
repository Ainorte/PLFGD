package unice.plfgd.common.data;

import unice.plfgd.common.forme.Point;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DrawTest {

	Draw draw;
	Draw drawmem;

	@org.junit.Before
	public void setUp() throws Exception {
		draw = new Draw(100,100);

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
	}

	@org.junit.Test
	public void convertRefactor() {
	}

	/*
		draw.addLine();
		draw.addPoint(new Point(0,0));
		draw.addPoint(new Point(100,0));
		draw.addPoint(new Point(100,100));
		draw.addPoint(new Point(0,100));

		System.out.println("test 0: " + draw.getPoints());
		System.out.println("test 1: " + draw.convertRefactor(2,2).getPoints());
		System.out.println("test 2: " + draw.convertRefactor(200,200).getPoints());
		System.out.println("test 3: " + draw.convertRefactor(50,30).getPoints());
		System.out.println("test 4: " + draw.convertRefactor(200,300).getPoints());
		System.out.println("test 5: " + draw.convertRefactor(100,100).getPoints());
		System.out.println("test 6: " + draw.convertRefactor(100,200).getPoints());

	draw = new Draw(100,200);
		draw.addLine();
		draw.addPoint(new Point(0,0));
		draw.addPoint(new Point(100,0));
		draw.addPoint(new Point(100,200));
		draw.addPoint(new Point(0,200));

		System.out.println("test 0: " + draw.getPoints());
		System.out.println("test 1: " + draw.convertRefactor(10,10).getPoints());
		System.out.println("test 2: " + draw.convertRefactor(300,300).getPoints());
		System.out.println("test 3: " + draw.convertRefactor(50,30).getPoints());
		System.out.println("test 4: " + draw.convertRefactor(300,400).getPoints());
		System.out.println("test 5: " + draw.convertRefactor(100,200).getPoints());
		System.out.println("test 6: " + draw.convertRefactor(100,300).getPoints());
		*/

}
