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

import java.util.List;


public class DrawCanvas extends View {

	private Paint paint;
	private Path path;
	private Draw draw;
	private boolean overflow;

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

	public Draw getDraw() {
		return draw;
	}

	public void setDraw(Draw draw) {
		this.draw = draw;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawPath(path, paint);

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (isActive()) {
			double xPos = event.getX();
			double yPos = event.getY();

			Point fe = null;

			if (xPos >= 0 && xPos <= getWidth() && yPos >= 0 && yPos <= getHeight()) {
				boolean old = overflow;
				overflow = false;
				switch (event.getAction()) {
					case MotionEvent.ACTION_MOVE:
						if (!old) {
							path.lineTo((float) xPos, (float) yPos);
							draw.addPoint(new Point(xPos, yPos));
							break;
						}
						//no break here !
					case MotionEvent.ACTION_DOWN:
						draw.addLine();
						path.moveTo((float) xPos, (float) yPos);
						draw.addPoint(new Point(xPos, yPos));
						break;
					case MotionEvent.ACTION_UP:
						break;

					default:
						return false;
				}
			} else {
				overflow = true;
				return false;
			}

			invalidate();
			return true;
		}
		return false;
	}

	public void drawResult() {
		draw = draw.convertRefactor(getWidth(), getHeight());
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
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		boolean old_state = active;
		active = true;
		if (draw.getPoints() != null) {
			drawResult();
		} else {
			draw = new Draw(getWidth(), getHeight());
		}
		active = old_state;
	}

	public void clear() {
		overflow = false;
		paint = new Paint();

		draw = new Draw(getWidth(), getHeight());

		path = new Path();
		paint.setAntiAlias(true);
		paint.setColor(Color.RED);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(10f);

		invalidate();
	}
}

