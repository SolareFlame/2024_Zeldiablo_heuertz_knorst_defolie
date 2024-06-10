package gameLaby.laby;


import static gameLaby.laby.Labyrinthe.direction;

/**
 * gere un personnage situe en x,y
 */
public class Perso extends Entite{

    int pv = 10;

    /**
     * Constructeur de l'entité
     *
     * @param dx Position selon x
     * @param dy Position selon y
     */
    public Perso(int dx, int dy) {
        super(dx, dy);
    }

    /**
     * permet de savoir si le personnage est en x,y
     *
     * @param dx position testee
     * @param dy position testee
     * @return true si le personnage est bien en (dx,dy)
     */
    public boolean etrePresent(int dx, int dy) {
        return (this.x == dx && this.y == dy);
    }

    /**
     * deplace le personnage en fonction de l'action.
     *
     * @param action une des actions possibles
     */
    public void deplacerPerso(String action) {

        // calcule case suivante
        int[] suivante = Labyrinthe.getSuivant(this.x, this.y, action);

        // on met a jour personnage
        this.x = suivante[0];
        this.y = suivante[1];
    }

    public void attaquer(Monstre monstre) {
        monstre.subirDegat();
    }

    public void subirDegat() {
        if (pv > 0) {
            pv--;
            if (pv == 0) {
                System.out.println("Loutre a succombé");
            } else {
                System.out.println("PV restant de la loutre : " + pv);
            }
        }
        else
            throw new Error("Un personnage mort a ete attaque");
    }

    public boolean estMort() {
        return pv == 0;
    }

    // ############################################
    // GETTER
    // ############################################


}
