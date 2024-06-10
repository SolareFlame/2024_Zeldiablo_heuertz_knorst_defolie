package moteurJeu;

//https://github.com/zarandok/megabounce/blob/master/MainCanvas.java

//import gameLaby.laby.SoundLoader;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;

// copied from: https://gist.github.com/james-d/8327842
// and modified to use canvas drawing instead of shapes

public class MoteurJeu extends Application {

    /**
     * gestion du temps : nombre de frame par secondes et temps par iteration
     */
    private static double FPS = 100;
    private static double dureeFPS = 1000 / (FPS + 1);

    /**
     * taille par defaut
     */
    private static double WIDTH = 800;
    private static double HEIGHT = 600;

    /**
     * statistiques sur les frames
     */
    private final FrameStats frameStats = new FrameStats();

    /**
     * jeu en Cours et renderer du jeu
     */
    private static Jeu jeu = null;
    private static DessinJeu dessin = null;

    /**
     * touches appuyee entre deux frame
     */
    Clavier controle = new Clavier();

    /**
     * lancement d'un jeu
     *
     * @param jeu    jeu a lancer
     * @param dessin dessin du jeu
     */
    public static void launch(Jeu jeu, DessinJeu dessin) {
        // le jeu en cours et son afficheur
        MoteurJeu.jeu = jeu;
        MoteurJeu.dessin = dessin;

        // si le jeu existe, on lance le moteur de jeu
        if (jeu != null)
            launch();
    }

    /**
     * frame par secondes
     *
     * @param FPSSouhaitees nombre de frames par secondes souhaitees
     */
    public static void setFPS(int FPSSouhaitees) {
        FPS = FPSSouhaitees;
        dureeFPS = 1000 / (FPS + 1);
    }

    public static void setTaille(double width, double height) {
        WIDTH = width;
        HEIGHT = height;
    }


    //#################################
    // SURCHARGE Application
    //#################################

    @Override
    /**
     * creation de l'application avec juste un canvas et des statistiques
     */
    public void start(Stage primaryStage) throws Exception{
        // initialisation du canvas de dessin et du container
        final Canvas canvas = new Canvas();
        final Pane canvasContainer = new Pane(canvas);
        canvas.widthProperty().bind(canvasContainer.widthProperty());
        canvas.heightProperty().bind(canvasContainer.heightProperty());

        // affichage des stats
        final Label stats = new Label();
        stats.textProperty().bind(frameStats.textProperty());

        // ajout des statistiques en bas de la fenetre
        final BorderPane root = new BorderPane();
        root.setBottom(stats);
        VBox vb = new VBox();
        FileInputStream input = new FileInputStream("zeldiablo/ressources/textures/menu/Name.png");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);


        FileInputStream input1 = new FileInputStream("zeldiablo/ressources/textures/menu/Otter.jpg");
        Image image1 = new Image(input1);
        ImageView imageView1 = new ImageView(image1);

        StackPane p = new StackPane(imageView1,vb);

        FileInputStream input2 = new FileInputStream("zeldiablo/ressources/textures/menu/PlayButtonIcon.png");
        Image image2 = new Image(input2);
        ImageView imageView2 = new ImageView(image2);
        Button bplay = new Button("Play", imageView2);
        bplay.setMinHeight(40);
        bplay.setMinWidth(225);
        bplay.setFont(Font.font(40));
        bplay.setGraphicTextGap(30);
        Button bquit = new Button("Quit");
        bquit.setMinHeight(40);
        bquit.setMinWidth(175);
        vb.getChildren().addAll(imageView,bplay,bquit);
        vb.setSpacing(50);
        root.setCenter(p);
        vb.setAlignment(Pos.CENTER);



        /**

        // TODO Auto-generated method stub
        //Initialising path of the media file, replace this with your file path
        String path = "zeldiablo/ressources/sound/m3.mp3";

        //Instantiating Media class
        Media media = new Media(new File(path).toURI().toString());

        //Instantiating MediaPlayer class
        MediaPlayer mediaPlayer = new MediaPlayer(media);

        //by setting this property to true, the audio will be played

        MediaView mediaView = new MediaView(mediaPlayer);

        root.getChildren().add(mediaView);
        mediaPlayer.play();

        **/

        // creation de la scene
        final Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();










        // listener clavier
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                controle.appuyerTouche(event);
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                controle.relacherTouche(event);
            }
        });


        // creation du listener souris
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getClickCount() == 2) {
                            jeu.init();
                        }
                    }
                });

        bplay.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                vb.getChildren().remove(bplay);
                root.setCenter(canvasContainer);
                startAnimation(canvas);
            }
        });

        bquit.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Platform.exit();
            }
        });

    }

    /**
     * gestion de l'animation (boucle de jeu)
     *
     * @param canvas le canvas sur lequel on est synchronise
     */
    private void startAnimation(final Canvas canvas) {
        // stocke la derniere mise e jour
        final LongProperty lastUpdateTime = new SimpleLongProperty(0);

        // timer pour boucle de jeu
        final AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long timestamp) {

                // si jamais passe dans la boucle, initialise le temps
                if (lastUpdateTime.get() == 0) {
                    lastUpdateTime.set(timestamp);
                }

                // mesure le temps ecoule depuis la derniere mise a jour
                long elapsedTime = timestamp - lastUpdateTime.get();
                double dureeEnMilliSecondes = elapsedTime / 1_000_000.0;


                // si le temps ecoule depasse le necessaire pour FPS souhaite
                if (dureeEnMilliSecondes > dureeFPS) {
                    // met a jour le jeu en passant les touches appuyees
                    jeu.update(dureeEnMilliSecondes / 1_000., controle);

                    // dessine le jeu
                    dessin.dessinerJeu(jeu, canvas);

                    // ajoute la duree dans les statistiques
                    frameStats.addFrame(elapsedTime);

                    // met a jour la date de derniere mise a jour
                    lastUpdateTime.set(timestamp);
                }
            }
        };

        // lance l'animation
        timer.start();
        /** MUSIQUE A ENLEVER SI PROBLEME **/
        /**
         SoundLoader s = new SoundLoader();
         s.playfirtsound();
         **/
    }
}