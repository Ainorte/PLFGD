package unice.plfgd.draw;

import android.support.annotation.NonNull;
import unice.plfgd.common.data.Draw;
import unice.plfgd.common.forme.Forme;
import unice.plfgd.common.forme.Point;
import unice.plfgd.tool.Connexion;

import java.util.List;

public class DrawPresenter implements DrawContract.Presenter {

    private DrawContract.View mView;
    private Connexion connexion;

    public DrawPresenter(@NonNull DrawContract.View view){
        this.mView = view;
        mView.setPresenter(this);

        this.connexion = Connexion.getInstance();
        connexion.setPresenter(this);
    }

    @Override
    public void start() {
        mView.showOrder(Forme.SQUARE);
    }

    @Override
    public void onSocketReset(Connexion.ResetSocketMessage message) {
        mView.onSocketReset(message);
    }

    @Override
    public void onResetCanvas() {
        mView.resetCanvas();
    }

    @Override
    public void onValid() {
        // test pour recup le tableau des points et le mettre dans un TextView
        mView.onSending();

        DrawCanvas canvas = mView.getCanvas();

        List<Point> points = canvas.getCoords();
        int width = canvas.getWidth();
        int height = canvas.getHeight();

        connexion.sendMessage("draw", new Draw(points,width,height));
    }

    @Override
    public void resultSwitch(Draw draw) {
        mView.resultSwitch(draw);
    }
}
