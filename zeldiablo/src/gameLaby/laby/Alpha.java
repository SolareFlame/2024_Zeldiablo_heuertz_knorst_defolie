package gameLaby.laby;

/**
 * Classe representant le monstre dans le labyrinthe
 */
public class Alpha extends Monstre {

    /**
     * Constructeur du monstre
     *
     * @param dx Position selon x
     * @param dy Position selon y
     */
    public Alpha(int dx, int dy, Labyrinthe laby) {
        super(dx, dy, laby);
        pv = 4;
        name = "alpha";
    }

    /**
     * deplace le monstre en fonction de l'action.
     */
    public void seDeplacer(){
        // TODO
    }


}
