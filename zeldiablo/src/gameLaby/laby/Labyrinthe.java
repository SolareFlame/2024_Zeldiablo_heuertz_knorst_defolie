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
                    return MONSTRE;
            }
        }

        if (getMur(posX, posY))
            return MUR;
        if (posX == sortie.x && posY == sortie.y)
            return SORTIE;
        else
            return VIDE;

        /**int[] res = {x, y};
         return res;**/
    }

    public void traitement(char caseDevant, String Direction) {
        direction = Direction;
        switch (caseDevant) {
            case MUR:
                break;

            case VIDE:
                deplacerPerso(direction);
                break;

            case MONSTRE:
                int[] suivante = getSuivant(pj.x, pj.y, direction);
                for (Monstre monstre : monstres) {
                    if (monstre.x == suivante[0] && monstre.y == suivante[1]) {
                        pj.attaquer(monstre);
                        if (monstre.getPv() == 0) {
                            monstres.remove(monstre);
                        }
                        break;
                    }
                }
                break;

            case SORTIE:
                MainLaby.chargerProchainNiveau();
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
                    case MONSTRE:
                        // pas de mur
                        this.murs[colonne][numeroLigne] = false;
                        // ajoute Monstre
                        this.monstres.add(new Monstre(colonne, numeroLigne));
                        break;
                    case SORTIE:
                        // pas de mur
                        this.murs[colonne][numeroLigne] = false;
                        // ajoute Monstre
                        this.sortie = new Sortie(colonne, numeroLigne);
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

        direction = action;

        deplacerMonstre();
    }

    public void dashDe2Cases() {
        int[] suivante = getSuivant(pj.x, pj.y, direction);
        int[] suivante2 = getSuivant(suivante[0], suivante[1], direction);

        if (!this.murs[suivante[0]][suivante[1]]) {
            for (Monstre monstre : monstres) {
                if (monstre.x == suivante[0] && monstre.y == suivante[1]) {
                    return;
                }
            }
            pj.x = suivante[0];
            pj.y = suivante[1];

            if (!this.murs[suivante2[0]][suivante2[1]]) {
                for (Monstre monstre : monstres) {
                    if (monstre.x == suivante2[0] && monstre.y == suivante2[1]) {
                        return;
                    }
                }
                pj.x = suivante2[0];
                pj.y = suivante2[1];
            }
        }
    }



    /**
     * le monstre se déplace en se rapprochant du personnage
     * gere la collision avec les murs et le personnage
     */
    public void deplacerMonstre() {
        for (Monstre monstre : monstres) {
            //pos du monstre
            int dx = monstre.x;
            int dy = monstre.y;
            //pos du perso
            int px = pj.x;
            int py = pj.y;
            //diff entre les deux
            int diffX = px - dx;
            int diffY = py - dy;
            //valeur absolue
            int absDiffX = Math.abs(diffX);
            int absDiffY = Math.abs(diffY);

            //si la diff en x est plus grande que la diff en y
            if (absDiffX > absDiffY) {
                //si la diff en x est positive
                if (diffX > 0) {
                    //si il n'y a pas de mur, de monstre ou de perso et si la case devant
                    // le monstre n'est pas la suivante du perso
                    if (!murs[dx + 1][dy] && !estMonstre(dx + 1, dy) && !pj.etrePresent(dx + 1, dy)) {
                        monstre.x++;
                    }
                } else {
                    if (!murs[dx - 1][dy] && !estMonstre(dx - 1, dy) && !pj.etrePresent(dx - 1, dy)) {
                        monstre.x--;
                    }
                }
            } else {
                if (diffY > 0) {
                    if (!murs[dx][dy + 1] && !estMonstre(dx, dy + 1) && !pj.etrePresent(dx, dy + 1)) {
                        monstre.y++;
                    }
                } else {
                    if (!murs[dx][dy - 1] && !estMonstre(dx, dy - 1) && !pj.etrePresent(dx, dy - 1)) {
                        monstre.y--;
                    }
                }
            }
        }
    }

    /*public void deplacerMonstre() {

        for (Monstre monstre : monstres) {
            String action = ACTIONS[random.nextInt(ACTIONS.length)];
            int[] suivante = getSuivant(monstre.x, monstre.y, action);

            if (!this.murs[suivante[0]][suivante[1]] && (this.pj.x != suivante[0] || this.pj.y != suivante[1])) {
                for (Monstre monstre2 : monstres) {
                    if (monstre2.x == suivante[0] && monstre2.y == suivante[1]) {
                        return;
                    }
                }
                monstre.x = suivante[0];
                monstre.y = suivante[1];
            }
        }
    }*/



    public boolean estMonstre(int x, int y) {
        for (Monstre monstre : monstres) {
            if (monstre.x == x && monstre.y == y) {
                return true;
            }
        }
        return false;
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
