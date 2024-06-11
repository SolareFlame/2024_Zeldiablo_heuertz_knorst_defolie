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

    /**
     * Deplace le monstre, à définir pour chaque monstre
     */
    public abstract void seDeplacer();

    /**
     * Fait perdre 1PV au monstre
     */
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

    /**
     * Verifie si le monstre peut se deplacer à la position x, y
     * @param x
     * @param y
     * @return true si le monstre peut se deplacer à la position x, y
     */
    public boolean AllowedToMove(int x, int y) {
        for (Porte porte : laby.portes) {
            if (porte.etrePresent(x, y) && !porte.estOuverte()) {
                return false;
            }
        }
        return !laby.getMur(x, y) && !laby.estMonstre(x, y) && !laby.sortie.etrePresent(x, y);
    }

    /**
     * Attaque le personnage
     * @param perso
     */
    public void attaquer(Perso perso) {
        perso.subirDegat();
    }

    /**
     * Retourne le nombre de PV du monstre
     * @return le nombre de PV du monstre
     */
    public int getPv() {
        return pv;
    }
}
