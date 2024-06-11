package gameLaby.laby;

import java.util.Random;

/**
 * Classe representant le monstre dans le labyrinthe
 */
public class Idiot extends Monstre {

    private Random random = new Random();

    /**
     * Constructeur du monstre
     *
     * @param dx Position selon x
     * @param dy Position selon y
     */
    public Idiot(int dx, int dy, Labyrinthe lab) {
        super(dx, dy, lab);
        pv = 2;
        name = "Idiot";
    }

    /**
     * deplace le monstre en fonction de l'action.
     *
     */
    public void seDeplacer() {

        String action = this.laby.ACTIONS[random.nextInt(laby.ACTIONS.length)];
        int[] suivante = laby.getSuivant(this.x, this.y, action);

        if (AllowedToMove(suivante[0], suivante[1])) {
            if (laby.pj.etrePresent(suivante[0], suivante[1])) {
                attaquer(laby.pj);
            } else {
                this.x = suivante[0];
                this.y = suivante[1];
            }
        }
    }

}
