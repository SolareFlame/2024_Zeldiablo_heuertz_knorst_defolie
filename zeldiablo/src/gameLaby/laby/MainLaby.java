package gameLaby.laby;

import moteurJeu.MoteurJeu;

public class MainLaby {

    static int i = 0;
    static String chemin = "zeldiablo/labySimple/laby" + i + ".txt";
    static LabyJeu jeu;

    public static void RechargerNiveau() {
        try {
            chemin = "zeldiablo/labySimple/laby" + i + ".txt";
            jeu = new LabyJeu(chemin);
            MoteurJeu.setTaille( jeu.getLabyrinthe().getLength()*LabyDessin.TAILLE,  jeu.getLabyrinthe().getLengthY()*LabyDessin.TAILLE);
            LabyDessin dessin = new LabyDessin();
            MoteurJeu.launch(jeu, dessin);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void chargerProchainNiveau() {
        try {
            i++;
            chemin = "zeldiablo/labySimple/laby" + i + ".txt";
            jeu = new LabyJeu(chemin);
            MoteurJeu.setTaille( jeu.getLabyrinthe().getLength()*LabyDessin.TAILLE,  jeu.getLabyrinthe().getLengthY()*LabyDessin.TAILLE);
            LabyDessin dessin = new LabyDessin();
            MoteurJeu.launch(jeu, dessin);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MoteurJeu.setFPS(60);
        RechargerNiveau();
    }
}
