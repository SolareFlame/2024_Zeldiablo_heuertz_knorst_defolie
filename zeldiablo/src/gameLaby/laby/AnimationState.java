package gameLaby.laby;

import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * Classe représentant l'état d'une animation
 */
class AnimationState {
    private int frameCounter;
    private boolean animationEnCours;
    private ArrayList<Image> images;
    private String etat;
    private String direction;

    /**
     * Constructeur d'un état d'animation
     *
     * @param images liste des images de l'animation
     * @param etat état de l'animation
     * @param direction direction de l'animation
     */
    public AnimationState(ArrayList<Image> images, String etat, String direction) {
        this.frameCounter = 0;
        this.animationEnCours = false;
        this.images = images;
        this.etat = etat;
        this.direction = direction;
    }

    /**
     * Retourne le compteur de frames
     * @return le compteur de frames
     */
    public int getFrameCounter() {
        return frameCounter;
    }

    /**
     * Incrémente le compteur de frames
     */
    public void addFrameCounter() {
        frameCounter++;
    }

    /**
     * Retourne si l'animation est en cours
     * @return si l'animation est en cours
     */
    public boolean isAnimationEnCours() {
        return animationEnCours;
    }

    /**
     * Démarre l'animation
     * met l'attribut animationEnCours à true et remet le compteur de frames à 0
     */
    public void startAnimation() {
        this.animationEnCours = true;
        this.frameCounter = 0;
    }

    /**
     * Arrête l'animation
     * met l'attribut animationEnCours à false
     */
    public void stopAnimation() {
        this.animationEnCours = false;
    }

    /**
     * Retourne la liste des images de l'animation
     * @return liste des images de l'animation
     */
    public ArrayList<Image> getImages() {
        return images;
    }

    /**
     * Retourne l'état de l'animation
     * @return état de l'animation
     */
    public String getEtat() {
        return etat;
    }

    /**
     * Retourne la direction de l'animation
     * @return direction de l'animation
     */
    public String getDirection() {
        return direction;
    }
}
