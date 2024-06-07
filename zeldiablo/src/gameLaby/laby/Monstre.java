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

    public void subirDegat() {
        if (pv > 0)
            pv--;
        else
            throw new Error("Un monstre mort a ete attaque");
    }

    /**
     * deplace le monstre en fonction de l'action.
     *
     * @param action une des actions possibles
     */
    public void deplacerMonstre(String action) {

        // calcule case suivante
        int[] suivante = Labyrinthe.getSuivant(this.x, this.y, action);

        // on met a jour personnage
        this.x = suivante[0];
        this.y = suivante[1];
    }

    /*public void deplacerMonstre() {

        for (Monstre monstre : monstres) {
            String action = ACTIONS[random.nextInt(ACTIONS.length)];
            int[] suivante = getSuivant(monstre.x, monstre.y, action);

            if (!this.murs[suivante[0]][suivante[1]] && (this.pj.x != suivante[0] || this.pj.y != suivante[1])) {
                for (Monstre monstre2 : monstres) {
                    if (monstre2.x == suivante[0] && monstre2.y == suivante[1]) {
                        return;
                    }
                }
                monstre.x = suivante[0];
                monstre.y = suivante[1];
            }
        }
    }*/

    public void attaquer(Perso perso) {
        perso.subirDegat();
    }



    public int getPv() {
        return pv;
    }
}
