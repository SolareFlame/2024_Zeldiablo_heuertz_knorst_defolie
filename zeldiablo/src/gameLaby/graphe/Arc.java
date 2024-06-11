package gameLaby.graphe;

/**
 * Classe représentant un arc dans un graphe
 */
public class Arc {

    /**
     * Destination de l'arc
     */
    private String dest;
    /**
     * Poids de l'arc
     */
    private double cout; // poids de l'arc

    /**
     * @param dest destination de l'arc
     * @param cout cout de l'arc
     */
    public Arc(String dest, double cout) {
        this.dest = dest;
        this.cout = cout;
    }

    /**
     * @return destination de l'arc
     */
    public String getDest() {
        return dest;
    }

    /**
     * @return cout de l'arc
     */
    public double getCout() {
        return cout;
    }

}
