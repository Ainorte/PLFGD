package unice.plfgd.draw;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import unice.plfgd.common.data.Draw;
import unice.plfgd.common.forme.Point;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class DrawCanvas extends View {

	private Paint paint;
	private Path path;
	private List<Point> coords;

	private ResultContract.Presenter presenter;

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

	public void setPresenter(ResultContract.Presenter presenter) {
		this.presenter = presenter;
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
			double xPos = event.getX();
			double yPos = event.getY();

			Point fe = null;

			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					path.moveTo((float) xPos, (float) yPos);
					fe = new Point(xPos, yPos,true);
					break;

				case MotionEvent.ACTION_MOVE:
					path.lineTo((float) xPos, (float) yPos);
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

	public void drawResult(){
		Draw draw = presenter.getDraw();
		coords = draw.getPts();
		Log.d(TAG, "ancien width = " + draw.getWidth());
		Log.d(TAG, "ancien height = " + draw.getHeight());
		double cl = (double) getWidth() / (double) draw.getWidth();
		double ch = (double) getHeight() / (double) draw.getHeight();
		boolean b = (ch >= cl);
		if (b) {ch = cl;} else {cl = ch;}

		for(Point p : coords){
			float nvX = (float) (p.getX() * cl);
			float nvY = (float) (p.getY() * ch);
			if(b) {
				nvY += getHeight() * (1 - ch)/2;
			}
			else {
				nvX += getWidth() * (1 - cl) /2;
			}
			if (p.isStart()) {
				path.moveTo(nvX, nvY);
				//path.moveTo((float) p.getX() , (float) p.getY());
			}
			else {
				path.lineTo(nvX, nvY);
				//path.lineTo((float) p.getX() , (float) p.getY());
			}
		}

		invalidate();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		Log.d(TAG, "width = " + getWidth());
		Log.d(TAG, "height = " + getHeight());
		if(presenter != null) {
			drawResult();
		}

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

