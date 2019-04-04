package unice.plfgd.common.action;

import unice.plfgd.common.data.UserStore;
import unice.plfgd.common.net.Packet;

public abstract class Action<T extends Packet, U extends Packet> {

    public Action(Handler resultHandler){
        this.resultHandler= resultHandler;
    }

    private Handler resultHandler;

    public abstract U run(UserStore store, T payload);

	public Handler getResultHandler(){
	    return resultHandler;
    }
}
