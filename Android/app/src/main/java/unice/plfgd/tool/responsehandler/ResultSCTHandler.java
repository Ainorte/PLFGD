package unice.plfgd.tool.responsehandler;

import unice.plfgd.common.action.Handler;
import unice.plfgd.common.data.Game;
import unice.plfgd.common.data.packet.ResultDrawForme;
import unice.plfgd.common.data.packet.ResultSCT;
import unice.plfgd.draw.DrawContract;
import unice.plfgd.draw.SCTResultFragment;
import unice.plfgd.draw.SCTResultPresenter;
import unice.plfgd.tool.service.APIService;

public class ResultSCTHandler extends AbstractHandler {

    public ResultSCTHandler(APIService svc) {
        super(svc);
    }

    @Override
    public void call(Object... args) {
        if (args.length >= 1) {
            ResultSCT sct = castValue(args[0], ResultSCT.class);

            Game game = APIService.getInstance().getActualGame();

            APIService.getInstance().resetGame();

            getPresenter(DrawContract.Presenter.class).resultSCTSwitch(sct, game);
        }
    }
}
