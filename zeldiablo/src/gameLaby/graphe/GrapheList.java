package gameLaby.graphe;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class GrapheList implements Graphe {

    private ArrayList<String> noeuds; //nliste des noeuds du graphe (un noeud = un String) ;
    private ArrayList<Arcs> adjacence; // la liste des Arcs partant de chaque noeud du graphe.

    public GrapheList() {
        noeuds = new ArrayList<String>();
        adjacence = new ArrayList<Arcs>();
    }

    /**
     * Constructeur de la classe GrapheList
     * @param file prend en paramètre un fichier texte contenant la description du graphe a charger
     */
    public GrapheList(String file) {
        noeuds = new ArrayList<String>();
        adjacence = new ArrayList<Arcs>();

        try {
            BufferedReader br = new BufferedReader(new java.io.FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                String[] arcs = line.split("\n"); //arcs: noeuds + poids
                for (String arc : arcs) {
                    String[] val = arc.split("\t"); //val1: dep, val2: dest, val3: poids
                    ajouterArc(val[0], val[1], Double.parseDouble(val[2]));
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("Fichier ("+file+") non trouvé");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public ArrayList<String> listeNoeuds() {
        return noeuds;
    }

    public ArrayList<Arc> suivants(String noeud) {
        return adjacence.get(getIndice(noeud)).getArcs();
    }

    /**
     * Lorsqu’on cherche les arcs d’un noeud n, SUIVANT
     * 1. la position du noeud n dans la liste noeuds donne l’indice i de ce noeud ;
     * 2. les arcs partant du noeud n sont stock´es dans l’objet Arcs arcs au mˆeme
     * indice i de la liste adjacence
     */
    public int getIndice(String n) {
        return noeuds.indexOf(n);
    }

    /**
     * Ajout d'un arc dans le graphe
     *
     * @param depart      noeud de départ
     * @param destination noeud de destination
     * @param cout        cout de l'arc (poids)
     */
    public void ajouterArc(String depart, String destination, double cout) {

        if (!noeuds.contains(depart)) {
            noeuds.add(depart);
            adjacence.add(new Arcs());
        }
        if (!noeuds.contains(destination)) {
            noeuds.add(destination);
            adjacence.add(new Arcs());
        }

        int i = getIndice(depart);
        adjacence.get(i).ajouterArc(new Arc(destination, cout));
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < noeuds.size(); i++) {
            if (adjacence.get(i).getArcs().size() != 0) {
                s += noeuds.get(i) + " -> " + adjacence.get(i).toString() + "\n";
            }
        }
        return s;
    }


}
