package gameLaby.laby;

import moteurJeu.Clavier;
import moteurJeu.Jeu;

import java.io.IOException;

/**
 * Classe qui permet de faire fonctionner le jeu dans le labyrinthe
 */
public class LabyJeu implements Jeu {
    private final Labyrinthe labyrinthe;
    private boolean hautAppuye = false;
    private boolean basAppuye = false;
    private boolean gaucheAppuye = false;
    private boolean droiteAppuye = false;


    /**
     * Constructeur de la classe LabyJeu.
     *
     * @param nom Le nom du fichier contenant la configuration du labyrinthe.
     * @throws IOException Si une erreur de lecture du fichier se produit.
     */
    public LabyJeu(String nom) throws IOException {
        this.labyrinthe = new Labyrinthe(nom);
    }

    /**
     * Methode qui permet de mettre a jour le jeu
     * @param deltaTime temps ecoule depuis la derniere mise a jour
     * @param clavier objet contenant l'état du clavier'
     */
    @Override
    public void update(double deltaTime, Clavier clavier) {
        if (clavier.haut && !hautAppuye) {
            labyrinthe.traitement(labyrinthe.estDevant(Labyrinthe.HAUT), Labyrinthe.HAUT);
            hautAppuye = true;
        }
        if (clavier.bas && !basAppuye) {
            labyrinthe.traitement(labyrinthe.estDevant(Labyrinthe.BAS), Labyrinthe.BAS);
            basAppuye = true;
        }
        if (clavier.gauche && !gaucheAppuye) {
            labyrinthe.traitement(labyrinthe.estDevant(Labyrinthe.GAUCHE), Labyrinthe.GAUCHE);
            gaucheAppuye = true;
        }
        if (clavier.droite && !droiteAppuye) {
            labyrinthe.traitement(labyrinthe.estDevant(Labyrinthe.DROITE), Labyrinthe.DROITE);
            droiteAppuye = true;
        }
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
