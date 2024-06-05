package gameLaby.laby;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import moteurJeu.DessinJeu;
import moteurJeu.Jeu;

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
        LabyJeu labyrinthe = (LabyJeu) jeu;

        final GraphicsContext gc = canvas.getGraphicsContext2D();

        // fond
        gc.setFill(Color.DARKGREEN);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // SOL
        gc.setFill(Color.rgb(30, 30, 30));
        Labyrinthe laby = labyrinthe.getLabyrinthe();

        // MURS
        for (int j = 0; j < laby.getLength(); j++) {
            for (int i = 0; i < laby.getLengthY(); i++) {
                if (laby.getMur(j, i)) {
                    gc.setFill(Color.GRAY);
                    gc.fillRect(j * TAILLE, i * TAILLE, TAILLE, TAILLE);
                }
            }
        }

        // perso
        double persox = labyrinthe.getLabyrinthe().pj.getX();
        double persoy = labyrinthe.getLabyrinthe().pj.getY();

        gc.setFill(Color.RED);
        gc.fillOval(persox * TAILLE, persoy * TAILLE, TAILLE, TAILLE);

        // monstre
        if (labyrinthe.getLabyrinthe().monstres != null) {
            for (Monstre monstre : laby.monstres) {
                double monstrex = monstre.getX();
                double monstrey = monstre.getY();
                gc.setFill(Color.rgb(127, 0, 255));
                gc.fillOval(monstrex * TAILLE, monstrey * TAILLE, TAILLE, TAILLE);
            }
        }


        //TOP MUR
        for (int j = 0; j < laby.getLength(); j++) {
            for (int i = 0; i < laby.getLengthY(); i++) {
                if (laby.getMur(j, i)) {
                    // Pour le mur du haut
                    if (i > 0 && !laby.getMur(j, i-1)) {
                        gc.setFill(Color.BLACK);
                        gc.fillRect(j * TAILLE, (i * TAILLE)-TAILLE/2, TAILLE, TAILLE/2);
                    } else {
                        gc.setFill(Color.BLACK);
                        gc.fillRect(j * TAILLE, (i * TAILLE)-TAILLE, TAILLE, TAILLE);
                    }
                }
            }
        }
    }
}
