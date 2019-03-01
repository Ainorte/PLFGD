package unice.plfgd.draw;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import unice.plfgd.common.data.Draw;
import unice.plfgd.common.forme.Point;

import java.util.ArrayList;
import java.util.List;


public class DrawCanvas extends View {

	private Paint paint;
	private Path path;
	private List<Point> coords;

	private boolean active;

	public DrawCanvas(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);

		active = true;

		coords = new ArrayList<>();

		paint = new Paint();
		path = new Path();
		paint.setAntiAlias(true);
		paint.setColor(Color.RED);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(10f);
	}

	public boolean isActive() {
		return active;
	}

	public List<Point> getCoords() {
		return coords;
	}

	public void setCoords(List<Point> coords) {
		this.coords = coords;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawPath(path, paint);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (isActive()) {
			float xPos = event.getX();
			float yPos = event.getY();

			Point fe = null;

			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					path.moveTo(xPos, yPos);
					fe = new Point(xPos, yPos, true);
					break;

				case MotionEvent.ACTION_MOVE:
					path.lineTo(xPos, yPos);
					fe = new Point(xPos, yPos);
					break;

				case MotionEvent.ACTION_UP:
					break;

				default:
					return false;
			}

			if (fe != null && !coords.contains(fe)) {
				coords.add(fe);
			}

			invalidate();
			return true;
		}
		return false;
	}

	public void drawResult(Draw draw){
		coords = draw.getPoints();

		for(Point p : coords){
			if (p.isStart()) {
				//myCanvas.path.moveTo((float) (p.getX() * (myCanvas.getWidth() / d.getLar())), (float) (p.getY() * (myCanvas.getHeight() / d.getHaut())));
				path.moveTo((float) p.getX() , (float) p.getY());
			}
			else {
				//myCanvas.path.lineTo((float) (p.getX() * (myCanvas.getWidth() / d.getLar())), (float) (p.getY() * (myCanvas.getHeight() / d.getHaut())));
				path.lineTo((float) p.getX() , (float) p.getY());
			}
		}

		invalidate();
	}

	public void reset(){
		active = true;

		coords = new ArrayList<>();

		paint = new Paint();
		path = new Path();
		paint.setAntiAlias(true);
		paint.setColor(Color.RED);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(10f);

		invalidate();
	}
}

