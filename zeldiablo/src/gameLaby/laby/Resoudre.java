package gameLaby.laby;

import gameLaby.graphe.Arc;
import gameLaby.graphe.Graphe;
import gameLaby.graphe.GrapheList;
import gameLaby.graphe.Valeur;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

/**
 * Classe qui permet de résoudre un labyrinthe
 */
public class Resoudre {

    /**
     * Graphe du labyrinthe
     */
    private GrapheList g;


    /**
     * Permet de mettre à jour le graphe du labyrinthe
     */
    public void updateGraphe(Labyrinthe laby, int x, int y) {
        g = new GrapheList();

        for (int i = 1; i < laby.getLength() - 1; i++) {
            for (int j = 1; j < laby.getLengthY() - 1; j++) {
                String noeud = "[" + i + "," + j + "]";

                char type = laby.getCase(i, j);

                // vérifier si la case est valide (vide) ou que c'est lui-même
                if (type != Labyrinthe.VIDE && (i != x || j != y)) {
                    //System.out.println("Case invalide : " + noeud + " (" + type + ")");
                    continue;
                }

                // vérifier les directions possibles
                char up = laby.estDevant(i, j, Labyrinthe.HAUT);
                char down = laby.estDevant(i, j, Labyrinthe.BAS);
                char left = laby.estDevant(i, j, Labyrinthe.GAUCHE);
                char right = laby.estDevant(i, j, Labyrinthe.DROITE);

                /*
                System.out.println("LOG : Prochaines directions pour " + noeud + " : \n" +
                        "up : " + up + "\n" +
                        "down : " + down + "\n" +
                        "left : " + left + "\n" +
                        "right : " + right);

                 */

                // ajouter les arcs
                if (up == Labyrinthe.VIDE) {
                    g.ajouterArc(noeud, "[" + i + "," + (j - 1) + "]", 1);
                    //System.out.println("Arc ajouté (UP) : " + noeud + " -> " + "[" + i + "," + (j - 1) + "]");
                } else {
                    //System.out.println("Arc non ajouté (UP) : " + noeud + " -> " + "[" + i + "," + (j - 1) + "]");
                }

                if (down == Labyrinthe.VIDE) {
                    g.ajouterArc(noeud, "[" + i + "," + (j + 1) + "]", 1);
                    //System.out.println("Arc ajouté (DOWN) : " + noeud + " -> " + "[" + i + "," + (j + 1) + "]");
                } else {
                    //System.out.println("Arc non ajouté (DOWN) : " + noeud + " -> " + "[" + i + "," + (j + 1) + "]");
                }

                if (left == Labyrinthe.VIDE) {
                    g.ajouterArc(noeud, "[" + (i - 1) + "," + j  + "]", 1);
                    //System.out.println("Arc ajouté (LEFT) : " + noeud + " -> " + "[" + (i - 1) + "," + j + "]");
                } else {
                    //System.out.println("Arc non ajouté (LEFT) : " + noeud + " -> " + "[" + (i - 1) + "," + j + "]");
                }

                if (right == Labyrinthe.VIDE) {
                    g.ajouterArc(noeud, "[" + (i + 1) + "," + j  + "]", 1);
                    //System.out.println("Arc ajouté (RIGHT) : " + noeud + " -> " + "[" + (i + 1) + "," + j + "]");
                } else {
                    //System.out.println("Arc non ajouté (RIGHT) : " + noeud + " -> " + "[" + (i + 1) + "," + j + "]");
                }
            }
        }
    }


    /**
     * @return int[] Prochaine case vers la solution
     */
    public int[] resoudre(int ax, int ay, int px, int py) {
        if (g == null) {
            throw new IllegalStateException("Graphe non initialisé. Appelez updateGraphe avant resoudre.");
        }

        String depart = "[" + ax + "," + ay + "]";
        String arrivee = "[" + px + "," + py + "]";

        Valeur v = new Valeur();
        PriorityQueue<String> Q = new PriorityQueue<>(Comparator.comparingDouble(v::getValeur));
        Set<String> visited = new HashSet<>();

        // Initialisation
        for (String noeud : g.listeNoeuds()) {
            v.setValeur(noeud, Double.POSITIVE_INFINITY);
            v.setParent(noeud, null);
        }
        v.setValeur(depart, 0.0);
        Q.add(depart);

        while (!Q.isEmpty()) {
            String u = Q.poll();
            if (visited.contains(u)) continue;
            visited.add(u);

            // Vérifiez si le noeud a des suivants
            List<Arc> suivants = g.suivants(u);
            if (suivants == null) continue;

            for (Arc arc : suivants) {
                String val = arc.getDest();
                double poids = arc.getCout();

                if (visited.contains(val)) continue; // Skip if already visited

                double d = v.getValeur(u) + poids;

                if (d < v.getValeur(val)) {
                    v.setValeur(val, d);
                    v.setParent(val, u);
                    Q.add(val);
                }
            }
        }

        // Vérifiez si un chemin a été trouvé
        if (v.getParent(arrivee) == null) {
            //System.out.println("Aucun chemin trouvé de " + depart + " à " + arrivee);
            return null;
        }

        // Prochaine case vers la solution
        String next = arrivee;
        while (!depart.equals(next) && v.getParent(next) != null) {
            String parent = v.getParent(next);
            if (depart.equals(parent)) break;
            next = parent;
        }

        // Direction
        int next_x = Integer.parseInt(next.substring(1, next.indexOf(",")));
        int next_y = Integer.parseInt(next.substring(next.indexOf(",") + 1, next.length() - 1));

        //System.out.println("Direction Alpha: " + next_x + ", " + next_y);

        return new int[]{next_x, next_y};
    }
}