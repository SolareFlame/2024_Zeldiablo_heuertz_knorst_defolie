package gameLaby.laby;

/**
 * Classe representant une porte dans le labyrinthe
 */
public class Porte extends Entite{
    /**
     * Etat de la porte (ouverte ou fermée)
     */
    private boolean ouvert = false;
    /**
     * Type de porte (0 = porte à clef, 1 = porte à mécanisme)
     */
    public int type;
    /**
     * Orientation de la porte (true = verticale, false = horizontale)
     */
    public boolean vertical;

    /**
     * Constructeur de la porte
     *
     * @param dx Position selon x
     * @param dy Position selon y
     * @param typePorte Type de porte
     * @param verti Orientation de la porte
     */
    public Porte(int dx, int dy, int typePorte, boolean verti) {
        super(dx, dy);
        this.type = typePorte;
        this.vertical = verti;
    }

    /**
     * Ouvre la porte
     */
    public void ouvrir() {
        System.out.println(getType() +  " ouverte");
        ouvert = true;
    }

    /**
     * Ferme la porte
     */
    public void fermer() {
        ouvert = false;
        System.out.println(getType() +  " fermée");
    }

    /**
     * Permet de savoir si la porte est ouverte
     *
     * @return true si la porte est ouverte
     */
    public boolean estOuverte() {
        return ouvert;
    }

    /**
     * Permet de connaitre le type de porte
     * @return le type de porte
     */
    public String getType() {
        if (type == 0) {
            return "porte à clef";
        } if (type == 1) {
            return "porte à mécanisme";
        }
        else {
            throw new Error("Type de porte inconnu");
        }
    }


}
