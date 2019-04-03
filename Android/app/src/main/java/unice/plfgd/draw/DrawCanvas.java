package unice.plfgd.draw;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import unice.plfgd.common.data.packet.Draw;
import unice.plfgd.common.forme.Point;

import java.util.List;


public class DrawCanvas extends View {

	private Paint paint;
	private Path path;

	private OnSizeChange sizeLisner;
	private boolean active;

	public DrawCanvas(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		active = true;
		clear();
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setOnSizeChange(OnSizeChange sizeLisner) {
		this.sizeLisner = sizeLisner;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawPath(path, paint);
	}

	public Draw drawResult(Draw result) {
		Draw draw = result.convertRefactor(getWidth(), getHeight());
		for (List<Point> pts : draw.getPoints()) {
			for (int i = 0; i < pts.size(); i++) {
				if (i == 0) {
					path.moveTo((float) pts.get(i).getX(), (float) pts.get(i).getY());
				} else {
					path.lineTo((float) pts.get(i).getX(), (float) pts.get(i).getY());
				}
			}
		}
		invalidate();
		return draw;
	}

	public Draw getEmptyDraw() {
		return new Draw(getWidth(), getHeight());
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if (sizeLisner != null) {
			sizeLisner.onSizeChange();
		}
	}

	public void lineTo(double x, double y) {
		path.lineTo((float) x, (float) y);
	}

	public void moveTo(double x, double y) {
		path.moveTo((float) x, (float) y);
	}

	public void clear() {
		paint = new Paint();

		path = new Path();
		paint.setAntiAlias(true);
		paint.setColor(Color.RED);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(10f);

		invalidate();
	}

	public interface OnSizeChange {
		void onSizeChange();
	}
}
