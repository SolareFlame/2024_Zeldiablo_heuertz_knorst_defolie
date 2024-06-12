package gameLaby.laby;

import moteurJeu.Clavier;
import moteurJeu.Jeu;

import java.io.IOException;

/**
 * Classe qui permet de faire fonctionner le jeu dans le labyrinthe
 */
public class LabyJeu implements Jeu {
    private Labyrinthe labyrinthe = null;
    public static boolean hautAppuye = false;
    public static boolean basAppuye = false;
    public static boolean gaucheAppuye = false;
    public static boolean droiteAppuye = false;

    public static boolean dashAppuye = false;
    public static boolean attackAppuye = false;


    /**
     * Constructeur de la classe LabyJeu.
     *
     * @param nom Le nom du fichier contenant la configuration du labyrinthe.
     * @throws IOException Si une erreur de lecture du fichier se produit.
     */
    public LabyJeu(String nom) throws IOException {
        if (Labyrinthe.pj == null) {
            labyrinthe = new Labyrinthe(nom, new Perso(0, 0));
        } else {
            labyrinthe = new Labyrinthe(nom, Labyrinthe.pj);
        }
    }

    /**
     * Methode qui permet de mettre a jour le jeu
     * @param deltaTime temps ecoule depuis la derniere mise a jour
     * @param clavier objet contenant l'état du clavier'
     */
    @Override
    public void update(double deltaTime, Clavier clavier) {
        int posX = labyrinthe.pj.x;
        int posY = labyrinthe.pj.y;


        if(clavier.attack && !attackAppuye) {
            attackAppuye = true;
        }
        if(clavier.dash && !dashAppuye) {
            labyrinthe.dashDe2Cases();
            dashAppuye = true;
        }
        if (clavier.haut && !hautAppuye) {
            labyrinthe.traitement(labyrinthe.estDevant(posX, posY, Labyrinthe.HAUT), Labyrinthe.HAUT);
            hautAppuye = true;
        }
        if (clavier.bas && !basAppuye) {
            labyrinthe.traitement(labyrinthe.estDevant(posX, posY, Labyrinthe.BAS), Labyrinthe.BAS);
            basAppuye = true;
        }
        if (clavier.gauche && !gaucheAppuye) {
            labyrinthe.traitement(labyrinthe.estDevant(posX, posY, Labyrinthe.GAUCHE), Labyrinthe.GAUCHE);
            gaucheAppuye = true;
        }
        if (clavier.droite && !droiteAppuye) {
            labyrinthe.traitement(labyrinthe.estDevant(posX, posY, Labyrinthe.DROITE), Labyrinthe.DROITE);
            droiteAppuye = true;
        }


        // FALSE
        if (!clavier.droite) {
            droiteAppuye = false;
        }
        if(!clavier.gauche){
            gaucheAppuye = false;
        }
        if(!clavier.haut){
            hautAppuye = false;
        }
        if(!clavier.bas){
            basAppuye = false;
        }
        if(!clavier.dash){
            dashAppuye = false;
        }
        if(!clavier.attack) {
            attackAppuye = false;
        }
    }

    @Override
    public void init() {
        //pas besoin
    }

    @Override
    public boolean etreFini() {
        return this.labyrinthe.etreFini();
    }

    /**
     * @return Retourne le labyrinthe
     */
    public Labyrinthe getLabyrinthe() {
        return this.labyrinthe;
    }
}
