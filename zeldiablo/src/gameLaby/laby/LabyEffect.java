package gameLaby.laby;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.File;
import java.util.ArrayList;

public class LabyEffect {

    final String PATH = "zeldiablo/ressources/effects/";

    public void dessinerEffet(GraphicsContext gc, String effet, double x, double y, int delay) throws Exception {
        switch (effet) {
            case "claw":
                chargerEffet(gc, x, y, PATH + "claw", delay);
                break;
            case "hit":
                chargerEffet(gc, x, y, PATH + "hit", delay);
                break;
            default:
                System.out.println("Effet inconnu : " + effet);
        }
    }

    boolean animationEnCours = false;
    int frameCounter = 0;

    /**
     * Charge une animation dans le canvas
     * @param x        position x de l'entité
     * @param y        position y de l'entité
     * @param path     chemin de l'image
     * @param delay    délai entre chaque image
     * @throws Exception si l'image n'est pas trouvée
     */
    public void chargerEffet(GraphicsContext gc, double x, double y, String path, int delay) throws Exception {
        ArrayList<Image> images = new ArrayList<>(); // liste des images

        File folder = new File(path);
        File[] listOfFiles = folder.listFiles(); // liste des fichiers dans le dossier

        if (listOfFiles != null) {
            for (File file : listOfFiles) { // ajout des images dans la liste
                if (file.isFile()) {
                    String abs_path = file.getAbsolutePath();
                    Image img = new Image(abs_path);
                    images.add(img);
                }
            }
        } else {
            System.out.println("Le dossier est vide ou n'existe pas.");
        }

        double imgsize = 1.7 * LabyDessin.TAILLE;

        // Vérifie si des images ont été chargées
        if (images.isEmpty()) {
            throw new Exception("Aucune image");
        }

        if (!animationEnCours) {
            animationEnCours = true; // Démarrer l'animation
            frameCounter = 0; // Réinitialiser le compteur de frames pour démarrer l'animation
        }

        // Détermine l'index de l'image à afficher en fonction du nombre de frames écoulées
        int frameIndex = (frameCounter / delay) % images.size();

        // Affiche l'image de l'animation
        Image img = images.get(frameIndex);
        gc.drawImage(img, x * LabyDessin.TAILLE + LabyDessin.TAILLE / 2 - imgsize / 2, y * LabyDessin.TAILLE + LabyDessin.TAILLE - imgsize, imgsize, imgsize);

        // Incrémente le compteur de frames
        frameCounter++;

        // Vérifie si l'animation est terminée
        if (frameCounter / delay >= images.size()) {
            animationEnCours = false; // Animation terminée
        }
    }
}
