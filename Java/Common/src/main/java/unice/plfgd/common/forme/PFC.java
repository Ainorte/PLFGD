package unice.plfgd.common.forme;

public class PFC {

    private int nbManches; //nombre de manches de la partie
    private int pointsA = 0; //nombre de points du joueur 1
    private int pointsB = 0; //nombre de points du joueur 2
    private boolean endGame = false; //savoir si la partie est terminée

    public PFC(int nbManches){
        this.nbManches = nbManches;
    }
    //pierre = cercle
    //feuille = carre
    //ciseau = triangle

    public String getResult(String a, String b){
        if(a != b){
            String res = "";
            if(getWin(a, b) == true){
                pointsA += 1;
                res += a + " gagne contre " + b + ".\n";
                res += "Joueur 1 marque le point.\n";
            } else {
                pointsB += 1;
                res += b + " gagne contre " + a + ".\n";
                res += "Joueur 2 marque le point.\n";
            }
            if(pointsA >= nbManches | pointsB >= nbManches){
                endGame = true;
                res += "Fin de la partie.\n";
                String gagnant = pointsA > pointsB ? "Joueur 1" : "Joueur 2";
                res += gagnant + " est le vainqueur.";
            }
            return res;
        }
        return "Ex-aequo. Recommencez.\n";
    }

    private boolean getWin(String a, String b){
        if(a == "cercle"){
            if(b == "triangle") return true;
            return false;
        }
        if(a == "carre"){
            if(b == "cercle") return true;
            return false;
        }
        if(a == "triangle"){
            if(b == "carre") return true;
            return false;
        }
        return false;
    }

    public boolean getEndGame(){
        return endGame;
    }

    public static void main(String[] args) {
        PFC partie = new PFC(2);
        System.out.println(partie.getResult("carre", "triangle"));
        System.out.println(partie.getResult("cercle", "triangle"));
        System.out.println(partie.getResult("cercle", "cercle"));
        System.out.println(partie.getResult("carre", "cercle"));
        System.out.println(partie.getEndGame());
    }
}
