package gameLaby.laby;

/**
 *Classe abstraite qui représente les entités qui sont dans le labyrinthe
 */

public abstract class Entite {

    /**
     * Position de l'entité
     */
    protected int x, y;

    /**
     * Constructeur de l'entité
     *
     * @param dx Position selon x
     * @param dy Position selon y
     */
    public Entite(int dx, int dy) {
        this.x = dx;
        this.y = dy;
    }

    /**
     * Vérifie si l'entité est présente à une certaine position
     *
     * @param dx Position x testée
     * @param dy Position y testée
     * @return vrai si l'entité est en (dx, dy)
     */
    public boolean etrePresent(int dx, int dy){
        return (this.x == dx && this.y == dy);
    }

    // ############################################
    // GETTER
    // ############################################

    /**
     * @return Position x de l'entité
     */
    public int getX() {
        return this.x;
    }

    /**
     * @return Position y de l'entité
     */
    public int getY() {
        return this.y;
    }
}
