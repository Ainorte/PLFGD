package unice.plfgd.common.action;

import unice.plfgd.common.data.UserStore;
import unice.plfgd.common.data.packet.Draw;
import unice.plfgd.common.data.packet.FormeRequest;
import unice.plfgd.common.data.packet.ResultDrawForme;
import unice.plfgd.common.data.packet.ResultSCT;
import unice.plfgd.common.forme.forme.Forme;
import unice.plfgd.common.forme.forme.Point;
import unice.plfgd.common.forme.generation.GenerationFormes;

import java.util.ArrayList;
import java.util.List;

public class ResultSCTAction extends Action<Draw, ResultSCT> {
    public ResultSCTAction(Handler resultHandler) {
        super(resultHandler);
    }

    @Override
    public ResultSCT run(UserStore store, Draw payload) {
        ResultSCT result = new ResultSCT();

        final Forme forme = store.getData("forme", FormeRequest.class).getForme();
        ResultDrawForme recog = new ResultDrawFormeAction(null).run(new UserStore(),payload);
        Forme player = recog.getForme();

         Draw enemy = new Draw(
                new ArrayList<List<Point>>(){{add(GenerationFormes.generateEnumForme(forme,1000,1000));}},
                1000,1000
        );

        result.setEnemy(enemy);
        result.setEnemyF(forme);
        result.setPlayer(recog.getDraw());
        result.setPlayerF(player);

        switch (player) {
            case SQUARE:
            case RECTANGLE:
                switch (forme) {
                    case SQUARE:
                    case RECTANGLE:
                        result.setWin(null);
                        break;
					case CIRCLE:
                        result.setWin(false);
                        break;
                    default:
                        result.setWin(true);
                }
                break;
            case CIRCLE:
                switch (forme) {
					case TRIANGLE:
                        result.setWin(false);
                        break;
                    case CIRCLE:
                        result.setWin(null);
                        break;
                    default:
                        result.setWin(true);
                }
                break;
            case TRIANGLE:
                switch (forme) {
                    case SQUARE:
					case RECTANGLE:
                        result.setWin(false);
                        break;
                    case TRIANGLE:
                        result.setWin(null);
                        break;
                    default:
                        result.setWin(true);
                }
                break;
			default:
				result.setWin(false);
        }

        if (result.getWin()) {
			store.incrementScore();
		}

        store.resetGame();
        return result;
    }
}
