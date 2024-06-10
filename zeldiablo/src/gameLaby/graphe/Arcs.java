package gameLaby.graphe;

import java.util.*;

public class Arcs {

    private ArrayList<Arc> arcs;

    public Arcs() {
        arcs = new ArrayList<Arc>();
    }

    /**
     * @param a arc à ajouter
     */
    public void ajouterArc(Arc a) {
        arcs.add(a);
    }

    /**
     * @return liste des arcs
     */
    public ArrayList<Arc> getArcs() {
        return arcs;
    }

    public String toString(){
        String s = "";
        for (int i = 0; i < arcs.size(); i++){
            s += arcs.get(i).getDest() + "(" + arcs.get(i).getCout() + ") ";
        }
        return s;
    }
}
