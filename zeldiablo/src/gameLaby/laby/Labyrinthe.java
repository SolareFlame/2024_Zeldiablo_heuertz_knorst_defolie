package gameLaby.laby;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * classe labyrinthe. represente un labyrinthe avec
 * <ul> des murs </ul>
 * <ul> un personnage (x,y) </ul>
 * <ul> un monstre (x,y) </ul>
 */
public class Labyrinthe {

    /**
     * Constantes char
     */
    public static final char MUR = 'X';
    public static final char PJ = 'P';
    public static final char MONSTRE = 'M';
    public static final char VIDE = '.';
    public static final char SORTIE = 'S';
    public static final char IDIOT = '1';
    public static final char MALIN = '2';
    public static final char ALPHA = '3';
    public static final char PORTE_CLE = '9';
    public static final char PORTE_MECA = '8';
    public static final char CLE = 'K';
    public static final char PLAQUE = 'Q';
    public static final char KATANA = '$';

    /**
     * constantes actions possibles
     */
    public static final String HAUT = "haut";
    public static final String BAS = "bas";
    public static final String GAUCHE = "gauche";
    public static final String DROITE = "droite";

    public static String direction = GAUCHE;
    public static String etat = "idle";

    // Ajoutez les constantes pour les directions possibles
    public static final String[] ACTIONS = {HAUT, BAS, GAUCHE, DROITE};

    /**
     * attributs du personnage et du monstre
     */
    public Perso pj;
    public ArrayList<Monstre> monstres = new ArrayList<>();
    public Sortie sortie;
    public Entree entree;
    public ArrayList<Porte> portes = new ArrayList<>();
    public Cle cle;
    public Plaque plaque;
    public Katana katana;

    /**
     * les murs du labyrinthe
     */
    public boolean[][] murs;
    //public boolean[][] murs_secret;

    /**
     * attributs pour le dash
     */
    private long lastDashTime = 0;
    private static final long DASH_COOLDOWN = 3000;

    private Random random;

    public char estDevant(int posX, int posY, String direction) {
        switch (direction) {
            case HAUT:
                posY--;
                break;
            case BAS:
                posY++;
                break;
            case DROITE:
                posX++;
                break;
            case GAUCHE:
                posX--;
                break;
            default:
                System.out.println("LOG: (direction inconnue) '" + direction + "'");
                throw new Error("action inconnue");
        }

        // Vérifiez les limites
        if (posX < 0 || posX >= getLength() || posY < 0 || posY >= getLengthY()) {
            System.out.println("LOG: Hors des limites pour la position (" + posX + ", " + posY + ")");
            return 'v';
        }

        for (Monstre monstre : monstres) {
            if (posX == monstre.x && posY == monstre.y) {
                return MONSTRE;
            }
        }

        if (getMur(posX, posY)) {
            return MUR;
        }
        if (sortie.etrePresent(posX, posY)) {
            return SORTIE;
        }
        if (cle != null && cle.etrePresent(posX, posY)) {
            return CLE;
        }

        for (Porte porte : portes) {
            if (porte.etrePresent(posX, posY)) {
                    if (porte.type == 0) {  // porte à clef
                        return PORTE_CLE;
                    } else if (porte.type == 1) {  // porte à mécanisme
                        return PORTE_MECA;
                    } else {
                        throw new Error("Type de porte inconnu");
                    }
            }
        }
        if (plaque != null && plaque.etrePresent(posX, posY)) {
            return PLAQUE;
        }
        if (katana != null && katana.etrePresent(posX, posY)) {
            return KATANA;
        }
        else {
            return VIDE;
        }
    }


