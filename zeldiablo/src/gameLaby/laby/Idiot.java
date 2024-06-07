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

        if (!laby.murs[suivante[0]][suivante[1]] && (laby.pj.x != suivante[0] || laby.pj.y != suivante[1])) {
            for (Monstre monstre2 : laby.monstres) {
                if (monstre2.x == suivante[0] && monstre2.y == suivante[1]) {
                    return;
                }
            }
            this.x = suivante[0];
            this.y = suivante[1];

            }
    }


}
