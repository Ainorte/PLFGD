package unice.plfgd.tools.handler;

import io.socket.emitter.Emitter;
import unice.plfgd.base.BasePresenter;
import unice.plfgd.home.HomeContract;
import unice.plfgd.tools.Connexion;

public class ConnectHandler implements Emitter.Listener {

    private Connexion connexion;

    public ConnectHandler(Connexion connexion) {
        this.connexion = connexion;
    }

    @Override
    public void call(Object... args) {
        connexion.Identify();
        BasePresenter presenter = connexion.getPresenter();
        if (presenter instanceof HomeContract.Presenter){
            HomeContract.Presenter homePresenter = (HomeContract.Presenter) presenter;
            homePresenter.onSocketActive();
        }
    }
}
