package unice.plfgd.draw;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import unice.plfgd.common.data.Game;
import unice.plfgd.common.data.packet.DevinerFormeResult;
import unice.plfgd.common.data.packet.Draw;
import unice.plfgd.common.forme.forme.Forme;
import unice.plfgd.common.forme.forme.Point;
import unice.plfgd.common.forme.generation.GenerationFormes;
import unice.plfgd.common.net.Packet;
import unice.plfgd.tool.service.APIService;
import unice.plfgd.tool.service.RemoteAPIImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DrawPresenter implements DrawContract.Presenter {

	private DrawContract.View mView;

	private Draw mDraw;
	private boolean canvasOverflow;
	private VelocityTracker velocityTracker;

	private Forme order;
	private List<Forme> formeList;

	private int numberFormes;
	private int counterForme;

	private Handler handler;
	private Runnable runnable;

	private int time;
	private boolean white;

	private String textForDevine;

	public DrawPresenter(@NonNull DrawContract.View view) {
		this.mView = view;
		mView.setPresenter(this);
		canvasOverflow = false;
	}

	@Override
	public void start() {
		switch (APIService.getInstance().getActualGame()) {
			case DRAWFORME:
				mView.showOrder(order);
				break;
			default:
				mView.showOrder(APIService.getInstance().getActualGame());
		}
		APIService.getInstance().setPresenter(this);
	}

	@Override
	public void onSocketReset(RemoteAPIImpl.ResetSocketMessage message) {
		stopTimer();
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

		APIService.getInstance().sendResponse(mDraw.convertRefactor(1000, 1000));
	}

	@Override
	public void resultSwitch(Packet result, Game game) {
		mView.resultSwitch(result, game);
	}

	@Override
	public void resultSCTSwitch(Packet result, Game game) {
		mView.resultSCTSwitch(result, game);
	}

	@Override
	public void setDraw(Draw draw) {
		mDraw = draw;
	}

	@Override
	public void setOrder(Forme forme) {
		order = forme;
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

	@Override
	public void setDevine(DevinerFormeResult devine) {
		formeList = new ArrayList<>(devine.getFormes());
		numberFormes = devine.getScoreToReach();
		mDraw = new Draw(new ArrayList<List<Point>>(){{ add(GenerationFormes.generateEnumForme(formeList.remove(0), 1000, 1000));}},1000,1000);
		counterForme++;
		time = 0;
	}

	@Override
	public void startTimer() {

		if (formeList.size() > 0) {

			mView.hideButtons();

			white = false;

			handler = new Handler();
			runnable = new Runnable() {
				@Override
				public void run() {
					if (white) {
						mDraw = new Draw(new ArrayList<List<Point>>() {{
							add(GenerationFormes.generateEnumForme(formeList.remove(0), 1000, 1000));
						}}, 1000, 1000);
						mView.getCanvas().drawResult(mDraw);
						handler.postDelayed(this, 0);
						white = false;
					} else if (time <= 2) {
						mView.showText(2 - time + "s : " + counterForme + "/" + numberFormes);
						time++;
						handler.postDelayed(this, 1000);
					} else if (counterForme < numberFormes) {
						counterForme++;
						time = 0;
						mView.getCanvas().clear();
						handler.postDelayed(this, 500);
						white = true;
					} else {
						counterForme = 1;
						handler.removeCallbacks(this);
						mView.showButtons();
						textForDevine = "A toi de jouer !";
						mView.showOrder(APIService.getInstance().getActualGame());
						onResetCanvas();
						mView.getCanvas().setActive(true);
					}
				}
			};

			handler.postDelayed(runnable, 0);
		}
	}

	@Override
	public void switchNext() {
		onResetCanvas();
		counterForme++;
		textForDevine = "Bien ! " + counterForme + "/" + numberFormes;
		mView.showOrder(APIService.getInstance().getActualGame());
		mView.unlockButtons();
	}

	@Override
	public void stopTimer() {
		if (handler != null && runnable != null) {
			handler.removeCallbacks(runnable);
		}
	}

	@Override
	public String getTextForDevine() {
		return textForDevine;
	}
}
