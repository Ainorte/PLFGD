package unice.plfgd.draw;

import unice.plfgd.base.BasePresenter;
import unice.plfgd.base.BaseView;
import unice.plfgd.common.data.Draw;

public interface ResultContract {
    interface View extends BaseView<Presenter>{
        void back();
        void drawResult(Draw draw);
        void replay();
    }

    interface Presenter extends BasePresenter{
        void back();
        void showResult(Draw draw);
        void replay();
    }
}
