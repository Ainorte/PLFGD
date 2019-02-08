package unice.plfgd.common.data;

import unice.plfgd.common.net.Packet;

import java.io.Serializable;

public class Result implements Packet, Serializable {
    private final boolean right;

    public Result (boolean right){
        this.right = right;
    }

    public boolean isRight() {
        return right;
    }
}
