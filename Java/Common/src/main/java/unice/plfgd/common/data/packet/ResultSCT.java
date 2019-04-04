package unice.plfgd.common.data.packet;

import unice.plfgd.common.forme.Forme;
import unice.plfgd.common.net.Packet;

public class ResultSCT extends Packet {
    private Draw player;
    private Draw enemy;
    private Boolean win;
    private Forme playerF;
    private Forme enemyF;

    public ResultSCT(Draw player, Draw enemy, Boolean win, Forme playerF, Forme enemyF) {
        this.player = player;
        this.enemy = enemy;
        this.win = win;
        this.playerF = playerF;
        this.enemyF = enemyF;
    }

    public ResultSCT() {
    }

    public Draw getPlayer() {
        return player;
    }

    public void setPlayer(Draw player) {
        this.player = player;
    }

    public Draw getEnemy() {
        return enemy;
    }

    public void setEnemy(Draw enemy) {
        this.enemy = enemy;
    }

    public Boolean getWin() {
        return win;
    }

    public void setWin(Boolean win) {
        this.win = win;
    }

    public Forme getPlayerF() {
        return playerF;
    }

    public void setPlayerF(Forme playerF) {
        this.playerF = playerF;
    }

    public Forme getEnemyF() {
        return enemyF;
    }

    public void setEnemyF(Forme enemyF) {
        this.enemyF = enemyF;
    }
}
