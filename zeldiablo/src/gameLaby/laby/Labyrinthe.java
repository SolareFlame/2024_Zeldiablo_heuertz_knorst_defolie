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
public class
Labyrinthe {

    /**
     * Constantes char
     */
    public static final char MUR = 'X';
    public static final char PJ = 'P';
    public static final char MONSTRE = 'M';
    public static final char VIDE = '.';

    /**
     * constantes actions possibles
     */
    public static final String HAUT = "haut";
    public static final String BAS = "bas";
    public static final String GAUCHE = "gauche";
    public static final String DROITE = "droite";

    // Ajoutez les constantes pour les directions possibles
    public static final String[] ACTIONS = {HAUT, BAS, GAUCHE, DROITE};

    /**
     * attributs du personnage et du monstre
     */
    public Perso pj;
    public ArrayList<Monstre> monstres = new ArrayList<>();

    /**
     * les murs du labyrinthe
     */
    public boolean[][] murs;

    private Random random;

    public char estDevant(String direction) {
        int posX = pj.x;
        int posY = pj.y;
        switch (direction) {
            case HAUT:
                posY --;
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
                throw new Error("action inconnue");
        }
        for(Monstre monstre : monstres) {
            if (posX == monstre.x) {
                if (posY == monstre.y)
                    return MONSTRE; // DOIT ATTAQUER LE MONSTRE
            }
        }

        if (getMur(posX, posY))
            return MUR;
        else
            return VIDE;

        /**int[] res = {x, y};
         return res;**/
    }

    public void traitement(char caseDevant, String direction) {
        switch (caseDevant) {
            case MUR:
                break;

            case VIDE:
                deplacerPerso(direction);
                break;

            case MONSTRE:
                pj.attaquer(direction);
                break;

            default:
                throw new Error("case inconnue");
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
                        break;
                    case MONSTRE:
                        // pas de mur
                        this.murs[colonne][numeroLigne] = false;
                        // ajoute Monstre
                        this.monstres.add(new Monstre(colonne, numeroLigne));
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
     * deplace le personnage en fonction de l'action.
     * gere la collision avec les murs et le monstre
     *
     * @param action une des actions possibles
     */
    public void deplacerPerso(String action) {

        // calcule case suivante
        int[] suivante = getSuivant(pj.x, pj.y, action);

        // on met a jour personnage
        this.pj.x = suivante[0];
        this.pj.y = suivante[1];

        String actions = ACTIONS[random.nextInt(ACTIONS.length)];
        deplacerMonstre(actions);
    }

    /**
     * deplace le monstre en fonction de l'action.
     * gere la collision avec les murs et le personnage
     *
     * @param action une des actions possibles
     */
    public void deplacerMonstre(String action) {

        for (Monstre monstre : monstres) {
            int[] suivante = getSuivant(monstre.x, monstre.y, action);

            if (!this.murs[suivante[0]][suivante[1]] && (this.pj.x != suivante[0] || this.pj.y != suivante[1])) {
                monstre.x = suivante[0];
                monstre.y = suivante[1];
            }
        }
    }


    /**
     *
     * @return fin du jeu
     */
    public boolean etreFini() {
        // TODO
        return false;
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
     * @param x
     * @param y
     * @return
     */
    public boolean getMur(int x, int y) {
        return this.murs[x][y];
    }
}
