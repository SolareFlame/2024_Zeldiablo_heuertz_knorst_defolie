package moteurJeu;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

public class FrameStats {

    /**
     * nombre de frames
     */
    private long frameCount;
    /**
     * moyenne des intervalles entre les frames
     */
    private double meanFrameInterval; // millis
    /**
     * afficher les statistiques de rendu de chaque frame sous forme de texte.
     */
    private final ReadOnlyStringWrapper text = new ReadOnlyStringWrapper(this, "text", "Frame count: 0 Average frame interval: N/A");

    public long getFrameCount() {
        return frameCount;
    }

    public double getMeanFrameInterval() {
        return meanFrameInterval;
    }

    /**
     * ajoute une frame
     * met à jour l'intervalle moyen de la frame et incrémente le compteur de frames.
     * Ensuite, elle met à jour le texte des statistiques.
     * @param frameDurationNanos
     */
    public void addFrame(long frameDurationNanos) {
        meanFrameInterval = (meanFrameInterval * frameCount + frameDurationNanos / 1_000_000.0) / (frameCount + 1);
        frameCount++;
        text.set(toString());
    }

    public String getText() {
        return text.get();
    }

    public ReadOnlyStringProperty textProperty() {
        return text.getReadOnlyProperty();
    }

    @Override
    public String toString() {
        return String.format("Frame count: %,d Average frame interval: %.3f milliseconds", getFrameCount(), getMeanFrameInterval());
    }
}