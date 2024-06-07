package gameLaby.laby;

import javafx.scene.image.Image;

import java.util.ArrayList;

class AnimationState {
    private int frameCounter;
    private boolean animationEnCours;
    private ArrayList<Image> images;
    private String etat;
    private String direction;

    public AnimationState(ArrayList<Image> images, String etat, String direction) {
        this.frameCounter = 0;
        this.animationEnCours = false;
        this.images = images;
        this.etat = etat;
        this.direction = direction;
    }

    public int getFrameCounter() {
        return frameCounter;
    }

    public void addFrameCounter() {
        frameCounter++;
    }

    public boolean isAnimationEnCours() {
        return animationEnCours;
    }

    public void startAnimation() {
        this.animationEnCours = true;
        this.frameCounter = 0;
    }

    public void stopAnimation() {
        this.animationEnCours = false;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public String getEtat() {
        return etat;
    }

    public String getDirection() {
        return direction;
    }
}
