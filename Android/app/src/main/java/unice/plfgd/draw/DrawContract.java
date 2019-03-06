package unice.plfgd.draw;

import unice.plfgd.base.BasePresenter;
import unice.plfgd.base.BaseView;
import unice.plfgd.common.data.Draw;
import unice.plfgd.common.forme.Forme;

public interface DrawContract {
    interface View extends BaseView<Presenter>{
        void showOrder(Forme forme);
        void resetCanvas();
        DrawCanvas getCanvas();
        void onSending();
        void resultSwitch(Draw draw);
    }

    interface Presenter extends BasePresenter{
        void onResetCanvas();
        void onValid();
        void resultSwitch(Draw draw);
    }
}
