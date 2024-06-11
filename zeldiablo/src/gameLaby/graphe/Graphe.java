package gameLaby.graphe;

import java.util.ArrayList;

/**
 * Interface représentant un graphe
 */
public interface Graphe {
    /**
     * listeNoeuds fournit la liste des nœuds d’un graphe donné.
     * @return la liste des noeuds du graphe
     */
    public ArrayList<String> listeNoeuds();

    /**
     * Qui retourne la liste des arcs partant du nœud n passé en paramètre.
     * @param noeud
     * @return
     */
    public ArrayList<Arc> suivants(String noeud);
}
