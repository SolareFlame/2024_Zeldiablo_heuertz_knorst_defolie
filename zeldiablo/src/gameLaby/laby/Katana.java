package gameLaby.laby;

/**
 * Classe representant le Katana dans le labyrinthe
 */
public class Katana extends Entite {
    public Katana(int dx, int dy) {
        super(dx, dy);
    }

    /**
     * Ramasse le katana
     */
    public void ramasser() {
        System.out.println("katana ramassé");
        this.x = -1;
        this.y = -1;
    }
}
