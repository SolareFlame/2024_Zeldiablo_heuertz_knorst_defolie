package gameLaby.laby;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import moteurJeu.DessinJeu;
import moteurJeu.Jeu;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe LabyDessin
 */
public class LabyDessin implements DessinJeu {

    public static final int TAILLE = 50;

    private Map<String, AnimationState> animationStates = new HashMap<>();

    @Override
    public void dessinerJeu(Jeu jeu, Canvas canvas) {
        final String PATH = "zeldiablo/ressources/textures/";

        final String WALL = PATH + "wall/wall_rock_grass_midlarge.png";

        final String PJ = PATH + "pj/";
        final String MONSTRE = PATH + "monstre/";

        LabyJeu labyrinthe = (LabyJeu) jeu;
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        Labyrinthe laby = labyrinthe.getLabyrinthe();

         /*
        -------- SOL --------
        */
        gc.setFill(Color.GREEN);
        gc.fillRect(0, 0, laby.getLength() * TAILLE, laby.getLengthY() * TAILLE);

        gc.setFill(Color.PURPLE);
        gc.fillRect(laby.sortie.getX(), laby.sortie.getY(), TAILLE, TAILLE);


        /*
        -------- MURS --------
        */
        File imgf_wall = new File(WALL);
        String abs_wall = imgf_wall.getAbsolutePath();
        Image img_wall = new Image(abs_wall);

        for (int j = 0; j < laby.getLength(); j++) {
            for (int i = 0; i < laby.getLengthY(); i++) {
                if (laby.getMur(j, i)) {
                    gc.drawImage(img_wall, j * TAILLE, i * TAILLE, TAILLE, TAILLE);
                }
            }
        }

        /*
        -------- ENTITES --------
         */
        double pj_x = labyrinthe.getLabyrinthe().pj.getX();
        double pj_y = labyrinthe.getLabyrinthe().pj.getY();

        if (labyrinthe.getLabyrinthe().monstres != null) {
            for (Monstre monstre : laby.monstres) {
                double monstre_x = monstre.getX();
                double monstre_y = monstre.getY();

                if (monstre_y <= pj_y) {
                    try {
                        String monstreId = "monstre_" + monstre.hashCode();
                        chargerEntite(gc, monstre_x, monstre_y, MONSTRE, "gauche", "", monstreId);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        String direction = Labyrinthe.direction;
        String etat = LabyJeu.attackAppuye ? "attack" : "idle";

        try {
            String joueurId = "joueur";
            chargerEntite(gc, pj_x, pj_y, PJ, direction, etat, joueurId);
            dessinerHitbox(gc, pj_x, pj_y);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (labyrinthe.getLabyrinthe().monstres != null) {
            for (Monstre monstre : laby.monstres) {
                double monstre_x = monstre.getX();
                double monstre_y = monstre.getY();

                if (monstre_y > pj_y) {
                    try {
                        String monstreId = "monstre_" + monstre.hashCode();
                        chargerEntite(gc, monstre_x, monstre_y, MONSTRE, "gauche", "", monstreId);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        /*
        -------- MURS TOP --------
         */


        gc.setFill(Color.rgb(25, 22, 20));
        for (int j = 0; j < laby.getLength(); j++) {
            for (int i = 0; i < laby.getLengthY(); i++) {
                if (laby.getMur(j, i)) {
                    if (i > 0 && !laby.getMur(j, i - 1)) {
                        gc.fillRect(j * TAILLE, (i * TAILLE) - TAILLE / 2, TAILLE, TAILLE / 2);
                    } else {
                        gc.fillRect(j * TAILLE, (i * TAILLE) - TAILLE, TAILLE, TAILLE);
                    }
                }
            }
        }
    }

    public void chargerEntite(GraphicsContext gc, double x, double y, String path, String dir, String etat, String entityId) throws Exception {
        int delay;

        // GERER L'ETAT DE L'ANIMATION
        AnimationState currentAnimation = animationStates.get(entityId);

        if (currentAnimation != null && (!currentAnimation.getEtat().equals(etat) || !currentAnimation.getDirection().equals(dir))) {
            animationStates.remove(entityId);
        }

        // CHOIX DE L'ANIMATION
        switch (etat) {
            case "attack":
                delay = 2;
                path += "attack/" + dir;
                chargerAnimation(gc, x, y, path, delay, entityId, etat, dir);
                break;
            default:
                delay = 10;
                path += "idle/" + dir;
                chargerAnimation(gc, x, y, path, delay, entityId, etat, dir);
        }
    }

    public void chargerAnimation(GraphicsContext gc, double x, double y, String path, int delay, String entityId, String etat, String dir) throws Exception {

        // SI L'ANIMATION N'EXISTE PAS DANS LA MAP
        if (!animationStates.containsKey(entityId)) {
            ArrayList<Image> images = new ArrayList<>();
            File folder = new File(path);
            System.out.println("Chemin du dossier: " + folder.getAbsolutePath());
            File[] listOfFiles = folder.listFiles();

            if (listOfFiles != null) {
                for (File file : listOfFiles) {
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

            if (images.isEmpty()) {
                throw new Exception("Aucune image");
            }

            AnimationState n_etat = new AnimationState(images, etat, dir);
            animationStates.put(entityId, n_etat);
        }

        // SI L'ANIMATION EXISTE DANS LA MAP
        AnimationState currentEtat = animationStates.get(entityId);
        ArrayList<Image> images = currentEtat.getImages();

        double imgsize = 1.7 * TAILLE;

        if (!currentEtat.isAnimationEnCours()) {
            currentEtat.startAnimation();
        }

        int frameIndex = (currentEtat.getFrameCounter() / delay) % images.size();

        Image img = images.get(frameIndex);
        gc.drawImage(img, x * TAILLE + TAILLE / 2 - imgsize / 2, y * TAILLE + TAILLE - imgsize, imgsize, imgsize);

        currentEtat.addFrameCounter();

        if (currentEtat.getFrameCounter() / delay >= images.size()) {
            currentEtat.stopAnimation();
        }
    }

    public void dessinerHitbox(GraphicsContext gc, double x, double y) {
        gc.setStroke(Color.RED);
        gc.strokeRect(x * TAILLE, y * TAILLE, TAILLE, TAILLE);
    }
}
