package gameLaby.laby;

/**
 * Classe representant le monstre dans le labyrinthe
 */
public class Malin extends Monstre {

    /**
     * Constructeur du monstre
     *
     * @param dx Position selon x
     * @param dy Position selon y
     */
    public Malin(int dx, int dy, Labyrinthe laby) {
        super(dx, dy, laby);
        pv = 3;
        name = "Malin";
    }

    /**
     * deplace le monstre en fonction de l'action.
     *
     */
    public void seDeplacer(){

        //pos du perso
        int px = laby.pj.x;
        int py = laby.pj.y;

        //pos du monstre
        int dx = this.x;
        int dy = this.y;

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
                //s'il n'y a pas de mur, de monstre ou de perso et si la case devant
                // le monstre n'est pas la suivante du perso
                if (AllowedToMove(dx + 1, dy)) {
                    if (laby.pj.etrePresent(dx + 1, dy))
                        this.attaquer(laby.pj);
                    else
                        this.x ++; // déplacement à droite
                }

            } else {
                if (AllowedToMove(dx - 1, dy)) {
                    if (laby.pj.etrePresent(dx - 1, dy))
                        this.attaquer(laby.pj);
                    else
                        this.x --; // déplacement à gauche
                }
            }
        } else {
            if (diffY > 0) {
                if (AllowedToMove(dx, dy + 1)) {
                    if (laby.pj.etrePresent(dx, dy + 1))
                        this.attaquer(laby.pj);
                    else
                        this.y ++; // déplacement en bas
                }
            } else {
                if (AllowedToMove(dx, dy - 1)) {
                    if (laby.pj.etrePresent(dx, dy - 1))
                        this.attaquer(laby.pj);
                    else
                        this.y --; // déplacement en haut
                }
            }
        }
    }

}
