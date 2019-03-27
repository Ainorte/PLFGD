package unice.plfgd.tool.responsehandler;

import unice.plfgd.common.data.packet.Draw;
import unice.plfgd.draw.DrawContract;
import unice.plfgd.tool.service.APIService;

public class DrawHandler extends AbstractHandler {
	public DrawHandler(APIService svc) {
		super(svc);
	}

	@Override
	public void call(Object... args) {
		if (args.length >= 1) {
			Draw draw = castValue(args[0], Draw.class);

			getPresenter(DrawContract.Presenter.class).resultSwitch(draw);
		}
	}
}
