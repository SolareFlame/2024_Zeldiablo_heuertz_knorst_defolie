package gameLaby.laby;

/**
 * Classe representant une cle dans le labyrinthe
 */
public class Cle extends Entite{

    /**
     * Constructeur de la cle
     *
     * @param dx Position selon x
     * @param dy Position selon y
     */
    public Cle(int dx, int dy) {
        super(dx, dy);
    }

    /**
     * Ramasse la cle
     */
    public void ramasser() {
        System.out.println("cle ramassée");
        this.x = -1;
        this.y = -1;
    }

    /**
     * Verifie si la cle est ramassée
     * @return true si la cle est ramassée
     */
    public boolean estRamasse() {
        return this.x == -1 && this.y == -1;
    }
}
