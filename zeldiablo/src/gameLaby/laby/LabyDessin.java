package gameLaby.laby;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import moteurJeu.DessinJeu;
import moteurJeu.Jeu;
import moteurJeu.MoteurJeu;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static javafx.scene.paint.Color.rgb;

/**
 * Classe LabyDessin
 */
public class LabyDessin implements DessinJeu {

    public static double TAILLE = 50;

    private Map<String, AnimationState> animationStates = new HashMap<>();


    final String PATH = "zeldiablo/ressources/textures/";

    final String WALL = PATH + "wall/wall_rock_grass_midlarge.png";
    final String EXIT = PATH + "ground/exit.png";
    final String KEY = PATH + "ground/key.png";
    final String ENTER = PATH + "ground/enter.png";

    final String PJ = PATH + "pj/";

    final String LOUP_IDIOT = PATH + "loup_idiot/";
    final String LOUP_MALIN = PATH + "loup_malin/";
    final String LOUP_ALPHA = PATH + "loup_alpha/";

    final LabyEffect labyEffect = new LabyEffect();



    /**
     * Méthode permettant de dessiner l'état du jeu dans le canvas
     * @param jeu    jeu a afficher dans le canvas
     * @param canvas canvas dans lequel dessiner l'etat du jeu
     */
    @Override
    public void dessinerJeu(Jeu jeu, Canvas canvas) {
        // DONNES DU JEU
        LabyJeu labyrinthe = (LabyJeu) jeu;
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        Labyrinthe laby = labyrinthe.getLabyrinthe();

        // DONNES VISUELLES
        TAILLE = (int) canvas.getWidth() / 20;

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        /*
        -------- SOL --------
        */
        // SOL
        gc.setFill(Color.rgb(20, 160, 46));
        gc.fillRect(0, 0, laby.getLength() * TAILLE, laby.getLengthY() * TAILLE);

        // CLE
        if (laby.cle != null) {
            File imgf_key = new File(KEY);
            String abs_key = imgf_key.getAbsolutePath();
            Image img_key = new Image(abs_key);

            gc.drawImage(img_key, laby.cle.getX() * TAILLE, laby.cle.getY() * TAILLE, TAILLE, TAILLE);
        }

        //PLAQUE
        if (laby.plaque != null) {
            String plaque_path = PATH + "ground/button_";
            if(laby.plaque.active) plaque_path += "active.png";
            else plaque_path += "notactive.png";

            File imgf_plaque = new File(plaque_path);
            String abs_plaque = imgf_plaque.getAbsolutePath();
            Image img_plaque = new Image(abs_plaque);

            gc.drawImage(img_plaque, laby.plaque.getX() * TAILLE, laby.plaque.getY() * TAILLE, TAILLE, TAILLE);
        }

        //SORTIE
        File imgf_exit = new File(EXIT);
        String abs_exit = imgf_exit.getAbsolutePath();
        Image img_exit = new Image(abs_exit);

        gc.drawImage(img_exit, laby.sortie.getX() * TAILLE, laby.sortie.getY() * TAILLE, TAILLE, TAILLE);

        //ENTREE
        double imgsize = 1.7 * TAILLE;

        File imgf_enter = new File(ENTER);
        String abs_enter = imgf_enter.getAbsolutePath();
        Image img_enter = new Image(abs_enter);

        gc.drawImage(img_enter, laby.entree.getX() * TAILLE + TAILLE / 2 - imgsize / 2, laby.entree.getY() * TAILLE + TAILLE - imgsize, imgsize, imgsize);

        // KATANA
        gc.setFill(Color.rgb(80, 10, 40));
        if (laby.katana != null)
            gc.fillRect(laby.katana.getX() * TAILLE, laby.katana.getY() * TAILLE, TAILLE, TAILLE);


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

        // PORTE
        for (Porte porte : laby.portes) {
            String porte_path = PATH + "door/";
            int etirement_horizontal = 1;
            int decalage_horizontal = 0;

            if(porte.type == 0) porte_path += "key/"; // 0 = key
            else porte_path += "mechanical/"; // 1 = mechanical

            if(porte.vertical) {
                porte_path += "vertical/";
                etirement_horizontal = 2;
                decalage_horizontal = (int) TAILLE;
                }
            else porte_path += "horizontal/";

            if(porte.estOuverte()) porte_path += "open.png";
            else porte_path += "close.png";

            File imgf_porte = new File(porte_path);
            String abs_porte = imgf_porte.getAbsolutePath();
            Image img_porte = new Image(abs_porte);

            gc.drawImage(img_porte, porte.getX() * TAILLE - decalage_horizontal, porte.getY() * TAILLE - TAILLE, TAILLE * etirement_horizontal, TAILLE*2);
        }


        /*
        -------- ENTITES --------
         */
        //DONNES DU JOUEUR
        double pj_x = labyrinthe.getLabyrinthe().pj.getX();
        double pj_y = labyrinthe.getLabyrinthe().pj.getY();

        String direction = Labyrinthe.direction;
        String etat = LabyJeu.attackAppuye ? "attack" : "idle";

        //MONSTRES AU DESSUS DU JOUEUR
        if (labyrinthe.getLabyrinthe().monstres != null) {
            chargerMonstres(gc, laby.monstres, pj_y, true);
        }

        //JOUEUR
        try {
            chargerEntite(gc, pj_x, pj_y, PJ, direction, etat, "joueur");
            dessinerHitbox(gc, pj_x, pj_y);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //MONSTRES EN DESSOUS DU JOUEUR
        if (labyrinthe.getLabyrinthe().monstres != null) {
            chargerMonstres(gc, labyrinthe.getLabyrinthe().monstres, pj_y, false);
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
        //numCases(gc, laby);

        if(laby.pj.estAttaque){
            try {
                labyEffect.dessinerEffet(gc, "claw", laby.pj.x, laby.pj.y, 2); // Dessine l'effet
                if(!labyEffect.animationEnCours){
                    laby.pj.estAttaque = false;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        for (Monstre monstre : laby.monstres) {
            if(monstre.estAttaque){
                try {
                    labyEffect.dessinerEffet(gc, "hit", monstre.x, monstre.y, 2); // Dessine l'effet
                    if(!labyEffect.animationEnCours){
                        monstre.estAttaque = false;
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        // vie
        LabyHUD hud = new LabyHUD(gc, laby);
        hud.chargerVie();

        if(laby.cle != null)
            if (laby.cle.estRamasse()) {
                hud.chargerIcon(KEY);
            }
    }








    /**
     * Charge une entité dans le canvas
     * @param gc GraphicsContext (canvas)
     * @param x position x de l'entité
     * @param y position y de l'entité
     * @param path chemin de l'image
     * @param dir direction de l'entité ("" si pas de direction)
     * @param etat état de l'entité (si non reconnu -> idle)
     * @param entityId identifiant de l'entité
     * @throws Exception si l'image n'est pas trouvée
     */
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

    /**
     * Charge une animation dans le canvas
     * @param gc GraphicsContext (canvas)
     * @param x position x de l'entité
     * @param y position y de l'entité
     * @param path chemin de l'image
     * @param dir direction de l'entité ("" si pas de direction)
     * @param etat état de l'entité (si non reconnu -> idle)
     * @param entityId identifiant de l'entité
     * @throws Exception si l'image n'est pas trouvée
     */
    public void chargerAnimation(GraphicsContext gc, double x, double y, String path, int delay, String entityId, String etat, String dir) throws Exception {

        // SI L'ANIMATION N'EXISTE PAS DANS LA MAP
        if (!animationStates.containsKey(entityId)) {
            ArrayList<Image> images = new ArrayList<>();
            File folder = new File(path);
            //System.out.println("Chemin du dossier: " + folder.getAbsolutePath());
            File[] listOfFiles = folder.listFiles();

            if (listOfFiles != null) {
                for (File file : listOfFiles) {
                    //System.out.println("-> " + file.getName());
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

    /**
     * Dessine la hitbox de l'entité (DEBUG)
     * @param gc GraphicsContext (canvas)
     * @param x position x de l'entité
     * @param y position y de l'entité
     */
    public void dessinerHitbox(GraphicsContext gc, double x, double y) {
        gc.setStroke(Color.RED);
        gc.strokeRect(x * TAILLE, y * TAILLE, TAILLE, TAILLE);
    }


    /**
     * Numérote les cases du labyrinthe (DEBUG)
     * @param gc GraphicsContext (canvas)
     * @param laby Labyrinthe
     */
    public void numCases(GraphicsContext gc, Labyrinthe laby) {
        gc.setFill(Color.BLACK);
        for (int i = 0; i < laby.getLength(); i++) {
            for (int j = 0; j < laby.getLengthY(); j++) {
                gc.fillText(i + "," + j, i * TAILLE + 5, j * TAILLE + 15);
                gc.setStroke(Color.BLACK);
                gc.strokeRect(i * TAILLE, j * TAILLE, TAILLE, TAILLE);
            }
        }


    }

    /**
     * Charge les monstres dans le canvas
     * @param gc GraphicsContext (canvas)
     * @param monstres liste des monstres
     * @param pj_y position y du joueur
     * @param isAbove si les monstres sont au dessus du joueur
     */
    private void chargerMonstres(GraphicsContext gc, ArrayList<Monstre> monstres, double pj_y, boolean isAbove) {
        for (Monstre monstre : monstres) {
            double monstre_x = monstre.getX();
            double monstre_y = monstre.getY();


            String texture = "";

            switch (monstre.name) {
                case "Idiot":
                    texture = LOUP_IDIOT;
                    break;
                case "Malin":
                    texture = LOUP_MALIN;
                    break;
                case "Alpha":
                    texture = LOUP_ALPHA;
                    break;
                default:
                    System.out.println(monstre.name);
                    throw new RuntimeException("Monstre non reconnu");
            }

            if ((isAbove && monstre_y <= pj_y) || (!isAbove && monstre_y > pj_y)) {
                try {
                    String monstreId = "monstre_" + monstre.hashCode();
                    chargerEntite(gc, monstre_x, monstre_y, texture, "", "", monstreId);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
