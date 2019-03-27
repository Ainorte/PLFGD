package unice.plfgd.draw;

import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import unice.plfgd.common.data.Draw;
import unice.plfgd.common.forme.Forme;
import unice.plfgd.common.forme.Point;
import unice.plfgd.tool.service.APIService;
import unice.plfgd.tool.service.RemoteAPIImpl;

public class DrawPresenter implements DrawContract.Presenter {

	private DrawContract.View mView;

	private Draw mDraw;
	private boolean canvasOverflow;
	private VelocityTracker velocityTracker;

	public DrawPresenter(@NonNull DrawContract.View view) {
		this.mView = view;
		mView.setPresenter(this);
		canvasOverflow = false;
	}

	@Override
	public void start() {
		mView.showOrder(Forme.SQUARE);
		APIService.getInstance().setPresenter(this);
	}

	@Override
	public void onSocketReset(RemoteAPIImpl.ResetSocketMessage message) {
		mView.onSocketReset(message);
	}

	@Override
	public void onResetCanvas() {
		canvasOverflow = false;
		mDraw = mView.getCanvas().getEmptyDraw();
		mView.resetCanvas();
	}

	@Override
	public void onValid() {
		// test pour recup le tableau des points et le mettre dans un TextView
		mView.onSending();

		APIService.getInstance().sendMessage("draw", mDraw.convertRefactor(100, 100));
	}

	@Override
	public void resultSwitch(Draw draw) {
		mView.resultSwitch(draw);
	}

	@Override
	public void setDraw(Draw draw) {
		mDraw = draw;
	}

	@Override
	public DrawCanvas.OnSizeChange onDrawSizeChange() {
		return new DrawCanvas.OnSizeChange() {
			@Override
			public void onSizeChange() {
				if (mDraw != null && mDraw.getPoints() != null) {
					mDraw = mView.getCanvas().drawResult(mDraw);
				} else {
					mDraw = mView.getCanvas().getEmptyDraw();
				}
			}
		};
	}

	@Override
	public View.OnTouchListener onCanvasTouch() {
		return new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				DrawCanvas canvas = (DrawCanvas) v;
				if (canvas.isActive()) {
					double xPos = event.getX();
					double yPos = event.getY();

					Point fe = null;

					if (xPos >= 0 && xPos <= canvas.getWidth() && yPos >= 0 && yPos <= canvas.getHeight()) {
						boolean old = canvasOverflow;
						canvasOverflow = false;
						switch (event.getAction()) {
							case MotionEvent.ACTION_MOVE:
								if (!old) {
									velocityTracker.addMovement(event);
									velocityTracker.computeCurrentVelocity(1000);

									double xV = velocityTracker.getXVelocity(event.getPointerId(event.getActionIndex()));
									double yV = velocityTracker.getYVelocity(event.getPointerId(event.getActionIndex()));

									canvas.lineTo(xPos, yPos);

									mDraw.addPoint(new Point(xPos, yPos, xV, yV));
									break;
								}
								//no break here !
							case MotionEvent.ACTION_DOWN:
								if (velocityTracker == null) {
									velocityTracker = VelocityTracker.obtain();
								} else {
									velocityTracker.clear();
								}
								velocityTracker.addMovement(event);

								mDraw.addLine();

								canvas.moveTo(xPos, yPos);

								mDraw.addPoint(new Point(xPos, yPos));
								break;
							case MotionEvent.ACTION_UP:
								break;

							default:
								return false;
						}
					} else {
						canvasOverflow = true;
						return false;
					}

					canvas.invalidate();
					return true;
				}
				return false;
			}
		};
	}
}
