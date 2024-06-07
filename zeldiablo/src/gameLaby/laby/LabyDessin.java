package gameLaby.laby;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import moteurJeu.DessinJeu;
import moteurJeu.Jeu;

import java.io.File;
import java.util.ArrayList;


/**
 * Classe LabyDessin
 */
public class LabyDessin implements DessinJeu {

    public static final int TAILLE = 50;


    /**
     *
     * @param jeu le jeu
     * @param canvas canvas représentant l'état du jeu
     */
    @Override
    public void dessinerJeu(Jeu jeu, Canvas canvas) {

        /*
        -------------------- VARIABLES --------------------
         */
        final String PATH = "zeldiablo/ressources/textures/";

        final String WALL = PATH + "wall/wall_rock_midlarge.png";

        final String PJ = PATH + "pj/";
        final String MONSTRE = PATH + "monstre/";


        /*
        -------------------- SETUP --------------------
         */

        LabyJeu labyrinthe = (LabyJeu) jeu;

        final GraphicsContext gc = canvas.getGraphicsContext2D();
        Labyrinthe laby = labyrinthe.getLabyrinthe();

        /*
        -------------------- SOL --------------------
         */
        gc.setFill(Color.GREEN);
        gc.fillRect(0, 0, laby.getLength()*TAILLE, laby.getLengthY()*TAILLE);

        /*
        -------------------- MURS --------------------
         */

        File imgf_wall = new File(WALL);
        String abs_wall = imgf_wall .getAbsolutePath();
        Image img_wall = new Image(abs_wall );

        //MUR FACE

        for (int j = 0; j < laby.getLength(); j++) {
            for (int i = 0; i < laby.getLengthY(); i++) {
                if (laby.getMur(j, i)) {
                    gc.drawImage(img_wall ,j * TAILLE, i * TAILLE, TAILLE, TAILLE);
                }
            }
        }


        /*
        -------------------- PLAYER --------------------
         */

        double pj_x = labyrinthe.getLabyrinthe().pj.getX();
        double pj_y = labyrinthe.getLabyrinthe().pj.getY();

        String direction = Labyrinthe.direction;
        String etat = "";

        if(LabyJeu.attackAppuye) {
            etat = "attack";
        }





        try {
            chargerEntite(gc, pj_x, pj_y, PJ, direction, etat);
            dessinerHitbox(gc, pj_x, pj_y); // Hitbox
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        /*
        -------------------- MONSTRES --------------------
         */
        if (labyrinthe.getLabyrinthe().monstres != null) {
            for (Monstre monstre : laby.monstres) {
                double monstre_x = monstre.getX();
                double monstre_y = monstre.getY();

                String direction_monstre = "gauche"; //temp

                try {
                    gc.setFill(Color.RED);
                    gc.fillRect(monstre_x * TAILLE, monstre_y * TAILLE, TAILLE, TAILLE);


                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        /*
        -------------------- TOP --------------------
         */
        gc.setFill(Color.rgb(25, 22, 20));
        for (int j = 0; j < laby.getLength(); j++) {
            for (int i = 0; i < laby.getLengthY(); i++) {
                if (laby.getMur(j, i)) {
                    if (i > 0 && !laby.getMur(j, i-1)) {
                        gc.fillRect(j * TAILLE, (i * TAILLE)-TAILLE/2, TAILLE, TAILLE/2);
                    } else {
                        gc.fillRect(j * TAILLE, (i * TAILLE)-TAILLE, TAILLE, TAILLE);
                    }
                }
            }
        }
    }

    public void chargerEntite(GraphicsContext gc, double x, double y, String path, String dir, String etat) throws Exception {
        int delay = 2; // nb frames pour chaque image

        switch (etat) {
            case "attack": // ATTAQUE
                delay = 2;
                path += "attack/" + dir;
                chargerAnimation(gc, x, y, path, delay);
                break;
            default: // IDLE
                delay = 10;
                path += "idle/" + dir;
                chargerAnimation(gc, x, y, path, delay);
        }
    }

/*
-------------------- ANIMATION --------------------
*/

    // Ajoutez un compteur de frames global et un booléen pour l'animation
    private int frameCounter = 0;
    private boolean animationEnCours = false;

    /**
     * @param gc GraphicsContext
     * @param x position x (de l'entité)
     * @param y position y (de l'entité)
     * @param path chemin du dossier de frames
     * @param delay nombre de frames pour chaque image
     * @throws Exception
     */
    public void chargerAnimation(GraphicsContext gc, double x, double y, String path, int delay) throws Exception {
        ArrayList<Image> images = new ArrayList<>(); // liste des images

        File folder = new File(path);
        System.out.println("Chemin du dossier: " + folder.getAbsolutePath());
        File[] listOfFiles = folder.listFiles(); // liste des fichiers dans le dossier

        if (listOfFiles != null) {
            for (File file : listOfFiles) { // ajout des images dans la liste
                System.out.println("-> " + file.getName());
                if (file.isFile()) {
                    String abs_path = file.getAbsolutePath();
                    Image img = new Image(abs_path);
                    images.add(img);
                }
            }
        } else {
            System.out.println("Le dossier est vide ou n'existe pas.");
        }

        double imgsize = 1.7 * TAILLE;

        // Vérifie si des images ont été chargées
        if (images.isEmpty()) {
            throw new Exception("Aucune image");
        }

        if (!animationEnCours) {
            animationEnCours = true;
            frameCounter = 0; // Réinitialiser le compteur de frames pour démarrer l'animation
        }

        // Détermine l'index de l'image à afficher en fonction du nombre de frames écoulées
        int frameIndex = (frameCounter / delay) % images.size();

        // Affiche l'image de l'animation
        Image img = images.get(frameIndex);
        gc.drawImage(img, x * TAILLE + TAILLE / 2 - imgsize / 2, y * TAILLE + TAILLE - imgsize, imgsize, imgsize);

        // Incrémente le compteur de frames
        frameCounter++;

        // Vérifie si l'animation est terminée
        if (frameCounter / delay >= images.size()) {
            animationEnCours = false; // Animation terminée
        }
    }

    public void dessinerHitbox(GraphicsContext gc, double x, double y) {
        gc.setStroke(Color.RED);
        gc.strokeRect(x * TAILLE, y * TAILLE, TAILLE, TAILLE);
    }
}

