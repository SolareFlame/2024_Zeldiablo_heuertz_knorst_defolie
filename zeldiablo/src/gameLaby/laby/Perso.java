package gameLaby.laby;


import java.util.ArrayList;

import static gameLaby.laby.Labyrinthe.direction;

/**
 * gere un personnage situe en x,y
 */
public class Perso extends Entite{

    int pv = 10;
    private String arme = "baton";
    private final String[] arme_possible = {"baton", "katana"};
    boolean estAttaque = false;

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

    public void attaquer(ArrayList<Monstre> monstres, String direction) {
        int[] suivante = Labyrinthe.getSuivant(x, y, direction);

        switch (arme) {
            case "baton":
                for (Monstre monstre : monstres) {
                    if (monstre.etrePresent(suivante[0], suivante[1])) {
                        monstre.subirDegat();
                        break;
                    }
                }
                break;
            case "katana":  // attaque sur 3 case horizontalement devant le joueur
                ArrayList<Monstre> monstresTouches = new ArrayList<>();
                switch (direction) {
                    case Labyrinthe.HAUT:
                    case Labyrinthe.BAS:
                        for (Monstre monstre : monstres) {
                            if (monstre.etrePresent(suivante[0], suivante[1]) || monstre.etrePresent(suivante[0] + 1, suivante[1]) || monstre.etrePresent(suivante[0] - 1, suivante[1])) {
                                monstresTouches.add(monstre);
                            }
                        }
                        break;
                    case Labyrinthe.GAUCHE:
                    case Labyrinthe.DROITE:
                        for (Monstre monstre : monstres) {
                            if (monstre.etrePresent(suivante[0], suivante[1]) || monstre.etrePresent(suivante[0], suivante[1] + 1) || monstre.etrePresent(suivante[0], suivante[1] - 1)) {
                                monstresTouches.add(monstre);
                            }
                        }
                        break;
                    default:
                        throw new Error("Direction inconnue");
                }
                for (Monstre monstre : monstresTouches) {
                    monstre.subirDegat();
                }
        }

    }

    public void subirDegat() {
        if (pv > 0) {
            estAttaque = true;
            pv--;
            if (pv == 0) {
                mort();
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

    private void mort() {
        System.out.println("Loutre a succombé");
        MainLaby.RechargerNiveau();
    }

    public void ramasserArme(String arme) {
        boolean changer = false;
        for (String arme_possible : arme_possible) {
            if (arme.equals(arme_possible)) {
                this.arme = arme;
                changer = true;
                System.out.println("Arme ramassée : " + arme);
            }
        }
        if (!changer)
            throw new Error("Arme inconnue");
    }

    // ############################################
    // GETTER
    // ############################################


}
