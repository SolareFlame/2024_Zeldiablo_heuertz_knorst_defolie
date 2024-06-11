package gameLaby.laby;

/**
 * Classe representant une plaque de pression dans le labyrinthe
 */
public class Plaque extends Entite{

    /**
     * Etat de la plaque
     */
    public boolean active = false;

    /**
     * Constructeur de la plaque
     *
     * @param dx Position selon x
     * @param dy Position selon y
     */
    public Plaque(int dx, int dy) {
        super(dx, dy);
    }

    /**
     * Change l'etat de la plaque
     */
    public void changerEtat() {
        active = !active;
    }

    /**
     * Permet de savoir si la plaque est activee
     *
     * @return true si la plaque est activee
     */
    public boolean estActivee() {
        return active;
    }
}
