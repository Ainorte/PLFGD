package unice.plfgd.tool.handler;

import unice.plfgd.common.data.DetecForme;
import unice.plfgd.draw.DrawContract;
import unice.plfgd.tool.service.APIService;

public class RecogHandler extends AbstractHandler {

	public RecogHandler(APIService svc) {
		super(svc);
	}

	@Override
	public void call(Object... args) {
		if (args.length >= 1) {
			DetecForme detecForme = castValue(args[0], DetecForme.class);

			getPresenter(DrawContract.Presenter.class).resultSwitch(detecForme.getDraw());
		}
	}
}
