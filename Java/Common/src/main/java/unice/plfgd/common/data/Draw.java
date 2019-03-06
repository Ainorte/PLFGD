package unice.plfgd.common.data;

import unice.plfgd.common.forme.Point;
import unice.plfgd.common.net.Packet;

import java.util.List;

public class Draw extends Packet {
	private List<Point> pts;
	private int width;
	private int height;

	public Draw(List<Point> pts, int width, int height) {
		this.pts = pts;
		this.width = width;
		this.height = height;
	}

	public Draw() {
	}

	public List<Point> getPts() {
		return pts;
	}

	public void setPts(List<Point> pts) {
		this.pts = pts;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
