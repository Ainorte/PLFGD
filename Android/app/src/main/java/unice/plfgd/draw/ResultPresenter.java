package unice.plfgd.draw;

import android.support.annotation.NonNull;
import unice.plfgd.common.data.Draw;
import unice.plfgd.tools.Connexion;

public class ResultPresenter implements ResultContract.Presenter{

    private ResultContract.View mView;
    private Connexion connexion;

    public ResultPresenter(@NonNull ResultContract.View view){
        this.mView = view;
        mView.setPresenter(this);

        this.connexion = Connexion.getInstance();
        connexion.setPresenter(this);
    }

    @Override
    public void start(){

    }

    @Override
    public void onSocketReset(Connexion.ResetSocketMessage message) {
        mView.onSocketReset(message);
    }

    @Override
    public void back()  {
        mView.back();
    }

    @Override
    public void showResult(Draw draw) {
        if(draw != null)
            mView.drawResult(draw);
    }

    @Override
    public void replay() {
        mView.replay();
    }
}
