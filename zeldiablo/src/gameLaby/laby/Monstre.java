package gameLaby.laby;

import java.util.Locale;

/**
 * Classe representant le monstre dans le labyrinthe
 */
public abstract class Monstre extends Entite implements IA{

    protected int pv;
    protected Labyrinthe laby;
    protected String name;

    // LABYRINTHE EN PARAMETRE CONSTRUCTEUR = JUSTE UNE REFERENCE MAIS CONNAISSANCE ENTIERETE LABYRINTHE
    /**
     * Constructeur du monstre
     *
     * @param dx Position selon x
     * @param dy Position selon y
     */
    public Monstre(int dx, int dy, Labyrinthe lab) {
        super(dx, dy);
        this.laby = lab;
    }

    public abstract void seDeplacer();

    public void subirDegat() {
        if (pv > 0) {
            pv--;
            if (pv == 0) {
                System.out.println("Monstre " + name + " éliminé");
                laby.monstres.remove(this);
            } else {
                System.out.println("PV restant au monstre " + name.toLowerCase() + " : " + this.getPv());
            }
        } else
            throw new Error("Un monstre mort a ete attaque");
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
