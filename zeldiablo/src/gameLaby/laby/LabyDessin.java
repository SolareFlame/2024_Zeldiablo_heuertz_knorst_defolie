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

    public static final String PATH = "zeldiablo/textures/";

    public static final String GAUCHE = PATH + "pj_left_large.png";
    public static final String DROITE = PATH + "pj_right_large.png";
    public static final String HAUT = PATH + "pj_up_large.png";
    public static final String DOWN = PATH + "pj_down_large.png";






    /**
     *
     * @param jeu le jeu
     * @param canvas canvas représentant l'état du jeu
     */
    @Override
    public void dessinerJeu(Jeu jeu, Canvas canvas) {
        LabyJeu labyrinthe = (LabyJeu) jeu;

        final GraphicsContext gc = canvas.getGraphicsContext2D();

        /*
        -------------------- SOL --------------------
         */
        gc.setFill(Color.DARKGREEN);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        /*
        -------------------- MURS --------------------
         */

        Labyrinthe laby = labyrinthe.getLabyrinthe();

        //MUR FACE
        gc.setFill(Color.GRAY);
        for (int j = 0; j < laby.getLength(); j++) {
            for (int i = 0; i < laby.getLengthY(); i++) {
                if (laby.getMur(j, i)) {
                    gc.fillRect(j * TAILLE, i * TAILLE, TAILLE, TAILLE);
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
            chargerImage(gc, pj_x, pj_y, direction);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }




        /*
        -------------------- MONSTRES --------------------
         */
        if (labyrinthe.getLabyrinthe().monstres != null) {
            for (Monstre monstre : laby.monstres) {
                double monstrex = monstre.getX();
                double monstrey = monstre.getY();
                gc.setFill(Color.rgb(127, 0, 255));
                gc.fillOval(monstrex * TAILLE, monstrey * TAILLE, TAILLE, TAILLE);
            }
        }







        /*
        -------------------- TOP --------------------
         */
        gc.setFill(Color.BLACK);
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

    public void chargerImage(GraphicsContext gc, double x, double y, String path) throws Exception {
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
