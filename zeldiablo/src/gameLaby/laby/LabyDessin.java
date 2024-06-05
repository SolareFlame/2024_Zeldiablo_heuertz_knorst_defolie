package gameLaby.laby;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import moteurJeu.DessinJeu;
import moteurJeu.Jeu;

import java.io.File;

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

        final String GAUCHE = PATH + "pj/pj_left_large.png";
        final String DROITE = PATH + "pj/pj_right_large.png";
        final String HAUT = PATH + "pj/pj_up_large.png";
        final String DOWN = PATH + "pj/pj_down_large.png";

        final String MONSTRE_GAUCHE = PATH + "monster/monstre_left_large.png";
        final String MONSTRE_DROITE = PATH + "monster/monstre_right_large.png";
        final String MONSTRE_HAUT = PATH + "monster/monstre_up_large.png";
        final String MONSTRE_DOWN = PATH + "monster/monstre_down_large.png";

        final String WALL = PATH + "wall/wall_rock_midlarge.png";

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
        switch (direction) {
            case "haut":
                direction = HAUT;
                break;
            case "bas":
                direction = DOWN;
                break;
            case "gauche":
                direction = GAUCHE;
                break;
            case "droite":
                direction = DROITE;
                break;
        }



        try {
            chargerEntite(gc, pj_x, pj_y, direction);
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
                switch (direction_monstre) {
                    case "haut":
                        direction_monstre = MONSTRE_HAUT;
                        break;
                    case "bas":
                        direction_monstre = MONSTRE_DOWN;
                        break;
                    case "gauche":
                        direction_monstre = MONSTRE_GAUCHE;
                        break;
                    case "droite":
                        direction_monstre = MONSTRE_DROITE;
                        break;
                }



                try {
                    chargerEntite(gc, monstre_x, monstre_y, direction_monstre);
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

    public void chargerEntite(GraphicsContext gc, double x, double y, String path) throws Exception {
        double imgsize = 1.7 * TAILLE;

        File imageFile = new File(path);
        String abs_path = imageFile.getAbsolutePath();

        //carré (vide) de collision (coutour rouge)
        gc.setStroke(Color.RED);
        gc.setLineWidth(1);
        gc.strokeRect(x * TAILLE, y * TAILLE, TAILLE, TAILLE);


        //image
        Image img = new Image(abs_path);
        gc.drawImage(img,x * TAILLE + TAILLE/2 - imgsize/2, y * TAILLE + TAILLE - imgsize, imgsize, imgsize);
    }




}