    public void traitement(char caseDevant, String Direction) {
        //System.out.println("LOG: caseDestination = " + caseDevant);
        direction = Direction;
        switch (caseDevant) {
            case MUR:
                break;

            case VIDE:
                pj.deplacerPerso(direction);
                deplacerMonstres();
                break;

            case MONSTRE:
                pj.attaquer(monstres, direction);
                deplacerMonstres();
                break;

            case SORTIE:
                MainLaby.chargerProchainNiveau();
                break;

            case PORTE_CLE:
            case PORTE_MECA:
                int[] suivante1 = getSuivant(pj.x, pj.y, direction);
                for (Porte porte : portes) {
                    if (porte.etrePresent(suivante1[0], suivante1[1])) {
                        if (porte.estOuverte()) {
                            pj.deplacerPerso(direction);
                            deplacerMonstres();
                        } else {
                            if (porte.type == 0) {  // porte à clef
                                if (cle.estRamasse()) {
                                    porte.ouvrir();
                                    pj.deplacerPerso(direction);
                                    deplacerMonstres();
                                }
                                else {
                                    System.out.println("Porte à clef fermée");
                                }
                            } else {
                                System.out.println("Porte à mécanisme fermée");
                            }
                        }
                    }
                }
                break;

            case CLE:
                pj.deplacerPerso(direction);
                cle.ramasser();
                deplacerMonstres();
                break;

            case PLAQUE:
                plaque.changerEtat();
                ChangerEtatPorte();
                pj.deplacerPerso(direction);
                deplacerMonstres();
                break;

            case KATANA:
                pj.deplacerPerso(direction);
                katana.ramasser();
                pj.ramasserArme("katana");
                deplacerMonstres();
                break;

            default:
                throw new Error("case inconnue");
        }
        //System.out.println("LOG: case arrivé = " + estDevant(pj.x, pj.y, direction));
    }

    public void ChangerEtatPorte() {
        for (Porte porte : portes) {
            if (porte.type == 1) {
                if (plaque.estActivee()) {
                    porte.ouvrir();
                } else {
                    porte.fermer();
                }
            }
        }
    }

    /**
     * retourne la case suivante selon une actions
     *
     * @param x      case depart
     * @param y      case depart
     * @param action action effectuee
     * @return case suivante
     */
    static int[] getSuivant(int x, int y, String action) {
        switch (action) {
            case HAUT:
                // on monte une ligne
                y--;
                break;
            case BAS:
                // on descend une ligne
                y++;
                break;
            case DROITE:
                // on augmente colonne
                x++;
                break;
            case GAUCHE:
                // on diminue colonne
                x--;
                break;
            default:
                throw new Error("action inconnue");
        }
        int[] res = {x, y};
        return res;
    }

    /**
     * charge le labyrinthe
     *
     * @param nom nom du fichier de labyrinthe
     * @return labyrinthe cree
     * @throws IOException probleme a la lecture / ouverture
     */
    public Labyrinthe(String nom) throws IOException {
        // ouvrir fichier
        FileReader fichier = new FileReader(nom);
        BufferedReader bfRead = new BufferedReader(fichier);

        int nbLignes, nbColonnes;
        // lecture nblignes
        nbLignes = Integer.parseInt(bfRead.readLine());
        // lecture nbcolonnes
        nbColonnes = Integer.parseInt(bfRead.readLine());

        // creation labyrinthe vide
        this.murs = new boolean[nbColonnes][nbLignes];
        this.pj = null;
        this.random = new Random();
        this.sortie = null;

        // lecture des cases
        String ligne = bfRead.readLine();

        // stocke les indices courants
        int numeroLigne = 0;

        // parcours le fichier
        while (ligne != null) {

            // parcours de la ligne
            for (int colonne = 0; colonne < ligne.length(); colonne++) {
                char c = ligne.charAt(colonne);
                switch (c) {
                    case MUR:
                        this.murs[colonne][numeroLigne] = true;
                        break;
                    case VIDE:
                        this.murs[colonne][numeroLigne] = false;
                        break;
                    case PJ:
                        // pas de mur
                        this.murs[colonne][numeroLigne] = false;
                        // ajoute PJ
                        this.pj = new Perso(colonne, numeroLigne);
                        this.entree = new Entree(colonne, numeroLigne);
                        break;
                    case IDIOT:
                        // pas de mur
                        this.murs[colonne][numeroLigne] = false;
                        // ajoute Monstre
                        this.monstres.add(new Idiot(colonne, numeroLigne, this));
                        break;
                    case MALIN:
                        // pas de mur
                        this.murs[colonne][numeroLigne] = false;
                        // ajoute Monstre
                        this.monstres.add(new Malin(colonne, numeroLigne, this));
                        break;
                    case ALPHA:
                        // pas de mur
                        this.murs[colonne][numeroLigne] = false;
                        // ajoute Monstre
                        this.monstres.add(new Alpha(colonne, numeroLigne, this));
                        break;
                    case SORTIE:
                        // pas de mur
                        this.murs[colonne][numeroLigne] = false;
                        // ajoute Sortie
                        this.sortie = new Sortie(colonne, numeroLigne);
                        break;
                    case '9':  // porte à clef
                        // pas de mur
                        this.murs[colonne][numeroLigne] = false;
                        // ajoute Porte
                        if (this.murs[colonne-1][numeroLigne])  // un mur à gauche est present
                            this.portes.add(new Porte(colonne, numeroLigne, 0, true));
                        else
                            this.portes.add(new Porte(colonne, numeroLigne, 0, false));
                        break;
                    case '8':  // porte à mécanisme
                        // pas de mur
                        this.murs[colonne][numeroLigne] = false;
                        // ajoute Porte
                        if (this.murs[colonne-1][numeroLigne])  // un mur à gauche est present
                            this.portes.add(new Porte(colonne, numeroLigne, 1, true));
                        else
                            this.portes.add(new Porte(colonne, numeroLigne, 1, false));
                        break;
                    case CLE:
                        // pas de mur
                        this.murs[colonne][numeroLigne] = false;
                        // ajoute Cle
                        this.cle = new Cle(colonne, numeroLigne);
                        break;
                    case PLAQUE:
                        // pas de mur
                        this.murs[colonne][numeroLigne] = false;
                        // ajoute Plaque
                        this.plaque = new Plaque(colonne, numeroLigne);
                        break;
                    case KATANA:
                        // pas de mur
                        this.murs[colonne][numeroLigne] = false;
                        // ajoute Katana
                        this.katana = new Katana(colonne, numeroLigne);
                        break;
                    default:
                        throw new Error("caractere inconnu " + c);
                }
            }

            // lecture
            ligne = bfRead.readLine();
            numeroLigne++;
        }

        // ferme fichier
        bfRead.close();

    }

