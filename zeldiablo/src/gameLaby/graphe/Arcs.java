package gameLaby.graphe;

import java.util.*;

/**
 * Classe représentant une liste d'arcs
 */
public class Arcs {

    /**
     * Liste des arcs
     */
    private ArrayList<Arc> arcs;

    /**
     * Constructeur
     */
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
