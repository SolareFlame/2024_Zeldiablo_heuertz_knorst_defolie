package gameLaby.laby;

/**
 * Classe representant le monstre dans le labyrinthe
 */
public class Monstre extends Entite {

    private int pv = 3;

    /**
     * Constructeur du monstre
     *
     * @param dx Position selon x
     * @param dy Position selon y
     */
    public Monstre(int dx, int dy) {
        super(dx, dy);
    }

    public int getPv() {
        return pv;
    }

    public void subirDegat() {
        if (pv > 0)
            pv--;
        else
            throw new Error("Un monstre mort a ete attaque");
    }
}
