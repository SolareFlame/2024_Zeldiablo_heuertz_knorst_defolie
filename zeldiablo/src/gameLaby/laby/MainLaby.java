package gameLaby.laby;

import moteurJeu.MoteurJeu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MainLaby {

    /** Niveau actuel ou niveau chargé au depart si modifié */
    static int i = 7;

    static int nbrNiveaux = 0;
    static String chemin = "zeldiablo/labySimple/laby" + i + ".txt";
    static LabyJeu jeu;

    /**
     * Recharge le niveau actuel
     */
    public static void RechargerNiveau() {
        try {
            chemin = "zeldiablo/labySimple/laby" + i + ".txt";
            jeu = new LabyJeu(chemin);
            MoteurJeu.setTaille( jeu.getLabyrinthe().getLength()*LabyDessin.TAILLE,  jeu.getLabyrinthe().getLengthY()*LabyDessin.TAILLE);
            LabyDessin dessin = new LabyDessin();
            MoteurJeu.launch(jeu, dessin);
        } catch (IllegalStateException e1){
            System.out.println("Rechargement du niveau");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Charge le prochain niveau
     */
    public static void chargerProchainNiveau() {
        try {
            if (i == nbrNiveaux - 1) {
                i = 0;
                Labyrinthe.pj.levelup();
            } else {
                i++;
            }

            chemin = "zeldiablo/labySimple/laby" + i + ".txt";
            jeu = new LabyJeu(chemin);
            MoteurJeu.setTaille(jeu.getLabyrinthe().getLength() * LabyDessin.TAILLE, jeu.getLabyrinthe().getLengthY() * LabyDessin.TAILLE);
            LabyDessin dessin = new LabyDessin();
            MoteurJeu.launch(jeu, dessin);
        } catch (FileNotFoundException e) {
            System.out.println("Fin du jeu");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int CalculerNbrNiveaux() {
        File dossier = new File("zeldiablo/labySimple");
        File[] files = dossier.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith("laby") && name.endsWith(".txt");
            }
        });
        int nbrNiveaux = files != null ? files.length : 0;
        return nbrNiveaux;
    }

    public static void main(String[] args) {
        /**
        Media pick = new Media("file:// zeldiablo/ressources/sound/m1.mp3"); //throws here
        MediaPlayer player = new MediaPlayer(pick);
        player.play();
        **/
        MoteurJeu.setFPS(60);
        nbrNiveaux = CalculerNbrNiveaux();
        RechargerNiveau();
    }
}
