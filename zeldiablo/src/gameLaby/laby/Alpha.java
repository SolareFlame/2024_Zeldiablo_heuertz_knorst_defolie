package gameLaby.laby;

/**
 * Classe representant le monstre dans le labyrinthe
 */
public class Alpha extends Monstre {

    /**
     * Constructeur du monstre
     *
     * @param dx Position selon x
     * @param dy Position selon y
     */
    public Alpha(int dx, int dy, Labyrinthe laby) {
        super(dx, dy, laby);
        pv = 4;
        name = "Alpha";
    }

    /**
     * deplace le monstre en fonction de l'action.
     */
    public void seDeplacer(){
        Resoudre resoudre = new Resoudre();
        resoudre.updateGraphe(laby, this.x, this.y);

        int [] suivante = resoudre.resoudre(this.x, this.y, laby.pj.x, laby.pj.y);

        //vérifier si non null
        if(suivante == null){
            return;
        }

        //si la suivante est le joueur
        if (suivante[0] == laby.pj.x && suivante[1] == laby.pj.y) {
            this.attaquer(laby.pj);
        } else {
            this.x = suivante[0];
            this.y = suivante[1];
        }



    }
}
