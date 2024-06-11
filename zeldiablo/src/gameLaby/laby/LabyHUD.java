package gameLaby.laby;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.io.File;

import static javafx.scene.paint.Color.rgb;

public class LabyHUD {
    double size_x;
    double size_y;

    Labyrinthe laby;
    GraphicsContext gc;

    int nb_icon = 0;

    public LabyHUD(GraphicsContext gc, Labyrinthe laby) {
        size_x = gc.getCanvas().getWidth();
        size_y = gc.getCanvas().getHeight();
        this.gc = gc;
        this.laby = laby;

    }


    public void barreVie(double healthPercentage, double x, double y, double maxWidth, double height) {
        gc.setFill(rgb(255, 0, 0, 0.5));
        gc.fillRect(x, y, maxWidth, height);

        double healthWidth = maxWidth * healthPercentage;

        gc.setFill(Color.RED);
        gc.fillRect(x, y, healthWidth, height);
    }

    public void chargerVie() {
        double maxWidth = 200;
        double height = 20;
        double healthPercentage = (double) laby.pj.pv / 10;

        //avant la barre de vie
        gc.setFill(Color.WHITE);
        gc.fillText("Vie:", 5, 20);


        barreVie(healthPercentage, 30, 5, maxWidth, height);
    }

    public void chargerIcon (String path) {
        File imgf_icon = new File(path);
        String abs_icon = imgf_icon.getAbsolutePath();
        Image img_icon= new Image(abs_icon);

        double size_x = gc.getCanvas().getWidth();
        double size_y = gc.getCanvas().getHeight();

        //les icones en haut à droite par rapport au canvas
        double x = size_x - 50 - nb_icon * 50;
        double y = 5;

        gc.drawImage(img_icon, x, y, 50, 50);

    }
}
