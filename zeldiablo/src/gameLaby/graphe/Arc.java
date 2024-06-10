package gameLaby.graphe;

public class Arc {

    private String dest;
    private double cout; // poids de l'arc

    /**
     * @param dest destination de l'arc
     * @param cout cout de l'arc
     */
    public Arc(String dest, double cout) {
        this.dest = dest;
        this.cout = cout;
    }

    public String getDest() {
        return dest;
    }

    public double getCout() {
        return cout;
    }

}
