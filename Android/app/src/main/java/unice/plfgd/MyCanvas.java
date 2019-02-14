package unice.plfgd;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import unice.plfgd.common.forme.Point;


public class MyCanvas extends View {

	Paint paint;
	Path path;
	List<Point> coords;

	public MyCanvas(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);

		coords = new ArrayList<>();

		paint = new Paint();
		path = new Path();
		paint.setAntiAlias(true);
		paint.setColor(Color.RED);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(10f);
	}

	@Override protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawPath(path,paint);

	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {

		float xPos = event.getX();
		float yPos = event.getY();

		Point fe = new Point(xPos,yPos);

		if (!coords.contains(fe)) {
			coords.add(fe);
		}

		switch(event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				path.moveTo(xPos,yPos);
				return true;

			case MotionEvent.ACTION_MOVE:
				path.lineTo(xPos,yPos);
				break;

			case MotionEvent.ACTION_UP:
				break;

			default:
				return false;
		}
		invalidate();
		return true;
	}

}

