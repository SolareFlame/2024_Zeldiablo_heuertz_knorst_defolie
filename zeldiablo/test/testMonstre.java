import gameLaby.laby.Idiot;
import gameLaby.laby.Labyrinthe;
import gameLaby.laby.Monstre;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;


public class testMonstre{

    @Test
    public void test_prise_degat() {
        try {
            Labyrinthe laby = new Labyrinthe("zeldiablo/test/labytest0.txt");
            Monstre monstre = laby.monstres.get(0);
            monstre.subirDegat();
            assertEquals(1, monstre.getPv());  // le monstre a pris un dégat
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la création du monstre");
        }
    }

    @Test
    public void test_mort() {
        try {
            Labyrinthe laby = new Labyrinthe("zeldiablo/test/labytest0.txt");
            Monstre monstre = laby.monstres.get(0);
            monstre.subirDegat();
            monstre.subirDegat();
            // le monstre a doit être supprimé de la liste dès qu'il meurt
            assertTrue(monstre.getPv() == 0 && laby.monstres.size() == 0);
        } catch (IOException e) {
            System.out.println("Erreur lors de la création du monstre");
        }
    }

    @Test
    public void test_mort_et_reattaque() {
        try {
            Labyrinthe laby = new Labyrinthe("zeldiablo/test/labytest0.txt");
            Monstre monstre = laby.monstres.get(0);
            try {
                monstre.subirDegat();
                monstre.subirDegat();
                monstre.subirDegat();
                monstre.subirDegat();
                monstre.subirDegat();
                fail("Erreur non levée");  // le monstre est supprimé de la liste dès qu'il meurt
            } catch (Error e) {
                assertTrue(monstre.getPv() == 0 && laby.monstres.size() == 0);
            }
        } catch (IOException e) {
            System.out.println("Erreur lors de la création du monstre");
        }
    }

}