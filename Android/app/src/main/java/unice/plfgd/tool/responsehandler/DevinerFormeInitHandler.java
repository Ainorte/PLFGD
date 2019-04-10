package unice.plfgd.tool.responsehandler;

import unice.plfgd.common.data.packet.DevinerFormeResult;
import unice.plfgd.draw.ResultPresenter;
import unice.plfgd.menu.MenuPresenter;
import unice.plfgd.tool.service.APIService;

public class DevinerFormeInitHandler extends AbstractHandler {

    public DevinerFormeInitHandler(APIService svc) {
        super(svc);
    }

    @Override
    public void call(Object... args) {
        if (args.length >= 1) {
            DevinerFormeResult devine = castValue(args[0], DevinerFormeResult.class);
            MenuPresenter presenter = getPresenter(MenuPresenter.class);
            if(presenter != null) {
                presenter.displayGame(devine);
            }
            else{
                ResultPresenter resultPresenter = getPresenter(ResultPresenter.class);
                resultPresenter.changeFragment(devine);
            }
        }
    }
}