    /**
     * le personnage se deplace de 2 cases
     * gere la collision avec les murs et les monstres
     */
    public void dashDe2Cases() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastDashTime >= DASH_COOLDOWN) {
            int[] suivante = getSuivant(pj.x, pj.y, direction);
            int[] suivante2 = getSuivant(suivante[0], suivante[1], direction);

            // si la case suivante n'est pas un mur et qu'il n'y a pas de monstre
            if (!this.murs[suivante[0]][suivante[1]] && !estMonstre(suivante[0], suivante[1])) {
                pj.x = suivante[0];
                pj.y = suivante[1];

                // si la case suivante de la case suivante n'est pas un mur et qu'il n'y a pas de monstre
                if (!this.murs[suivante2[0]][suivante2[1]] && !estMonstre(suivante2[0], suivante2[1])) {
                    pj.x = suivante2[0];
                    pj.y = suivante2[1];
                }
            }
            deplacerMonstres();
            lastDashTime = currentTime;
        }
    }

    /**
     * le monstre se déplace en se rapprochant du personnage
     * gere la collision avec les murs et le personnage
     */
    public void deplacerMonstres() {
        for (Monstre monstre : monstres) {
            monstre.seDeplacer();
        }
    }

    public boolean estMonstre(int x, int y) {
        for (Monstre monstre : monstres) {
            if (monstre.x == x && monstre.y == y) {
                return true;
            }
        }
        return false;
    }


    /**
     * @return fin du jeu
     */
    public boolean etreFini() {
        // TODO
        return false;
    }



    public char getCase(int x, int y) {
        if (pj.x == x && pj.y == y) {
            return PJ;
        }
        for (Monstre monstre : monstres) {
            if (monstre.x == x && monstre.y == y) {
                return MONSTRE;
            }
        }
        if (sortie.x == x && sortie.y == y) {
            return SORTIE;
        }
        if (entree.x == x && entree.y == y) {
            return IDIOT;
        }
        if (murs[x][y]) {
            return MUR;
        }
        return VIDE;
    }


    // ##################################
    // GETTER
    // ##################################

    /**
     * return taille selon Y
     *
     * @return
     */
    public int getLengthY() {
        return murs[0].length;
    }

    /**
     * return taille selon X
     *
     * @return
     */
    public int getLength() {
        return murs.length;
    }

    /**
     * return mur en (i,j)
     *
     * @param x
     * @param y
     * @return
     */
    public boolean getMur(int x, int y) {
        return this.murs[x][y];
    }

}
